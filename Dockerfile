#
# Build stage
#
FROM gradle:jdk17-corretto AS build
COPY . .
RUN gradle bootJar

#
# Package stage
#
FROM azul/zulu-openjdk:17-latest
VOLUME /tmp
COPY --from=build build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
