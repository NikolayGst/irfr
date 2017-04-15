package ru.irfr.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.irfr.Fragments.Discount.View.GetDiscountFragment;
import ru.irfr.Fragments.Discount.View.GetDiscountFragment_;
import ru.irfr.Fragments.Exam.View.ExamSelectedFragment;
import ru.irfr.Fragments.Exam.View.ExamSelectedFragment_;
import ru.irfr.Fragments.Irfr.View.IrfrFragment;
import ru.irfr.Fragments.Irfr.View.IrfrFragment_;
import ru.irfr.Fragments.Main.View.MainFragment;
import ru.irfr.Fragments.Main.View.MainFragment_;
import ru.irfr.Fragments.Result.View.ResultFragment;
import ru.irfr.Fragments.Result.View.ResultFragment_;
import ru.irfr.Fragments.Rules.View.RulesFragment;
import ru.irfr.Fragments.Rules.View.RulesFragment_;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Interface.OnFragmentClickListener;
import ru.irfr.R;
import ru.irfr.Utils.Utils;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements OnFragmentClickListener {

    @ViewById(R.id.drawer_layout) DrawerLayout drawer;
    @ViewById Toolbar toolbar;
    @ViewById TextView titleMainToolbar;
    @ViewById TextView btnStop;
    @ViewById TextView txtTime;
    @ViewById LinearLayout lrTime;

    private MainFragment mMainFragment;
    private ExamSelectedFragment mExamSelectedFragment;
    private ResultFragment mResultFragment;
    private RulesFragment mRulesFragment;
    private IrfrFragment mIrfrFragment;
    private GetDiscountFragment mGetDiscountFragment;

    @AfterViews
    void init() {

        initDrawerAndToolbar();

        initFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mMainFragment).commit();

    }

    private void initDrawerAndToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFragment() {
        mMainFragment = MainFragment_.builder().build();
        mMainFragment.setOnFragmentClickListener(this);
        mExamSelectedFragment = ExamSelectedFragment_.builder().build();
        mResultFragment = ResultFragment_.builder().build();
        mRulesFragment = RulesFragment_.builder().build();
        mIrfrFragment = IrfrFragment_.builder().build();
        mGetDiscountFragment = GetDiscountFragment_.builder().build();
    }

    @Click(R.id.btnMainNav)
    void goToMain() {
        showMain();
        closeDrawerForMain();
    }

    @Click(R.id.btnExamNav)
    void goToExam() {
        showFragment(mExamSelectedFragment);
        closeDrawer();
    }

    @Click(R.id.btnResultNav)
    void goToResult() {
        showFragment(mResultFragment);
        closeDrawer();
    }

    @Click(R.id.btnRulesNav)
    void goToRules() {
        showFragment(mRulesFragment);
        closeDrawer();
    }

    @Click(R.id.btnIrfrNav)
    void goToIrfr() {
        showFragment(mIrfrFragment);
        closeDrawer();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("stack")
                .commit();
    }

    private void showMain() {
        finish();
        MainActivity_.intent(this).start();
    }

    @UiThread(delay = 500)
    public void closeDrawerForMain() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        TestSelectedFragment testSelectedFragment = (TestSelectedFragment)
                getSupportFragmentManager().findFragmentByTag("testSelectedFragment");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (testSelectedFragment != null && testSelectedFragment.isStartTest() ||
                testSelectedFragment != null && testSelectedFragment.isStartTestMode()) {
            testSelectedFragment.stopTest();
        } else {
            super.onBackPressed();

        }
    }

    //Обработка нажатий во фрагменте
    @Override
    public void OnClick(Utils.Fragments tag) {
        switch (tag){
            case EXAM:
                showFragment(mExamSelectedFragment);
                break;
            case RESULT:
                showFragment(mResultFragment);
                break;
            case RULES:
                showFragment(mRulesFragment);
                break;
            case IRFR:
                showFragment(mIrfrFragment);
                break;
            case DISCOUNT:
                showFragment(mGetDiscountFragment);
                break;
        }
    }
}
