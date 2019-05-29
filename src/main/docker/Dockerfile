FROM centos

RUN yum install -y java-11-openjdk-devel

VOLUME /tmp

ADD *.jar myapp.jar

RUN sh -c 'touch myapp.jar'

RUN sh -c 'ls -ltr'

ENTRYPOINT ["java", "-jar", "myapp.jar"]