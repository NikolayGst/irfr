package ru.irfr.Utils;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import ru.irfr.Interface.OnResultSaveListener;
import ru.irfr.Model.DB.Answer;
import ru.irfr.Model.DB.Exam;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.DB.Result;
import ru.irfr.Model.Test.Test;
import ru.irfr.Model.Test.TestAnswer;


public class SaveResult {

    private Realm realm = Realm.getDefaultInstance();

    public void saveResult(Exam exam, long duration, List<Test> resultTest,
                           final OnResultSaveListener onResultSaveListener) {
        realm.beginTransaction();
        Result realmResult = realm.createObject(Result.class);
        realmResult.setId(getNextKey());
        realmResult.setTitle(exam.getHeader());
        realmResult.setPoint(calcPoint(resultTest));
        realmResult.setType(getIdByType(exam.getCode()));
        realmResult.setDuration(duration);
        realmResult.setDate(new Date());
        RealmList<Question> questionRealmList = new RealmList<>();
        for (Test test : resultTest) {
            Question question = realm.createObject(Question.class);
            question.setId(test.getId());
            question.setWeight(test.getWeight());
            question.setChapterId(test.getChapterId());
            question.setHeader(test.getHeader());
            question.setCode(test.getCode());
            question.setCorrectnum(test.getCorrectnum());
            question.setTestSelectedAnswer(test.getTestSelectedAnswer());
            question.setSelectableAnswer(test.getSelectableAnswer());
            RealmList<Answer> answerRealmList = new RealmList<>();
            for (TestAnswer testAnswer : test.getTestAnswers()) {
                Answer answer = realm.createObject(Answer.class);
                answer.setText(testAnswer.getText());
                answer.setNum(testAnswer.getNum());
                answerRealmList.add(answer);
            }
            question.setAnswers(answerRealmList);
            questionRealmList.add(question);
        }
        realmResult.setQuestions(questionRealmList);
        realm.commitTransaction();
        onResultSaveListener.onSuccess(realmResult.getId());
    }

    private int calcPoint(List<Test> result) {
        int point = 0;
        for (int i = 0; i < result.size(); i++) {
            Test test = result.get(i);
            if (test.getCorrectnum() == test.getTestSelectedAnswer() && test.getSelectableAnswer() == 1) {
                point += result.get(i).getWeight();
            }
        }
        return point;
    }

    private int getNextKey() {
        return realm.where(Result.class).max("id").intValue() + 1;
    }

    private int getIdByType(String type) {
        switch (type) {
            case "Basic":
                return 0;
            case "S1":
                return 1;
            case "S2":
                return 2;
            case "S3":
                return 3;
            case "S4":
                return 4;
            case "S5":
                return 5;
            case "S6":
                return 6;
            case "S7":
                return 7;
        }
        return -1;
    }
}
