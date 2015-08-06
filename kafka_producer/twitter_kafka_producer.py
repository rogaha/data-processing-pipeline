#!/usr/bin/env python

import os
from kafka.client import KafkaClient
from kafka.producer import SimpleProducer
from time import sleep

#Import the necessary methods from tweepy library
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream

# List of the interested topics
KEYWORDS_LIST = []
KEYWORDS_LIST += filter(
    bool, os.environ.get('KEYWORDS_LIST', '').split(','))

#Variables that contains the user credentials to access Twitter API
ACCESS_TOKEN = os.environ.get('ACCESS_TOKEN')
ACCESS_TOKEN_SECRET = os.environ.get('ACCESS_TOKEN_SECRET')
CONSUMER_KEY = os.environ.get('CONSUMER_KEY')
CONSUMER_SECRET = os.environ.get('CONSUMER_SECRET')

# Kafka Configurations
KAFKA_ENV_KAFKA_ADVERTISED_HOST_NAME = os.environ.get('KAFKA_ENV_KAFKA_ADVERTISED_HOST_NAME')
KAFKA_PORT_9092_TCP_PORT = os.environ.get('KAFKA_PORT_9092_TCP_PORT')
KAFKA_TOPIC_NAME = os.environ.get('KAFKA_TOPIC_NAME')

client = KafkaClient('{0}:{1}'.format(KAFKA_ENV_KAFKA_ADVERTISED_HOST_NAME, KAFKA_PORT_9092_TCP_PORT))
producer = SimpleProducer(client)

#This is a basic listener that just prints received tweets to stdout.
class StdOutListener(StreamListener):

    def on_data(self, data):
        print "message sent to Kafka"
        producer.send_messages(KAFKA_TOPIC_NAME, str(data))
        return True


class Producer(object):

    def run(self, filters):
        #This handles Twitter authetification and the connection to Twitter Streaming API
        l = StdOutListener()
        auth = OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
        auth.set_access_token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
        stream = Stream(auth, l)
        #This line filter Twitter Streams to capture data by the keywords: 'python', 'javascript', 'ruby'
        stream.filter(track=filters)


def main():
    twitter_producer = Producer()
    twitter_producer.run(KEYWORDS_LIST)


if __name__ == "__main__":
    main()
