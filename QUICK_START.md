# Quick Start Guide

A quick reference guide for getting started with the Spring AI project suite.

## 📚 Project Overview

| Project | Purpose | Port | Key Tech |
|---------|---------|------|----------|
| **springai** | Main Spring AI demo | 8080 | OpenAI, RAG, Chat Memory |
| **bedrock** | AWS Bedrock integration | 8080 | AWS, Bedrock API |
| **openai** | Simple OpenAI example | 8080 | OpenAI |
| **multimodel** | Multi-provider orchestration | 8080 | OpenAI + Bedrock |
| **mcpclient** | MCP client implementation | 8080 | MCP Protocol |
| **mcpserverstdio** | MCP server (STDIO) | Console | MCP + STDIO |
| **mcpserverremote** | MCP server (HTTP) | 9090 | MCP + HTTP/REST |

## 🚀 Quick Start

### Option 1: Start Main Application (Spring AI)
```bash
cd springai

# Set environment variable
export OPENAI_API_KEY=sk-your-api-key

# Start Docker services
docker-compose up -d

# Build and run
mvn clean install
mvn spring-boot:run
```

Access at: `http://localhost:8080`

### Option 2: Start Bedrock Integration
```bash
cd bedrock

# Set AWS credentials
export AWS_ACCESS_KEY_ID=your-key
export AWS_SECRET_ACCESS_KEY=your-secret
export AWS_REGION=us-east-1

# Build and run
mvn clean install
mvn spring-boot:run
```

Access at: `http://localhost:8080`

### Option 3: Start MCP Demo
```bash
# Terminal 1: Start HTTP MCP Server
cd mcpserverremote
mvn spring-boot:run

# Terminal 2: Start MCP Client
cd ../mcpclient
export MCP_SERVER_URL=http://localhost:9090
export OPENAI_API_KEY=sk-your-api-key
mvn spring-boot:run
```

Access client at: `http://localhost:8080`

## 🔑 Environment Variables

### OpenAI
```bash
export OPENAI_API_KEY=sk-your-key
```

### AWS Bedrock
```bash
export AWS_ACCESS_KEY_ID=your-access-key
export AWS_SECRET_ACCESS_KEY=your-secret-key
export AWS_REGION=us-east-1
```

### Vector Store (Qdrant)
```bash
export QDRANT_HOST=localhost
export QDRANT_PORT=6333
```

## 📝 Common Commands

### Build All Projects
```bash
cd spring-prep-projects
mvn clean install
```

### Build Single Project
```bash
cd spring-prep-projects/springai
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=ChatControllerTest
```

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/springai-0.0.1-SNAPSHOT.jar
```

## 🧪 Test API Endpoints

### Spring AI Service
```bash
# Basic chat
curl "http://localhost:8080/api/chat?message=Hello"

# Chat memory
curl -X POST http://localhost:8080/api/chat-memory/message \
  -H "Content-Type: application/json" \
  -d '{"conversationId":"user-1","message":"Hello"}'

# Streaming
curl "http://localhost:8080/api/stream?message=Tell%20me%20a%20story"

# RAG query
curl -X POST http://localhost:8080/api/rag/query \
  -H "Content-Type: application/json" \
  -d '{"query":"HR policies"}'

# Structured output
curl -X POST http://localhost:8080/api/structured \
  -H "Content-Type: application/json" \
  -d '{"prompt":"List countries and cities"}'

# Help desk
curl -X POST http://localhost:8080/api/helpdesk/ticket \
  -H "Content-Type: application/json" \
  -d '{"title":"Cannot login","description":"Reset needed"}'
```

### MCP Server
```bash
# List tools
curl http://localhost:9090/api/mcp/tools/list

# Call tool
curl -X POST http://localhost:9090/api/mcp/tools/call \
  -H "Content-Type: application/json" \
  -d '{"name":"get_current_time","arguments":{"timezone":"UTC"}}'

