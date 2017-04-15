package ru.irfr.Utils.Unzip;

public interface UnzipListener {
    void unZipCompleted();
    void unZipError(Exception e);
}
