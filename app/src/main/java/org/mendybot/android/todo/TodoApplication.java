package org.mendybot.android.todo;

import android.app.Application;

import org.mendybot.android.todo.model.TodoModel;

public class TodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TodoModel.getInstance().init(this);
    }
}
