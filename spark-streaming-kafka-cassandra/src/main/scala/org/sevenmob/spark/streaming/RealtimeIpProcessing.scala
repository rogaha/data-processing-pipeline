/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package org.sevenmob.spark.streaming

import kafka.serializer.StringDecoder
import java.util.Calendar
import org.apache.spark.streaming._
import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{Row, SQLContext}

//import org.json4s_
//import org.json4s.jackson.JsonMethods._
//import org.json4s.native.JsonParser

import com.eaio.uuid.UUIDGen

import org.sevenmob.geocode._

/*
 * Consumes messages from one or more topics in Kafka process and send them to cassandra.
 * Usage: DirectKafkaProcessing <brokers> <topics>
 *   <brokers> is a list of one or more Kafka brokers
 *   <topics> is a list of one or more kafka topics to consume from
 *   <cassandra-host> is hostname or IP to any of the cassandra nodes
 *   <google-api-key> Google Geoconding API Key
 *
 * Example:
 *    $ bin/run-example org.sevenmob.spark.streaming.DirectKafkaProcessing broker1-host:port,broker2-host:port \
 *    topic1,topic2 cassandra-host apikey123
 */
object DirectKafkaProcessing {

  def toDouble: (Any) => Double = { case i: Int => i case f: Float => f case d: Double => d }

  def main(args: Array[String]) {
    if (args.length < 3) {
      System.err.println(s"""
        |Usage: DirectKafkaProcessing <brokers> <topics> <cassandra-host> <google-api-key>
        |  <brokers> is a list of one or more Kafka brokers
        |  <topics> is a list of one or more kafka topics to consume from
        |  <cassandra-host> is hostname or IP to any of the cassandra nodes
        |
        """.stripMargin)
      System.exit(1)
    }

    //implicit val formats = DefaultFormats + UUIDSerialiser

    StreamingExamples.setStreamingLogLevels()

    val Array(brokers, topics, cassandraHost) = args

    // Create context with 2 second batch interval
    val conf = new SparkConf().setMaster("spark://sparkmaster.weave.local:7077")
        .set("spark.shuffle.manager", "SORT")
        .set("spark.streaming.unpersist", "true")
        .set("spark.cassandra.connection.host", cassandraHost)
        .set("spark.cleaner.ttl", "5000")


    var sqlStatement = new String
    if (topics == "repo_events") {
      conf.setAppName("PushesAndPullsEventsProcessing")
  .set("spark.cores.max", "32")
      sqlStatement = ("""
        SELECT
          payload.event_name,
          payload.ip_address,
          count(*)
        FROM ApiCall
        GROUP BY payload.event_name, payload.ip_address""")
    }else if (topics == "search_events") {
      conf.setAppName("SearchesEventsProcessing")
  .set("spark.cores.max", "12")
      sqlStatement = ("""
        SELECT
          event_type,
          payload.ip_address,
          count(*)
        FROM ApiCall
        GROUP BY event_type, payload.ip_address""")
    }

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(7))
    val sqlContext = new SQLContext(sc)
    val geoUtil = new GeoIPLookup()

    // val keySpaceName = "twitter"
    // val tableName = "tweets"

    /* Cassandra setup */
    CassandraConnector(conf).withSessionDo { session =>
      session.execute("CREATE KEYSPACE IF NOT EXISTS docker_api_calls WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
    session.execute("CREATE TABLE IF NOT EXISTS docker_api_calls.aggregated_metrics (action_time timeuuid, action text, count int, ip_address text, lat double, lon double, city text, region text, country text, PRIMARY KEY (ip_address, action_time))")
    }
    //INSERT INTO docker_api_calls.aggregated_metrics (action_time, action, count, ip_address)
    //VALUES(now(), 'pull', 1, '123.123.123.123');

    // Create direct kafka stream with brokers and topics and save the results to Cassandra
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)
        .map(_._2)

    stream.foreachRDD { rdd =>
    /* this check is here to handle the empty collection error
       after the 3 items in the static sample data set are processed */
      if (rdd.toLocalIterator.nonEmpty) {
          println("---------------")
          println(Calendar.getInstance().getTime())
          sqlContext.jsonRDD(rdd).registerTempTable("ApiCall")
          val streamData = sqlContext.sql(sqlStatement)
          streamData.na.drop().map(t => {
          //streamData.map(t => {
              val action = t(0).toString
              val action_time = java.util.UUID.fromString(new com.eaio.uuid.UUID().toString())
              val ip_address = t(1).toString
              val counter = t(2).toString
              val geo = geoUtil.fromIP(ip_address)
              ApiCall(
                action,
                BigInt(counter),
                ip_address,
                action_time,
                toDouble(geo.map(_.lng).getOrElse(0.0)),
                toDouble(geo.map(_.lng).getOrElse(0.0)),
                geo.map(_.city).flatten.getOrElse("N/A"),
                geo.map(_.region).flatten.getOrElse("N/A"),
                geo.map(_.countryCode).flatten.getOrElse("N/A"))
              }
          ).saveToCassandra("docker_api_calls","aggregated_metrics")
          println("events processed")
    println(Calendar.getInstance().getTime())
          println("---------------")
      }
    }

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}
