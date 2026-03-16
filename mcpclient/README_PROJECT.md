# Model Context Protocol (MCP) - Client Implementation

A Spring Boot application implementing the Model Context Protocol (MCP) client for advanced model interactions and tool discovery.

## 📋 Overview

This project demonstrates the MCP client implementation, enabling communication with MCP servers to discover and invoke tools dynamically through LLM interactions.

## 🚀 Features

- **MCP Client Implementation** - Full protocol support
- **Tool Discovery** - Dynamic tool registration from server
- **Remote Server Connection** - HTTP-based MCP server communication
- **OpenAI Integration** - Model reasoning about available tools
- **Function Calling** - LLM-driven tool invocation
- **Error Handling** - Robust connection and protocol handling

## 📦 Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **MCP Client** - Model Context Protocol support
- **OpenAI API** - Language model
- **Java** 21
- **Maven** 3.6+

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│   Spring Boot Application       │
│   (MCP Client)                  │
├─────────────────────────────────┤
│  ChatClient                     │
│  + Tool Discovery               │
│  + Function Calling             │
├─────────────────────────────────┤
│  MCP Client Protocol Handler    │
│  (JSON-RPC)                     │
├─────────────────────────────────┤
│  OpenAI Integration             │
└─────────────────────────────────┘
         ↓ (HTTP)
┌─────────────────────────────────┐
│  MCP Server (Remote)            │
│  (Tool Definitions)             │
└─────────────────────────────────┘
```

## 📋 Prerequisites

- Java 21+
- Maven 3.6+
- OpenAI API Key
- Running MCP Server (mcpserverremote or mcpserverstdio)

## 🔧 Installation & Configuration

### 1. Clone Repository
```bash
git clone <repository-url>
cd mcpclient
```

### 2. Set Environment Variables
```bash
# OpenAI
export OPENAI_API_KEY=sk-your-key

# MCP Server
export MCP_SERVER_URL=http://localhost:9090
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:
```properties
# Server
server.port=8080

# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4

# MCP Client Configuration
mcp.client.url=${MCP_SERVER_URL:http://localhost:9090}
mcp.client.connect-timeout=30000
mcp.client.request-timeout=60000
```

### 4. Build Application
```bash
mvn clean install
```

## 🚀 Running the Application

### Prerequisites: Start MCP Server
```bash
# In another terminal
cd ../mcpserverremote
mvn spring-boot:run
```

### Run MCP Client
```bash
mvn spring-boot:run
```

Client will start on `http://localhost:8080`

## 📡 API Endpoints

### Discover Tools
```
GET /api/mcp/tools
```

**Response:**
```json
{
  "tools": [
    {
      "name": "weather",
      "description": "Get weather information",
      "inputSchema": {
        "type": "object",
        "properties": {
          "location": { "type": "string" }
        }
      }
    }
  ]
}
```

### Chat with Tool Support
```
POST /api/mcp/chat
Content-Type: application/json
{
  "message": "What's the weather in Paris?"
}
```

**Response:**
```json
{
  "response": "The weather in Paris is sunny with 22°C",
  "toolsCalled": ["weather"],
  "executionTime": 1234
}
```

### Invoke Tool Directly
```
POST /api/mcp/tool/invoke
Content-Type: application/json
{
  "toolName": "weather",
  "parameters": {
    "location": "London"
  }
}
```

### Get Tool Details
```
GET /api/mcp/tool/{toolName}
```

## 💡 Code Examples

### MCP Client Service
```java
@Service
public class MCPClientService {
    
    private final ChatClient chatClient;
    private final MCPServerClient mcpClient;

    public List<Tool> discoverTools() {
        return mcpClient.listTools();
    }

    public String chatWithTools(String message) {
        return chatClient
            .prompt()
            .user(message)
            .functions(getToolNames())
            .call()
            .content();
    }

    private String[] getToolNames() {
        return discoverTools().stream()
            .map(Tool::getName)
            .toArray(String[]::new);
    }
}
```

### Tool Invocation Handler
```java
@RestController
@RequestMapping("/api/mcp")
public class MCPController {
    
    private final MCPClientService mcpService;

    @PostMapping("/tool/invoke")
    public Object invokeTool(@RequestBody ToolInvocationRequest request) {
        return mcpService.invokeTool(
            request.getToolName(),
            request.getParameters()
        );
    }

    @PostMapping("/chat")
    public ChatResponse chatWithTools(@RequestBody ChatRequest request) {
        return mcpService.chatWithTools(request.getMessage());
    }
}
```

### Server Connection Management
```java
@Configuration
public class MCPClientConfiguration {
    
    @Bean
    public MCPServerClient mcpServerClient(
            @Value("${mcp.client.url}") String serverUrl) {
        return new MCPServerClient(serverUrl);
    }

    @Bean
    public ChatClient chatClient(
            ChatClientBuilder builder,
            MCPServerClient mcpClient) {
        return builder
            .defaultOptions(buildOptions(mcpClient))
            .build();
    }
}
```

## 🔌 MCP Protocol Details

### Tool Discovery
```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "tools/list",
  "params": {}
}
```

### Tool Invocation
```json
{
  "jsonrpc": "2.0",
  "id": 2,
  "method": "tools/call",
  "params": {
    "name": "weather",
    "arguments": {
      "location": "Paris"
    }
  }
}
```

## 🧪 Testing

```bash
# Run tests
mvn test

# Integration tests (requires running server)
mvn verify

# Test tool discovery
curl http://localhost:8080/api/mcp/tools

# Test chat with tools
curl -X POST http://localhost:8080/api/mcp/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"What tools are available?"}'
```

## 🔐 Security

- Validate all tool parameters
- Implement authorization checks
- Sanitize tool responses
- Log all tool invocations
- Rate limit tool calls
- Authenticate server connections

## 🐛 Troubleshooting

### Cannot Connect to MCP Server
```bash
# Verify server is running
curl http://localhost:9090/api/mcp/health

# Check network connectivity
ping localhost
```

### Tool Not Found
- Verify tool name spelling
- Check server tool registration
- Ensure tool is exported from server

### Timeout Errors
- Increase request timeout in properties
- Check server responsiveness
- Monitor network latency

## 📈 Performance Tips

- Cache tool list after discovery
- Batch tool invocations when possible
- Implement connection pooling
- Monitor MCP server latency
- Use appropriate timeout settings

## 🗺️ Next Steps

1. Implement custom tools on MCP server
2. Add tool result caching
3. Implement retry logic with backoff
4. Add monitoring and metrics
5. Support multiple MCP servers

## 📚 Resources

- [MCP Specification](https://spec.modelcontextprotocol.io/)
- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [OpenAI Function Calling](https://platform.openai.com/docs/guides/function-calling)

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

