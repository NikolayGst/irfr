package ru.irfr.Model.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    @SerializedName("Questions")
    @Expose
    private List<QuestionsSubject> questions = new ArrayList<QuestionsSubject>();

    public List<QuestionsSubject> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsSubject> questions) {
        this.questions = questions;
    }
}
