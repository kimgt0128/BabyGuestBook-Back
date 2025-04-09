FROM openjdk:17
WORKDIR /app
COPY ./build/libs/guestbook-0.0.1-SNAPSHOT.jar guestbook.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "guestbook.jar"]