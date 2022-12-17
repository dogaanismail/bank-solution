FROM openjdk:17-jdk-slim
EXPOSE 5003

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/customer-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx32m"
CMD exec java $JAVA_OPTS -jar customer-0.0.1-SNAPSHOT.jar