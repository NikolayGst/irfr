package ru.irfr.Model.DB;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Result extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private long duration;
    private int point;
    private int type;
    private Date date;
    private RealmList<Question> mQuestions = new RealmList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(RealmList<Question> questions) {
        mQuestions = questions;
    }
}
