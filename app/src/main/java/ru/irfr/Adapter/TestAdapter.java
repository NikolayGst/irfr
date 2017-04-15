package ru.irfr.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ru.irfr.Fragments.Test.View.TestListFragment;
import ru.irfr.Interface.OnTestSelectedListener;
import ru.irfr.Model.Test.Test;

public class TestAdapter extends FragmentPagerAdapter implements OnTestSelectedListener {

    private List<Test> mTestList;
    private OnTestSelectedListener mOnTestSelectedListener;
    private boolean mTestMode;

    public TestAdapter(FragmentManager fm, OnTestSelectedListener onTestSelectedListener, boolean testMode) {
        super(fm);
        mOnTestSelectedListener = onTestSelectedListener;
        mTestMode = testMode;
    }

    public void setTestList(List<Test> testList) {
        mTestList = testList;
        notifyDataSetChanged();
    }

    public List<Test> getTestList() {
        return mTestList;
    }

    public void updateViewPager(int position, int selectAnswer){
        Test Test = mTestList.get(position);
        Test.setTestSelectedAnswer(selectAnswer);
        Test.setSelectableAnswer(1);
        mTestList.set(position, Test);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return TestListFragment.newInstance(mTestList.get(position), this, mTestMode);
    }

    @Override
    public int getCount() {
        if(mTestList != null) return mTestList.size();
        else return 0;
    }


    @Override
    public void onTestAnswerSelected(int id, int selectAnswer, int correctAnswer) {
        if (mOnTestSelectedListener != null) {
            mOnTestSelectedListener.onTestAnswerSelected(id, selectAnswer, correctAnswer);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void onSkipTestAnswer() {
        if (mOnTestSelectedListener != null) {
            mOnTestSelectedListener.onSkipTestAnswer();
        }
    }
}
