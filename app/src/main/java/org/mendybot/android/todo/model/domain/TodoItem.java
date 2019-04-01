package org.mendybot.android.todo.model.domain;

import java.util.UUID;

public class TodoItem {
    private final UUID id;
    private TaskType taskType;
    private String task;
    private String details;

    public TodoItem(UUID id, TaskType taskType, String task, String details) {
        this.id = id;
        this.taskType = taskType;
        this.task = task;
        this.details = details;
    }

    public UUID getId() {
        return id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
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

    @Override
    public String toString() {
        return taskType+":"+task;
    }

}
