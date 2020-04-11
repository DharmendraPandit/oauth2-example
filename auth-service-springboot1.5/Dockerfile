FROM openjdk:14-jdk-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/* /usr/app/app.jar
CMD ["java","-jar","app.jar"]
EXPOSE 9091
