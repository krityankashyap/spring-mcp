# SpringMCP — Todo Management MCP Server

A Spring Boot application that exposes a Todo management system as an **MCP (Model Context Protocol) Server**. AI assistants like Claude can connect to this server and manage todos through structured tool calls.

---

## What is MCP?

The [Model Context Protocol](https://modelcontextprotocol.io) is an open standard that lets AI models interact with external tools and data sources in a structured, secure way. This project implements an MCP server using [Spring AI](https://docs.spring.io/spring-ai/reference/), making todo operations available as callable tools for any MCP-compatible client.

---

## Features

- Create todos with title, description, and priority (LOW / MEDIUM / HIGH)
- List all todos
- Retrieve a specific todo by ID
- Filter todos by status (PENDING / IN_PROGRESS / COMPLETED) or priority
- Fully synchronous MCP server over Server-Sent Events (SSE)
- Zero-config in-memory storage — no database required

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 24 |
| Framework | Spring Boot 4.1.0 |
| MCP Integration | Spring AI MCP Server (WebMVC) 2.0.0 |
| Build Tool | Gradle 9.5+ |
| Boilerplate reduction | Lombok |

---

## Project Structure

```
SpringMCP/
├── src/main/java/com/example/SpringMCP/
│   ├── SpringMcpApplication.java      # Application entry point
│   ├── config/
│   │   └── MCPServerConfig.java       # Registers tools with the MCP server
│   ├── models/
│   │   └── Todo.java                  # Todo entity with Status & Priority enums
│   ├── services/
│   │   └── TodoServices.java          # Business logic & in-memory storage
│   └── tools/
│       └── TodoManagementTool.java    # @Tool methods exposed via MCP
└── src/main/resources/
    └── application.properties         # Server name, port, SSE endpoint config
```

---

## MCP Tools

These are the tools exposed to MCP clients:

### `createTodo`
Creates a new todo item.

| Parameter | Type | Description |
|-----------|------|-------------|
| `title` | String | Short title for the todo |
| `description` | String | Detailed description |
| `priority` | String | `HIGH`, `MEDIUM`, or `LOW` |

Returns the UUID of the created todo on success.

---

### `listAllTodos`
Returns all todos currently stored. No parameters required.

---

### `getTodoById`
Fetches a single todo by its UUID.

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | String | UUID of the todo to retrieve |

---

## Todo Model

```java
Todo {
    id          String          // Auto-generated UUID
    title       String
    description String
    priority    Priority        // LOW | MEDIUM | HIGH  (default: MEDIUM)
    status      Status          // PENDING | IN_PROGRESS | COMPLETED  (default: PENDING)
    createdAt   LocalDateTime
    updatedAt   LocalDateTime
}
```

---

## Getting Started

### Prerequisites

- Java 24+
- Gradle 9.5+ (or use the included `./gradlew` wrapper)

### Run the Server

```bash
./gradlew bootRun
```

The MCP server starts on **port 8080** and listens for SSE connections at:

```
http://localhost:8080/mcp/sse/messages
```

### Build a JAR

```bash
./gradlew build
java -jar build/libs/SpringMCP-*.jar
```

---

## Configuration

All settings live in `src/main/resources/application.properties`:

```properties
spring.application.name=SpringMCP
spring.ai.mcp.server.name=todo-management-server
spring.ai.mcp.server.version=1.0.0
spring.ai.mcp.server.type=SYNC
spring.ai.mcp.server.sse-message-endpoint=/mcp/sse/messages
server.port=8080
```

---

## Connecting Claude to This Server

Add the following to your Claude Desktop `claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "todo-management": {
      "url": "http://localhost:8080/mcp/sse/messages",
      "transport": "sse"
    }
  }
}
```

Once connected, Claude can call `createTodo`, `listAllTodos`, and `getTodoById` directly in conversation.

---

## Architecture

```
MCP Client (Claude)
       │
       │  SSE / MCP Protocol
       ▼
TodoManagementTool   ← @Tool methods registered via MCPServerConfig
       │
       ▼
TodoServices         ← Business logic
       │
       ▼
HashMap<String, Todo>  ← In-memory store (resets on restart)
```

---

## Limitations

- **In-memory storage only** — all todos are lost on application restart.
- No update or delete tools yet (only create, list, get).
- No authentication on the MCP endpoint.
- Minimal test coverage (context-load test only).

---

## Roadmap

- [ ] Persist todos with JPA + H2/PostgreSQL
- [ ] Add `updateTodoStatus` and `deleteTodo` MCP tools
- [ ] Add unit and integration tests
- [ ] Secure the SSE endpoint
- [ ] Docker support
