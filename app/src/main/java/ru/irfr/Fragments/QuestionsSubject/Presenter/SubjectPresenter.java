package ru.irfr.Fragments.QuestionsSubject.Presenter;

import java.util.List;

import ru.irfr.Model.Test.QuestionsSubject;

public interface SubjectPresenter {

    void loadSubjectQuestions(String code);

    void onSubjectQuestionsLoaded(List<QuestionsSubject> questionsSubjectList);
}
