FROM adoptopenjdk:11.0.4_11-jre-hotspot
EXPOSE 5002

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/account.cmd-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx32m"
CMD exec java $JAVA_OPTS -jar account.cmd-0.0.1-SNAPSHOT.jar