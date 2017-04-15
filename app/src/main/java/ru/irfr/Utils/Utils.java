package ru.irfr.Utils;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import ru.irfr.Model.DB.Answer;
import ru.irfr.Model.DB.Exam;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.Test.Test;
import ru.irfr.Model.Test.TestAnswer;
import ru.irfr.Model.Test.TestStatus;

public class Utils {

    public static void timeSplash(final OnTimeSplashListener onTimeSplashListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onTimeSplashListener.onTimeCompleted();
            }
        }, 3000);
    }


    public static Exam getExam(int id) {
        Realm realm = Realm.getDefaultInstance();
        Exam exam = realm.where(Exam.class).equalTo("id", id).findFirst();
        return exam;
    }

    public static RealmList<Question> getQuestionRealmList(List<Test> testList) {
        RealmList<Question> questionRealmList = new RealmList<>();
        for (Test test : testList) {
            Question question = new Question();
            question.setId(test.getId());
            question.setHeader(test.getHeader());
            question.setChapterId(test.getChapterId());
            question.setWeight(test.getWeight());
            question.setCode(test.getCode());
            question.setCorrectnum(test.getCorrectnum());
            RealmList<Answer> answers = new RealmList<>();
            for (TestAnswer testAnswer : test.getTestAnswers()) {
                Answer answer = new Answer();
                answer.setText(testAnswer.getText());
                answer.setNum(testAnswer.getNum());
                answers.add(answer);
            }
            question.setAnswers(answers);
            question.setTestSelectedAnswer(test.getTestSelectedAnswer());
            question.setSelectableAnswer(test.getSelectableAnswer());
            questionRealmList.add(question);

        }
        return questionRealmList;
    }

    public static List<Test> getTestFormattedList(RealmList<Question> questionRealmList) {

        List<Test> testList = new ArrayList<>();

        for (Question question : questionRealmList) {
            Test test = new Test();
            test.setId(question.getId());
            test.setHeader(question.getHeader());
            test.setChapterId(question.getChapterId());
            test.setWeight(question.getWeight());
            test.setCode(question.getCode());
            test.setCorrectnum(question.getCorrectnum());
            List<TestAnswer> testAnswerList = new ArrayList<>();
            for (Answer answer : question.getAnswers()) {
                TestAnswer testAnswer = new TestAnswer();
                testAnswer.setText(answer.getText());
                testAnswer.setNum(answer.getNum());
                testAnswerList.add(testAnswer);
            }
            test.setTestAnswers(testAnswerList);
            test.setTestSelectedAnswer(question.getTestSelectedAnswer());
            test.setSelectableAnswer(question.isSelectableAnswer());
            if (test.getSelectableAnswer() == 0) test.setSelectableAnswer(2);
            testList.add(test);
        }

        return testList;
    }

    public static List<TestStatus> getTestStatusList(List<Test> testList) {
        List<TestStatus> mTestStatusList = new ArrayList<>();
        for (int i = 0; i < testList.size(); i++) {
            Test test = testList.get(i);
            TestStatus testStatus = new TestStatus();
            testStatus.setId(test.getId());
            if (test.getSelectableAnswer() == 1 && test.getTestSelectedAnswer() == test.getCorrectnum())
                testStatus.setTrueAnswer(true);
            testStatus.setCorrectAnswer(test.getCorrectnum());
            testStatus.setSelectableAnswer(test.getSelectableAnswer());
            testStatus.setSelectedAnswer(test.getTestSelectedAnswer());
            mTestStatusList.add(testStatus);
        }
        return mTestStatusList;
    }

    public static String getPoint(int point) {
        String text = "";
        if ((point % 10 == 1) && (point % 100 != 11))
            text = point + " балл";
        else if ((((point % 10 < 5) && (1 < point % 10))) && (!(((12 <= point % 100) && (point % 100 <= 14)))))
            text = point + " баллa";
        else
            text = point + " баллов";
        return text;
    }

    public static String getPointWithoutPoint(int point) {
        String text = "";
        if ((point % 10 == 1) && (point % 100 != 11))
            text = "балл";
        else if ((((point % 10 < 5) && (1 < point % 10))) && (!(((12 <= point % 100) && (point % 100 <= 14)))))
            text = "баллa";
        else
            text = "баллов";
        return text;
    }

    public interface OnTimeSplashListener {
        void onTimeCompleted();
    }

    public enum Fragments {
        EXAM,
        RESULT,
        RULES,
        IRFR,
        DISCOUNT
    }
}
