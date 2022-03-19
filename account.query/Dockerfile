FROM adoptopenjdk:11.0.4_11-jre-hotspot
EXPOSE 5003

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/account.query-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx32m"
CMD exec java $JAVA_OPTS -jar account.query-0.0.1-SNAPSHOT.jar