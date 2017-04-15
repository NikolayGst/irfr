package ru.irfr.Fragments.QuestionsSubject.Presenter;

import java.util.List;

import ru.irfr.Fragments.QuestionsSubject.Interactor.SubjectInteractor;
import ru.irfr.Fragments.QuestionsSubject.Interactor.SubjectInteractorImpl;
import ru.irfr.Fragments.QuestionsSubject.View.SubjectView;
import ru.irfr.Model.Test.QuestionsSubject;

public class SubjectPresenterImpl implements SubjectPresenter {

    private SubjectView mSubjectView;
    private SubjectInteractor mSubjectInteractor;

    public SubjectPresenterImpl(SubjectView subjectView) {
        mSubjectView = subjectView;
        mSubjectInteractor = new SubjectInteractorImpl(this);
    }

    @Override
    public void loadSubjectQuestions(String code) {
        mSubjectInteractor.getSubjectQuestions(code);
    }

    @Override
    public void onSubjectQuestionsLoaded(List<QuestionsSubject> questionsSubjectList) {
        mSubjectView.onSubjectLoaded(questionsSubjectList);
    }
}
