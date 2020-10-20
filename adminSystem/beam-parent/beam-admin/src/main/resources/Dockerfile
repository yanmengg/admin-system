FROM openjdk:8
#发布者
MAINTAINER 457030599@qq.com
#指定端口
EXPOSE 8080
#指定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#创建目录
RUN mkdir -p /beam-admin
#切换到目录
WORKDIR /beam-admin
#复制到当前目录 ./
copy beam-admin-0.0.1-SNAPSHOT.jar ./beam-admin.jar
#修改jar包时间
RUN bash -c 'touch beam-admin.jar'
#运行项目
ENTRYPOINT ["nohup","java","-jar","beam-admin.jar","--spring.profiles.active=prod",">/dev/null","2>&1"]
