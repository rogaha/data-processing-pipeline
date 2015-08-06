#!/bin/bash

# Wait 10 seconds before trying to connect to kafka
echo "Sleeping 10 seconds..."
sleep 10

exec /twitter_kafka_producer.py