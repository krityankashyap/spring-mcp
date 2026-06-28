package com.example.SpringMCP.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class Todo {
  
  public enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED
  };

  public enum Priority {
    LOW,
    MEDIUM,
    HIGH
  }

  private String id;

  private String title;

  private String description;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Status staus;

  private Priority priority;

  public Todo() {
    this.id= UUID.randomUUID().toString();
    this.createdAt= LocalDateTime.now();
    this.updatedAt= LocalDateTime.now();
    this.staus= Status.PENDING;
    this.priority= Priority.MEDIUM;
  }

  public Todo(String title, String description, Status status, Priority priority) {
    this.title= title;
    this.description= description;
    this.staus= status;
    this.priority= priority;
}
  
   public String toString() {
    return "Todo [id=" + id + ", title=" + title + ", description=" + description + ", createdAt=" + createdAt
        + ", updatedAt=" + updatedAt + ", status=" + staus + ", priority=" + priority + "]";
   }
}