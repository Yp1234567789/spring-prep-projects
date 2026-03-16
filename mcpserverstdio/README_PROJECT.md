# Model Context Protocol (MCP) - Server Implementation (STDIO)

A Spring Boot application implementing the Model Context Protocol (MCP) server using STDIO protocol for tool and resource management.

## 📋 Overview

This project demonstrates the MCP server implementation with STDIO protocol, enabling external clients and LLMs to discover and invoke tools through standardized protocol.

## 🚀 Features

- **MCP Server Implementation** - Full STDIO protocol support
- **Tool Registration** - Define and expose custom tools
- **Resource Management** - Manage server resources
- **JPA Integration** - Database-backed resources
- **Auto-Discovery** - Client-side tool discovery
- **Error Handling** - Protocol-compliant error responses

## 📦 Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **MCP Server** - Model Context Protocol support
- **Spring Data JPA** - Database integration
- **Java** 21
- **Maven** 3.6+

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│  MCP Server (STDIO)             │
│  Spring Boot Application        │
├─────────────────────────────────┤
│  Tool Definition Registry       │
│  - Weather Tool                 │
│  - Database Tool                │
│  - File Tool                    │
├─────────────────────────────────┤
│  MCP Protocol Handler           │
│  (JSON-RPC over STDIO)          │
├─────────────────────────────────┤
│  JPA Repository Layer           │
│  (H2/PostgreSQL)                │
└─────────────────────────────────┘
         ↑ (STDIO)
   ┌─────────────┴──────────────┐
   │                            │
   │ MCP Client                 │
   │ (subprocess)               │
```

## 📋 Prerequisites

- Java 21+
- Maven 3.6+
- H2 or PostgreSQL database

## 🔧 Installation & Configuration

### 1. Clone Repository
```bash
git clone <repository-url>
cd mcpserverstdio
```

### 2. Configure Application

Edit `src/main/resources/application.properties`:
```properties
# Server
server.port=8080

# MCP Server Configuration
mcp.server.name=Spring AI MCP Server
mcp.server.version=1.0.0

# Database Configuration
spring.datasource.url=jdbc:h2:mem:mcpdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

# Logging
logging.level.root=INFO
logging.level.org.springframework.ai.mcp=DEBUG
```

### 3. Build Application
```bash
mvn clean install
```

## 🚀 Running the Application

### Standard Startup
```bash
mvn spring-boot:run
```

### As Subprocess (for MCP Clients)
```bash
mvn clean package
java -jar target/mcpserverstdio-0.0.1-SNAPSHOT.jar
```

## 📦 Built-in Tools

### 1. Time Tool
```json
{
  "name": "get_current_time",
  "description": "Get the current time",
  "inputSchema": {
    "type": "object",
    "properties": {
      "timezone": {
        "type": "string",
        "description": "Timezone (e.g., UTC, EST)"
      }
    }
  }
}
```

### 2. Database Tool
```json
{
  "name": "query_database",
  "description": "Execute database queries",
  "inputSchema": {
    "type": "object",
    "properties": {
      "query": {
        "type": "string",
        "description": "SQL query"
      }
    }
  }
}
```

### 3. File Operations
```json
{
  "name": "read_file",
  "description": "Read file contents",
  "inputSchema": {
    "type": "object",
    "properties": {
      "filepath": {
        "type": "string"
      }
    }
  }
}
```

## 💡 Defining Custom Tools

### Tool Registration
```java
@Configuration
public class MCPToolConfiguration {

    @Bean
    public ToolDefinition weatherTool() {
        return ToolDefinition.builder()
            .name("get_weather")
            .description("Get weather for a location")
            .inputSchema(buildWeatherSchema())
            .build();
    }

    private Map<String, Object> buildWeatherSchema() {
        return Map.of(
            "type", "object",
            "properties", Map.of(
                "location", Map.of("type", "string"),
                "units", Map.of("type", "string", "enum", List.of("C", "F"))
            ),
            "required", List.of("location")
        );
    }
}
```

### Tool Implementation
```java
@Service
public class WeatherToolService {

    @MCPTool(name = "get_weather")
    public String getWeather(String location, String units) {
        // Implementation
        return "Weather data for " + location;
    }
}
```

### Tool Handler
```java
@RestController
@RequestMapping("/api/tools")
public class ToolInvocationHandler {

    private final WeatherToolService weatherService;

    @PostMapping("/invoke")
    public Object invokeTool(@RequestBody ToolInvocation invocation) {
        switch (invocation.getToolName()) {
            case "get_weather":
                return weatherService.getWeather(
                    (String) invocation.getParams().get("location"),
                    (String) invocation.getParams().get("units")
                );
            default:
                throw new ToolNotFoundException(invocation.getToolName());
        }
    }
}
```

## 📡 MCP Protocol Examples

### List Tools Request
```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "tools/list",
  "params": {}
}
```

### List Tools Response
```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "result": {
    "tools": [
      {
        "name": "get_weather",
        "description": "Get weather information",
        "inputSchema": { ... }
      }
    ]
  }
}
```

### Tool Call Request
```json
{
  "jsonrpc": "2.0",
  "id": 2,
  "method": "tools/call",
  "params": {
    "name": "get_weather",
    "arguments": {
      "location": "Paris",
      "units": "C"
    }
  }
}
```

### Tool Call Response
```json
{
  "jsonrpc": "2.0",
  "id": 2,
  "result": {
    "content": [
      {
        "type": "text",
        "text": "Paris: 22°C, Sunny"
      }
    ]
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
# List tools
curl -X POST http://localhost:8080/api/mcp/tools/list

# Call tool
curl -X POST http://localhost:8080/api/mcp/tools/call \
  -H "Content-Type: application/json" \
  -d '{
    "name": "get_weather",
    "arguments": {"location": "London"}
  }'
```

### STDIO Testing
```bash
# Run server
java -jar target/mcpserverstdio-0.0.1-SNAPSHOT.jar

# In another terminal, send JSON-RPC
echo '{"jsonrpc":"2.0","id":1,"method":"tools/list","params":{}}' | java -cp target/mcpserverstdio-0.0.1-SNAPSHOT.jar
```

## 🔐 Security

- Validate all tool inputs
- Implement authorization checks
- Rate limit tool invocations
- Audit tool calls
- Sanitize tool output
- Restrict file access paths
- Validate database queries

## 🐛 Troubleshooting

### Tools Not Listed
- Check tool registration in configuration
- Verify bean definitions
- Review application logs

### Tool Invocation Fails
- Validate input parameters
- Check tool implementation
- Review error responses

### STDIO Protocol Issues
- Ensure proper JSON formatting
- Check line-ending conventions
- Verify process stdio redirection

## 📈 Performance Optimization

### Caching
```java
@Cacheable(value = "toolDefinitions")
public List<ToolDefinition> getTools() {
    return toolRegistry.listTools();
}
```

### Async Execution
```java
@Async
public CompletableFuture<String> invokeToolAsync(
        String toolName, 
        Map<String, Object> args) {
    return CompletableFuture.completedFuture(
        invokeTool(toolName, args)
    );
}
```

## 🗺️ Adding More Tools

1. Define tool schema
2. Implement tool service
3. Register in configuration
4. Add handler in controller
5. Update documentation
6. Add tests

## 📚 Resources

- [MCP Specification](https://spec.modelcontextprotocol.io/)
- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

