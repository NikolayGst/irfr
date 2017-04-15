package ru.irfr.Fragments.Result.Presenter;

import io.realm.RealmResults;
import ru.irfr.Model.DB.Result;

public interface ResultPresenter {

    void getTestResultList();

    void onTestResultLoaded(RealmResults<Result> result);

}
