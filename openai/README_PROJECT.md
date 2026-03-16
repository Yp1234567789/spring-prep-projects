# OpenAI Integration - Spring AI

A simple Spring Boot application demonstrating basic OpenAI model integration with Spring AI framework.

## 📋 Overview

This project provides a straightforward example of integrating OpenAI models into Spring Boot applications using the Spring AI framework.

## 🚀 Features

- **OpenAI Model Integration** - ChatGPT/GPT-4 support
- **Chat Completions** - Real-time conversational responses
- **Simple REST API** - Easy HTTP endpoints
- **Spring Boot Integration** - Full Spring ecosystem support
- **DevTools Support** - Hot reload for development
- **Error Handling** - Comprehensive exception management

## 📦 Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **OpenAI API** - Language models
- **Java** 17
- **Maven** 3.6+

## 📋 Prerequisites

- Java 17+
- Maven 3.6+
- OpenAI API Key (get at https://platform.openai.com/api-keys)

## 🔧 Installation & Configuration

### 1. Clone Repository
```bash
git clone <repository-url>
cd openai
```

### 2. Set OpenAI API Key

#### Option A: Environment Variable
```bash
# Linux/macOS
export OPENAI_API_KEY=sk-your-key

# Windows PowerShell
$env:OPENAI_API_KEY="sk-your-key"
```

#### Option B: application.properties
```properties
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:
```properties
# Server
server.port=8080

# OpenAI Configuration
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4
spring.ai.openai.chat.options.temperature=0.7

# Logging
logging.level.root=INFO
```

### 4. Build Application
```bash
mvn clean install
```

## 🚀 Running the Application

### Development Mode
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/openai-0.0.1-SNAPSHOT.jar
```

Application will start on `http://localhost:8080`

## 📡 API Endpoints

### Chat Endpoint
```http
GET /api/chat?message=Your+message
```

**Example:**
```bash
curl "http://localhost:8080/api/chat?message=What%20is%20Spring%20Boot?"
```

**Response:**
```json
{
  "response": "Spring Boot is a framework that simplifies the development of stand-alone, production-grade Spring-based applications..."
}
```

## 💡 Code Example

### Simple Chat Controller
```java
@RestController
@RequestMapping("/api")
public class ChatController {
    
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
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

### With Custom Options
```java
@GetMapping("/chat/custom")
public String chatWithOptions(@RequestParam String message) {
    return chatClient
        .prompt()
        .user(message)
        .options(OpenAiChatOptions.builder()
            .withModel("gpt-4")
            .withTemperature(0.5)
            .build())
        .call()
        .content();
}
```

## 🧪 Testing

```bash
# Run tests
mvn test

# Test with curl
curl "http://localhost:8080/api/chat?message=Hello%20World"
```

## 🔐 Security

- Never commit API keys to repository
- Use environment variables in production
- Implement rate limiting
- Monitor API usage and costs
- Validate user inputs

## 🐛 Troubleshooting

### API Key Error
```bash
# Verify key is set
echo $OPENAI_API_KEY

# Update properties if needed
```

### Model Not Available
- Ensure your OpenAI account has access to the model
- Check for quota limits
- Verify region settings

## 📚 Resources

- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [OpenAI API Docs](https://platform.openai.com/docs)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

