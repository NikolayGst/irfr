package ru.irfr.Fragments.QuestionsSubject.Interactor;

import java.util.List;

import io.realm.Realm;
import ru.irfr.App;
import ru.irfr.Fragments.QuestionsSubject.Presenter.SubjectPresenter;
import ru.irfr.Interface.OnSuccessFormatListener;
import ru.irfr.Model.Test.QuestionsSubject;
import ru.irfr.Utils.Db.FormatExam;


public class SubjectInteractorImpl implements SubjectInteractor {

    private Realm mRealm = Realm.getDefaultInstance();
    private SubjectPresenter mSubjectPresenter;
    private FormatExam mFormatExam;

    public SubjectInteractorImpl(SubjectPresenter subjectPresenter) {
        mSubjectPresenter = subjectPresenter;
        mFormatExam = FormatExam.init(App.getAppContext(), mRealm);
    }

    @Override
    public void getSubjectQuestions(String code) {
        mFormatExam.getQuestionsSubject(code, new OnSuccessFormatListener<QuestionsSubject>() {
            @Override
            public void onSuccess(List<QuestionsSubject> questionsSubjectList) {
                mSubjectPresenter.onSubjectQuestionsLoaded(questionsSubjectList);
            }
        });
    }
}
