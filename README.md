 # 🌼 동시편집 기능을 제공하는 릴리즈 노트 공유 시스템, A-LOG

**개발 기간** 2023.07 ~ 2023.08 <br/>
**사이트 바로가기** https://alog.acceler.kr/ (🔧업데이트 중) <br/>
**Team repo** https://github.com/orgs/KEA-ACCELER/repositories <br/>

# 🐳 Overview Architecture

![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/b9f31a1a-6375-4f6e-af24-02d4b308002a)

Here, the domains(service)'s relationship is shown. <br/>

![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/a64a35f6-0ac2-445b-a50f-a9bf3be15135)

# 📚  Implementation

Use it for the purpose of managing bugs in an Issue project or functions that need to be added. Users can create, query, modify, and close issues and designate their members for them.

## Service Flows and Features

### 1. Create Issue

- Users can create a new issue through the API.
- The required information is the title and content.
- When an issue is created, the initial state is set to 'open'.

### 2. List Issues

- Users can view a list of all issues.

### 3. View Issue Details

- Users can view detailed information about a specific issue.

### 4. Change Issue Status

- The status of an issue can be changed to 'TODO', 'INPROGRESS', 'DONE', or 'EMERGENCY'.

### 5. Write a comment

- Users can write comments on a specific issue.

### 6. Delete a comment

- Users can delete the comments they have written.

## Interface

> [check swagger section below](#🍀-swagger)

## ERD
![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/a70f65d3-61c7-4ceb-b0dc-00297be3eafc)

## Dependencies
> ./gradlew dependencies
```
implementation - Implementation only dependencies for source set 'main'. (n)
+--- org.springframework.boot:spring-boot-starter (n)
+--- org.springframework.boot:spring-boot-starter-data-jpa (n)
+--- org.springframework.boot:spring-boot-starter-web (n)
+--- org.springframework.cloud:spring-cloud-starter-openfeign:4.0.2 (n)
+--- org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2 (n)
\--- org.springframework.boot:spring-boot-starter-validation (n)

(n) - A dependency or dependency configuration that cannot be resolved.
```

# ✨ Installation

## Running the user app only 

- use docker-compose.yml
```
docker compose up -d
```


# 🍀 Swagger
```
http://localhost:30000/api/users
```
![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/d46e8f59-e46b-40b9-b306-e30c988ba6c4)
![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/f577569e-0a00-43b7-87b5-0d1630f02a61)

# 📝 Conclusion and Suggestion

## **If try to improve the quality**

- **Code Refactoring**: Add comments or set variable and function names clearly to improve code readability and maintainability.
- **Optimized Query**: Optimize database queries and improve performance by making full use of Spring Data JPA features.

## **If try to improve the performance**

- **Asynchronous Processing**: Introduce asynchronous programming to minimize response time through parallel processing and improve overall system performance.
- **Database Indexing**: Set the index of the database table appropriately to improve performance for searching and filtering.

## **Conclusion**

This project implements issue and comment management functions using Spring Boot and MySQL. In the future, we will reflect user feedback and continue to develop and maintain the service through updates. We plan to improve the user experience by adding new features or upgrading technology.
