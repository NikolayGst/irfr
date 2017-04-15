package ru.irfr.Utils.Storage;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.irfr.Utils.Unzip.UnzipListener;

public interface MyInternalStorage {

    boolean isFileExist(String irfr, String s);

    void createFile(Response<ResponseBody> response);

    String readFile(String dir, String fileName);

    void unzip(UnzipListener unziplistener);

    String readAssetsFile(String name);

    String readFromFile(String dir, String name);

    File getInternalFile(String directoryName, String fileName);

    String buildPath(String directoryName, String fileName);

    void deleteDir(String dir);

}
