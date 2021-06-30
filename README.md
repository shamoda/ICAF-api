## International Conference on Application Frameworks-2021

ICAF-2021 is a Conference management tool, which is developed with the intent to manage and automate tasks of the conference organizing committee efficiently and effectively.

System Requirements
```
JDK 11
Apache Maven
Node v14.17.0 or above
Docker
```

AWS S3 service uses for storage purposes. Therefore, AWS IAM credentials needs to be passed as env variables to the Docker containers.

To deploy on Ubuntu server

```
# git clone https://github.com/shamoda/ICAF-api
# cd ICAF-api
# mvn clean install
# docker build -t icaf-api:latest
# docker run --name instance-01 -d -p 8081:8080 -e AWS_ACCESS_KEY_ID=<access-key> -e AWS_SECRET_ACCESS_KEY=<secret-key> icaf-api:latest
```



