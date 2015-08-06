# Author: Roberto Gandolfo Hashioka
# Date: 07/22/2015

FROM gettyimages/spark:1.4.0-hadoop-2.6
MAINTAINER Roberto G. Hashioka "roberto_hashioka@hotmail.com"

# Add SBT package for Spark development
RUN echo "deb http://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-get update
RUN apt-get install -y --force-yes sbt git \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

# Copy the project source code
ADD ./project /spark-job/project
ADD ./src /spark-job/src
ADD ./build.sbt /spark-job/
ADD ./version.sbt /spark-job/
ADD ./start.sh /spark-job/

WORKDIR /spark-job

# Compile the spark job
RUN sbt assembly

CMD ["./start.sh"]