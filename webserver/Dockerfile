# Author: Roberto Gandolfo Hashioka
# Date: 07/22/2015

FROM stackbrew/ubuntu:14.04
MAINTAINER Roberto Gandolfo Hashioka

# Install Pip
RUN apt-get update
RUN apt-get install -y python-pip

# Install and configure python packages
ADD requirements.txt /build/
RUN pip install -r /build/requirements.txt

# Copy python app
ADD ./webserver.py /
ADD ./templates /templates

# Start the Kafka producer process
CMD ["/usr/bin/python","/webserver.py"] 


