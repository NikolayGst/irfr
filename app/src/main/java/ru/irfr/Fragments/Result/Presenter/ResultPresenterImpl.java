package ru.irfr.Fragments.Result.Presenter;

import io.realm.RealmResults;
import ru.irfr.Fragments.Result.Interactor.ResultInteractor;
import ru.irfr.Fragments.Result.Interactor.ResultInteractorImpl;
import ru.irfr.Fragments.Result.View.ResultView;
import ru.irfr.Model.DB.Result;

public class ResultPresenterImpl implements ResultPresenter {

    private ResultView mResultView;
    private ResultInteractor mTestErrorInteractor;

    public ResultPresenterImpl(ResultView resultView) {
        mResultView = resultView;
        mTestErrorInteractor = new ResultInteractorImpl(this);
    }

    @Override
    public void getTestResultList() {
        mTestErrorInteractor.loadTestResultList();
    }

    @Override
    public void onTestResultLoaded(RealmResults<Result> result) {
        mResultView.onTestResultListSuccess(result);
    }
}
