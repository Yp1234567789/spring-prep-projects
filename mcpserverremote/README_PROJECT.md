# Model Context Protocol (MCP) - Server Implementation (HTTP Remote)

A Spring Boot application implementing the Model Context Protocol (MCP) server with HTTP/Remote protocol for tool and resource management.

## 📋 Overview

This project demonstrates the MCP server implementation with HTTP/Remote protocol, enabling network-based client access to tools and resources through REST endpoints.

## 🚀 Features

- **MCP HTTP Server** - Remote protocol support
- **Tool Registration** - Define and expose custom tools
- **Resource Management** - Manage server-side resources
- **RESTful API** - HTTP-based tool access
- **Auto-Discovery** - Automatic tool discovery
- **Error Handling** - HTTP-compliant error responses
- **DevTools Support** - Hot reload for development

## 📦 Technology Stack

- **Spring Boot** 4.0.3
- **Spring AI** 2.0.0-M2
- **MCP Server** - Model Context Protocol support
- **Spring Web** - REST API
- **Java** 21
- **Maven** 3.6+

## 🏗️ Architecture

```
┌─────────────────────────────────────────┐
│  MCP HTTP Server                        │
│  Spring Boot Application                │
├─────────────────────────────────────────┤
│  REST Controllers                       │
│  - /api/mcp/tools/list                  │
│  - /api/mcp/tools/call                  │
│  - /api/mcp/resources/list              │
├─────────────────────────────────────────┤
│  Tool Definition Registry               │
│  Service Layer                          │
├─────────────────────────────────────────┤
│  HTTP/REST Handler                      │
└─────────────────────────────────────────┘
         ↑ (HTTP)
   ┌─────────────┴──────────────┐
   │                            │
   │ MCP Client                 │
   │ mcpclient:8080             │
```

## 📋 Prerequisites

- Java 21+
- Maven 3.6+
- Network connectivity

## 🔧 Installation & Configuration

### 1. Clone Repository
```bash
git clone <repository-url>
cd mcpserverremote
```

### 2. Configure Application

Edit `src/main/resources/application.properties`:
```properties
# Server
server.port=9090
server.servlet.context-path=/api

# MCP Server Configuration
mcp.server.name=Spring AI HTTP MCP Server
mcp.server.version=1.0.0
mcp.server.description=HTTP-based MCP server

# Logging
logging.level.root=INFO
logging.level.org.springframework.ai.mcp=DEBUG
logging.level.com.test.mcp=DEBUG
```

### 3. Build Application
```bash
mvn clean install
```

## 🚀 Running the Application

### Development Mode
```bash
mvn spring-boot:run
```

### Production Mode
```bash
mvn clean package
java -jar target/mcpserverremote-0.0.1-SNAPSHOT.jar
```

Server will start on `http://localhost:9090/api`

## 📡 API Endpoints

### List Available Tools
```
GET /api/mcp/tools/list
GET /api/mcp/tools
```

**Response:**
```json
{
  "tools": [
    {
      "name": "get_time",
      "description": "Get current time",
      "inputSchema": {
        "type": "object",
        "properties": {
          "timezone": { "type": "string" }
        }
      }
    }
  ]
}
```

### Invoke Tool
```
POST /api/mcp/tools/call
Content-Type: application/json
{
  "name": "get_time",
  "arguments": {
    "timezone": "UTC"
  }
}
```

**Response:**
```json
{
  "toolName": "get_time",
  "result": "2026-03-16 14:30:45 UTC",
  "executionTime": 123,
  "success": true
}
```

### List Resources
```
GET /api/mcp/resources/list
GET /api/mcp/resources
```

### Get Resource
```
GET /api/mcp/resources/{resourceId}
```

### Health Check
```
GET /api/mcp/health
```

### Server Information
```
GET /api/mcp/info
```

## 💡 Built-in Tools

### Tool 1: Current Time
```
POST /api/mcp/tools/call
{
  "name": "get_current_time",
  "arguments": {
    "timezone": "UTC"
  }
}
```

### Tool 2: Add Numbers
```
POST /api/mcp/tools/call
{
  "name": "add_numbers",
  "arguments": {
    "a": 10,
    "b": 20
  }
}
```

### Tool 3: String Manipulation
```
POST /api/mcp/tools/call
{
  "name": "concat_strings",
  "arguments": {
    "str1": "Hello",
    "str2": "World"
  }
}
```

## 💡 Code Examples

