package ru.irfr.Fragments.Main.View;

public interface MainView {

    void showProgressDialog();

    void hideProgressDialog();

    void onTestDownloadCompleted();

    void onErrorDownload();

    void continueTest(int idExam);

}
