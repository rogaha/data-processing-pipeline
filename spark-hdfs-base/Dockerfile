# HDFS DataNode
FROM gettyimages/spark:1.5.2-hadoop-2.6

MAINTAINER Roberto G. Hashioka "roberto_hashioka@hotmail.com"

# Install ssh and sshpass
RUN apt-get update && \
  	apt-get install -y openssh-server sshpass wget && \
  	rm -rf /var/lib/apt/lists/*

RUN mkdir /var/run/sshd
RUN echo 'root:screencast' | chpasswd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -i 's/#   StrictHostKeyChecking ask/StrictHostKeyChecking no/' /etc/ssh/ssh_config
RUN ssh-keygen -f /root/.ssh/id_rsa -t rsa -N ''

# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

# bootstrap script
COPY scripts /usr/bin/.

# Install HADOOP
RUN wget http://www.us.apache.org/dist/hadoop/common/hadoop-2.6.0/hadoop-2.6.0.tar.gz
RUN mv hadoop-2.6.0.tar.gz /usr/
RUN cd /usr/ && tar zxf hadoop-2.6.0.tar.gz && rm hadoop-2.6.0.tar.gz

ENV JAVA_HOME=/usr/lib/jvm/java-6-openjdk-amd64
ENV HADOOP_HOME=/usr/hadoop-2.6.0
ENV HADOOP_CONF_DIR=/usr/hadoop-2.6.0/etc/hadoop

# HDFS configuration
COPY conf /usr/hadoop-2.6.0/etc/hadoop/.
RUN sed -i 's/export JAVA_HOME=${JAVA_HOME}/export JAVA_HOME=\/usr\/jdk1.8.0_31/' /usr/hadoop-2.6.0/etc/hadoop/hadoop-env.sh

# Prepare the hdfs directories
RUN mkdir -p /opt/ && \
	mkdir -p /opt/data/apps/tmp && \
	mkdir -p /opt/data/apps/dfs/name && \
	mkdir -p /opt/data/apps/dfs/data

# Startup a HDFS Namenode by default
CMD ["/usr/bin/startup_namenode.sh"]