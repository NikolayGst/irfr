package ru.irfr.Fragments.TestMode;

import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import ru.irfr.Base.BaseFragment;
import ru.irfr.Fragments.QuestionsSubject.View.QuestionsSubjectFragment;
import ru.irfr.Fragments.QuestionsSubject.View.QuestionsSubjectFragment_;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Fragments.Test.View.TestSelectedFragment_;
import ru.irfr.Interface.OnTestListener;
import ru.irfr.Model.Test.Test;
import ru.irfr.R;

@EFragment(R.layout.fragment_test_mode)
public class TestModeFragment extends BaseFragment implements OnTestListener<Test> {

    @FragmentArg
    String code;

    @FragmentArg
    String header;

    @StringRes(R.string.test_str)
    String testMode;

    @ViewById
    SwitchCompat switchRandom;

    private boolean randomCheck = false;

    @AfterViews
    void init() {
        setTitle(testMode);
        switchRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) randomCheck = true;
            }
        });
    }

    @Click(R.id.btnQuestionsSubject)
    void goToQuestionsSubject(){
        QuestionsSubjectFragment questionsSubjectFragment = QuestionsSubjectFragment_
                .builder()
                .code(code)
                .header(header)
                .isRandom(randomCheck)
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("stack")
                .replace(R.id.container, questionsSubjectFragment)
                .commit();
    }

    @Click(R.id.btnAllQuestions)
    void getAllQuestions(){
        TestSelectedFragment testSelectedFragment = TestSelectedFragment_.builder()
                .code(code)
                .isRandom(randomCheck)
                .testMode(true)
                .testModeName("all")
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, testSelectedFragment, "testSelectedFragment")
                .addToBackStack("stack")
                .commit();
        testSelectedFragment.setOnTestListener(this);
    }

    @Override
    public void onTestFinish(List<Test> result, long duration, boolean testMode) {
        showResultDetailFragment(header, calcPoint(result),code,duration, testMode);
    }
}
