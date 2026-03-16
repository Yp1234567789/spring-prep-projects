# Multi-Model Integration - Spring AI

Advanced Spring Boot application demonstrating multi-model AI orchestration, combining OpenAI and AWS Bedrock models.

## 📋 Overview

This project showcases how to integrate multiple AI providers (OpenAI and AWS Bedrock) into a single Spring Boot application, enabling model selection, fallback mechanisms, and comparative analysis.

## 🚀 Features

- **Multi-Model Support** - OpenAI + AWS Bedrock integration
- **Model Selection Logic** - Dynamic provider selection
- **Fallback Mechanisms** - Graceful degradation between models
- **Comparative Analysis** - Side-by-side model comparison
- **Cost Optimization** - Model selection based on requirements
- **Unified API** - Single interface for multiple providers

## 📦 Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **OpenAI API** - Primary language model
- **AWS Bedrock** - Alternative AI provider
- **Java** 17
- **Maven** 3.6+

## 📋 Prerequisites

- Java 17+
- Maven 3.6+
- OpenAI API Key
- AWS Credentials with Bedrock access

## 🔧 Installation & Configuration

### 1. Clone Repository
```bash
git clone <repository-url>
cd multimodel
```

### 2. Set Environment Variables
```bash
# OpenAI
export OPENAI_API_KEY=sk-your-key

# AWS Bedrock
export AWS_ACCESS_KEY_ID=your-access-key
export AWS_SECRET_ACCESS_KEY=your-secret-key
export AWS_REGION=us-east-1
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:
```properties
# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4
spring.ai.openai.chat.options.temperature=0.7

# AWS Bedrock
spring.ai.bedrock.region=${AWS_REGION}
spring.ai.bedrock.converse.model=anthropic.claude-3-5-sonnet-20241022-v2:0
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
java -jar target/multimodel-0.0.1-SNAPSHOT.jar
```

Application will start on `http://localhost:8080`

## 📡 API Endpoints

### OpenAI Only
```
GET /api/openai/chat?message=Your+message
```

### Bedrock Only
```
GET /api/bedrock/chat?message=Your+message
```

### Auto-Select Model
```
GET /api/chat?message=Your+message
```

### Compare Models
```
POST /api/compare
Content-Type: application/json
{
  "prompt": "Explain quantum computing",
  "includeOpenAI": true,
  "includeBedrock": true
}
```

**Response:**
```json
{
  "prompt": "Explain quantum computing",
  "openai": {
    "response": "Quantum computing uses...",
    "model": "gpt-4",
    "tokens": 150
  },
  "bedrock": {
    "response": "Quantum computing leverages...",
    "model": "claude-3-5-sonnet",
    "tokens": 145
  }
}
```

### Fallback Chain
```
GET /api/chat/fallback?message=Your+message&priority=openai
```

## 💡 Architecture

### Model Registry
```
MultiModelRegistry
  ├── OpenAI Provider
  │   └── gpt-4, gpt-3.5-turbo
  ├── Bedrock Provider
  │   └── Claude, Llama, Titan
  └── Selection Logic
      ├── Cost optimizer
      ├── Quality optimizer
      └── Speed optimizer
```

### Routing Strategy
```
Request → Model Selector
         ├─→ Quality Mode → Use best model (GPT-4)
         ├─→ Cost Mode → Use budget model (Claude 3 Haiku)
         ├─→ Speed Mode → Use fastest model
         └─→ Fallback → Try secondary providers
```

## 💡 Code Examples

### Multi-Model Chat Controller
```java
@RestController
@RequestMapping("/api")
public class MultiModelController {
    
    private final OpenAiChatClient openAiClient;
    private final BedrockChatClient bedrockClient;

    @GetMapping("/chat")
    public String chat(@RequestParam String message,
                      @RequestParam(defaultValue = "auto") String provider) {
        if ("openai".equals(provider)) {
            return openAiClient.chat(message);
        } else if ("bedrock".equals(provider)) {
            return bedrockClient.chat(message);
        } else {
            return selectBestModel(message);
        }
    }

    private String selectBestModel(String message) {
        // Selection logic here
        return openAiClient.chat(message);
    }
}
```

### Model Comparison
```java
@PostMapping("/compare")
public ComparisonResult compareModels(@RequestBody ComparisonRequest request) {
    String openAiResponse = openAiClient.chat(request.getPrompt());
    String bedrockResponse = bedrockClient.chat(request.getPrompt());
    
    return ComparisonResult.builder()
        .prompt(request.getPrompt())
        .openai(openAiResponse)
        .bedrock(bedrockResponse)
        .build();
}
```

### Fallback Handler
```java
@GetMapping("/chat/fallback")
public String chatWithFallback(@RequestParam String message) {
    try {
        return openAiClient.chat(message);
    } catch (Exception e) {
        logger.warn("OpenAI failed, trying Bedrock", e);
        try {
            return bedrockClient.chat(message);
        } catch (Exception e2) {
            logger.error("All providers failed", e2);
            throw new ChatException("All models unavailable");
        }
    }
}
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Test specific controller
mvn test -Dtest=MultiModelControllerTest

# Integration tests
mvn verify
```

### Manual Testing
```bash
# OpenAI
curl "http://localhost:8080/api/openai/chat?message=Hello"

# Bedrock
curl "http://localhost:8080/api/bedrock/chat?message=Hello"

# Auto-select
curl "http://localhost:8080/api/chat?message=Hello"

# Compare
curl -X POST http://localhost:8080/api/compare \
  -H "Content-Type: application/json" \
  -d '{"prompt":"Explain AI"}'
```

## 📊 Decision Matrix

| Scenario | Recommended Provider | Reason |
|----------|---------------------|--------|
| Complex analysis | OpenAI GPT-4 | Best quality |
| Cost-sensitive | Bedrock Haiku | Lowest cost |
| Speed critical | Bedrock Llama | Fast inference |
| Consistency | Claude | Reliable output |
| Mixed workload | Auto-select | Optimized per request |

## 🔐 Security

- Store API keys in environment variables
- Use IAM roles in production
- Implement request validation
- Monitor API usage across providers
- Set rate limits per model
- Audit model selection decisions

## 🐛 Troubleshooting

### Model Not Available
- Verify API keys and credentials
- Check provider account access
- Ensure models are enabled in respective services

### Fallback Not Working
- Check provider health endpoints
- Review logs for timeout settings
- Verify network connectivity

### Cost Optimization
- Monitor token usage per provider
- Adjust selection strategy
- Consider caching common queries

## 📈 Performance Tuning

### Parallel Requests
```java
CompletableFuture<String> openAiTask = CompletableFuture
    .supplyAsync(() -> openAiClient.chat(message));
CompletableFuture<String> bedrockTask = CompletableFuture
    .supplyAsync(() -> bedrockClient.chat(message));

String faster = CompletableFuture.anyOf(openAiTask, bedrockTask)
    .get();
```

### Caching
```properties
# Cache responses
spring.cache.type=redis
spring.cache.redis.time-to-live=3600000
```

## 📚 Resources

- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [OpenAI API Reference](https://platform.openai.com/docs)
- [AWS Bedrock Docs](https://docs.aws.amazon.com/bedrock/)
- [Spring Cloud Function](https://spring.io/projects/spring-cloud-function)

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

