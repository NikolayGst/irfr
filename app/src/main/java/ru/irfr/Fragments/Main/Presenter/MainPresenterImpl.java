package ru.irfr.Fragments.Main.Presenter;

import java.io.File;

import ru.irfr.Fragments.Main.Interactor.MainInteractor;
import ru.irfr.Fragments.Main.Interactor.MainInteractorImpl;
import ru.irfr.Fragments.Main.View.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;
    private MainInteractor mMainInteractor;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
        mMainInteractor = new MainInteractorImpl(this);
    }

    @Override
    public void checkZip() {
        mMainInteractor.checkZip();
    }

    @Override
    public boolean checkTest() {
        return mMainInteractor.checkTest();
    }

    @Override
    public void showDialog(){
        mMainView.showProgressDialog();
    }

    /*@Override
    public void onTestContinue() {
        //Метод для реализации продолжения теста
        mMainView.continueTest();
    }*/

    @Override
    public void continueTest(int idExam) {
        mMainView.continueTest(idExam);
    }

    @Override
    public void cancelContinueTest() {
        mMainInteractor.cancelContinue();
    }

    @Override
    public void onErrorDownload() {
        mMainView.onErrorDownload();
        mMainView.hideProgressDialog();
    }

    @Override
    public void onErrorZip(Exception e) {
        mMainView.hideProgressDialog();
    }

    @Override
    public void onTestWriteEnd() {
        mMainView.onTestDownloadCompleted();
        mMainView.hideProgressDialog();
    }

    @Override
    public void onDestroy() {

    }

}
