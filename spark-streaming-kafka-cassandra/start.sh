#!/bin/bash

# Wait 15 seconds before trying to connect to cassandra
echo "Sleeping 15 seconds..."
sleep 15

# Start spark job
spark-submit --class org.sevenmob.spark.streaming.DirectKafkaProcessing \
     --master local[*] target/scala-2.10/TwitterProcessingPipeLine-assembly-0.1.jar \
     $KAFKA_BROKERS \
     $KAFKA_TOPIC_NAMES \
     $CASSANDRA_HOSTNAME
