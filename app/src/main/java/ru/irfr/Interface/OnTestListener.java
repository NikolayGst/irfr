package ru.irfr.Interface;

import java.util.List;

public interface OnTestListener<T> {

    void onTestFinish(List<T> result, long duration, boolean testMode);

}
