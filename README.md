#FileStoring

A simple Spring Boot application that allows users to register, log in, upload files, and manage them. Uploaded files are stored on the server using java.io.File, and their metadata is saved in a MySQL database. Each user can view and manage only their own files.
Features
User registration and login
File upload with custom file name
File metadata stored in MySQL
View uploaded files with pagination (10 entries per page)
Delete uploaded files
File access restricted to respective users
Simple frontend using Thymeleaf and Bootstrap
Tech Stack
Java 17
Spring Boot
Spring Security
Hibernate (JPA)
MySQL
Thymeleaf (HTML templating)
Bootstrap (styling)
Getting Started
Prerequisites
Java 17+
Maven
MySQL
Configuration
Create a MySQL database named filestoring.
Update your application.properties with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/filestoring
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Build and Run
mvn spring-boot:run
Visit: http://localhost:8080
Usage
Register a new user account.
Log in using your credentials.
Upload files with an optional custom name.
View and delete your uploaded files from the dashboard.
Folder Structure
src
├── main
│   ├── java
│   │   └── com.example.filestoring
│   │       ├── controller
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       └── FilestoringApplication.java
│   └── resources
│       ├── static
│       ├── templates
│       └── application.properties
License
This project is licensed under the MIT License.
