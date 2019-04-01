package org.mendybot.android.todo.model;

import org.mendybot.android.todo.model.domain.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TodoModel {
    private static TodoModel singleton;
    private final List<DummyItem> itemsL = new ArrayList<DummyItem>();
    private final Map<String, DummyItem> itemsM = new HashMap<String, DummyItem>();
    static final int COUNT = 25;

    private TodoModel() {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private void addItem(DummyItem item) {
        itemsL.add(item);
        itemsM.put(item.id, item);
    }

    private DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "ToDo " + position, makeDetails(position));
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

    public List<DummyItem> getItems() {
        return itemsL;
    }

    public DummyItem lookupItem(String id) {
        return itemsM.get(id);
    }
}
