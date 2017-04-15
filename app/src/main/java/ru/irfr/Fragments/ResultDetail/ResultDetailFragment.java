package ru.irfr.Fragments.ResultDetail;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.Result.View.ResultFragment;
import ru.irfr.Fragments.Result.View.ResultFragment_;
import ru.irfr.Fragments.ResultDetail.Presenter.ResultDetailPresenter;
import ru.irfr.Fragments.ResultDetail.Presenter.ResultDetailPresenterImpl;
import ru.irfr.Fragments.ResultDetail.View.ResultDetailView;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Fragments.Test.View.TestSelectedFragment_;
import ru.irfr.Interface.OnRetryTestListener;
import ru.irfr.Model.ResultDetail;
import ru.irfr.R;

@EFragment(R.layout.fragment_detail_result)
public class ResultDetailFragment extends BaseFragment implements ResultDetailView {

    @FragmentArg
    int id;

    @FragmentArg
    String title;

    @FragmentArg
    boolean testMode;

    @FragmentArg
    String code;

    @FragmentArg
    long time;

    @FragmentArg
    int point;

    @ViewById
    LinearLayout lrDate;

    @ViewById
    LinearLayout lrTime;

    @ViewById
    LinearLayout lrCountAnswer;

    @ViewById
    LinearLayout lrCountPoint;

    @ViewById
    LinearLayout lrErrorAnswer;

    @ViewById
    LinearLayout lrResult;

    @ViewById
    TextView textDate;

    @ViewById
    TextView textTime;

    @ViewById
    TextView textCountAnswer;

    @ViewById
    TextView textCountPoint;

    @ViewById
    TextView textErrorAnswer;

    @ViewById
    TextView textResult;

    @ViewById
    TextView myResult;

    @ViewById
    TextView txtRetry;

    @ViewById
    TextView error;

    @ViewById
    TextView txtNameExam;

    @StringRes(R.string.txt_result)
    String result;

    private OnRetryTestListener mOnRetryTestListener;
    private ResultDetailPresenter mResultDetailPresenter;

    public void setOnRetryTestListener(OnRetryTestListener onRetryTestListener) {
        mOnRetryTestListener = onRetryTestListener;
    }

    @AfterViews
    void init() {
        setTitle(result);
        txtNameExam.setText(title);
       /* txtPoint.setText(Integer.toString(point));
        textPoint.setText(Utils.getPointWithoutPoint(point));
        if (time != 0){
            String t = String.format("%02d мин. %02d cекунд", time / 60000, (time % 60000) / 1000);
            textTime.setText("На тест потрачено:\n" + t);
        }
*/
        if (!testMode) {
            mResultDetailPresenter = new ResultDetailPresenterImpl(this);
            mResultDetailPresenter.getResultById(id);
        } else {
            lrDate.setVisibility(View.INVISIBLE);
            lrTime.setVisibility(View.INVISIBLE);
            lrCountAnswer.setVisibility(View.INVISIBLE);
            lrErrorAnswer.setVisibility(View.INVISIBLE);
            lrResult.setVisibility(View.INVISIBLE);
            error.setVisibility(View.INVISIBLE);
            myResult.setVisibility(View.INVISIBLE);
            textCountPoint.setText(point + "/100");
        }

        if (mOnRetryTestListener == null) txtRetry.setVisibility(View.INVISIBLE);

    }

    @Click(R.id.txtRetry)
    void retryTest() {
        getActivity().getSupportFragmentManager().popBackStack();
        if (mOnRetryTestListener != null) mOnRetryTestListener.onRetry(code);
    }

    @Click(R.id.error)
    void goToError() {
        TestSelectedFragment errorTestFragment = TestSelectedFragment_.builder()
                .idError(id)
                .errorMode(true)
                .build();
        showFragment(errorTestFragment);
    }

    @Click(R.id.myResult)
    void goToResultFragment() {
        ResultFragment resultFragment = ResultFragment_.builder().build();
        showFragment(resultFragment);
    }

    @Override
    public void onResultLoaded(ResultDetail resultDetail) {
        textDate.setText(resultDetail.getDate());
        textTime.setText(resultDetail.getTime());
        textCountAnswer.setText(resultDetail.getCountAnswer());
        textCountPoint.setText(resultDetail.getCountPoint());
        textErrorAnswer.setText(resultDetail.getCountErrorAnswer());
        textResult.setText(resultDetail.getResult());
        Log.d("tagResult", "onResultLoaded: " + resultDetail);
    }
}
