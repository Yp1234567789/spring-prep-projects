# Bedrock Integration - Spring AI

A Spring Boot application demonstrating AWS Bedrock integration with Spring AI framework. This project showcases how to leverage Amazon's managed AI service for chat completions and AI-powered features.

## 📋 Overview

This application provides REST API endpoints for interacting with AWS Bedrock models through the Bedrock Converse API. It demonstrates production-ready patterns for integrating with AWS's managed foundation models.

## 🚀 Features

- **Bedrock Model Integration** - Access to multiple foundation models (Claude, Llama, etc.)
- **Chat Completions** - Real-time conversational AI responses
- **AWS Credential Management** - Secure credential handling
- **REST API** - Easy-to-use HTTP endpoints
- **Spring Boot Integration** - Full Spring ecosystem support
- **Error Handling** - Comprehensive exception management
- **DevTools Support** - Hot reload for development

## 📦 Technology Stack

### Framework & Platform
- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **Java** 17
- **Maven** 3.6+

### AWS Services
- **AWS Bedrock** - Managed AI/ML inference service
- **Bedrock Converse API** - Chat and conversation management

### Build & Development
- **Maven** - Build automation
- **Spring Boot Maven Plugin** - Application packaging
- **Spring Boot DevTools** - Development convenience

## 📋 Prerequisites

### System Requirements
- Java 17 or higher
- Maven 3.6 or higher
- AWS Account with Bedrock access

### AWS Setup

1. **Enable Bedrock Service**
   - Navigate to AWS Management Console
   - Go to Bedrock service
   - Enable region (e.g., us-east-1)
   - Request model access for desired foundation models

2. **Configure AWS Credentials**
   - Create IAM user with Bedrock permissions
   - Generate access keys
   - Configure credentials locally

3. **Required IAM Permissions**
   ```json
   {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Effect": "Allow",
         "Action": [
           "bedrock:InvokeModel",
           "bedrock:InvokeModelWithResponseStream"
         ],
         "Resource": "arn:aws:bedrock:*::foundation-model/*"
       }
     ]
   }
   ```

## 🔧 Installation & Configuration

### 1. Clone Repository
```bash
git clone <repository-url>
cd bedrock
```

### 2. Configure AWS Credentials

#### Option A: Environment Variables
```bash
# Linux/macOS
export AWS_ACCESS_KEY_ID=your-access-key
export AWS_SECRET_ACCESS_KEY=your-secret-key
export AWS_REGION=us-east-1

# Windows PowerShell
$env:AWS_ACCESS_KEY_ID="your-access-key"
$env:AWS_SECRET_ACCESS_KEY="your-secret-key"
$env:AWS_REGION="us-east-1"
```

#### Option B: AWS Credentials File
Create `~/.aws/credentials`:
```
[default]
aws_access_key_id=your-access-key
aws_secret_access_key=your-secret-key
```

#### Option C: application.properties
```properties
aws.accessKeyId=${AWS_ACCESS_KEY_ID}
aws.secretKey=${AWS_SECRET_ACCESS_KEY}
aws.region=us-east-1
```

### 3. Update Configuration

Edit `src/main/resources/application.properties`:
```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/

# AWS Region (default: us-east-1)
spring.ai.bedrock.region=us-east-1

# Model Configuration
spring.ai.bedrock.converse.model=anthropic.claude-3-5-sonnet-20241022-v2:0

# Logging
logging.level.root=INFO
logging.level.com.amazonaws=DEBUG
```

### 4. Build Application
```bash
mvn clean install
```

## 🚀 Running the Application

### Development Mode (with hot reload)
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/bedrock-0.0.1-SNAPSHOT.jar
```

Application will start on `http://localhost:8080`

## 📡 API Endpoints

### Chat Endpoint
```http
GET /api/chat?message=Your+message+here
```

**Query Parameters:**
- `message` (required): The user message to send to Bedrock

**Example Request:**
```bash
curl "http://localhost:8080/api/chat?message=What%20is%20Spring%20Boot?"
```

**Example Response:**
```json
{
  "response": "Spring Boot is a framework that simplifies the development of stand-alone, production-grade Spring-based applications..."
}
```

### Health Check
```http
GET /actuator/health
```

## 🔌 Configuration Options

### Bedrock Model Selection

Available models depend on your Bedrock access:

```properties
# Claude Models (Anthropic)
spring.ai.bedrock.converse.model=anthropic.claude-3-5-sonnet-20241022-v2:0
spring.ai.bedrock.converse.model=anthropic.claude-3-opus-20250219-v1:0
spring.ai.bedrock.converse.model=anthropic.claude-3-sonnet-20240229-v1:0

# Llama Models (Meta)
spring.ai.bedrock.converse.model=meta.llama3-70b-instruct-v1:0

# Titan Models (Amazon)
spring.ai.bedrock.converse.model=amazon.titan-text-express-v1:0
```

### Temperature & Parameters
```properties
# Model temperature (0.0 - 1.0, default: 0.7)
spring.ai.bedrock.converse.options.temperature=0.7

# Maximum tokens (default: 1024)
spring.ai.bedrock.converse.options.max-tokens=1024

# Top-p sampling (0.0 - 1.0)
spring.ai.bedrock.converse.options.top-p=0.9
```

