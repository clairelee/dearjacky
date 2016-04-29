package com.dearjacky;

import android.support.annotation.NonNull;

import com.alorma.timeline.TimelineView;

class Events {
    private String name;
    private int type;
    private int alignment;
    private int mood;
    public final int EXCITED = 0;
    public final int CALM = 1;
    public final int DEPRESSED = 2;
    public final int ANGRY = 3;

//    public Events(@NonNull String name) {
//        this(name, TimelineView.TYPE_DEFAULT);
//    }

    public Events(@NonNull String name, int type, int mood) {
        this(name, type, mood, TimelineView.ALIGNMENT_DEFAULT);
    }

    public Events(@NonNull String name, int type, int mood, int alignment) {
        this.name = name;
        this.type = type;
        this.mood = mood;
        this.alignment = alignment;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setMood(int mood){this.mood = mood;}

    public int getMood(){return this.mood;}

    public @TimelineView.TimelineType int getType() {
        return type;
    }

    public void setType(@TimelineView.TimelineType int type) {
        this.type = type;
    }

    public @TimelineView.TimelineAlignment int getAlignment() {
        return alignment;
    }

    public void setAlignment(@TimelineView.TimelineAlignment int alignment) {
        this.alignment = alignment;
    }

    @Override public String toString() {
        return "Events{" +
            "name='" + name + '\'' +
            ", type=" + type +
            ", alignment=" + alignment +
            '}';
    }
}