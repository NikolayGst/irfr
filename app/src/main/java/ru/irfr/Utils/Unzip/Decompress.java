package ru.irfr.Utils.Unzip;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.sromku.simple.storage.Storage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Decompress {
    private String location;
    private Storage mStorage;
    private UnzipListener mUnzipListener;

    public Decompress(Storage storage, String location, UnzipListener unzipListener) {
        this.mStorage = storage;
        this.location = location;
        mUnzipListener = unzipListener;
        dirChecker("");
    }

    public void unzip() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fin = new FileInputStream(mStorage.getFile("irfr","1.zip"));
                    ZipInputStream zin = new ZipInputStream(fin);
                    ZipEntry ze;
                    while ((ze = zin.getNextEntry()) != null) {
                        Log.v("Decompress", "Unzipping " + ze.getName());

                        if (ze.isDirectory()) {
                            dirChecker(ze.getName());
                        } else {
                            FileOutputStream fout = new FileOutputStream(location + ze.getName());
                            BufferedOutputStream bufout = new BufferedOutputStream(fout);
                            byte[] buffer = new byte[1024];
                            int read;
                            while ((read = zin.read(buffer)) != -1) {
                                bufout.write(buffer, 0, read);
                            }

                            zin.closeEntry();
                            bufout.close();
                            fout.close();
                        }

                    }
                    zin.close();
                } catch (Exception e) {
                    Log.e("Decompress", "unzip", e);
                    if (mUnzipListener != null) mUnzipListener.unZipError(e);
                } finally {
                    if (mUnzipListener != null) mUnzipListener.unZipCompleted();
                }
            }
        });
    }

    private void dirChecker(String dir) {
        File f = new File(location + dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}
