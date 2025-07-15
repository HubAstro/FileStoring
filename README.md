<h1 class="mb-4">FileStoring</h1>
<p>A simple Spring Boot application that allows users to register, log in, upload files, and manage them. Uploaded files are stored on the server using <code>java.io.File</code>, and their metadata is saved in a MySQL database. Each user can view and manage only their own files.</p>
<h2>Features</h2>
        <ul>
            <li>User registration and login</li>
            <li>File upload with custom file name</li>
            <li>File metadata stored in MySQL</li>
            <li>View uploaded files with pagination (10 entries per page)</li>
            <li>Delete uploaded files</li>
            <li>File access restricted to respective users</li>
            <li>Simple frontend using Thymeleaf and Bootstrap</li>
        </ul>

        <h2>Tech Stack</h2>
        <ul>
            <li>Java 17</li>
            <li>Spring Boot</li>
            <li>Spring Security</li>
            <li>Hibernate (JPA)</li>
            <li>MySQL</li>
            <li>Thymeleaf</li>
            <li>Bootstrap</li>
        </ul>

        <h2>Getting Started</h2>

        <h4>Prerequisites</h4>
        <ul>
            <li>Java 17+</li>
            <li>Maven</li>
            <li>MySQL</li>
        </ul>

        <h4>Configuration</h4>
        <p>Create a MySQL database named <code>filestoring</code>. Then, update your <code>application.properties</code> file:</p>
        <pre><code>spring.datasource.url=jdbc:mysql://localhost:3306/filestoring
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
</code></pre>

        <h4>Build and Run</h4>
        <pre><code>mvn spring-boot:run</code></pre>
        <p>Visit: <a href="http://localhost:8080">http://localhost:8080</a></p>

        <h2>Usage</h2>
        <ol>
            <li>Register a new user account.</li>
            <li>Log in using your credentials.</li>
            <li>Upload files with an optional custom name.</li>
            <li>View and delete your uploaded files from the dashboard.</li>
        </ol>

        <h2>Folder Structure</h2>
        <pre><code>src
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
</code></pre>

        <h2>License</h2>
        <p>This project is licensed under the MIT License.</p>
