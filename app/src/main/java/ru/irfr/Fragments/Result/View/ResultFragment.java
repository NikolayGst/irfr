package ru.irfr.Fragments.Result.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import io.realm.RealmResults;
import ru.irfr.Adapter.TestErrorAdapter;
import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.Result.Presenter.ResultPresenter;
import ru.irfr.Fragments.Result.Presenter.ResultPresenterImpl;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Fragments.Test.View.TestSelectedFragment_;
import ru.irfr.Interface.OnClickAdapterListener;
import ru.irfr.Model.DB.Result;
import ru.irfr.R;

@EFragment(R.layout.fragment_test_result)
public class ResultFragment extends BaseFragment implements ResultView {

    @ViewById
    RecyclerView recyclerTestError;

    @StringRes(R.string.txt_result)
    String result;

    private RecyclerView.LayoutManager mLayoutManager;
    private TestErrorAdapter mTestErrorAdapter;
    private ResultPresenter mResultPresenter;


    @AfterViews
    void init() {

        setTitle(result);

        initList();

        mResultPresenter = new ResultPresenterImpl(this);
        mResultPresenter.getTestResultList();

    }

    private void initList() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mTestErrorAdapter = new TestErrorAdapter(getContext());
        recyclerTestError.setLayoutManager(mLayoutManager);
        recyclerTestError.setAdapter(mTestErrorAdapter);
        mTestErrorAdapter.setOnClickAdapterListener(new OnClickAdapterListener<Result>() {
            @Override
            public void onClick(Result result) {
                showTestFragment(result.getId());
            }
        });
    }

    private void showTestFragment(int idError) {
        TestSelectedFragment testSelectedFragment = TestSelectedFragment_.builder()
                .idError(idError)
                .errorMode(true)
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, testSelectedFragment, "testSelectedFragment")
                .addToBackStack("stack")
                .commit();
     //   testSelectedFragment.setOnTestListener(this);
    }

    @Override
    public void onTestResultListSuccess(RealmResults<Result> result) {
        mTestErrorAdapter.setItems(result);
    }
}
