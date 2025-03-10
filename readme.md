# GitHub Repositories API

This project is a Quarkus application that retrieves GitHub repository data (excluding forks) and maps branch information into domain DTOs. The API returns an empty list if the user has no repositories.

## Prerequisites

- **Java 21** (or the version specified in your project)
- **Gradle** (or use the included Gradle wrapper)
- [GraalVM](https://www.graalvm.org/) if you wish to build a native image (optional)
- Docker installed (for container-based execution)

## Environment Variables
The application uses environment variables for configuration. Variable GITHUB_TOKEN is needed for github authentication, as not to exceed rate limits of their API. For example, to configure the GitHub token, set the following in your shell or Dockerfile:

```bash
export GITHUB_TOKEN="your_github_personal_access_token"
```
In Docker, you can pass the variable as follows:

```bash
docker run -i --rm -p 8080:8080 -e GITHUB_TOKEN="your_github_personal_access_token" quarkus/github_repos-jvm
```

## 1. Running Locally with Gradle

### 1.1. Run in Development Mode
#### 1.1.1. Run the following command in the root project directory:

```bash
./gradlew quarkusDev
```

#### 1.1.2. Open your browser and navigate to http://localhost:8080 (or the configured port).
#### 1.1.3. Test the API endpoint by visiting:
Replace {username} with a GitHub username (e.g., octocat).

http://localhost:8080/repositories/{username}

### 1.2.1. Run Tests
   To execute the tests using Gradle, run in the root project directory:

```bash
./gradlew test
```
This command compiles the project and runs the tests, outputting the results to the console.

## 2. Running with Docker
Quarkus supports containerized deployments. Two Dockerfiles are provided: one for running in JVM mode and one for building a native image.

### 2.1. Build and Run in JVM Mode
   #### 2.1.1: Build the Application
   Build the application using Gradle:
```bash
./gradlew build
```
#### 2.1.2: Build the Docker Image
Assuming you are using the JVM Dockerfile (typically Dockerfile.jvm located in src/main/docker):

```bash
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/github_repos-jvm .
```
#### 2.1.3: Run the Docker Container
Run the container:

```bash
docker run -i --rm -p 8080:8080 quarkus/github_repos-jvm
```
#### 2.1.4: Now you can access the API at 
http://localhost:8080/repositories/{username}.

## 3. Build and Run a Native Image (Optional)
   If you have GraalVM with native-image installed and configured:

### 3.1. Build the Native Executable
```bash
./gradlew build -Dquarkus.native.enabled=true
```
### 3.2. Build the Docker Image
Assuming you are using the native Dockerfile (e.g., Dockerfile.native):

```bash
docker build -f src/main/docker/Dockerfile.native -t quarkus/github_repos .
```

### 3.3. Run the Native Container
```bash
docker run -i --rm -p 8080:8080 quarkus/github_repos
```

### 3.4. Access the API at http://localhost:8080/repositories/{username}.

## Summary
* Gradle Dev Mode: ./gradlew quarkusDev
* Run Tests: ./gradlew test
* Docker JVM Image: Build with Dockerfile.jvm and run with docker run -p 8080:8080 quarkus/github_repos-jvm
* Docker Native Image (optional): Build with Dockerfile.native and run with docker run -p 8080:8080 quarkus/github_repos