FROM adoptopenjdk/openjdk14:alpine-jre

# See https://learn.hashicorp.com/consul/datacenter-deploy/deployment-guide
ENV CONSUL_VERSION 1.7.4
ADD https://releases.hashicorp.com/consul/${CONSUL_VERSION}/consul_${CONSUL_VERSION}_linux_amd64.zip consul_${CONSUL_VERSION}.zip
#ADD ./consul_${CONSUL_VERSION}_darwin_amd64.zip consul_${CONSUL_VERSION}.zip
RUN unzip consul_${CONSUL_VERSION}.zip
RUN ["ls" ,"-al"]
RUN rm consul_${CONSUL_VERSION}.zip
RUN ["./consul", "--version"]
RUN ["mkdir", "--parents", "/var/lib/consul"]


ADD ./build/libs/*.jar app.jar

ADD ./entrypoint.sh entrypoint.sh
RUN ["chmod", "755", "entrypoint.sh"]

ENTRYPOINT ["./entrypoint.sh"]
