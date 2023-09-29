FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-alpine

RUN mkdir /app

COPY --from=builder /app/target/prospect.jar /app/prospect.jar

WORKDIR /app

ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENV APPLICATION_NAME=prospect.jar

RUN echo "#!/bin/sh" >> ./entrypoint.sh
RUN echo "java $JAVA_OPTS -jar $APPLICATION_NAME" >> ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["./entrypoint.sh"]
