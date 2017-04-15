package ru.irfr.Model.DB;

import io.realm.RealmObject;

public class Share extends RealmObject {

    private boolean vkShare;
    private boolean fbShare;
    private boolean twitterShare;
    private int socialPoint;

    public boolean isVkShare() {
        return vkShare;
    }

    public void setVkShare(boolean vkShare) {
        this.vkShare = vkShare;
    }

    public boolean isFbShare() {
        return fbShare;
    }

    public void setFbShare(boolean fbShare) {
        this.fbShare = fbShare;
    }

    public boolean isTwitterShare() {
        return twitterShare;
    }

    public void setTwitterShare(boolean twitterShare) {
        this.twitterShare = twitterShare;
    }

    public void setSocialPoint(int socialPoint) {
        this.socialPoint = socialPoint;
    }

    public int getSocialPoint() {
        return socialPoint;
    }
}
