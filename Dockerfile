# Build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:19.2.1 AS build

COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml dependency:go-offline -B

COPY src /usr/src/app/src
USER root
RUN chown -R quarkus /usr/src/app
USER quarkus
RUN mvn -f /usr/src/app/pom.xml -Pnative clean package

# Use the image to copy watchdog instead of downloading using curl or add
FROM openfaas/of-watchdog:0.7.2 as watchdog

# Create the final docker image, containing the native executable(s)
FROM debian:buster-slim
WORKDIR /work/
COPY --from=build /usr/src/app/target/*-runner /work/application
RUN chmod 775 /work

COPY --from=watchdog /fwatchdog /usr/bin/fwatchdog
RUN chmod +x /usr/bin/fwatchdog

EXPOSE 8080

ENV upstream_url="http://127.0.0.1:8000"
ENV mode="http"

ENV fprocess="/work/application" 

# watchdog is the main process
CMD ["/usr/bin/fwatchdog"]