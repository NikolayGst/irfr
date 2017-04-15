package ru.irfr.Fragments.Exam.View;

import java.util.List;

import io.realm.RealmResults;
import ru.irfr.Model.DB.Exam;

public interface ExamView {

    void onLoadExamList(RealmResults<Exam> mExamList);

    void onLoadStatusList(List<String> mStatusList);

}
