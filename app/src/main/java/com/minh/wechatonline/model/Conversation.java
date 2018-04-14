package com.minh.wechatonline.model;

/**
 * Created by HP on 4/13/2018.
 */

public class Conversation {
    private boolean seen;
    private long time;
    public Conversation(){}

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Conversation(boolean seen, long time) {
        this.seen = seen;
        this.time = time;
    }
}
