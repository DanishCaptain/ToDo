package org.mendybot.android.todo.model;

import org.mendybot.android.todo.model.domain.TaskType;
import org.mendybot.android.todo.model.domain.TodoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class TodoModel {
    private static TodoModel singleton;
    private final List<TodoItem> itemsL = new ArrayList<>();
    private final Map<UUID, TodoItem> itemsM = new HashMap<>();
    static final int COUNT = 25;

    private TodoModel() {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createTodoItem(i));
        }
    }

    private void addItem(TodoItem item) {
        itemsL.add(item);
        itemsM.put(item.getId(), item);
    }

    private TodoItem createTodoItem(int position) {
        return new TodoItem(UUID.randomUUID(), TaskType.SHOPPING, "ToDo " + position, makeDetails(position));
    }

    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public synchronized static TodoModel getInstance() {
        if (singleton == null) {
            singleton = new TodoModel();
        }
        return singleton;
    }

    public List<TodoItem> getItems() {
        return itemsL;
    }

    public TodoItem lookupItem(String id) {
        return itemsM.get(id);
    }
}
