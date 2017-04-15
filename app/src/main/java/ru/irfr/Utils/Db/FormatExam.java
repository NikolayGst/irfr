package ru.irfr.Utils.Db;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.irfr.Interface.OnSuccessChapterListener;
import ru.irfr.Interface.OnSuccessFormatContinueListener;
import ru.irfr.Interface.OnSuccessFormatListener;
import ru.irfr.Model.DB.Answer;
import ru.irfr.Model.DB.Chapter;
import ru.irfr.Model.DB.ContinueTest;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.Test.QuestionsSubject;
import ru.irfr.Model.Test.Subject;
import ru.irfr.Model.Test.Test;
import ru.irfr.Model.Test.TestAnswer;
import ru.irfr.Utils.Storage.MyStorage;
import ru.irfr.Utils.Test.GeneratedTest;
import ru.irfr.Utils.Test.QuestionTest;

public class FormatExam {

    private static FormatExam instance;
    private final Context mContext;
    private final Realm mRealm;
    private Gson mGson;
    private List<Test> mTestsList;
    private List<QuestionsSubject> mQuestionsSubjectList;
    private List<GeneratedTest> mTestList;
    private MyStorage mStorage;
    private String json;

    private FormatExam(Context context, Realm realm) {
        this.mContext = context;
        mRealm = realm;
        initList();
    }

    public static FormatExam init(Context context, Realm realm) {
        if (instance == null) {
            instance = new FormatExam(context, realm);
        }
        return instance;
    }

    private void initList() {
        mGson = new Gson();
        mStorage = MyStorage.init(mContext);
        json = mStorage.readAssetsFile("questions.json");
        mTestList = mGson.fromJson(json, new TypeToken<List<GeneratedTest>>() {
        }.getType());
    }

    public void continueTest(final OnSuccessFormatContinueListener onSuccessFormatContinueListener) {
        mTestsList = new ArrayList<>();
        ContinueTest continueTests = mRealm.where(ContinueTest.class).findFirst();
        for (Question question : continueTests.getTestList()) {
            Test test = new Test();
            test.setId(question.getId());
            test.setWeight(question.getWeight());
            test.setChapterId(question.getChapterId());
            test.setHeader(question.getHeader());
            test.setCode(question.getCode());
            test.setCorrectnum(question.getCorrectnum());
            List<TestAnswer> testAnswerList = new ArrayList<>();
            for (Answer answer : question.getAnswers()) {
                TestAnswer testAnswer = new TestAnswer();
                testAnswer.setText(answer.getText());
                testAnswer.setNum(answer.getNum());
                testAnswerList.add(testAnswer);
            }
            test.setTestAnswers(testAnswerList);
            test.setTestSelectedAnswer(question.getTestSelectedAnswer());
            test.setSelectableAnswer(question.isSelectableAnswer());
            mTestsList.add(test);
        }

        onSuccessFormatContinueListener.onSuccess(mTestsList, continueTests.getCode(),
                continueTests.getTime(), continueTests.getId());


    }

    public void getFormatQuestions(String examType,
                                   OnSuccessFormatListener<Test> onSuccessFormatListener) {
        mTestsList = new ArrayList<>();
        switch (examType) {
            case "Basic":
                formattedQuestions(0, onSuccessFormatListener);
                break;
            case "S1":
                formattedQuestions(1, onSuccessFormatListener);
                break;
            case "S2":
                formattedQuestions(2, onSuccessFormatListener);
                break;
            case "S3":
                formattedQuestions(3, onSuccessFormatListener);
                break;
            case "S4":
                formattedQuestions(4, onSuccessFormatListener);
                break;
            case "S5":
                formattedQuestions(5, onSuccessFormatListener);
                break;
            case "S6":
                formattedQuestions(6, onSuccessFormatListener);
                break;
            case "S7":
                formattedQuestions(7, onSuccessFormatListener);
                break;
        }
    }

