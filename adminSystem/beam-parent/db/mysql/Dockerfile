FROM mysql:5.7
MAINTAINER hsshy(457030599@qq.com)
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#定义会被容器自动执行的目录
ENV AUTO_RUN_DIR /docker-entrypoint-initdb.d
#定义初始化sql文件
ENV INSTALL_DB_SQL beam.sql
COPY ./$INSTALL_DB_SQL $AUTO_RUN_DIR/
RUN chmod a+x $AUTO_RUN_DIR/$INSTALL_DB_SQL