package ru.irfr.Fragments.Test.Interactor;

import java.util.List;

import ru.irfr.Model.Test.Test;

public interface TestInteractor {

    void getFormatQuestions(String code);

    void getQuestionsChapter(Integer chapterId, boolean isRandom);

    void getAllQuestions(String code, boolean isRandom);

    void saveTest(List<Test> testList, String code, long time, int id);

    void continueTest(boolean testMode);

    void getErrorTest(int id);

    void clear();

}
