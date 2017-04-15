package ru.irfr.Model.Test;

import android.os.Parcel;
import android.os.Parcelable;

public class TestStatus implements Parcelable {
    private int id;
    private int selectedAnswer;
    private int correctAnswer;
    private boolean trueAnswer;
    private int selectableAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int isSelectableAnswer() {
        return selectableAnswer;
    }

    public void setSelectableAnswer(int selectableAnswer) {
        this.selectableAnswer = selectableAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.selectedAnswer);
        dest.writeInt(this.correctAnswer);
        dest.writeByte(this.trueAnswer ? (byte) 1 : (byte) 0);
        dest.writeInt(this.selectableAnswer);
    }

    public TestStatus() {
    }

    protected TestStatus(Parcel in) {
        this.id = in.readInt();
        this.selectedAnswer = in.readInt();
        this.correctAnswer = in.readInt();
        this.trueAnswer = in.readByte() != 0;
        this.selectableAnswer = in.readInt();
    }

    public static final Creator<TestStatus> CREATOR = new Creator<TestStatus>() {
        @Override
        public TestStatus createFromParcel(Parcel source) {
            return new TestStatus(source);
        }

        @Override
        public TestStatus[] newArray(int size) {
            return new TestStatus[size];
        }
    };
}
