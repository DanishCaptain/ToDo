package org.mendybot.android.todo.ads.model.store;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.mendybot.android.todo.ads.model.ModelException;
import org.mendybot.android.todo.model.TodoModel;
import org.mendybot.android.todo.model.domain.Todo;
import org.mendybot.android.todo.model.domain.TodoType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExternalFileStore extends Store {
    private int STORAGE_PERMISSION_CODE = 1443;
    private static final String TOKEN  = "\u0002";
    private static final String TOKEN2 = "\u0003";
    private String fileName = "ToDo.dat";

    @Override
    public void dump(Activity activity, TodoModel model) throws ModelException {
        try {
            File dumpFile = getDumpFile(activity);
            PrintWriter pw = new PrintWriter(new FileWriter(dumpFile));
            List<Todo> list = model.getItems();
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String json = gson.toJson(list);
            pw.println(json);
            pw.close();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    private File getDumpFile(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        File folder = Environment.getExternalStorageDirectory();
        return new File(folder, fileName);
    }

    @Override
    public void save(Activity activity, TodoModel model, Todo todo) throws ModelException {
        // we save them all
        dump(activity, model);
    }

    @Override
    public void load(Activity activity, TodoModel model) throws ModelException {
        try {
            File dumpFile = getDumpFile(activity);
            BufferedReader br = new BufferedReader(new FileReader(dumpFile));
            String json = br.readLine();
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            Type listType = new TypeToken<ArrayList<Todo>>(){}.getType();
            List<Todo> list = gson.fromJson(json, listType);
            for (Todo todo: list) {
                model.addItem(todo);
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
            getDumpFile(activity).delete();
        } catch (Exception ex) {
            throw new ModelException(ex);
        }
    }

    private String makeSafe(String string) {
        return string.replaceAll("\n", TOKEN2);
    }

}
