FROM openjdk:8
MAINTAINER 457030599@qq.com
EXPOSE 8080
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /beam-api
WORKDIR /beam-api
#复制到当前目录
ADD beam-api.jar ./beam-api.jar
RUN bash -c 'touch beam-api.jar'
ENTRYPOINT ["nohup","java","-jar","beam-api.jar","--spring.profiles.active=prod",">/dev/null","2>&1"]

