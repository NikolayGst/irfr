package ru.irfr.Activities;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import ru.irfr.R;
import ru.irfr.Utils.Utils;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity implements Utils.OnTimeSplashListener {

    @AfterViews
    void init() {
        Utils.timeSplash(this);
    }

    @Override
    public void onTimeCompleted() {
        MainActivity_.intent(this).start();
        finish();
    }
}
