package ru.irfr.Fragments.Exam.Presenter;

import java.util.List;

import io.realm.RealmResults;
import ru.irfr.Fragments.Exam.Interactor.ExamInteractor;
import ru.irfr.Fragments.Exam.Interactor.ExamInteractorImpl;
import ru.irfr.Fragments.Exam.View.ExamView;
import ru.irfr.Model.DB.Exam;

public class ExamPresenterImpl implements ExamPresenter {

    private ExamView mExamView;
    private ExamInteractor mExamInteractor;

    public ExamPresenterImpl(ExamView examView) {
        mExamView = examView;
        mExamInteractor = new ExamInteractorImpl(this);
    }

    @Override
    public void loadExam() {
        mExamInteractor.loadAllExam();
    }

    @Override
    public void setExamList(RealmResults<Exam> mExamList) {
        mExamView.onLoadExamList(mExamList);
    }

    @Override
    public void setStatusList(List<String> mStatusList) {
        mExamView.onLoadStatusList(mStatusList);
    }
}
