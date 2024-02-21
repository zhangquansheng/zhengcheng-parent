# Docker image for springboot file run
# VERSION 0.0.1
# Author: zqs
# 基础镜像使用java
# FROM java:8
FROM registry.cn-hangzhou.aliyuncs.com/zhangquanshengnamespace/openjdk:8-jdk-alpine
# 作者
MAINTAINER zqs <952547584@qq.com>
COPY ./target/ums.jar /root/startup/
WORKDIR /root/startup/
EXPOSE 8081
ENTRYPOINT ["java", "-XX:+UseG1GC", "-jar", "ums.jar"]
