#!/bin/bash

# Replace "localhost" in Hadoop core-site xml with actual hostname which is passed
# as NAMENODE_HOSTNAME env variable
sed -i "s#localhost#$NAMENODE_HOSTNAME#g" ${HADOOP_HOME}/etc/hadoop/core-site.xml

# Add jar file to HDFS
hadoop fs -put -f target/scala-2.10/DockerCLIProcessingPipeLine-assembly-0.1.jar /tmp/

# Start spark job
spark-submit --class org.sevenmob.spark.streaming.DirectKafkaProcessing \
     --master local[*] \
     --deploy-mode cluster \
     --driver-memory 4g \
     --driver-cores 4 \
     --executor-memory 4g \
     --executor-cores 4 \
     hdfs://$NAMENODE_HOSTNAME:9000/tmp/DockerCLIProcessingPipeLine-assembly-0.1.jar \
     $KAFKA_BROKERS \
     $KAFKA_TOPIC_NAMES \
     $CASSANDRA_HOSTNAME