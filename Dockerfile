FROM adoptopenjdk/openjdk11
WORKDIR /opt
ADD /target/icaf.jar .
CMD ["java", "-jar", "icaf.jar"]