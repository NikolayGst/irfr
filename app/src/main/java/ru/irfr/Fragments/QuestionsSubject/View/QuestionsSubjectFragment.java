package ru.irfr.Fragments.QuestionsSubject.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import ru.irfr.Adapter.QuestionsSubjectAdapter;
import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.QuestionsSubject.Presenter.SubjectPresenter;
import ru.irfr.Fragments.QuestionsSubject.Presenter.SubjectPresenterImpl;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Fragments.Test.View.TestSelectedFragment_;
import ru.irfr.Interface.OnClickAdapterListener;
import ru.irfr.Interface.OnTestListener;
import ru.irfr.Model.Test.QuestionsSubject;
import ru.irfr.Model.Test.Test;
import ru.irfr.R;

@EFragment(R.layout.fragment_questions_subject)
public class QuestionsSubjectFragment extends BaseFragment implements SubjectView, OnTestListener<Test> {

    @FragmentArg
    String code;

    @FragmentArg
    String header;

    @StringRes(R.string.str_subject)
    String subject;

    @FragmentArg
    boolean isRandom;

    @ViewById
    RecyclerView recyclerSubject;

    private RecyclerView.LayoutManager mLayoutManager;
    private QuestionsSubjectAdapter mQuestionsSubjectAdapter;
    private SubjectPresenter mSubjectPresenter;


    @AfterViews
    void init(){

        setTitle(subject);

        mSubjectPresenter = new SubjectPresenterImpl(this);
        mSubjectPresenter.loadSubjectQuestions(code);

    }

    @Override
    public void onSubjectLoaded(List<QuestionsSubject> questionsSubjectList) {
        initAdapterAndList(questionsSubjectList);
    }

    private void initAdapterAndList(List<QuestionsSubject> questionsSubjects){
        mLayoutManager = new LinearLayoutManager(getContext());
        mQuestionsSubjectAdapter = new QuestionsSubjectAdapter(questionsSubjects,getContext());
        recyclerSubject.setLayoutManager(mLayoutManager);
        recyclerSubject.setAdapter(mQuestionsSubjectAdapter);
        mQuestionsSubjectAdapter.setOnClickAdapterListener(new OnClickAdapterListener<QuestionsSubject>() {
            @Override
            public void onClick(QuestionsSubject questionsSubject) {
                showTestFragment(questionsSubject.getChapterid());
            }
        });
    }

    private void showTestFragment(int chapterId) {
        TestSelectedFragment testSelectedFragment = TestSelectedFragment_.builder()
                .chapterId(chapterId)
                .isRandom(isRandom)
                .testMode(true)
                .testModeName("subject")
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, testSelectedFragment, "testSelectedFragment")
                .addToBackStack("stack")
                .commit();
        testSelectedFragment.setOnTestListener(this);
    }

    @Override
    public void onTestFinish(List<Test> result, long duration, boolean testMode) {
        showResultDetailFragment(header, calcPoint(result), code, duration, testMode);
    }
}