package ru.irfr.Model;

public class Delete {
    private int id;
    private int chapterId;

    public Delete(int id, int chapterId) {
        this.id = id;
        this.chapterId = chapterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}

