package ru.irfr.Fragments.Irfr.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

import ru.irfr.Base.BaseFragment;
import ru.irfr.R;

@EFragment(R.layout.fragment_irfr)
public class IrfrFragment extends BaseFragment {

    @StringRes(R.string.txt_irfr)
    String irfr;

    @AfterViews
    void init() {
        setTitle(irfr);
    }
}
