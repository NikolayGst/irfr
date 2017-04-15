
package ru.irfr.Utils.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GeneratedTest {

    @SerializedName("Questions")
    @Expose
    private List<QuestionTest> questions = new ArrayList<>();

    public List<QuestionTest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionTest> questions) {
        this.questions = questions;
    }

}
