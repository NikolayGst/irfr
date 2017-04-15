package ru.irfr.Fragments.ResultDetail.Presenter;

import ru.irfr.Model.ResultDetail;

public interface ResultDetailPresenter {

    void getResultById(int id);

    void onResult(ResultDetail resultDetail);

}