## 📚 Usage Examples

### Java Example
```java
@RestController
@RequestMapping("/api")
public class BedrockChatController {
    
    private final ChatClient chatClient;

    public BedrockChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient
            .prompt()
            .user(message)
            .call()
            .content();
    }
}
```

### Using Different Models
```java
@GetMapping("/chat/{model}")
public String chatWithModel(
    @RequestParam String message,
    @PathVariable String model) {
    
    return chatClient
        .prompt()
        .user(message)
        .options(BedrockChatOptions.builder()
            .withModel(model)
            .withTemperature(0.8)
            .build())
        .call()
        .content();
}
```

### Advanced Configuration
```java
@Configuration
public class BedrockConfig {
    
    @Bean
    public ChatClient chatClient(ChatClientBuilder builder) {
        return builder
            .defaultOptions(BedrockChatOptions.builder()
                .withModel("anthropic.claude-3-5-sonnet-20241022-v2:0")
                .withTemperature(0.7)
                .withMaxTokens(1024)
                .build())
            .build();
    }
}
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Manual Testing with curl
```bash
# Simple chat
curl "http://localhost:8080/api/chat?message=Hello"

# Complex message
curl "http://localhost:8080/api/chat?message=Explain%20the%20difference%20between%20microservices%20and%20monolithic%20architecture"
```

### Testing with Postman
1. Import the following request:
   - **Method:** GET
   - **URL:** `http://localhost:8080/api/chat`
   - **Params:** 
     - Key: `message`
     - Value: `Your test message`

## 🔐 Security Best Practices

1. **Never commit credentials**
   ```bash
   # Add to .gitignore
   .env
   .aws/
   application-local.properties
   ```

2. **Use IAM Roles in Production**
   - Deploy on EC2 with IAM instance roles
   - Use ECS task roles for container deployments
   - Use Lambda execution roles for serverless

3. **API Rate Limiting**
   - Implement rate limiting at application level
   - Use AWS WAF for additional protection
   - Monitor Bedrock API usage

4. **Input Validation**
   ```java
   @GetMapping("/chat")
   public String chat(@RequestParam @NotBlank String message) {
       // Message is validated
   }
   ```

5. **Error Handling**
   - Don't expose AWS error details to clients
   - Log sensitive information securely
   - Implement proper exception handling

## 🐛 Troubleshooting

### Issue: InvalidInputException - Model Not Found
**Solution:** 
- Verify Bedrock is enabled in your region
- Check model is available in your region
- Confirm IAM permissions include the model ARN

### Issue: Access Denied (AccessDenied)
**Solution:**
- Verify AWS credentials are correct
- Check IAM user has Bedrock permissions
- Ensure region matches IAM policy

### Issue: Request Throttled (ThrottlingException)
**Solution:**
- Implement exponential backoff
- Reduce request rate
- Contact AWS for quota increase

### Issue: No Models Available
**Solution:**
- Log into AWS Console
- Navigate to Bedrock > Model Access
- Request access to desired models
- Wait for approval (usually immediate)

### Issue: ClassNotFoundException for AWS SDK
**Solution:**
- Ensure AWS SDK is in classpath
- Verify `spring-ai-starter-model-bedrock-converse` dependency
- Run `mvn dependency:tree` to check versions

## 📈 Performance Optimization

1. **Connection Pooling**
   ```properties
   # Configure connection settings
   aws.connection.pool.size=10
   ```

2. **Caching Responses**
   ```java
   @Cacheable(value = "bedrockCache")
   public String getCachedResponse(String message) {
       return chatClient.prompt().user(message).call().content();
   }
   ```

3. **Async Processing**
   ```java
   @Async
   public CompletableFuture<String> chatAsync(String message) {
       return CompletableFuture.completedFuture(
           chatClient.prompt().user(message).call().content()
       );
   }
   ```

## 📦 Deployment

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/bedrock-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t bedrock-app .
docker run -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
           -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
           -e AWS_REGION=us-east-1 \
           -p 8080:8080 \
           bedrock-app
```

### AWS Deployment Options

1. **Elastic Beanstalk**
   ```bash
   eb create bedrock-env
   eb deploy
   ```

2. **ECS/Fargate**
   - Create task definition
   - Configure IAM task role with Bedrock permissions
   - Deploy service

3. **Lambda**
   - Use Spring Cloud Function
   - Configure Lambda handler
   - Set environment variables

## 📚 Resources

- [AWS Bedrock Documentation](https://docs.aws.amazon.com/bedrock/)
- [Spring AI Bedrock Integration](https://spring.io/projects/spring-ai)
- [Bedrock Model IDs](https://docs.aws.amazon.com/bedrock/latest/userguide/model-ids.html)
- [AWS IAM Documentation](https://docs.aws.amazon.com/iam/)

## 🗺️ Next Steps

1. Integrate multiple models
2. Add streaming responses
3. Implement chat memory
4. Add prompt engineering patterns
5. Set up monitoring and alerting

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

For issues or questions, please refer to the main project README.

