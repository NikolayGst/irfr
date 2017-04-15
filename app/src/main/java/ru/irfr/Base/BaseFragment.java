package ru.irfr.Base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.irfr.Fragments.ResultDetail.ResultDetailFragment;
import ru.irfr.Fragments.ResultDetail.ResultDetailFragment_;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Fragments.Test.View.TestSelectedFragment_;
import ru.irfr.Model.Test.Test;
import ru.irfr.R;

public abstract class BaseFragment extends Fragment {

    public ProgressDialog mProgressDialog;
    public TextView btnStop;
    public TextView txtTime;
    public LinearLayout lrTime;
    public Toolbar toolbar;
    private TestSelectedFragment testSelectedFragment;

    public void initToolbarBtn() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        btnStop = (TextView) getActivity().findViewById(R.id.btnStop);
        txtTime = (TextView) getActivity().findViewById(R.id.txtTime);
        lrTime = (LinearLayout) getActivity().findViewById(R.id.lrTime);
    }

    public void showToolbarBtn() {
        toolbar.setNavigationIcon(null);
        btnStop.setVisibility(View.VISIBLE);
        lrTime.setVisibility(View.VISIBLE);
    }

    public void showToolbarForTestMode() {
        toolbar.setNavigationIcon(null);
        btnStop.setVisibility(View.VISIBLE);
    }

    public void hideToolbarBtn() {
        if (btnStop.getVisibility() == View.VISIBLE) btnStop.setVisibility(View.GONE);
        if (lrTime.getVisibility() == View.VISIBLE) lrTime.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.ic_menu);
    }

    public void initProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
    }

    public void setTitle(String title) {
        TextView titleMainToolbar = (TextView) getActivity().findViewById(R.id.titleMainToolbar);
        if (!title.equals("")) titleMainToolbar.setText(title);
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void makeWarningDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_cancel, null);
        Button yes = (Button) view.findViewById(R.id.yesButton);
        Button no = (Button) view.findViewById(R.id.noButton);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTest();
                dialog.dismiss();
            }
        });
        dialog.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void makeContinueDialog() {
        createFragment();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_continue, null);
        Button yes = (Button) view.findViewById(R.id.yesButton);
        Button no = (Button) view.findViewById(R.id.noButton);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContinueFragment(testSelectedFragment);
                dialog.dismiss();
            }
        });
        dialog.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                cancel();
            }
        });
    }

    private void createFragment() {
        testSelectedFragment = TestSelectedFragment_.builder()
                .testMode(false)
                .isContinueTest(true)
                .build();
    }

    public TestSelectedFragment getTestSelectedFragment() {
        return testSelectedFragment;
    }

    public void finishTest() {

    }

    public void cancel() {

    }

    public void showResultDetailFragment(int id, String title, boolean testMode) {
        ResultDetailFragment resultDetailFragment = ResultDetailFragment_
                .builder()
                .id(id)
                .title(title)
                .testMode(testMode)
                .build();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("stack")
                .replace(R.id.container, resultDetailFragment)
                .commit();
        //    resultDetailFragment.setOnRetryTestListener(this);
    }

    public void showResultDetailFragment(String title, int point, String code, long duration, boolean testMode) {
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
        //    resultDetailFragment.setOnRetryTestListener(this);
    }

    public int calcPoint(List<Test> result) {
        int point = 0;
        for (int i = 0; i < result.size(); i++) {
            Test test = result.get(i);
            if (test.getCorrectnum() == test.getTestSelectedAnswer() && test.getSelectableAnswer() == 1) {
                point += result.get(i).getWeight();
            }
        }
        return point;
    }

    public void showContinueFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "testSelectedFragment")
                .addToBackStack("stack")
                .commit();
    }

    public void showFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("stack")
                .commit();
    }

}
