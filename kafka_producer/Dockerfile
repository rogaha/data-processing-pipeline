# Author: Roberto Gandolfo Hashioka
# Date: 07/22/2015

FROM stackbrew/ubuntu:14.04
MAINTAINER Roberto G. Hashioka "roberto_hashioka@hotmail.com"

# Install Pip
RUN apt-get update
RUN apt-get install -y python-pip

# Install and configure python packages
ADD requirements.txt /build/
RUN pip install -r /build/requirements.txt

# Copy python app
ADD ./twitter_kafka_producer.py /
ADD ./start.sh /

# Start the Kafka producer process
CMD ["/usr/bin/python","/twitter_kafka_producer.py"] 