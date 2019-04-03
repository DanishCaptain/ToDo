package org.mendybot.android.todo.ads.model;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ModelException extends Throwable {
    private final Throwable t;

    public ModelException(Throwable t) {
        super(t);
        this.t = t;
    }

    @Override
    public void printStackTrace() {
        t.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream ps) {
        t.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
        t.printStackTrace();
    }
}
