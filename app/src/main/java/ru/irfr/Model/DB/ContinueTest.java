package ru.irfr.Model.DB;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ContinueTest extends RealmObject {

    private String code;
    private int id;
    private long time;
    private RealmList<Question> testList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public RealmList<Question> getTestList() {
        return testList;
    }

    public void setTestList(RealmList<Question> testList) {
        this.testList = testList;
    }
}
