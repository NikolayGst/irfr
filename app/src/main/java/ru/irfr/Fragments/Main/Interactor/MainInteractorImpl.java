package ru.irfr.Fragments.Main.Interactor;

import android.content.Context;

import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.irfr.App;
import ru.irfr.Fragments.Main.Presenter.MainPresenter;
import ru.irfr.Interface.OnWriteTestListener;
import ru.irfr.Model.DB.ContinueTest;
import ru.irfr.Network.Request;
import ru.irfr.Utils.Db.Database;
import ru.irfr.Utils.Storage.MyStorage;
import ru.irfr.Utils.Unzip.UnzipListener;

public class MainInteractorImpl implements MainInteractor, UnzipListener, OnWriteTestListener {
    private MainPresenter mMainPresenter;
    private Database mDatabase;
    private MyStorage mStorage;
    private Realm mRealm = Realm.getDefaultInstance();
    private Context mContext = App.getAppContext();


    public MainInteractorImpl(MainPresenter mainPresenter) {
        mMainPresenter = mainPresenter;
        mDatabase = Database.init(mContext, mRealm);
        mStorage = MyStorage.init(mContext);
    }

    @Override
    public void checkZip() {
        if (!mStorage.isFileExist("irfr", "1.zip")) {
            mMainPresenter.showDialog();
            downloadZip();
        } else {
            ContinueTest continueTest = mRealm.where(ContinueTest.class).findFirst();
            if (continueTest != null) mMainPresenter.continueTest(continueTest.getId());
        }

    }

    @Override
    public boolean checkTest() {
        return mStorage.isFileExist("irfr", "1.zip");
    }

    private void downloadZip() {
        Request.getRetrofit().getFile().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mStorage.createFile(response);
                    mStorage.unzip(MainInteractorImpl.this);
                } else {
                    mMainPresenter.onErrorDownload();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mMainPresenter.onErrorDownload();
            }
        });
    }

    @Override
    public void cancelContinue() {
        mRealm.beginTransaction();
        mRealm.delete(ContinueTest.class);
        mRealm.commitTransaction();
    }

    @Override
    public void unZipCompleted() {
        createTable();
    }

    @Override
    public void unZipError(Exception e) {
        mMainPresenter.onErrorZip(e);
    }

    private void createTable() {
        mDatabase.setOnWriteTestListener(this);
        mDatabase.createTable();
    }

    @Override
    public void onTestWriteEnd() {
        mMainPresenter.onTestWriteEnd();
    }
}
