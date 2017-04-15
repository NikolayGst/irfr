package ru.irfr.Fragments.Exam.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.Realm;
import ru.irfr.Fragments.ResultDetail.ResultDetailFragment;
import ru.irfr.Fragments.ResultDetail.ResultDetailFragment_;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Fragments.Test.View.TestSelectedFragment_;
import ru.irfr.Fragments.TestMode.TestModeFragment;
import ru.irfr.Fragments.TestMode.TestModeFragment_;
import ru.irfr.Interface.OnResultSaveListener;
import ru.irfr.Interface.OnRetryTestListener;
import ru.irfr.Interface.OnTestListener;
import ru.irfr.Model.DB.Exam;
import ru.irfr.Model.Test.Test;
import ru.irfr.R;
import ru.irfr.Utils.SaveResult;


@EFragment(R.layout.item_exam)
public class ExamListFragment extends Fragment implements OnTestListener<Test>, OnRetryTestListener {

    public static ExamListFragment_ newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        ExamListFragment_ fragment = new ExamListFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById
    TextView txtNameExam;

    private int mId;
    private Realm mRealm;
    private Exam mExam;

    @AfterViews
    void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getInt("id");
            mRealm = Realm.getDefaultInstance();
            mExam = mRealm.where(Exam.class).equalTo("id", mId).findFirst();
            txtNameExam.setText(mExam.getHeader());
        }
    }

    @Click(R.id.startExam)
    void startExam() {
        makeWDialogModeTest();
    }

    private void showTestFragment(String code) {
        TestSelectedFragment testSelectedFragment = TestSelectedFragment_.builder().idExam(mId)
                .code(code).build();
     /*   TestSelectedFragment testSelectedFragment = TestSelectedFragment_.builder().idExam(mId)
                .testMode(true).chapterId(96).testModeName("subject").build();*/
             /*  TestSelectedFragment testSelectedFragment = TestSelectedFragment_.builder().idExam(mId)
                .testMode(true).code(code).testModeName("all").build();*/
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, testSelectedFragment, "testSelectedFragment")
                .addToBackStack("stack")
                .commit();
        testSelectedFragment.setOnTestListener(this);
    }

    private void showTestModeFragment(String code) {
        TestModeFragment testModeFragment = TestModeFragment_.builder()
                .code(code)
                .header(mExam.getHeader())
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("stack")
                .replace(R.id.container, testModeFragment)
                .commit();
    }

    private void makeWDialogModeTest() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_test_mode, null);
        Button test = (Button) view.findViewById(R.id.testButton);
        Button exam = (Button) view.findViewById(R.id.examButton);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTestFragment(mExam.getCode());
                dialog.dismiss();
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTestModeFragment(mExam.getCode());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onTestFinish(List<Test> result, long duration, final boolean testMode) {
        if (!testMode) {
            new SaveResult().saveResult(mExam, duration, result, new OnResultSaveListener() {
                @Override
                public void onSuccess(int id) {
                    showResultDetailFragment(id, mExam.getHeader(),mExam.getCode(), false);
                }
            });
        } else {
            showResultDetailFragment(mExam.getHeader(), calcPoint(result), mExam.getCode(), duration, true);
        }
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

    private void showResultDetailFragment(int id, String title, String code, boolean testMode) {
        ResultDetailFragment resultDetailFragment = ResultDetailFragment_
                .builder()
                .id(id)
                .code(code)
                .title(title)
                .testMode(testMode)
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("stack")
                .replace(R.id.container, resultDetailFragment)
                .commit();
        resultDetailFragment.setOnRetryTestListener(this);
    }

    private void showResultDetailFragment(String title, int point, String code, long duration, boolean testMode) {
        ResultDetailFragment resultDetailFragment = ResultDetailFragment_
                .builder()
                .title(title)
                .testMode(testMode)
                .code(code)
                .time(duration)
                .point(point)
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("stack")
                .replace(R.id.container, resultDetailFragment)
                .commit();
        resultDetailFragment.setOnRetryTestListener(this);
    }

    @Override
    public void onRetry(String code) {
        showTestFragment(code);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) mRealm.close();
    }
}
