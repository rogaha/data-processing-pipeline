# Author: Roberto Gandolfo Hashioka
# Date: 06/12/2016

FROM rogaha/spark-hdfs-base
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
ADD ./geocode_db /spark-job/geocode_db

# Move 

WORKDIR /spark-job

# Compile the spark job
RUN sbt assembly

CMD ["./start.sh"]