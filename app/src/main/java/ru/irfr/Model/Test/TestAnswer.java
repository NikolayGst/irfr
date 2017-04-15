
package ru.irfr.Model.Test;

import android.os.Parcel;
import android.os.Parcelable;

public class TestAnswer implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.num);
        dest.writeString(this.text);
    }

    public TestAnswer() {
    }

    protected TestAnswer(Parcel in) {
        this.num = in.readInt();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<TestAnswer> CREATOR = new Parcelable.Creator<TestAnswer>() {
        @Override
        public TestAnswer createFromParcel(Parcel source) {
            return new TestAnswer(source);
        }

        @Override
        public TestAnswer[] newArray(int size) {
            return new TestAnswer[size];
        }
    };
}
