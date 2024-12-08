# **Task Management and Report Subscription System**

This Java Spring Boot application provides task management, report subscriptions, and automated email reporting functionalities.

---

## **Features**

- User authentication with JWT.
- Task creation, retrieval, update, and deletion.
- Filter tasks by status and date range.
- Subscribe to automated task reports with customizable frequency (daily, weekly, monthly).
- Restore deleted tasks and batch task deletion.

---

## **Setup Instructions**

### **Prerequisites**

#### **Option 1: Running with the JAR File**
1. **Java 21**:
    - Install a JDK 21 distribution.
    - Ensure `java` is available in your `PATH`:
      ```bash
      java -version
      ```

2. **PostgreSQL**:
    - Install PostgreSQL (version 14 or later) on your machine.
    - Create a database named `task_manager`:
      ```sql
      CREATE DATABASE task_manager;
      ```


#### **Option 2: Running with Docker Compose**
1. **Docker**:
    - Install Docker

---

## **Run Options**

### **Option 1: Run with Java 21 and JAR File**

#### **Steps**

1. **Build the Application**:
   Use Gradle to build the application from root directory:
   ```bash
   ./gradlew clean build
   ```

2. **Run PostgreSQL: Start your local PostgreSQL instance, or use a hosted one. Update the database credentials in application.properties**:
  ```propertires
  spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager
  spring.datasource.username=postgres
  spring.datasource.password=password
```

3. **Run the JAR File: Use Java to run the application**:
   ```bash
   java -jar build/libs/Task_Manager-0.0.1-SNAPSHOT.jar
   ```

4. **Access the Application**:
   The backend will be available at http://localhost:8080/api/v1


### **Option 2: Run with Docker Compose**
#### **Steps**

1. **Prepare the Application: Ensure the application is built (From Root Directory)**:
   ```bash
   ./gradlew clean build
   ```
2. **Run Docker Compose: Use Docker Compose to start both the backend and PostgreSQL containers**:
   ```bash
   docker-compose up
   ```
3. **Access the Application**:
   The backend will be available at http://localhost:8080/api/v1

4. **Stop the Containers: When you're done, stop the containers**:
   ```bash
   docker-compose down
   ```


## **API Documentation**

The API is well-documented and can be accessed through the following options:


### **1. Swagger Documentation**
The application includes an interactive API documentation using **Swagger**. You can access it at the following URL after starting the application:

- [Swagger UI](http://localhost:8080/api/v1/swagger-ui/index.html)

Swagger provides a user-friendly interface to explore and test all the available API endpoints.

---

### **2. Postman Public Workspace**
For detailed API examples and testing, you can also use our **Public Postman Workspace**. This includes all endpoints with pre-configured request examples and responses.

- [**Task Management and Subscription System - Postman Workspace**](https://www.postman.com/vodafone-task-manger/team-workspace/overview)   


## **API Examples**

This section provides example requests and responses for the core API endpoints.

---

### **Authentication**

#### **1. Sign Up**
- **Endpoint**: `/auth/sign-up`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "Password123!"
  }
  ```
- **Response**:
    ```json
    {
      "responseCode": "1",
      "success": false,
      "message": "This email already exists"
    }
    ```

#### **2. Login**
- **Endpoint**: `/auth/login`
- **Method**: `POST`
- No need to store the JWT token, it is being saved as HtppOnly Cookie
- **Request Body**:
  ```json
  {
    "email": "john.doe@example.com",
    "password": "Password123!"
  }
  ```
- **Response**:
    ```json
  {
      "responseCode": "0",
      "success": true,
      "message": "Request Successful",
      "userId": 1,
      "username": "john.doe@example.com",
      "createdAt": "2024-12-08T14:40:31.054+00:00"
    }
    ```

### **Task Management**

#### **1. Create Task**
- **Endpoint**: `/task/create`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
      "dueDate": "2024-12-08",
      "startDate": "2024-12-07",
      "title": "Test Task 3",
      "description": "This is to test A Task"
    }
  ```
- **Response**:
    ```json
  {
      "responseCode": "0",
      "success": true,
      "message": "Task Created Successfully"
    }
    ```

#### **2. Fetch Tasks**
- **Endpoint**: `/task/fetch-all`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "status": "PENDING",
    "startDate": "2024-12-01",
    "dueDate": "2024-12-31"
  }

  ```
- **Response**:
  ```json
  [
    {
      "id": 1,
      "title": "Sample Task",
      "description": "Task description",
      "startDate": "2024-12-08",
      "dueDate": "2024-12-10",
      "status": "PENDING"
    }
  ]


  

  






    
