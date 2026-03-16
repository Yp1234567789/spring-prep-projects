# Spring AI Preparation Projects

A comprehensive collection of Spring Boot applications demonstrating various AI and ML capabilities using Spring AI framework. This repository showcases multiple integration patterns with different AI/ML models and protocols.

## 📋 Overview

This project suite contains multiple Spring Boot microservices demonstrating:
- **OpenAI Integration** - Chat completions, structured outputs, and streaming
- **AWS Bedrock Integration** - Amazon's managed AI/ML service integration
- **Spring AI Features** - RAG (Retrieval Augmented Generation), chat memory, prompt templates, and more
- **Model Context Protocol (MCP)** - Client and server implementations for advanced model interactions
- **Multi-Model Support** - Combining multiple AI providers in a single application

## 🚀 Features

### Core Capabilities
- **Chat Completions** - Conversational AI with context awareness
- **Structured Output** - Generating typed JSON responses from LLMs
- **Chat Memory** - Persistent conversation history with JDBC backend
- **Streaming Responses** - Real-time token streaming from AI models
- **Prompt Templates** - Reusable prompt patterns for common tasks
- **RAG (Retrieval Augmented Generation)** - Enhance LLM responses with external knowledge
- **Tool Integration** - Function calling and dynamic tool management
- **Vector Store Integration** - Qdrant vector database support for semantic search
- **MCP Protocol** - Model Context Protocol for extended AI capabilities

### Advanced Features
- **Token Usage Auditing** - Track and audit AI API token consumption
- **PII Masking** - Document post-processing with sensitive data masking
- **Multi-Model Orchestration** - Use multiple AI models in a single pipeline
- **Document Processing** - PDF and document parsing with Tika
- **Help Desk Automation** - Intelligent ticket processing and routing

## 📦 Project Structure

### **springai** - Main Spring AI Demo
The primary demonstration project showcasing Spring AI features:
- **Controllers:**
  - `ChatController` - Basic chat completions
  - `ChatMemoryController` - Conversation history management
  - `RAGController` - Retrieval augmented generation
  - `StructuredOutPutController` - JSON schema-based outputs
  - `StreamController` - Streaming responses
  - `PromptTemplateController` - Prompt reuse patterns
  - `HelpDeskController` - Automated help desk ticket system
  - `TimeController` - Function calling examples

- **Features:**
  - OpenAI model integration
  - H2 in-memory database for chat memory
  - Qdrant vector store for RAG
  - Document parsing with Tika
  - Multi-model orchestration
  - Token usage auditing

- **Tech Stack:**
  - Spring Boot 4.0.3
  - Spring AI 2.0.0-M2
  - OpenAI API
  - H2 Database
  - Qdrant Vector Store
  - Lombok

### **bedrock** - AWS Bedrock Integration
Demonstrates integration with AWS's managed AI service:
- Uses Bedrock Converse API
- Real-time AI inference
- AWS credential management
- Bedrock model support

### **openai** - OpenAI Integration
Simple OpenAI model integration example:
- Chat completion endpoints
- Token management
- Basic prompt engineering

### **multimodel** - Multi-Model Orchestration
Combines multiple AI providers:
- OpenAI + Bedrock integration
- Model selection logic
- Fallback mechanisms
- Comparative analysis of different models

### **mcpclient** - Model Context Protocol Client
Implements MCP client functionality:
- Remote server connection
- Tool discovery and invocation
- OpenAI model integration
- JSON-RPC communication

### **mcpserverstdio** - MCP Server (STDIO)
Implements MCP server using STDIO protocol:
- Tool/resource definitions
- Request handling
- STDIO-based communication
- JPA integration

### **mcpserverremote** - MCP Server (HTTP Remote)
Implements MCP server with HTTP protocol:
- Remote HTTP endpoints
- Resource management
- RESTful communication

## 🛠️ Technology Stack

### Framework & Platform
- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **Java** 17-21

### AI/ML Services
- **OpenAI GPT** - Primary language model
- **AWS Bedrock** - Managed AI service
- **Qdrant** - Vector database for embeddings

### Data & Storage
- **H2 Database** - In-memory relational database
- **Spring Data JPA** - ORM framework
- **Spring Data JDBC** - SQL database access

### Additional Libraries
- **Lombok** - Reduce boilerplate code
- **Tika** - Document parsing
- **Docker Compose** - Containerized dependencies

## 📋 Prerequisites

### System Requirements
- Java 17+ (Java 21 recommended for MCP projects)
- Maven 3.6+
- Docker & Docker Compose (for Qdrant and other services)

### API Keys & Credentials
- **OpenAI API Key** - Required for OpenAI integration
- **AWS Credentials** - Required for Bedrock integration
- **Qdrant Instance** - For RAG/vector store features

## 🔧 Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd spring-prep-projects
```

### 2. Set Up Environment Variables

Create an `.env` file or configure system environment variables:

```bash
# OpenAI
OPENAI_API_KEY=your-api-key-here

# AWS Bedrock
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
AWS_REGION=us-east-1

