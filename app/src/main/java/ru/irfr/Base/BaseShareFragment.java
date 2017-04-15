package ru.irfr.Base;

import fr.tvbarthel.intentshare.IntentShare;
import fr.tvbarthel.intentshare.IntentShareListener;
import io.realm.Realm;
import ru.irfr.Model.DB.Share;
import ru.irfr.Model.Tag;

public class BaseShareFragment extends BaseFragment{

    public Realm mRealm;
    public Share mShare;

    public void initShareRealm() {
        mRealm = Realm.getDefaultInstance();
        mShare = mRealm.where(Share.class).findFirst();
        if (mShare == null) createTable();
    }

    public void shareVk() {
        IntentShare.with(getContext())
                .chooserTitle("Поделиться в ВК")
                .text("IRFR - бесплатное приложение для подготовки к тестированию")
                .listener(new IntentShareListener() {
                    @Override
                    public void onCompleted(String s) {
                        setCheck(Tag.VK);
                        setSocPoint(25);
                    }

                    @Override
                    public void onCanceled() {
                        setUnCheck(Tag.VK);
                    }
                })
                .deliver();
    }

    public void shareFb() {
        IntentShare.with(getContext())
                .chooserTitle("Поделиться в Facebook")
                .text("IRFR - бесплатное приложение для подготовки к тестированию")
                .listener(new IntentShareListener() {
                    @Override
                    public void onCompleted(String s) {
                        setCheck(Tag.FB);
                        setSocPoint(25);
                    }

                    @Override
                    public void onCanceled() {
                        setUnCheck(Tag.FB);
                    }
                })
                .deliver();
    }

    public void shareTwitter() {
        IntentShare.with(getContext())
                .chooserTitle("Поделиться в Twitter")
                .text("IRFR - бесплатное приложение для подготовки к тестированию")
                .listener(new IntentShareListener() {
                    @Override
                    public void onCompleted(String s) {
                        setCheck(Tag.TWITTER);
                        setSocPoint(25);
                    }

                    @Override
                    public void onCanceled() {
                        setUnCheck(Tag.TWITTER);
                    }
                })
                .deliver();
    }

    private void setCheck(Tag tag) {
        mRealm.beginTransaction();
        switch (tag) {
            case VK:
                mShare.setVkShare(true);
                break;
            case FB:
                mShare.setFbShare(true);
                break;
            case TWITTER:
                mShare.setTwitterShare(true);
                break;
        }
        mRealm.commitTransaction();
    }

    private void setUnCheck(Tag tag) {
        mRealm.beginTransaction();
        switch (tag) {
            case VK:
                mShare.setVkShare(false);
                break;
            case FB:
                mShare.setFbShare(false);
                break;
            case TWITTER:
                mShare.setTwitterShare(false);
                break;
        }
        mRealm.commitTransaction();
    }

    private void createTable() {
        mRealm.beginTransaction();
        Share share = mRealm.createObject(Share.class);
        share.setVkShare(false);
        share.setFbShare(false);
        share.setTwitterShare(false);
        mRealm.commitTransaction();
        mShare = mRealm.where(Share.class).findFirst();
    }

    private void setSocPoint(int point) {
        mRealm.beginTransaction();
        int old = mShare.getSocialPoint();
        mShare.setSocialPoint(old + point);
        mRealm.commitTransaction();
    }

   /* public void closeShareRealm(){
        if(mRealm != null) mRealm.close();
    }
    */
}
