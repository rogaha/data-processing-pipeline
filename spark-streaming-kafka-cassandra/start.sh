#!/bin/bash

# Wait 15 seconds before trying to connect to cassandra
echo "Sleeping 15 seconds..."
sleep 15

# Start spark job
spark-submit --class org.sevenmob.spark.streaming.DirectKafkaProcessing \
     --master local[*] target/scala-2.10/TwitterProcessingPipeLine-assembly-0.1.jar \
     $KAFKA_ENV_KAFKA_ADVERTISED_HOST_NAME:$KAFKA_PORT_9092_TCP_PORT \
     $KAFKA_TOPIC_NAME \
     $CASSANDRA_PORT_9042_TCP_ADDR \
     $GOOGLE_GEOCODING_API_KEY
