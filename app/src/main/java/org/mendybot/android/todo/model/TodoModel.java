package org.mendybot.android.todo.model;

import android.app.Activity;

import org.mendybot.android.todo.ads.model.ModelException;
import org.mendybot.android.todo.ads.model.store.ExternalFileStore;
import org.mendybot.android.todo.ads.model.store.InternalFileStore;
import org.mendybot.android.todo.ads.model.store.Store;
import org.mendybot.android.todo.model.domain.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class TodoModel {
    private static TodoModel singleton;
    private final List<Todo> itemsL = new ArrayList<>();
    private final Map<UUID, Todo> itemsM = new HashMap<>();
    private final Store store;
    private Activity activity;

    private TodoModel() {
//        store = new InternalFileStore();
        store = new ExternalFileStore();
    }

    public void addItem(Todo item) {
        itemsL.add(item);
        itemsM.put(item.getId(), item);
    }

    public void deleteItem(Todo item) {
        itemsL.remove(item);
        itemsM.remove(item.getId());
    }

    public List<Todo> getItems() {
        return itemsL;
    }

    public Todo lookupItem(String uuid) {
        return itemsM.get(UUID.fromString(uuid));
    }

    public Todo lookupItem(UUID uuid) {
        return itemsM.get(uuid);
    }

    public void init(Activity activity) throws ModelException {
        if (this.activity == null) {
            this.activity = activity;
            store.load(activity, this);
        }
    }

    public void save() throws ModelException {
        dumpToStore();
    }

    public void save(Todo todo) throws ModelException {
        dumpToStore(todo);
    }

    private void dumpToStore() throws ModelException {
        if (activity != null) {
            store.dump(activity, this);
        }
    }

    private void dumpToStore(Todo todo) throws ModelException {
        if (activity != null) {
            store.save(activity, this, todo);
        }
    }

    public synchronized static TodoModel getInstance() {
        if (singleton == null) {
            singleton = new TodoModel();
        }
        return singleton;
    }

}
