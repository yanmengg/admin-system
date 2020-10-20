FROM openjdk:8
MAINTAINER 457030599@qq.com
EXPOSE 8080
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /beam-admin
WORKDIR /beam-admin
#复制到当前目录
ADD beam-admin.jar ./beam-admin.jar
RUN bash -c 'touch beam-admin.jar'
ENTRYPOINT ["nohup","java","-jar","beam-admin.jar","--spring.profiles.active=prod",">/dev/null","2>&1"]

