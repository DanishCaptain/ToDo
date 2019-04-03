package org.mendybot.android.todo.ads.model.store;

import android.app.Activity;

import org.mendybot.android.todo.ads.model.ModelException;
import org.mendybot.android.todo.model.TodoModel;
import org.mendybot.android.todo.model.domain.Todo;

public abstract class Store {
    public abstract void dump(Activity activity, TodoModel model) throws ModelException;
    public abstract void save(Activity activity, TodoModel model, Todo todo) throws ModelException;
    public abstract void load(Activity activity, TodoModel model) throws ModelException;
    public abstract void drop(Activity activity) throws ModelException;
}
