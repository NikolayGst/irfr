package ru.irfr.Fragments.Result.Interactor;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.irfr.Fragments.Result.Presenter.ResultPresenter;
import ru.irfr.Model.DB.Result;

public class ResultInteractorImpl implements ResultInteractor {

    private Realm mRealm = Realm.getDefaultInstance();
    private ResultPresenter mResultPresenter;

    public ResultInteractorImpl(ResultPresenter resultPresenter) {
        mResultPresenter = resultPresenter;
    }

    @Override
    public void loadTestResultList() {
        RealmResults<Result> result = mRealm.where(Result.class).findAllSorted("date", Sort.DESCENDING);
        mResultPresenter.onTestResultLoaded(result);
    }
}
