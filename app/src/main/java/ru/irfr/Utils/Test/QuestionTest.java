
package ru.irfr.Utils.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionTest {

    @SerializedName("chapterid")
    @Expose
    private int chapterid;
    @SerializedName("weight")
    @Expose
    private Weight weight;

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

}
