package ru.irfr.Fragments.Main.View;


import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ru.irfr.Base.BaseShareFragment;
import ru.irfr.Fragments.Main.Presenter.MainPresenter;
import ru.irfr.Fragments.Main.Presenter.MainPresenterImpl;
import ru.irfr.Fragments.Test.View.TestSelectedFragment;
import ru.irfr.Interface.OnFragmentClickListener;
import ru.irfr.Interface.OnResultSaveListener;
import ru.irfr.Interface.OnTestListener;
import ru.irfr.Model.DB.Exam;
import ru.irfr.Model.Test.Test;
import ru.irfr.R;
import ru.irfr.Utils.SaveResult;
import ru.irfr.Utils.Utils;

@EFragment(R.layout.fragment_main)
public class MainFragment extends BaseShareFragment implements MainView, OnTestListener<Test>,
    View.OnClickListener {

  @StringRes(R.string.txt_main)
  String main;

  @ViewById
  ImageView shareFb;

  @ViewById
  ImageView shareTwitter;

  @ViewById
  ImageView shareVk;

  private MainPresenter mMainPresenter;
  private OnFragmentClickListener mOnFragmentClickListener;
  private Exam mExam;

  public void setOnFragmentClickListener(OnFragmentClickListener onFragmentClickListener) {
    mOnFragmentClickListener = onFragmentClickListener;
  }

  @AfterViews
  void init() {
    initShareRealm();
    setTitle(main);
    initProgressDialog(getString(R.string.loading_data));

    mMainPresenter = new MainPresenterImpl(this);
    mMainPresenter.checkZip();

    shareFb.setOnClickListener(this);
    shareTwitter.setOnClickListener(this);
    shareVk.setOnClickListener(this);

  }

  @Override
  public void showProgressDialog() {
    if (!mProgressDialog.isShowing()) {
      mProgressDialog.show();
    }
  }

  @Override
  public void hideProgressDialog() {
    if (mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  @Override
  public void onTestDownloadCompleted() {
    showToast(getString(R.string.test_download_completed));
    clear("irfr");
  }

  void clear(String file) {
    String dir = getContext().getFilesDir().getAbsolutePath();
    File fileOrDirectory = new File(dir, file);
    try {
      FileUtils.forceDelete(new File(dir, "12.zip"));
      FileUtils.deleteDirectory(fileOrDirectory);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onErrorDownload() {
    makeDWarningDialog();
  }

  private void makeDWarningDialog() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_error, null);
    Button yes = (Button) view.findViewById(R.id.yesButton);
    Button no = (Button) view.findViewById(R.id.noButton);
    builder.setView(view);
    final AlertDialog dialog = builder.create();
    yes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!mMainPresenter.checkTest()) {
          mMainPresenter.checkZip();
        }
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

  @Override
  public void continueTest(int idExam) {
    mExam = Utils.getExam(idExam);
    makeContinueDialog();
    TestSelectedFragment testSelectedFragment = getTestSelectedFragment();
    if (testSelectedFragment != null) {
      testSelectedFragment.setOnTestListener(this);
    }
    //Метод для реализации продолжения теста
  }

  @Override
  public void cancel() {
    super.cancel();
    mMainPresenter.cancelContinueTest();
  }

  @Click(R.id.btnExam)
  void goToExam() {
    if (!mMainPresenter.checkTest()) {
      mMainPresenter.checkZip();
      return;
    }
    if (mOnFragmentClickListener != null) {
      mOnFragmentClickListener
          .OnClick(Utils.Fragments.EXAM);
    }
  }

  @Click(R.id.btnResult)
  void goToResult() {
    if (mOnFragmentClickListener != null) {
      mOnFragmentClickListener
          .OnClick(Utils.Fragments.RESULT);
    }
  }

  @Click(R.id.btnRules)
  void goToRules() {
    if (mOnFragmentClickListener != null) {
      mOnFragmentClickListener
          .OnClick(Utils.Fragments.RULES);
    }
  }

  @Click(R.id.btnIrfr)
  void goToIrfr() {
    if (mOnFragmentClickListener != null) {
      mOnFragmentClickListener
          .OnClick(Utils.Fragments.IRFR);
    }
  }

  @Click(R.id.btnGetDiscount)
  void goToGetDiscount() {
    if (mOnFragmentClickListener != null) {
      mOnFragmentClickListener
          .OnClick(Utils.Fragments.DISCOUNT);
    }
  }

  @Override
  public void onTestFinish(List<Test> result, long duration, final boolean testMode) {
    if (!testMode) {
      new SaveResult().saveResult(mExam, duration, result, new OnResultSaveListener() {
        @Override
        public void onSuccess(int id) {
          showResultDetailFragment(id, mExam.getHeader(), false);
        }
      });
    } else {
      showResultDetailFragment(mExam.getHeader(), calcPoint(result), mExam.getCode(), duration,
          true);
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.shareFb:
        if (!mShare.isFbShare()) {
          shareFb();
        }
        break;
      case R.id.shareTwitter:
        if (!mShare.isTwitterShare()) {
          shareTwitter();
        }
        break;
      case R.id.shareVk:
        if (!mShare.isVkShare()) {
          shareVk();
        }
        break;
    }
  }
}
