package org.mendybot.android.todo.ads.model;

import android.app.Activity;
import android.widget.ImageView;

public abstract class AdHandler {
    public abstract Activity getActivity();
    public abstract ImageView getView();
}
