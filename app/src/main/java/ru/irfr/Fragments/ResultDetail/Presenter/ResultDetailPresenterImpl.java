package ru.irfr.Fragments.ResultDetail.Presenter;

import ru.irfr.Fragments.ResultDetail.Interactor.ResultDetailInteractor;
import ru.irfr.Fragments.ResultDetail.Interactor.ResultDetailInteractorImpl;
import ru.irfr.Fragments.ResultDetail.View.ResultDetailView;
import ru.irfr.Model.ResultDetail;

public class ResultDetailPresenterImpl implements ResultDetailPresenter {

    private ResultDetailView mResultDetailView;
    private ResultDetailInteractor mResultDetailInteractor;

    public ResultDetailPresenterImpl(ResultDetailView resultDetailView) {
        mResultDetailView = resultDetailView;
        mResultDetailInteractor = new ResultDetailInteractorImpl(this);
    }

    @Override
    public void getResultById(int id) {
        mResultDetailInteractor.loadResult(id);
    }

    @Override
    public void onResult(ResultDetail resultDetail) {
        mResultDetailView.onResultLoaded(resultDetail);
    }
}
