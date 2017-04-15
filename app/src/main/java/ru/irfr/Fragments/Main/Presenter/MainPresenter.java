package ru.irfr.Fragments.Main.Presenter;

public interface MainPresenter {

    void checkZip();

    boolean checkTest();

    void showDialog();

    void continueTest(int idExam);

    void cancelContinueTest();

    void onErrorDownload();

    void onErrorZip(Exception e);

    void onTestWriteEnd();

    void onDestroy();

}
