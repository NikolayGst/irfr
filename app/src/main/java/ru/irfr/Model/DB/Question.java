package ru.irfr.Model.DB;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Question extends RealmObject {
    private int id;
    private int weight;
    private int chapterId;
    private String header;
    private String code;
    private int correctnum;
    private RealmList<Answer> answers = new RealmList<>();
    private int testSelectedAnswer;
    private int selectableAnswer;

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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCorrectnum() {
        return correctnum;
    }

    public RealmList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<Answer> answers) {
        this.answers = answers;
    }

    public void setCorrectnum(int correctnum) {
        this.correctnum = correctnum;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTestSelectedAnswer() {
        return testSelectedAnswer;
    }

    public void setTestSelectedAnswer(int testSelectedAnswer) {
        this.testSelectedAnswer = testSelectedAnswer;
    }

    public int isSelectableAnswer() {
        return selectableAnswer;
    }

    public void setSelectableAnswer(int selectableAnswer) {
        this.selectableAnswer = selectableAnswer;
    }
}
