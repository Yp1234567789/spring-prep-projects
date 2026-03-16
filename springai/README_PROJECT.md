# Spring AI - Comprehensive AI Integration Demo

The flagship Spring AI demonstration project showcasing advanced AI/ML capabilities including chat memory, RAG, streaming, structured outputs, and multi-model orchestration.

## 📋 Overview

A production-grade Spring Boot application demonstrating the complete Spring AI ecosystem with OpenAI models, vector databases, and enterprise patterns.

## 🎯 Key Features

### 1. **Chat Management**
- Real-time conversational AI
- Context-aware responses
- Support for system prompts and instructions

### 2. **Chat Memory**
- Persistent conversation history
- H2 database backend
- JDBC-based repository pattern
- Automatic context preservation

### 3. **RAG (Retrieval Augmented Generation)**
- Document ingestion and parsing
- Vector embeddings with OpenAI
- Semantic search with Qdrant
- Context injection for accurate responses
- PDF and document support

### 4. **Streaming Responses**
- Real-time token streaming
- Server-Sent Events (SSE) support
- Progressive response display
- Memory-efficient processing

### 5. **Structured Outputs**
- JSON schema-based responses
- Type-safe generation
- Validation and error handling
- Country/City classification example

### 6. **Prompt Templates**
- Reusable prompt patterns
- Variable substitution
- Template caching
- Role-based prompting

### 7. **Help Desk Automation**
- Intelligent ticket routing
- Automatic categorization
- Agent function calling
- Ticket management system

### 8. **Advanced Features**
- Token usage auditing
- Multi-model support
- Document processing
- PII masking
- Time-based function tools

## 🏗️ Project Structure

```
springai/
├── src/main/java/com/test/springai/
│   ├── SpringaiApplication.java           # Main application
│   ├── controller/                         # REST endpoints
│   │   ├── ChatController.java
│   │   ├── ChatMemoryController.java
│   │   ├── RAGController.java
│   │   ├── StreamController.java
│   │   ├── StructuredOutPutController.java
│   │   ├── PromptTemplateController.java
│   │   ├── HelpDeskController.java
│   │   └── TimeController.java
│   ├── model/                              # Data models
│   │   ├── TicketRequest.java
│   │   └── CountryCities.java
│   ├── tools/                              # Function tools
│   │   ├── TimeTools.java
│   │   └── HelpDeskTools.java
│   ├── advisors/                           # AI advisors
│   │   └── TokenUsageAuditAdvisor.java
│   ├── rag/                                # RAG utilities
│   │   ├── RandomDataLoader.java
│   │   ├── WebSearchDocumentRetriever.java
│   │   └── PIIMaskingDocumentPostProcessor.java
│   ├── repository/                         # Data access
│   │   └── HelpDeskTicketRepository.java
│   └── config/                             # Configuration
│       └── ChatClientConfig.java
├── src/main/resources/
│   ├── application.properties
│   ├── promptTemplates/                    # Reusable prompts
│   ├── schema/                             # JSON schemas
│   └── Eazybytes_HR_Policies.pdf          # Sample document
├── pom.xml
└── compose.yaml                            # Docker services
```

## 📦 Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **OpenAI API** - Chat completions and embeddings
- **H2 Database** - Chat memory storage
- **Qdrant** - Vector store for RAG
- **Apache Tika** - Document parsing
- **Lombok** - Code generation
- **Docker Compose** - Service orchestration

## 🔧 Installation

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- OpenAI API Key
- Qdrant running (via Docker)

### Setup Steps

1. **Clone and Navigate**
   ```bash
   cd springai
   ```

2. **Set Environment Variables**
   ```bash
   export OPENAI_API_KEY=your-api-key
   export QDRANT_HOST=localhost
   export QDRANT_PORT=6333
   ```

3. **Start Services**
   ```bash
   docker-compose up -d
   ```

4. **Build Application**
   ```bash
   mvn clean install
   ```

5. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

Application starts on `http://localhost:8080`

## 📡 API Endpoints

### Chat Operations

**Basic Chat**
```
GET /api/chat?message=Hello
```

**Chat with System Instructions**
```
POST /api/chat
Content-Type: application/json
{
  "message": "Your question",
  "systemPrompt": "You are a helpful assistant"
}
```

### Chat Memory

**Start Conversation**
```
POST /api/chat-memory/conversation
```

**Send Message**
```
POST /api/chat-memory/message
Content-Type: application/json
{
  "conversationId": "conv-123",
  "message": "What did you say before?"
}
```

**Get History**
```
GET /api/chat-memory/history/{conversationId}
```

### RAG Features

**Query Documents**
```
POST /api/rag/query
Content-Type: application/json
{
  "query": "What are the benefits of the retirement plan?",
  "similarity": 0.8
}
```

**Ingest Document**
```
POST /api/rag/ingest
Content-Type: multipart/form-data
{
  "file": <PDF file>
}
```

### Streaming

