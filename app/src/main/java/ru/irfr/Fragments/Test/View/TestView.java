package ru.irfr.Fragments.Test.View;

import java.util.List;

import ru.irfr.Model.Test.Test;
import ru.irfr.Model.Test.TestStatus;

public interface TestView {

    void onTestLoaded(List<Test> testList);

    void onStatusTestLoaded(List<TestStatus> testStatusList);

    void onTestContinue(String code, int id);

    void updateViewPager(int position, int selectAnswer);

    void updateIndicator(int position, int selectAnswer, int correctAnswer);

    void startNextQuestion();

    int getUnansweredQuestion();

    void getTime(String time);

    void startTest(boolean testMode);

    void stopTest();

    void finishTest();

    boolean isStartTest();

    boolean isStartTestMode();

}
