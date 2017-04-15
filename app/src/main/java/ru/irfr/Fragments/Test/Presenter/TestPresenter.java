package ru.irfr.Fragments.Test.Presenter;

import java.util.List;

import ru.irfr.Model.Test.Test;

public interface TestPresenter {

    void loadTest(String code, Integer chapterId, boolean testMode, String testModeName, boolean isRandom);

    void loadErrorTest(int id);

    void getStatusTestList(List<Test> testList);

    void onLoadTestList(List<Test> testList);

    void continueTest(boolean testMode);

    void getStatusTestListForContinue(List<Test> testList);

    void onLoadTestListForContinue(List<Test> testList);

    void getStatusTestListForError(List<Test> testList);

    void onLoadTestListForError(List<Test> testList);

    void onContinueTestLoaded(List<Test> testList, String code, long time, boolean testMode, int id);

    boolean isStartTest();

    boolean isStartTestMode();

    void stopTimer();

    long getTime();

    void saveTestWhenActivityDestroyed(List<Test> testList, String code, int id);

}
