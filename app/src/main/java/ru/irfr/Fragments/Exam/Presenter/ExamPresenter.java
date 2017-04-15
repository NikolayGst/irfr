package ru.irfr.Fragments.Exam.Presenter;

import java.util.List;

import io.realm.RealmResults;
import ru.irfr.Model.DB.Exam;

public interface ExamPresenter {

    void loadExam();

    void setExamList(RealmResults<Exam> mExamList);

    void setStatusList(List<String> mStatusList);

}
