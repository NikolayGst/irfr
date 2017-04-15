package ru.irfr.Fragments.Exam.View;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import io.realm.RealmResults;
import ru.irfr.Adapter.ExamAdapter;
import ru.irfr.Adapter.ExamSelectedAdapter;
import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.Exam.Presenter.ExamPresenter;
import ru.irfr.Fragments.Exam.Presenter.ExamPresenterImpl;
import ru.irfr.Interface.OnTabSelectListener;
import ru.irfr.Model.DB.Exam;
import ru.irfr.R;

@EFragment(R.layout.fragment_select_exam)
public class ExamSelectedFragment extends BaseFragment implements ExamView, OnTabSelectListener {

    @StringRes(R.string.txt_exam)
    String exam;

    @ViewById
    RecyclerView recyclerExam;

    @ViewById
    ViewPager viewpager;

    private RecyclerView.LayoutManager mLayoutManager;
    private ExamAdapter mExamAdapter;
    private ExamSelectedAdapter mExamSelectedAdapter;
    private ExamPresenter mExamPresenter;

    @AfterViews
    void init() {

        setTitle(exam);

        initViewPager();

        initIndicator();

        mExamPresenter = new ExamPresenterImpl(this);
        mExamPresenter.loadExam();

        mExamSelectedAdapter.setOnTabSelectListener(this);
    }

    private void initViewPager() {
        mExamAdapter = new ExamAdapter(getChildFragmentManager());
        viewpager.setAdapter(mExamAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mExamSelectedAdapter.notifyItem(position);
                recyclerExam.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setClipToPadding(false);
        viewpager.setPadding(32, 32, 32, 0);
        viewpager.setPageMargin(12);
    }

    private void initIndicator() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerExam.setLayoutManager(mLayoutManager);
        mExamSelectedAdapter = new ExamSelectedAdapter(getContext());
        recyclerExam.setAdapter(mExamSelectedAdapter);
    }


    @Override
    public void onLoadExamList(RealmResults<Exam> mExamList) {
        mExamAdapter.setExamList(mExamList);
    }

    @Override
    public void onLoadStatusList(List<String> mStatusList) {
        mExamSelectedAdapter.setStatusList(mStatusList);
    }

    @Override
    public void onTabSelected(int position) {
        viewpager.setCurrentItem(position);
    }
}
