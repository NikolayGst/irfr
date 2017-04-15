package ru.irfr.Fragments.Result.View;


import io.realm.RealmResults;
import ru.irfr.Model.DB.Result;

public interface ResultView {

    void onTestResultListSuccess(RealmResults<Result> result);

}
