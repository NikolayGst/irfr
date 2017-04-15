package ru.irfr.Interface;

public interface OnTestSelectedListener {

    void onTestAnswerSelected(int  id, int selectAnswer, int correctAnswer);

    void onSkipTestAnswer();

}
