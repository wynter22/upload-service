# kakaopay (web-app)

> 카카오페이 웹(풀스택) 개발 과제 - BackEnd

## Run in Development

***

```
$ ./gradlew clean build
$ java -jar build/libs/web-app-0.0.1-SNAPSHOT.jar
```

## Dependencies

***

| No  |   Dependency   |     Version    |  
|:---:|----------------|----------------|
|  1  | Java           | 11             |
|  2  | Spring Boot    | 1.0.11.RELEASE |
|  3  | Gradle         | 7.0.2          |
|  4  | H2             |                |
|  5  | Lombok         |                |
|  6  | OpenCsv        | 5.4            |
|  7  | JUnit          | 5.7.2          |
|  8  | guava          | 30.1-jre       |

## Folder Structure

***

```sh
web-app
├── src
│   ├── main.java.com.example.webapp
│   │     ├── config
│   │     ├── entity
│   │     ├── repo                           
│   │     ├── service                       
│   │     ├── web                           
│   │     │   └── model
│   │     └── WebAppApplication.java
│   └── ...
├── ...
├── build.gradle
├── REDADME.md
└── settings.gradle
```





