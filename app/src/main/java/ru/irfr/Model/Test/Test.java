
package ru.irfr.Model.Test;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Test implements Parcelable {

    private int id;
    private int weight;
    private int chapterId;
    private String code;
    private String header;
    private List<TestAnswer> testAnswers = new ArrayList<>();
    private int correctnum;
    private int testSelectedAnswer;
    private int selectableAnswer;

    public void setSelectableAnswer(int selectableAnswer) {
        this.selectableAnswer = selectableAnswer;
    }

    public int getSelectableAnswer() {
        return selectableAnswer;
    }

    public int getTestSelectedAnswer() {
        return testSelectedAnswer;
    }

    public void setTestSelectedAnswer(int testSelectedAnswer) {
        this.testSelectedAnswer = testSelectedAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<TestAnswer> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(List<TestAnswer> testAnswers) {
        this.testAnswers = testAnswers;
    }

    public int getCorrectnum() {
        return correctnum;
    }

    public void setCorrectnum(int correctnum) {
        this.correctnum = correctnum;
    }


    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.weight);
        dest.writeInt(this.chapterId);
        dest.writeString(this.code);
        dest.writeString(this.header);
        dest.writeTypedList(this.testAnswers);
        dest.writeInt(this.correctnum);
        dest.writeInt(this.testSelectedAnswer);
        dest.writeInt(this.selectableAnswer);
    }

    public Test() {
    }

    protected Test(Parcel in) {
        this.id = in.readInt();
        this.weight = in.readInt();
        this.chapterId = in.readInt();
        this.code = in.readString();
        this.header = in.readString();
        this.testAnswers = in.createTypedArrayList(TestAnswer.CREATOR);
        this.correctnum = in.readInt();
        this.testSelectedAnswer = in.readInt();
        this.selectableAnswer = in.readInt();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
