package com.example.SpringMCP.tools;

import java.util.List;
import java.util.Optional;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.example.SpringMCP.models.Todo;
import com.example.SpringMCP.services.TodoServices;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class TodoManagementTool {
     
  private final TodoServices todoServices;

  @Tool(description = "Create a new todo with title, description and priority as HIGH, MEDIUM or LOW")
  public String createTodo(
    @ToolParam(description = "Title of the todo") String title,
    @ToolParam(description = "Description of the todo") String description,
    @ToolParam(description = "Priority of the todo") String priority
  ) {
      Todo.Priority p;
      try{
        p= Todo.Priority.valueOf(priority.toUpperCase());
      } catch(Exception e){
        return "Invalid priority. Please use HIGH, MEDIUM or LOW.";
      }

      Todo todo= todoServices.createTodo(title, description, p);
      return "Todo created with ID: " + todo.getId();
  }
  @Tool(description = "List of all todos in the system, including their ID, title, description, priority and status")
  public String listAllTodos(){
    List<Todo> todos= todoServices.getAllTodos();

    if(todos.isEmpty()){
      return "No todos found";
    }
    StringBuilder sb= new StringBuilder();
    for(Todo todo: todos){
      sb.append(todo.toString()).append("\n");
    }
    return sb.toString();
  }

  
  @Tool(description = "Get a todo by its ID , including its title, description, priority and status")
  public String getTodoById(
    @ToolParam(description = "ID of the todo") String id
  ) {
    Optional<Todo> todo = todoServices.getTodoById(id);
    if (todo == null) {
      return "Todo not found with ID: " + id;
    }
    return todo.toString();
  }

}
