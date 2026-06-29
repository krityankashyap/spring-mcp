package com.example.SpringMCP.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.SpringMCP.tools.TodoManagementTool;

@Configuration
public class MCPServerConfig {

  @Bean
  public ToolCallbackProvider toolCallbackProvider(TodoManagementTool todoManagementTool){
    return MethodToolCallbackProvider.builder()
        .toolObjects(todoManagementTool)
        .build();
  }

}
