package ru.irfr.Fragments.Discount.View;

import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import fr.tvbarthel.intentshare.IntentShare;
import fr.tvbarthel.intentshare.IntentShareListener;
import io.realm.Realm;
import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.SendResult.SendResultFragment;
import ru.irfr.Fragments.SendResult.SendResultFragment_;
import ru.irfr.Model.DB.Result;
import ru.irfr.Model.DB.Share;
import ru.irfr.Model.Tag;
import ru.irfr.R;

@EFragment(R.layout.fragment_get_discount)
public class GetDiscountFragment extends BaseFragment implements View.OnClickListener {

    @StringRes(R.string.get_discount)
    String Discount;

    @ViewById
    android.support.v7.widget.SwitchCompat switchFacebook;

    @ViewById
    android.support.v7.widget.SwitchCompat switchTwitter;

    @ViewById
    android.support.v7.widget.SwitchCompat switchVk;

    @ViewById
    TextView getDiscount;

    @ViewById
    TextView txtPrice;

    private Realm mRealm;
    int price;
    int point;
    private Share mShare;

    @AfterViews
    void init() {

        setTitle(Discount);

        initRealm();

        mShare = mRealm.where(Share.class).findFirst();

        if (mShare == null) createTable();

        //TODO настроить систему скидок(если 80 баллов = +10 к общим баллам)

        //  point = mShare.getSocialPoint() + getPoint();

        point = mShare.getSocialPoint();

     //   if (point >= 100) point = 100;
        //price = point * 10;

        price = point + getPriceFromExamResult();

        if (price >= 100) price = 100;

        txtPrice.setText(String.format("%d руб.", price));

        switchVk.setOnClickListener(this);
        switchTwitter.setOnClickListener(this);
        switchFacebook.setOnClickListener(this);
    }

    private void initRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    private int getPriceFromExamResult() {
        int price = 0;
        List<Result> resultList = mRealm.where(Result.class).findAll();
        for (Result result :
                resultList) {
            if (result.getPoint() >= 80) price += 10;
        }
        return price;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mShare.isVkShare()) switchVk.setChecked(true);
        else switchVk.setChecked(false);
        if (mShare.isFbShare()) {
            switchFacebook.setChecked(true);
        }   else switchFacebook.setChecked(false);
        if (mShare.isTwitterShare()) {
            switchTwitter.setChecked(true);
        } else switchTwitter.setChecked(false);
    }

    @Click(R.id.getDiscount)
    void goToSendResultFragment(){
        SendResultFragment sendResultFragment = SendResultFragment_.builder().build();
        showFragment(sendResultFragment);
    }

    private void shareVk() {
        IntentShare.with(getContext())
                .chooserTitle("Поделиться в ВК")
                .text("IRFR - бесплатное приложение для подготовки к тестированию")
                .listener(new IntentShareListener() {
                    @Override
                    public void onCompleted(String s) {
                        setCheck(Tag.VK);
                        setSocPoint(25);
                        updateDiscount();
                    }

                    @Override
                    public void onCanceled() {
                        setUnCheck(Tag.VK);
                    }
                })
                .deliver();
    }

    private void shareFb() {
        IntentShare.with(getContext())
                .chooserTitle("Поделиться в Facebook")
                .text("IRFR - бесплатное приложение для подготовки к тестированию")
                .listener(new IntentShareListener() {
                    @Override
                    public void onCompleted(String s) {
                        setCheck(Tag.FB);
                        setSocPoint(25);
                        updateDiscount();
                    }

                    @Override
                    public void onCanceled() {
                        setUnCheck(Tag.FB);
                    }
                })
                .deliver();
    }

    private void shareTwitter() {
        IntentShare.with(getContext())
                .chooserTitle("Поделиться в Twitter")
                .text("IRFR - бесплатное приложение для подготовки к тестированию")
                .listener(new IntentShareListener() {
                    @Override
                    public void onCompleted(String s) {
                        setCheck(Tag.TWITTER);
                        setSocPoint(25);
                        updateDiscount();
                    }

                    @Override
                    public void onCanceled() {
                        setUnCheck(Tag.TWITTER);
                    }
                })
                .deliver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchVk:
                if (!mShare.isVkShare()) {
                    shareVk();
                } else {
                    switchVk.setChecked(true);
                }
                break;
            case R.id.switchFacebook:
                if (!mShare.isFbShare()) {
                    shareFb();
                } else {
                    switchFacebook.setChecked(true);
                }
                break;
            case R.id.switchTwitter:
                if (!mShare.isTwitterShare()) {
                    shareTwitter();
                } else {
                    switchTwitter.setChecked(true);
                }
                break;
        }
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
                switchVk.setChecked(false);
                break;
            case FB:
                mShare.setFbShare(false);
                switchFacebook.setChecked(false);
                break;
            case TWITTER:
                mShare.setTwitterShare(false);
                switchTwitter.setChecked(false);
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

    private void updateDiscount() {
        point = mShare.getSocialPoint();
        //TODO настроить систему скидок
    //    if (point >= 100) point = 100;
        price = point;
        txtPrice.setText(String.format("%d руб.", price));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) mRealm.close();
    }
}
