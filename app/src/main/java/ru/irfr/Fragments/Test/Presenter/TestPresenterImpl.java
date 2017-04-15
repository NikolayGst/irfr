package ru.irfr.Fragments.Test.Presenter;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ru.irfr.Fragments.Test.Interactor.TestInteractor;
import ru.irfr.Fragments.Test.Interactor.TestInteractorImpl;
import ru.irfr.Fragments.Test.View.TestView;
import ru.irfr.Model.Test.Test;
import ru.irfr.Model.Test.TestStatus;
import ru.irfr.Utils.Utils;

public class TestPresenterImpl implements TestPresenter {

    private TestView mTestView;
    private TestInteractor mTestInteractor;
    private CountDownTimer mCountDownTimer;
    private long millis;
    private long time = 7200000;
    private boolean isRunning = false;
    private boolean isTestMode = false;
    //private boolean isFinishTestMode = false;

    public TestPresenterImpl(TestView testView) {
        mTestView = testView;
        mTestInteractor = new TestInteractorImpl(this);
    }

    @Override
    public void loadTest(String code,
                         Integer chapterId,
                         boolean testMode,
                         String testModeName,
                         boolean isRandom) {

        mTestView.startTest(testMode);

        if (!testMode) {

            mTestInteractor.getFormatQuestions(code);
            initTimer(time);
            mCountDownTimer.start();

        } else switch (testModeName) {

            case "subject":
                mTestInteractor.getQuestionsChapter(chapterId, isRandom);
                isTestMode = true;
                break;
            case "all":
                mTestInteractor.getAllQuestions(code, isRandom);
                isTestMode = true;
                break;

        }
    }

    @Override
    public void loadErrorTest(int id) {
        mTestInteractor.getErrorTest(id);
    }

    private void initTimer(long time) {
        mCountDownTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                millis = millisUntilFinished;
                String hm = String.format(Locale.UK, "%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
                mTestView.getTime(hm);
                isRunning = true;
            }

            @Override
            public void onFinish() {
                isRunning = false;
                mTestView.finishTest();
            }
        };
    }

    @Override
    public void getStatusTestList(List<Test> testList) {
        List<TestStatus> mTestStatusList = new ArrayList<>();
        for (int i = 0; i < testList.size(); i++) {
            Test test = testList.get(i);
            TestStatus testStatus = new TestStatus();
            testStatus.setId(test.getId());
            mTestStatusList.add(testStatus);
        }
        mTestView.onStatusTestLoaded(mTestStatusList);
    }

    @Override
    public void onLoadTestList(List<Test> testList) {
        mTestView.onTestLoaded(testList);
        getStatusTestList(testList);
    }

    @Override
    public void continueTest(boolean testMode) {
        mTestView.startTest(testMode);
        mTestInteractor.continueTest(testMode);
    }

    @Override
    public void getStatusTestListForContinue(List<Test> testList) {
        mTestView.onStatusTestLoaded(Utils.getTestStatusList(testList));
    }

    @Override
    public void onContinueTestLoaded(List<Test> testList, String code, long time, boolean testMode, int id) {
        if (!testMode) {
            initTimer(time);
            mCountDownTimer.start();
        }
        onLoadTestListForContinue(testList);
        mTestView.onTestContinue(code, id);
    }

    @Override
    public void onLoadTestListForContinue(List<Test> testList) {
        mTestView.onTestLoaded(testList);
        getStatusTestListForContinue(testList);
    }

    @Override
    public void getStatusTestListForError(List<Test> testList) {
        mTestView.onStatusTestLoaded(Utils.getTestStatusList(testList));
    }

    @Override
    public void onLoadTestListForError(List<Test> testList) {
        mTestView.onTestLoaded(testList);
        getStatusTestListForError(testList);
    }

    @Override
    public void stopTimer() {
        isRunning = false;
        mCountDownTimer.cancel();
        mTestInteractor.clear();
    }

    @Override
    public long getTime() {
        if (!isTestMode) return time - millis;
        else return 0;
    }

    @Override
    public void saveTestWhenActivityDestroyed(List<Test> testList, String code, int id) {
        mTestInteractor.saveTest(testList, code, millis, id);
    }

    @Override
    public boolean isStartTest() {
        return isRunning;
    }

    @Override
    public boolean isStartTestMode() {
        return isTestMode;
    }

}
