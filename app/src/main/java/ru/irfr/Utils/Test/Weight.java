
package ru.irfr.Utils.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weight {

    @SerializedName("oneLength")
    @Expose
    private int oneLength;
    @SerializedName("twoLength")
    @Expose
    private int twoLength;

    public int getOneLength() {
        return oneLength;
    }

    public void setOneLength(int oneLength) {
        this.oneLength = oneLength;
    }

    public int getTwoLength() {
        return twoLength;
    }

    public void setTwoLength(int twoLength) {
        this.twoLength = twoLength;
    }

}
