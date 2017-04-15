package ru.irfr.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.realm.RealmResults;
import ru.irfr.Fragments.Exam.View.ExamListFragment;
import ru.irfr.Model.DB.Exam;

public class ExamAdapter extends FragmentPagerAdapter {

    private RealmResults<Exam> mExamList;

    public void setExamList(RealmResults<Exam> examList) {
        mExamList = examList;
        notifyDataSetChanged();
    }

    public ExamAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Exam exam = mExamList.get(position);
        return ExamListFragment.newInstance(exam.getId());
    }

    @Override
    public int getCount() {
        if (mExamList != null) return mExamList.size();
        else return 0;
    }
}