# Server info
curl http://localhost:9090/api/mcp/info

# Health check
curl http://localhost:9090/api/mcp/health
```

### MCP Client
```bash
# Discover tools
curl http://localhost:8080/api/mcp/tools

# Chat with tools
curl -X POST http://localhost:8080/api/mcp/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"What time is it?"}'
```

### Bedrock
```bash
# Chat
curl "http://localhost:8080/api/chat?message=Hello"

# Health
curl http://localhost:8080/actuator/health
```

## 📁 File Structure

```
spring-prep-projects/
├── README.md                    # Main documentation
├── bedrock/                     # AWS Bedrock project
│   └── README.md
├── springai/                    # Main Spring AI project
│   ├── README_PROJECT.md
│   ├── compose.yaml            # Docker services
│   └── src/
├── openai/                      # OpenAI simple example
│   └── README_PROJECT.md
├── multimodel/                  # Multi-provider example
│   └── README_PROJECT.md
├── mcpclient/                   # MCP client
│   └── README_PROJECT.md
├── mcpserverstdio/             # MCP server (STDIO)
│   └── README_PROJECT.md
└── mcpserverremote/            # MCP server (HTTP)
    └── README_PROJECT.md
```

## 🔌 Configuration Files

### Default Ports
- Spring AI: `8080`
- Bedrock: `8080`
- OpenAI: `8080`
- MCP Server: `9090`
- Qdrant: `6333`
- H2 Console: `8080/h2-console`

### Key Properties
- `server.port` - Server port
- `spring.ai.openai.api-key` - OpenAI key
- `spring.ai.bedrock.region` - AWS region
- `spring.ai.vectorstore.qdrant.host` - Qdrant host

## 🐛 Common Issues

### API Key Not Found
```bash
# Check environment variable
echo $OPENAI_API_KEY

# Set if not found
export OPENAI_API_KEY=sk-your-key
```

### Port Already in Use
```bash
# Find process using port
lsof -i :8080

# Change port in application.properties
server.port=8081
```

### Qdrant Connection Failed
```bash
# Check if running
docker ps | grep qdrant

# Start services
cd springai
docker-compose up -d
```

### Java Version Error
```bash
# Check Java version
java -version

# Install correct version (17+)
# Update JAVA_HOME if needed
```

## 📚 Documentation

Each project has detailed documentation:

- **[springai/README_PROJECT.md](./springai/README_PROJECT.md)** - Full Spring AI features
- **[bedrock/README.md](./bedrock/README.md)** - AWS Bedrock integration
- **[openai/README_PROJECT.md](./openai/README_PROJECT.md)** - Simple OpenAI setup
- **[multimodel/README_PROJECT.md](./multimodel/README_PROJECT.md)** - Multi-provider orchestration
- **[mcpclient/README_PROJECT.md](./mcpclient/README_PROJECT.md)** - MCP client guide
- **[mcpserverstdio/README_PROJECT.md](./mcpserverstdio/README_PROJECT.md)** - MCP STDIO server
- **[mcpserverremote/README_PROJECT.md](./mcpserverremote/README_PROJECT.md)** - MCP HTTP server

## 🎯 Next Steps

1. Choose a project to start with
2. Set up required API keys
3. Run the project
4. Try the API endpoints
5. Explore the code
6. Build custom features

## 📞 Support

- Check project-specific README files
- Review inline code comments
- Check Spring AI documentation
- Review error messages and logs

## 🔗 Resources

- [Spring AI Official](https://spring.io/projects/spring-ai)
- [OpenAI API](https://platform.openai.com/docs)
- [AWS Bedrock](https://aws.amazon.com/bedrock/)
- [MCP Specification](https://spec.modelcontextprotocol.io/)
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring AI GitHub](https://github.com/spring-projects/spring-ai)

---

**Last Updated:** March 2026

Happy coding! 🚀

