package org.mendybot.android.todo.model.domain;

import java.util.UUID;

public class Todo {
    private final UUID id;
    private TodoType todoType;
    private String task;
    private String details;
    private int priority = 50;
    private boolean completed = false;

    public Todo(UUID id, TodoType todoType, String task, String details) {
        this.id = id;
        this.todoType = todoType;
        this.task = task;
        this.details = details;
    }

    public UUID getId() {
        return id;
    }

    public TodoType getTodoType() {
        return todoType;
    }

    public void setTodoType(TodoType todoType) {
        this.todoType = todoType;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return todoType +":"+task;
    }

}
