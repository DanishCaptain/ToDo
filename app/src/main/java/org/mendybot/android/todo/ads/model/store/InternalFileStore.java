package org.mendybot.android.todo.ads.model.store;

import android.app.Activity;
import android.content.Context;

import org.mendybot.android.todo.ads.model.ModelException;
import org.mendybot.android.todo.model.TodoModel;
import org.mendybot.android.todo.model.domain.Todo;
import org.mendybot.android.todo.model.domain.TodoType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.UUID;

public class InternalFileStore extends Store {
    private static final String TOKEN  = "\u0002";
    private static final String TOKEN2 = "\u0003";
    private String fileName = "ToDo.dat";

    @Override
    public void dump(Activity activity, TodoModel model) throws ModelException {
        try {
            PrintWriter pw = new PrintWriter(activity.openFileOutput(fileName, Context.MODE_PRIVATE));
            for (Todo item : model.getItems()) {
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
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void save(Activity activity, TodoModel model, Todo todo) throws ModelException {
        // we save them all
        dump(activity, model);
    }

    @Override
    public void load(Activity activity, TodoModel model) throws ModelException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(activity.openFileInput(fileName)));
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
                    model.addItem(todo);
                }
                br.close();
                if (model.getItems().size() == 0) {
                    drop(activity);
                }
            }

        } catch (FileNotFoundException e) {
            // acceptable here
        } catch (IOException e) {
            throw new ModelException(e);
        } catch (Exception e) {
            throw new ModelException(e);
        }

    }

    @Override
    public void drop(Activity activity) throws ModelException {
        try {
            activity.deleteFile(fileName);
        } catch (Exception ex) {
            throw new ModelException(ex);
        }
    }

    private String makeSafe(String string) {
        return string.replaceAll("\n", TOKEN2);
    }

}
