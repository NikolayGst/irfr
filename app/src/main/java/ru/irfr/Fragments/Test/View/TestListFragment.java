package ru.irfr.Fragments.Test.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ru.irfr.Interface.OnTestSelectedListener;
import ru.irfr.Model.Test.Test;
import ru.irfr.R;

@EFragment(R.layout.item_test)
public class TestListFragment extends Fragment {

    @ViewById
    TextView txtQuestion;

    @ViewById
    TextView txtTextQuestion;

    @ViewById
    TextView txtWeight;

    @ViewById
    TextView btnDoNotKhow;

    @ViewById
    Button btnOne;

    @ViewById
    Button btnTwo;

    @ViewById
    Button btnThree;

    @ViewById
    Button btnFour;

    @ViewById
    Button btnFive;

    @ViewById
    Button btnSkip;

    private Test mTest;
    private boolean testMode;

    private static OnTestSelectedListener mOnTestSelectedListener;

    public static TestListFragment_ newInstance(Test test, OnTestSelectedListener onTestSelectedListener,
                                                boolean  mTestMode) {
        mOnTestSelectedListener = onTestSelectedListener;
        Bundle args = new Bundle();
        args.putParcelable("test", test);
        args.putBoolean("testMode",mTestMode);
        TestListFragment_ fragment = new TestListFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @AfterViews
    void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTest = bundle.getParcelable("test");
            testMode = bundle.getBoolean("testMode");
            txtWeight.setText("вес " + mTest.getWeight());
            txtQuestion.setText(mTest.getHeader());
            txtTextQuestion.setText(generatedText(mTest));

            if (mTest.getTestAnswers().size() == 4)
                btnFive.setVisibility(View.GONE);
            else if (mTest.getTestAnswers().size() == 3) {
                btnFive.setVisibility(View.GONE);
                btnFour.setVisibility(View.GONE);
            } else if (mTest.getTestAnswers().size() == 2) {
                btnFive.setVisibility(View.GONE);
                btnThree.setVisibility(View.GONE);
                btnFour.setVisibility(View.GONE);
            }

            if(testMode) btnDoNotKhow.setVisibility(View.VISIBLE);
            else btnDoNotKhow.setVisibility(View.GONE);


            switch (mTest.getSelectableAnswer()){
                case 0:
                    break;
                case 1:
                    changeColorBtn(mTest.getTestSelectedAnswer(), mTest.getCorrectnum());
                    btnOne.setEnabled(false);
                    btnTwo.setEnabled(false);
                    btnThree.setEnabled(false);
                    btnFour.setEnabled(false);
                    btnFive.setEnabled(false);
                    btnDoNotKhow.setEnabled(false);
            //        btnSkip.setEnabled(false);
                    break;
                case 2:
                    btnOne.setEnabled(false);
                    btnTwo.setEnabled(false);
                    btnThree.setEnabled(false);
                    btnFour.setEnabled(false);
                    btnFive.setEnabled(false);
                    btnDoNotKhow.setEnabled(false);
           //         btnSkip.setEnabled(false);
                    break;
            }
        }
    }

    private String generatedText(Test test) {
        String text = "";
        for (int i = 0; i < test.getTestAnswers().size(); i++) {
            text += formatInt(i) + test.getTestAnswers().get(i).getText() + "\n\n";
        }
        return text;
    }

    private String formatInt(int i) {
        String text = "";
        switch (i) {
            case 0:
                text = "I. ";
                break;
            case 1:
                text = "II. ";
                break;
            case 2:
                text = "III. ";
                break;
            case 3:
                text = "IV. ";
                break;
            case 4:
                text = "V. ";
                break;
        }
        return text;
    }

    @Click(R.id.btnOne)
    void selectOne() {
        if (mOnTestSelectedListener != null) mOnTestSelectedListener
                .onTestAnswerSelected(mTest.getId(), 0, mTest.getCorrectnum());
        changeColorBtn(0, mTest.getCorrectnum());
    }

    @Click(R.id.btnTwo)
    void selectTwo() {
        if (mOnTestSelectedListener != null) mOnTestSelectedListener
                .onTestAnswerSelected(mTest.getId(), 1, mTest.getCorrectnum());
        changeColorBtn(1, mTest.getCorrectnum());
    }

    @Click(R.id.btnThree)
    void selectThree() {
        if (mOnTestSelectedListener != null) mOnTestSelectedListener
                .onTestAnswerSelected(mTest.getId(), 2, mTest.getCorrectnum());
        changeColorBtn(2, mTest.getCorrectnum());
    }

    @Click(R.id.btnFour)
    void selectFour() {
        if (mOnTestSelectedListener != null) mOnTestSelectedListener
                .onTestAnswerSelected(mTest.getId(), 3, mTest.getCorrectnum());
        changeColorBtn(3, mTest.getCorrectnum());
    }

    @Click(R.id.btnFive)
    void selectFive() {
        if (mOnTestSelectedListener != null) mOnTestSelectedListener
                .onTestAnswerSelected(mTest.getId(), 4, mTest.getCorrectnum());
        changeColorBtn(4, mTest.getCorrectnum());
    }

    @Click(R.id.btnDoNotKhow)
    void doNotKhow(){
        if (mOnTestSelectedListener != null) mOnTestSelectedListener
                .onTestAnswerSelected(mTest.getId(), mTest.getCorrectnum(), mTest.getCorrectnum());
        changeColorBtn(mTest.getCorrectnum(), mTest.getCorrectnum());
    }

    @Click(R.id.btnSkip)
    void selectSkip() {
        if (mOnTestSelectedListener != null) mOnTestSelectedListener.onSkipTestAnswer();
    }

    private void changeColorBtn(int selectAnswer, int correctAnswer) {
        switch (selectAnswer) {
            case 0:
                btnOne.setBackgroundResource(R.drawable.btn_test_false);
                break;
            case 1:
                btnTwo.setBackgroundResource(R.drawable.btn_test_false);
                break;
            case 2:
                btnThree.setBackgroundResource(R.drawable.btn_test_false);
                break;
            case 3:
                btnFour.setBackgroundResource(R.drawable.btn_test_false);
                break;
            case 4:
                btnFive.setBackgroundResource(R.drawable.btn_test_false);
                break;
        }
        switch (correctAnswer) {
            case 0:
                btnOne.setBackgroundResource(R.drawable.btn_test_true);
                break;
            case 1:
                btnTwo.setBackgroundResource(R.drawable.btn_test_true);
                break;
            case 2:
                btnThree.setBackgroundResource(R.drawable.btn_test_true);
                break;
            case 3:
                btnFour.setBackgroundResource(R.drawable.btn_test_true);
                break;
            case 4:
                btnFive.setBackgroundResource(R.drawable.btn_test_true);
                break;
        }
    }

}
