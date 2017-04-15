package ru.irfr.Fragments.Rules.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

import ru.irfr.Base.BaseFragment;
import ru.irfr.R;

@EFragment(R.layout.fragment_rules)
public class RulesFragment extends BaseFragment {

    @StringRes(R.string.txt_rules)
    String rules;

    @AfterViews
    void init() {
        setTitle(rules);
    }

}
