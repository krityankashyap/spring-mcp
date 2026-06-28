package com.example.SpringMCP.services;
import com.example.SpringMCP.models.Todo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TodoServices {
  private final Map<String, Todo> todos= new HashMap<>();

  public Todo createTodo(String title, String description, Todo.Priority priority) {
    Todo todo= Todo.builder()
        .title(title)
        .description(description)
        .priority(priority)
        .build();
        
      todos.put(todo.getId(), todo);
      log.info("Created new todo: {}", todo);
      return todo;  
  }

  public Optional<Todo> getTodoById(String id) {
    log.info("Retrieving todo with id: {}", id);
    return Optional.ofNullable(todos.get(id));
  }

  public List<Todo> getAllTodos() {
    log.info("Retrieving all todos");
    return new ArrayList<>(todos.values());
  }

  public List<Todo> getTodosByStatus(Todo.Status status) {
    return todos.values().stream()
           .filter(todo -> todo.getStaus() == status)
           .collect(Collectors.toList());
  }

  public List<Todo> getTodosByPriority(Todo.Priority priority) {
    return todos.values().stream()
            .filter(todo -> todo.getPriority() == priority)
            .collect(Collectors.toList());
  }

  public Optional<Todo> updateStaus(String id, Todo.Status status) {
    Todo todo= todos.get(id);
    if (todo != null) {
      todo.setStaus(status);
      log.info("Updated status of todo with id {}: {}", id, status);
      return Optional.of(todo);
    } else {
      return Optional.empty();
    }
  }
}
