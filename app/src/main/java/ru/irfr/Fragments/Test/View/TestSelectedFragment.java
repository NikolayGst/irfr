package ru.irfr.Fragments.Test.View;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import ru.irfr.Adapter.TestAdapter;
import ru.irfr.Adapter.TestSelectedAdapter;
import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.Test.Presenter.TestPresenter;
import ru.irfr.Fragments.Test.Presenter.TestPresenterImpl;
import ru.irfr.Interface.OnTabSelectListener;
import ru.irfr.Interface.OnTestListener;
import ru.irfr.Interface.OnTestSelectedListener;
import ru.irfr.Model.Test.Test;
import ru.irfr.Model.Test.TestStatus;
import ru.irfr.R;
import ru.irfr.View.SwipeableViewPager;

@EFragment(R.layout.fragment_test)
public class TestSelectedFragment extends BaseFragment implements TestView, OnTabSelectListener,
        OnTestSelectedListener, View.OnClickListener {

    @FragmentArg
    Integer idExam;
    @FragmentArg
    String code;
    @FragmentArg
    Integer chapterId;
    @FragmentArg
    boolean testMode;
    @FragmentArg
    boolean errorMode;
    @FragmentArg
    Integer idError;
    @FragmentArg
    String testModeName;
    @FragmentArg
    boolean isRandom;
    @FragmentArg
    boolean isContinueTest;
    @ViewById
    SwipeableViewPager viewpager;
    @ViewById
    RecyclerView recyclerTest;
    @StringRes(R.string.txt_exam)
    String exam;
    @StringRes(R.string.error_title)
    String errorTitle;

    private TestPresenter mTestPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TestSelectedAdapter mTestSelectedAdapter;
    private TestAdapter mTestAdapter;
    private OnTestListener<Test> mOnTestListener;

    @AfterViews
    void init() {

        if (errorMode) {
            setTitle(errorTitle);
            viewpager.setSwipe(true);
        }
        else setTitle(exam);

        initToolbarBtn();

        initViewPager();

        initIndicator();

        btnStop.setOnClickListener(this);

        mTestPresenter = new TestPresenterImpl(this);

        if (errorMode) {
            mTestPresenter.loadErrorTest(idError);
            return;
        }

        if (!isContinueTest)
            mTestPresenter.loadTest(code, chapterId, testMode, testModeName, isRandom);
        else mTestPresenter.continueTest(testMode);

    }

    private void initIndicator() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTest.setLayoutManager(mLayoutManager);
        mTestSelectedAdapter = new TestSelectedAdapter(getContext());
        mTestSelectedAdapter.setOnTabSelectListener(this);
        recyclerTest.setAdapter(mTestSelectedAdapter);
    }

    private void initViewPager() {
        if (testMode) viewpager.setSwipe(true);
        mTestAdapter = new TestAdapter(getChildFragmentManager(), this, testMode);
        viewpager.setAdapter(mTestAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTestSelectedAdapter.notifyItem(position);
                recyclerTest.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setClipToPadding(false);
        viewpager.setPadding(32, 16, 32, 0);
        viewpager.setPageMargin(12);
    }

    @Override
    public void onTestLoaded(List<Test> testList) {
        mTestAdapter.setTestList(testList);
        if (isContinueTest) startNextQuestion();
    }

    @Override
    public void onStatusTestLoaded(List<TestStatus> testStatusList) {
        mTestSelectedAdapter.setTestStatusList(testStatusList);
    }

    @Override
    public void onTestContinue(String code, int id) {
        this.idExam = id;
        this.code = code;
    }

    @Override
    public void onTabSelected(int position) {
        viewpager.setCurrentItem(position);
    }

    @Override
    public void onTestAnswerSelected(int id, int selectAnswer, int correctAnswer) {
        int pos = mTestSelectedAdapter.getSelectedPos();
        updateViewPager(pos, selectAnswer);
        updateIndicator(pos, selectAnswer, correctAnswer);
        startNextQuestion();
    }

    @Override
    public void onSkipTestAnswer() {
        startNextQuestion();
    }

    @Override
    public void updateViewPager(int position, int selectAnswer) {
        mTestAdapter.updateViewPager(position, selectAnswer);
    }

    @Override
    public void updateIndicator(int position, int selectAnswer, int correctAnswer) {
        mTestSelectedAdapter.updateIndicator(position, selectAnswer, correctAnswer);
    }

    @UiThread(delay = 1000)
    @Override
    public void startNextQuestion() {
        if (errorMode) {
            int pos = viewpager.getCurrentItem() + 1;
            if (pos == mTestAdapter.getCount()) pos = 0;
            viewpager.setCurrentItem(pos);
            mTestSelectedAdapter.notifyItem(pos);
            recyclerTest.scrollToPosition(pos);
        } else {
            int pos = getUnansweredQuestion();
            if (pos >= 0) {
                viewpager.setCurrentItem(pos);
                mTestSelectedAdapter.notifyItem(pos);
                recyclerTest.scrollToPosition(pos);
            } else if (getUnansweredQuestion() < 0) {
                finishTest();
            }
        }
    }

    @Override
    public int getUnansweredQuestion() {
        int size = mTestAdapter.getCount();
        int num = viewpager.getCurrentItem();
        for (int i = 0; i < size; i++) {
            num++;
            if (num >= size) num = 0;
            Test test = mTestAdapter.getTestList().get(num);
            if (test != null && test.getSelectableAnswer() != 1) {
                return num;
            }
        }
        return -1;
    }

    @Override
    public void getTime(String time) {
        txtTime.setText(time);
    }

    @Override
    public void startTest(boolean testMode) {
        if (testMode) showToolbarForTestMode();
        else showToolbarBtn();
    }

    @Override
    public void stopTest() {
        makeWarningDialog();
    }

    @Override
    public void finishTest() {
        getActivity().getSupportFragmentManager().popBackStack();
        if (!testMode) mTestPresenter.stopTimer();
        hideToolbarBtn();
        if (mOnTestListener != null) mOnTestListener.onTestFinish(mTestAdapter.getTestList(),
                mTestPresenter.getTime(), testMode);
    }

    @Override
    public boolean isStartTest() {
        return mTestPresenter.isStartTest();
    }

    @Override
    public boolean isStartTestMode() {
        return mTestPresenter.isStartTestMode();
    }

    public void setOnTestListener(OnTestListener<Test> onTestListener) {
        mOnTestListener = onTestListener;
    }

    @Override
    public void onClick(View view) {
        stopTest();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTestPresenter.isStartTest()) {
            mTestPresenter.saveTestWhenActivityDestroyed(mTestAdapter.getTestList(), code, idExam);
        }
    }
}