    private void formattedQuestions(int typeExam,
                                    final OnSuccessFormatListener<Test> onSuccessFormatListener) {

        try {
            final GeneratedTest generatedtest = mTestList.get(typeExam);
            final List<Question> temp = new ArrayList<>();

            for (QuestionTest question :
                    generatedtest.getQuestions()) {
                RealmResults<Question> weightOne = mRealm.where(Question.class).equalTo("chapterId",
                        question.getChapterid()).equalTo("weight", 1).findAll();
                temp.addAll(randList(weightOne));
                for (int i = 1; i <= question.getWeight().getOneLength(); i++) {
                    selectModel(temp, i);
                }
                temp.clear();
                RealmResults<Question> weightTwo = mRealm.where(Question.class).equalTo("chapterId",
                        question.getChapterid()).equalTo("weight", 2).findAll();
                temp.addAll(randList(weightTwo));
                for (int i = 1; i <= question.getWeight().getTwoLength(); i++) {
                    if (question.getWeight().getTwoLength() != 0) selectModel(temp, i);
                }
                temp.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (onSuccessFormatListener != null) {
            Collections.sort(mTestsList, new Comparator<Test>() {
                @Override
                public int compare(Test lhs, Test rhs) {
                    return ((Integer) lhs.getWeight()).compareTo(rhs.getWeight());
                }
            });
            onSuccessFormatListener.onSuccess(mTestsList);
        }
    }


    public void getAllQuestions(String examType, boolean rand,
                                OnSuccessFormatListener<Test> onSuccessFormatListener) {
        mTestsList = new ArrayList<>();
        switch (examType) {
            case "Basic":
                formattedAllQuestions(0, rand, onSuccessFormatListener);
                break;
            case "S1":
                formattedAllQuestions(1, rand, onSuccessFormatListener);
                break;
            case "S2":
                formattedAllQuestions(2, rand, onSuccessFormatListener);
                break;
            case "S3":
                formattedAllQuestions(3, rand, onSuccessFormatListener);
                break;
            case "S4":
                formattedAllQuestions(4, rand, onSuccessFormatListener);
                break;
            case "S5":
                formattedAllQuestions(5, rand, onSuccessFormatListener);
                break;
            case "S6":
                formattedAllQuestions(6, rand, onSuccessFormatListener);
                break;
            case "S7":
                formattedAllQuestions(7, rand, onSuccessFormatListener);
                break;
        }
    }

    private void formattedAllQuestions(int typeExam, boolean rand, OnSuccessFormatListener<Test> onSuccessFormatListener) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<Subject>>(){}.getType();

        List<Subject> questionsSubjectsList = gson.fromJson(json, type);

        Subject subject = questionsSubjectsList.get(typeExam);

        for (QuestionsSubject question :
                subject.getQuestions()) {
            RealmResults<Question> realmResultQuestions = mRealm.where(Question.class)
                    .equalTo("chapterId", question.getChapterid()).findAll();
            for (int i = 0; i < realmResultQuestions.size(); i++) {
                selectModel(realmResultQuestions, i);
            }
        }

        if (onSuccessFormatListener != null) {
            if (rand) onSuccessFormatListener.onSuccess(randQuestionsList(mTestsList));
            else onSuccessFormatListener.onSuccess(mTestsList);
        }
    }

    public void getQuestionsChapter(int chapterId, boolean rand, OnSuccessChapterListener
            onSuccessChapterListener) {
        mTestsList = new ArrayList<>();
        RealmResults<Question> questionRealmResults = mRealm.where(Question.class)
                .equalTo("chapterId", chapterId).findAll();
        for (int i = 0; i < questionRealmResults.size(); i++) {
            selectModel(questionRealmResults, i);
        }

        if (onSuccessChapterListener != null) {
            if (rand) onSuccessChapterListener.onSuccess(randQuestionsList(mTestsList));
            else onSuccessChapterListener.onSuccess(mTestsList);
        }

    }

    public void getQuestionsSubject(String examType,
                                    OnSuccessFormatListener<QuestionsSubject>
                                            questionsSubjectOnSuccessFormatSubjectListener) {
        mQuestionsSubjectList = new ArrayList<>();
        switch (examType) {
            case "Basic":
                formattedQuestionsSubject(0, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S1":
                formattedQuestionsSubject(1, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S2":
                formattedQuestionsSubject(2, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S3":
                formattedQuestionsSubject(3, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S4":
                formattedQuestionsSubject(4, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S5":
                formattedQuestionsSubject(5, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S6":
                formattedQuestionsSubject(6, questionsSubjectOnSuccessFormatSubjectListener);
                break;
            case "S7":
                formattedQuestionsSubject(7, questionsSubjectOnSuccessFormatSubjectListener);
                break;
        }
    }

    private void formattedQuestionsSubject(int typeExam,
                                           final OnSuccessFormatListener<QuestionsSubject>
                                                   questionsSubjectOnSuccessFormatSubjectListener) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<Subject>>() {
        }.getType();

        List<Subject> questionsSubjectsList = gson.fromJson(json, type);

        Subject subject = questionsSubjectsList.get(typeExam);

        for (QuestionsSubject question :
                subject.getQuestions()) {
            QuestionsSubject questionsSubject = new QuestionsSubject();
            Chapter chapter = mRealm.where(Chapter.class).equalTo("id",
                    question.getChapterid()).findFirst();
            questionsSubject.setName(chapter.getHeader());
            questionsSubject.setCode(chapter.getCode());
            questionsSubject.setChapterid(chapter.getId());
            mQuestionsSubjectList.add(questionsSubject);
        }
        if (questionsSubjectOnSuccessFormatSubjectListener != null) {
            Collections.sort(mQuestionsSubjectList, new Comparator<QuestionsSubject>() {
                @Override
                public int compare(QuestionsSubject lhs, QuestionsSubject rhs) {
                    Double one = Double.parseDouble(lhs.getCode());
                    Double two = Double.parseDouble(rhs.getCode());
                    return one.compareTo(two);
                }
            });
            questionsSubjectOnSuccessFormatSubjectListener.onSuccess(mQuestionsSubjectList);
        }
    }


    private List<Question> randList(RealmResults<Question> weight) {
        List<Question> rand = new ArrayList<>();
        rand.addAll(weight);
        Collections.shuffle(rand, new Random());
        return rand;
    }

    private List<Test> randQuestionsList(List<Test> list) {
        List<Test> rand = new ArrayList<>();
        rand.addAll(list);
        Collections.shuffle(rand, new Random());
        return rand;
    }

    private void selectModel(List<Question> temp, int i) {
        Question q = temp.get(i);
        Test test = new Test();
        test.setId(q.getId());
        test.setWeight(q.getWeight());
        test.setChapterId(q.getChapterId());
        test.setCode(q.getCode());
        test.setHeader(q.getHeader());
        test.setTestAnswers(getAnswer(q));
        test.setCorrectnum(q.getCorrectnum());
        mTestsList.add(test);
    }

    private List<TestAnswer> getAnswer(Question q) {
        List<TestAnswer> testAnswerList = new ArrayList<>();
        for (Answer qu : q.getAnswers()) {
            TestAnswer testAnswer = new TestAnswer();
            testAnswer.setNum(qu.getNum());
            testAnswer.setText(qu.getText());
            testAnswerList.add(testAnswer);
        }
        return testAnswerList;
    }
}