### Tool Definition
```java
@Configuration
public class ToolConfiguration {
    
    @Bean
    public ToolRegistry toolRegistry() {
        ToolRegistry registry = new ToolRegistry();
        
        registry.register(
            "get_current_time",
            "Get current time in specified timezone",
            buildTimeToolSchema()
        );
        
        return registry;
    }

    private Map<String, Object> buildTimeToolSchema() {
        return Map.of(
            "type", "object",
            "properties", Map.of(
                "timezone", Map.of(
                    "type", "string",
                    "description", "Timezone (e.g., UTC, EST)"
                )
            ),
            "required", List.of("timezone")
        );
    }
}
```

### Tool Service
```java
@Service
public class ToolService {
    
    public Object invokeTool(String toolName, Map<String, Object> args) {
        switch (toolName) {
            case "get_current_time":
                return getCurrentTime((String) args.get("timezone"));
            case "add_numbers":
                return addNumbers(
                    (Number) args.get("a"),
                    (Number) args.get("b")
                );
            default:
                throw new ToolNotFoundException(toolName);
        }
    }

    private String getCurrentTime(String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);
        return LocalDateTime.now(zoneId).toString();
    }

    private Number addNumbers(Number a, Number b) {
        return a.doubleValue() + b.doubleValue();
    }
}
```

### REST Controller
```java
@RestController
@RequestMapping("/mcp/tools")
@CrossOrigin
public class ToolController {
    
    private final ToolService toolService;
    private final ToolRegistry toolRegistry;

    @GetMapping("/list")
    public Map<String, Object> listTools() {
        return Map.of("tools", toolRegistry.getAllTools());
    }

    @PostMapping("/call")
    public ToolInvocationResult invokeTool(
            @RequestBody ToolInvocation invocation) {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = toolService.invokeTool(
                invocation.getName(),
                invocation.getArguments()
            );
            
            return ToolInvocationResult.builder()
                .toolName(invocation.getName())
                .result(result)
                .executionTime(System.currentTimeMillis() - startTime)
                .success(true)
                .build();
        } catch (Exception e) {
            return ToolInvocationResult.builder()
                .toolName(invocation.getName())
                .error(e.getMessage())
                .executionTime(System.currentTimeMillis() - startTime)
                .success(false)
                .build();
        }
    }

    @GetMapping("/{toolName}")
    public ToolDefinition getTool(@PathVariable String toolName) {
        return toolRegistry.getTool(toolName)
            .orElseThrow(() -> new ToolNotFoundException(toolName));
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
curl http://localhost:9090/api/mcp/tools/list

# Call tool
curl -X POST http://localhost:9090/api/mcp/tools/call \
  -H "Content-Type: application/json" \
  -d '{
    "name": "get_current_time",
    "arguments": {"timezone": "UTC"}
  }'

# Health check
curl http://localhost:9090/api/mcp/health

# Server info
curl http://localhost:9090/api/mcp/info
```

### Testing from Client
```bash
# Terminal 1: Start server
cd mcpserverremote
mvn spring-boot:run

# Terminal 2: Start client pointing to this server
cd ../mcpclient
export MCP_SERVER_URL=http://localhost:9090
mvn spring-boot:run

# Terminal 3: Test
curl http://localhost:8080/api/mcp/tools
```

## 🔐 Security

- Implement API authentication (API keys, OAuth)
- Use HTTPS in production
- Validate all input parameters
- Implement rate limiting
- Log all tool invocations
- Use CORS carefully
- Sanitize tool outputs
- Implement authorization checks

### Example: Add API Key Auth
```java
@Component
public class APIKeyFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String apiKey = request.getHeader("X-API-Key");
        if (!isValidApiKey(apiKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}
```

## 🐛 Troubleshooting

### Cannot Connect from Client
```bash
# Verify server is running
curl http://localhost:9090/api/mcp/health

# Check firewall settings
netstat -an | grep 9090
```

### Tool Not Found
- Verify tool name in request
- Check tool registration
- Review server logs

### Timeout Issues
- Check tool execution time
- Increase request timeout
- Monitor server resources

## 📈 Performance Optimization

### Response Caching
```properties
spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=100,expireAfterWrite=10m
```

### Async Processing
```java
@PostMapping("/tools/call/async")
public CompletableFuture<ToolInvocationResult> invokeToolAsync(
        @RequestBody ToolInvocation invocation) {
    return CompletableFuture.supplyAsync(() ->
        invokeTool(invocation)
    );
}
```

## 📚 Resources

- [MCP Specification](https://spec.modelcontextprotocol.io/)
- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [Spring Boot REST Documentation](https://spring.io/guides/gs/rest-service/)

## 🗺️ Next Steps

1. Add more sophisticated tools
2. Implement tool chaining
3. Add persistence layer
4. Implement caching strategy
5. Add monitoring and metrics
6. Deploy to production infrastructure

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** March 2026

