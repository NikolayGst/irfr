package ru.irfr.Fragments.ResultDetail.Interactor;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmList;
import ru.irfr.Fragments.ResultDetail.Presenter.ResultDetailPresenter;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.DB.Result;
import ru.irfr.Model.ResultDetail;

public class ResultDetailInteractorImpl implements ResultDetailInteractor{

    private ResultDetailPresenter mResultDetailPresenter;
    private Realm mRealm;

    public ResultDetailInteractorImpl(ResultDetailPresenter resultDetailPresenter) {
        mResultDetailPresenter = resultDetailPresenter;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void loadResult(int id) {

        ResultDetail resultDetail = new ResultDetail();
        Result result = mRealm.where(Result.class).equalTo("id", id).findFirst();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'в' HH:mm");
        resultDetail.setDate(dateFormat.format(result.getDate()));

        String time = String.format("%02d мин. %02d cек.", result.getDuration() / 60000,
                (result.getDuration() % 60000) / 1000);
        resultDetail.setTime(time);

        int countAnswer = 0;
        int errorAnswer = 0;
        for (Question question : result.getQuestions()) {
            if (question.isSelectableAnswer() == 1 && question.getCorrectnum()
                    == question.getTestSelectedAnswer()) countAnswer++;
            if (question.isSelectableAnswer() == 1 && question.getTestSelectedAnswer()
                    != question.getCorrectnum()) errorAnswer++;
        }

        resultDetail.setCountAnswer(countAnswer + "/80");
        resultDetail.setCountErrorAnswer(String.valueOf(errorAnswer));
        resultDetail.setCountPoint(calcPoint(result.getQuestions()) + "/100");

        if (calcPoint(result.getQuestions()) >= 80) resultDetail.setResult("Сдал");
        else resultDetail.setResult("Не сдал");

        mResultDetailPresenter.onResult(resultDetail);

    }

    public int calcPoint(RealmList<Question> result) {
        int point = 0;
        for (int i = 0; i < result.size(); i++) {
            Question q = result.get(i);
            if (q.getCorrectnum() == q.getTestSelectedAnswer() && q.isSelectableAnswer() == 1) {
                point += result.get(i).getWeight();
            }
        }
        return point;
    }
}
