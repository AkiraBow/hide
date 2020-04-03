# build stage
FROM openjdk:8-jdk-alpine AS build
COPY / /hide
WORKDIR /hide
RUN ./gradlew build

# run stage
FROM openjdk:8-jre-slim
# copy from "build" container
COPY --from=build /hide/build/libs/hide-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# build (-t docker image name  . use dockerfile in current folder)
# docker build -t arkirabow/hide .

# run (-d: detached mode -p portforwarding)
# docker run -p 8081:8081 -d -t arkirabow/hide

# push to docker hub (lastest is the tag)
# docker push arkirabow/bayonetta:latest

# remove docker image
# docker container ls -a
# docker image ls
# docker container rm <container_id>
# docker image rm <image_id>