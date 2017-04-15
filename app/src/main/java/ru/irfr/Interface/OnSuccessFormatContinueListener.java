package ru.irfr.Interface;

import java.util.List;

import ru.irfr.Model.Test.Test;


public interface OnSuccessFormatContinueListener {
    void onSuccess(List<Test> testList, String code, long time, int idExam);
}