**Stream Response**
```
GET /api/stream?message=Tell%20me%20a%20story
```

Returns Server-Sent Events (SSE) stream

### Structured Output

**Generate Structured Data**
```
POST /api/structured
Content-Type: application/json
{
  "prompt": "Identify the country and list cities"
}
```

Response:
```json
{
  "country": "France",
  "cities": ["Paris", "Lyon", "Marseille"]
}
```

### Prompt Templates

**Use Template**
```
POST /api/prompt-template
Content-Type: application/json
{
  "language": "Python",
  "task": "Read from database"
}
```

### Help Desk Automation

**Create Ticket**
```
POST /api/helpdesk/ticket
Content-Type: application/json
{
  "title": "Cannot login to system",
  "description": "Password reset needed",
  "email": "user@company.com"
}
```

**Get Ticket Analysis**
```
GET /api/helpdesk/analyze/{ticketId}
```

### Time Tools

**Get Current Time**
```
GET /api/time/current
```

**Get Time with Function Calling**
```
POST /api/time/with-ai
Content-Type: application/json
{
  "message": "What time is it and add 2 hours?"
}
```

## 🔌 Configuration

### application.properties

```properties
# Server
server.port=8080

# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4-turbo
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.embedding.options.model=text-embedding-3-small

# Database (Chat Memory)
spring.datasource.url=jdbc:h2:mem:chatdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true

# Vector Store (RAG)
spring.ai.vectorstore.qdrant.host=${QDRANT_HOST:localhost}
spring.ai.vectorstore.qdrant.port=${QDRANT_PORT:6333}

# Logging
logging.level.root=INFO
logging.level.org.springframework.ai=DEBUG
```

## 💡 Usage Examples

### Example 1: Simple Chat
```java
@GetMapping("/chat")
public String chat(@RequestParam String message) {
    return chatClient
        .prompt()
        .user(message)
        .call()
        .content();
}
```

### Example 2: Chat with Memory
```java
@PostMapping("/chat-memory")
public String chatWithMemory(@RequestBody ChatRequest request) {
    return chatClient
        .prompt()
        .user(request.getMessage())
        .advisors(new MessageChatMemoryAdvisor(chatMemory))
        .call()
        .content();
}
```

### Example 3: RAG Query
```java
@PostMapping("/rag/query")
public String ragQuery(@RequestBody String query) {
    return chatClient
        .prompt()
        .user(query)
        .advisors(new QuestionAnswerAdvisor(vectorStore))
        .call()
        .content();
}
```

### Example 4: Streaming
```java
@GetMapping("/stream")
public Flux<String> streamChat(@RequestParam String message) {
    return chatClient
        .prompt()
        .user(message)
        .stream()
        .content()
        .map(chunk -> chunk);
}
```

### Example 5: Structured Output
```java
@PostMapping("/structured")
public CountryCities getStructured(@RequestBody String prompt) {
    return chatClient
        .prompt()
        .user(prompt)
        .call()
        .entity(CountryCities.class);
}
```

### Example 6: Function Calling
```java
@PostMapping("/helpdesk")
public String helpdesk(@RequestBody String message) {
    return chatClient
        .prompt()
        .user(message)
        .functions("createTicket", "updateTicket", "searchTickets")
        .call()
        .content();
}
```

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=ChatControllerTest
```

### Integration Testing
```bash
mvn verify
```

### Manual Testing with curl
```bash
# Chat
curl "http://localhost:8080/api/chat?message=Hello"

# RAG Query
curl -X POST http://localhost:8080/api/rag/query \
  -H "Content-Type: application/json" \
  -d '{"query":"HR policies"}'

# Stream
curl "http://localhost:8080/api/stream?message=Story"
```

## 📊 Monitoring & Auditing

### Token Usage Audit
```java
// Automatically tracked with TokenUsageAuditAdvisor
.advisors(new TokenUsageAuditAdvisor())
```

### Logging Configuration
```properties
# Enable debug logging
logging.level.org.springframework.ai=DEBUG
logging.level.com.test.springai=DEBUG
```

## 🔐 Security

- Store API keys in environment variables
- Use IAM roles in production deployments
- Validate and sanitize user inputs
- Enable PII masking for sensitive documents
- Implement rate limiting
- Use HTTPS in production

## 🐛 Troubleshooting

### Qdrant Connection Error
```bash
# Check if Qdrant is running
docker ps | grep qdrant

# Restart services
docker-compose restart qdrant
```

### OpenAI API Error
- Verify API key: `echo $OPENAI_API_KEY`
- Check rate limits
- Ensure sufficient account credits

### H2 Database Issues
- Clear database: `rm *.db *.trace.db`
- Restart application
- Check database URL in properties

## 📚 Resources

- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [OpenAI API Reference](https://platform.openai.com/docs/api-reference)
- [Qdrant Documentation](https://qdrant.tech/documentation/)
- [Spring Boot Guide](https://spring.io/guides)

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