# Qdrant
QDRANT_HOST=localhost
QDRANT_PORT=6333
```

### 3. Start Required Services

#### Using Docker Compose (from springai directory)
```bash
cd springai
docker-compose up -d
```

This starts:
- Qdrant vector database
- PostgreSQL (if configured)

### 4. Build All Projects
```bash
mvn clean install
```

### 5. Build Individual Projects
```bash
cd springai
mvn clean install

cd ../bedrock
mvn clean install

cd ../openai
mvn clean install

# ... and so on for other modules
```

## 🚀 Usage

### Running Spring AI Main Application
```bash
cd springai
mvn spring-boot:run
```

Application will start on `http://localhost:8080`

### Running Other Services
```bash
# Bedrock
cd bedrock
mvn spring-boot:run

# OpenAI
cd openai
mvn spring-boot:run

# Multi-Model
cd multimodel
mvn spring-boot:run

# MCP Client
cd mcpclient
mvn spring-boot:run

# MCP Server
cd mcpserverstdio
mvn spring-boot:run
```

## 📡 API Endpoints

### Spring AI Service (Port 8080)

#### Basic Chat
```bash
GET /api/chat?message=Hello%20World
```

#### Chat with Memory
```bash
POST /api/chat-memory
Content-Type: application/json
{
  "message": "What did I ask before?",
  "conversationId": "user-123"
}
```

#### Structured Output
```bash
POST /api/structured
Content-Type: application/json
{
  "topic": "Spring Framework"
}
```

#### RAG Query
```bash
POST /api/rag/query
Content-Type: application/json
{
  "query": "How to implement RAG?"
}
```

#### Streaming Response
```bash
GET /api/stream?message=Tell%20me%20a%20story
```

#### Prompt Template
```bash
POST /api/prompt-template
Content-Type: application/json
{
  "topic": "AI",
  "context": "Spring Boot application"
}
```

#### Help Desk Ticket
```bash
POST /api/helpdesk/ticket
Content-Type: application/json
{
  "title": "Cannot login",
  "description": "Password reset required"
}
```

## 📚 Key Concepts

### Chat Memory
Persistent conversation history stored in H2 database:
- Maintains context across requests
- Automatic memory management
- JDBC-based repository pattern

### RAG (Retrieval Augmented Generation)
Enhance LLM responses with external knowledge:
- Document ingestion
- Vector embeddings
- Semantic search
- Context injection

### Prompt Templates
Reusable prompt patterns:
- System prompts
- Variable substitution
- Template caching
- Role-based prompting

### Structured Outputs
Type-safe LLM responses:
- JSON schema definition
- Java class binding
- Validation and parsing
- Error handling

### Tool Integration
Function calling and tool invocation:
- Tool discovery
- Parameter binding
- Async execution
- Result handling

## 🔌 Configuration

### application.properties (springai)
```properties
# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4
spring.ai.openai.chat.options.temperature=0.7

# Vector Store
spring.ai.vectorstore.qdrant.host=localhost
spring.ai.vectorstore.qdrant.port=6333

# Chat Memory
spring.datasource.url=jdbc:h2:mem:chatdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## 🧪 Testing

Run tests for a specific module:
```bash
cd springai
mvn test
```

Run all tests across all modules:
```bash
mvn test -DskipTests=false
```

## 📖 Documentation

Each module contains:
- `HELP.md` - Module-specific setup guide
- `README.md` - Feature documentation
- Inline code comments and Javadoc

## 🐛 Troubleshooting

### OpenAI API Key Not Found
- Verify `OPENAI_API_KEY` environment variable is set
- Check `application.properties` configuration
- Ensure API key is valid and has sufficient quota

### Vector Store Connection Failed
- Ensure Qdrant is running: `docker ps`
- Check Qdrant configuration in properties
- Verify network connectivity

### AWS Bedrock Authentication Error
- Verify AWS credentials are configured
- Check IAM permissions for Bedrock service
- Ensure region is correctly set

### Java Version Incompatibility
- Use Java 17+ for springai project
- Use Java 21+ for MCP projects
- Verify `java -version`

## 🔐 Security Considerations

- Never commit API keys or credentials
- Use environment variables for sensitive data
- Implement API rate limiting
- Monitor token usage and costs
- Validate and sanitize user inputs
- Enable PII masking where needed

## 📈 Performance Tips

- Use streaming for long responses
- Implement caching for common prompts
- Batch API calls when possible
- Monitor token consumption
- Optimize vector search queries
- Use connection pooling for databases

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Add tests
4. Ensure all tests pass
5. Submit a pull request

## 📝 License

This project is provided as educational material.

## 📞 Support & Resources

- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [OpenAI API Docs](https://platform.openai.com/docs)
- [AWS Bedrock Docs](https://docs.aws.amazon.com/bedrock/)
- [Qdrant Documentation](https://qdrant.tech/documentation/)
- [Spring Boot Docs](https://spring.io/projects/spring-boot)

## 🗺️ Roadmap

- [ ] Production deployment configurations
- [ ] Kubernetes manifests
- [ ] Advanced RAG patterns
- [ ] Fine-tuning examples
- [ ] Multi-language support
- [ ] Enhanced monitoring and observability
- [ ] Integration with more AI providers

---

**Last Updated:** March 2026

For questions or issues, please open an issue in the repository.

