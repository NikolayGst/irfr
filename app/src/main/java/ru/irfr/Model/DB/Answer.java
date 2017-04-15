
package ru.irfr.Model.DB;

import io.realm.RealmObject;

public class Answer extends RealmObject {

    private int num;
    private String text;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
