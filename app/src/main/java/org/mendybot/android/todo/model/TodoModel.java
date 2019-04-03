package org.mendybot.android.todo.model;

import android.app.Activity;
import android.content.Context;

import org.mendybot.android.todo.model.domain.TodoType;
import org.mendybot.android.todo.model.domain.Todo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

public final class TodoModel {
    private static final String TOKEN = "\u0002";
    private static final String TOKEN2 = "\u0003";
    private static TodoModel singleton;
    private final List<Todo> itemsL = new ArrayList<>();
    private final Map<UUID, Todo> itemsM = new HashMap<>();
    static final int COUNT = 25;
    private Activity activity;

    private TodoModel() {
    }

    public void addItem(Todo item) {
        itemsL.add(item);
        itemsM.put(item.getId(), item);
    }

    public void deleteItem(Todo item) {
        itemsL.remove(item);
        itemsM.remove(item.getId());
    }

    /*
    private Todo createTodoItem(int position) {
        return new Todo(UUID.randomUUID(), TodoType.SHOPPING, "ToDo " + position, makeDetails(position));
    }
    */

    /*
    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
    */

    public List<Todo> getItems() {
        return itemsL;
    }

    public Todo lookupItem(String uuid) {
        return itemsM.get(UUID.fromString(uuid));
    }

    public Todo lookupItem(UUID uuid) {
        return itemsM.get(uuid);
    }

    public void init(Activity activity) {
        this.activity = activity;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(activity.openFileInput("ToDo.dat")));
            String line;
            if (br != null) {
                while ((line = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(line, TOKEN);
                    UUID id = UUID.fromString(st.nextToken());
                    TodoType todoType = TodoType.valueOf(st.nextToken());
                    String task = st.nextToken();
                    String details = st.nextToken();
                    details = details.replaceAll(TOKEN2, "\n");
                    int priority = Integer.parseInt(st.nextToken());
                    boolean completed = Boolean.parseBoolean(st.nextToken());
                    Todo todo = new Todo(id, todoType, task, details);
                    todo.setPriority(priority);
                    todo.setCompleted(completed);
                    addItem(todo);
                }
                br.close();
                if (itemsL.size() == 0) {
                    activity.deleteFile("ToDo.dat");
                }
            }

        } catch (FileNotFoundException e) {
            /*
            for (int i = 1; i <= COUNT; i++) {
                addItem(createTodoItem(i));
            }
            dumpToStore();
            */
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            activity.deleteFile("ToDo.dat");
        }
    }

    public void save() {
        dumpToStore();
    }

    public void save(Todo todo) {
        dumpToStore();
    }

    private void dumpToStore() {
        if (activity != null) {
            try {
                PrintWriter pw = new PrintWriter(activity.openFileOutput("ToDo.dat", Context.MODE_PRIVATE));
                for (Todo item : itemsL) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(TOKEN);
                    sb.append(item.getId());
                    sb.append(TOKEN);
                    sb.append(item.getTodoType().toString());
                    sb.append(TOKEN);
                    sb.append(item.getTask());
                    sb.append(TOKEN);
                    sb.append(makeSafe(item.getDetails()));
                    sb.append(TOKEN);
                    sb.append(Integer.toString(item.getPriority()));
                    sb.append(TOKEN);
                    sb.append(Boolean.toString(item.isCompleted()));
                    sb.append(TOKEN);
                    pw.println(sb.toString());
                }
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeSafe(String string) {
        return string.replaceAll("\n", TOKEN2);
    }

    public synchronized static TodoModel getInstance() {
        if (singleton == null) {
            singleton = new TodoModel();
        }
        return singleton;
    }

}
