package ru.irfr.Utils.Storage;

import android.content.Context;
import android.util.Log;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.irfr.Utils.Unzip.Decompress;
import ru.irfr.Utils.Unzip.UnzipListener;

public class MyStorage implements MyInternalStorage {
    private static final String TAG = "MyStorage";

    private static MyStorage instance;
    private Storage mStorage;
    private Context mContext;

    private MyStorage(Context context) {
        mStorage = SimpleStorage.getInternalStorage(context);
        this.mContext = context;
    }

    public static MyStorage init(Context context) {
        if (instance == null) {
            instance = new MyStorage(context);
        }
        return instance;
    }

    @Override
    public boolean isFileExist(String irfr, String s) {
        return mStorage.isFileExist(irfr, s);
    }

    @Override
    public void createFile(Response<ResponseBody> response) {
        try {
            if (!mStorage.isDirectoryExists("irfr")) {
                mStorage.createDirectory("irfr");
            }
            mStorage.createFile("irfr", "1.zip", response.body().bytes());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "error file" + e.getLocalizedMessage());
        }
    }

    @Override
    public String readFile(String dir, String fileName) {
        return readFromFile(dir, fileName);
    }

    @Override
    public void unzip(final UnzipListener unziplistener) {
        final String unzipLocation;
        unzipLocation = mContext.getFilesDir().getAbsolutePath() + "/irfr/";
        Decompress d = new Decompress(mStorage, unzipLocation, unziplistener);
        d.unzip();
    }

    @Override
    public String readAssetsFile(String name) {
        String tContents = "";

        try {
            InputStream stream = mContext.getAssets().open(name);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tContents;
    }

    @Override
    public String readFromFile(String dir, String name) {
        String ret = "";
     //   Log.d(TAG, "readFromFile: " + getInternalFile(dir, name));
        try {
            FileInputStream inputStream = new FileInputStream(getInternalFile(dir, name));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        } catch (FileNotFoundException e) {
     //       Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
      //      Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    @Override
    public File getInternalFile(String directoryName, String fileName) {
        String path = buildPath(directoryName, fileName);
        return new File(path);
    }

    @Override
    public String buildPath(String directoryName, String fileName) {
        String path = mContext.getFilesDir().getAbsolutePath();
        path = path + File.separator + directoryName + File.separator + fileName;
        return path;
    }

    @Override
    public void deleteDir(String dir) {
        mStorage.deleteDirectory(dir);
    }
}
