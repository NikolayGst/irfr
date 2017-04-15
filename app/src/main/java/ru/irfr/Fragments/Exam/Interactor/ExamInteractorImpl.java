package ru.irfr.Fragments.Exam.Interactor;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.irfr.Fragments.Exam.Presenter.ExamPresenter;
import ru.irfr.Model.DB.Exam;

public class ExamInteractorImpl implements ExamInteractor {

    private Realm mRealm = Realm.getDefaultInstance();
    private ExamPresenter mExamPresenter;
    private RealmResults<Exam> mExamRealmResults;
    private ArrayList<String> mStatusList;

    public ExamInteractorImpl(ExamPresenter examPresenter) {
        mExamPresenter = examPresenter;
    }

    @Override
    public void loadAllExam() {
        mExamRealmResults = mRealm.where(Exam.class).findAll();
        mExamPresenter.setExamList(mExamRealmResults);
        loadStatusList();
    }

    @Override
    public void loadStatusList() {
        mStatusList = new ArrayList<>();
        for (int i = 0; i < mExamRealmResults.size(); i++) {
            if (mExamRealmResults.get(i).getCode().equals("Basic")) {
                mStatusList.add("Б");
            } else {
                mStatusList.add(Integer.toString(i));
            }
        }
        mExamPresenter.setStatusList(mStatusList);
    }
}
