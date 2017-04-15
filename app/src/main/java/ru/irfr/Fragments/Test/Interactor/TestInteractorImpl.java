package ru.irfr.Fragments.Test.Interactor;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import ru.irfr.App;
import ru.irfr.Fragments.Test.Presenter.TestPresenter;
import ru.irfr.Interface.OnSuccessChapterListener;
import ru.irfr.Interface.OnSuccessFormatContinueListener;
import ru.irfr.Interface.OnSuccessFormatListener;
import ru.irfr.Model.DB.ContinueTest;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.DB.Result;
import ru.irfr.Model.Test.Test;
import ru.irfr.Utils.Db.FormatExam;
import ru.irfr.Utils.Utils;

public class TestInteractorImpl implements TestInteractor {

    private final Realm mRealm = Realm.getDefaultInstance();
    private final FormatExam mFormatExam;
    private TestPresenter mTestPresenter;

    public TestInteractorImpl(TestPresenter testPresenter) {
        mTestPresenter = testPresenter;
        mFormatExam = FormatExam.init(App.getAppContext(), mRealm);
    }


    @Override
    public void getFormatQuestions(String code) {
        mFormatExam.getFormatQuestions(code, new OnSuccessFormatListener<Test>() {
            @Override
            public void onSuccess(List<Test> result) {
                mTestPresenter.onLoadTestList(result);
            }
        });
    }

    @Override
    public void getQuestionsChapter(Integer chapterId, boolean isRandom) {
        mFormatExam.getQuestionsChapter(chapterId, isRandom, new OnSuccessChapterListener() {
            @Override
            public void onSuccess(List<Test> result) {
                mTestPresenter.onLoadTestList(result);
            }
        });
    }

    @Override
    public void getAllQuestions(String code, boolean isRandom) {
        mFormatExam.getAllQuestions(code, isRandom, new OnSuccessFormatListener<Test>() {
            @Override
            public void onSuccess(List<Test> result) {
                mTestPresenter.onLoadTestList(result);
            }
        });
    }

    @Override
    public void saveTest(List<Test> testList, String code, long time, int id) {
        mRealm.beginTransaction();
        mRealm.delete(ContinueTest.class);
        ContinueTest continueTest = mRealm.createObject(ContinueTest.class);
        RealmList<Question> questionRealmList = Utils.getQuestionRealmList(testList);
        continueTest.setCode(code);
        continueTest.setId(id);
        continueTest.setTime(time);
        continueTest.getTestList().addAll(questionRealmList);
        mRealm.commitTransaction();
    }

    @Override
    public void continueTest(final boolean testMode) {
        mFormatExam.continueTest(new OnSuccessFormatContinueListener() {
            @Override
            public void onSuccess(List<Test> testList, String code, long time, int idExam) {
                mTestPresenter.onContinueTestLoaded(testList, code, time, testMode, idExam);
            }
        });
    }

    @Override
    public void getErrorTest(int id) {
        Result result = mRealm.where(Result.class).equalTo("id", id).findFirst();
        mTestPresenter.onLoadTestListForError(Utils.getTestFormattedList(result.getQuestions()));
    }

    @Override
    public void clear() {
        mRealm.beginTransaction();
        mRealm.delete(ContinueTest.class);
        mRealm.commitTransaction();
    }

}
