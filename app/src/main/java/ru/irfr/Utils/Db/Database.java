package ru.irfr.Utils.Db;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import ru.irfr.Interface.OnWriteTestListener;
import ru.irfr.Model.DB.Answer;
import ru.irfr.Model.DB.Chapter;
import ru.irfr.Model.DB.Exam;
import ru.irfr.Model.DB.Question;
import ru.irfr.Model.Delete;
import ru.irfr.Utils.Storage.MyStorage;

public class Database {

    private static final String TAG = "realm";
    private OnWriteTestListener mOnWriteTestListener;
    private static Database instance;
    private Context mContext;
    private Realm mRealm;
    private Gson mGson;
    private List<Exam> mExamList;
    private List<Question> mQuestionList;
    private MyStorage mStorage;
    private String json;
    private RealmAsyncTask mRealmAsyncTask;
    private ArrayList<Chapter> mChapterList;
    private List<Delete> idQuestionList;

    private Database(Context context, Realm realm) {
        this.mContext = context;
        mRealm = realm;
        mStorage = MyStorage.init(mContext);
        mGson = new Gson();
    }

    public static Database init(Context context, Realm realm) {
        if (instance == null) {
            instance = new Database(context, realm);
        }
        return instance;
    }

    public void setOnWriteTestListener(OnWriteTestListener onWriteTestListener) {
        mOnWriteTestListener = onWriteTestListener;
    }

    private void initExamTypeList() {
        mExamList = new ArrayList<>();
        json = mStorage.readFile("irfr", "et");
        mExamList = mGson.fromJson(json, new TypeToken<List<Exam>>() {
        }.getType());
    }

    private void initChapterList() {
        mChapterList = new ArrayList<>();
        json = mStorage.readFile("irfr", "c");
        mChapterList = mGson.fromJson(json, new TypeToken<List<Chapter>>() {
        }.getType());
        Collections.sort(mChapterList, new Comparator<Chapter>() {
            @Override
            public int compare(Chapter lhs, Chapter rhs) {
                return ((Integer) lhs.getId()).compareTo(rhs.getId());
            }
        });
    }

    public void createTable() {
        initExamTypeList();
        mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //     Log.d(TAG, "Начало записи createExamType: ");
                for (Exam exam : mExamList) {
                    Exam realmExam = realm.createObject(Exam.class);
                    realmExam.setId(exam.getId());
                    realmExam.setHeader(exam.getHeader());
                    realmExam.setCode(exam.getCode());
                    realmExam.setPrice(exam.getPrice());
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //     Log.d(TAG, "Конец записи createExamType: ");
                //    readExam();
                createChapter();
            }
        });

    }

    private void createChapter() {
        initChapterList();
        mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //  Log.d(TAG, "Начало записи createChapter: ");
                for (Chapter c : mChapterList) {
                    Chapter realmChapter = realm.createObject(Chapter.class);
                    realmChapter.setId(c.getId());
                    realmChapter.setHeader(c.getHeader());
                    realmChapter.setTypeId(c.getTypeId());
                    realmChapter.setCode(c.getCode());
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //   Log.d(TAG, "Конец записи createChapter: ");
                //   readChapter();
                createQuestion();
            }
        });

    }

    private void createQuestion() {
        mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<Chapter> chapterList = realm.where(Chapter.class).findAll();
                mQuestionList = new ArrayList<>();
                //Log.d(TAG, "Начало записи createQuestion: ");
                for (Chapter chapter : chapterList) {
                    mQuestionList.clear();
                    json = mStorage.readFile("irfr", "q" + chapter.getId());
                    mQuestionList = mGson.fromJson(json, new TypeToken<List<Question>>() {
                    }.getType());
                    for (Question q : mQuestionList) {
                        Question realmQuestion = realm.createObject(Question.class);
                        realmQuestion.setId(q.getId());
                        realmQuestion.setWeight(q.getWeight());
                        realmQuestion.setChapterId(chapter.getId());
                        realmQuestion.setHeader(q.getHeader());
                        realmQuestion.setCode(q.getCode());
                        realmQuestion.setCorrectnum(q.getCorrectnum());
                        realmQuestion.getAnswers().addAll(q.getAnswers());
                    }
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
            //    deletePicQuestions();
                if (mOnWriteTestListener != null) mOnWriteTestListener.onTestWriteEnd();
               // Log.d(TAG, "Конец записи createQuestion: ");
               // readQuestion();
            }
        });

    }

    private void deletePicQuestions(){
        initIdQuestionList();
        mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < idQuestionList.size(); i++) {
                    Delete delete = idQuestionList.get(i);
                    Question questions = realm.where(Question.class).equalTo("id", delete.getId())
                            .equalTo("chapterId", delete.getChapterId()).findFirst();
                    questions.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (mOnWriteTestListener != null) mOnWriteTestListener.onTestWriteEnd();
           //     readQuestion();
            }
        });
    }

    private void initIdQuestionList(){
        idQuestionList = new ArrayList<>();
        idQuestionList.add(new Delete(2160,93));
        idQuestionList.add(new Delete(2161,93));
        idQuestionList.add(new Delete(2162,93));
        idQuestionList.add(new Delete(3192,18));
        idQuestionList.add(new Delete(3193,18));
        idQuestionList.add(new Delete(3194,18));
        idQuestionList.add(new Delete(3195,18));
        idQuestionList.add(new Delete(4506,120));
        idQuestionList.add(new Delete(4508,120));
        idQuestionList.add(new Delete(4509,120));
        idQuestionList.add(new Delete(4510,120));
        idQuestionList.add(new Delete(4511,120));
        idQuestionList.add(new Delete(4512,120));
        idQuestionList.add(new Delete(4710,37));
        idQuestionList.add(new Delete(4712,37));
        idQuestionList.add(new Delete(4713,37));
        idQuestionList.add(new Delete(4714,37));
        idQuestionList.add(new Delete(4715,37));
        idQuestionList.add(new Delete(4716,37));
    }

    private void readExam() {
        RealmResults<Exam> realmResults = mRealm.where(Exam.class).findAll();
        Log.d(TAG, "################# Exam #################");
        for (Exam ex : realmResults) {
            Log.d(TAG, "id = " + ex.getId());
            Log.d(TAG, "title = " + ex.getHeader());
            Log.d(TAG, "code = " + ex.getCode());
        }
        Log.d(TAG, "################# Exam #################");
    }

    private void readChapter() {
        RealmResults<Chapter> realmResults = mRealm.where(Chapter.class).findAll();
        Log.d(TAG, "################# Chapter #################");
        for (Chapter chapter : realmResults) {
            Log.d(TAG, "id = " + chapter.getId());
            Log.d(TAG, "header = " + chapter.getHeader());
            Log.d(TAG, "typeId = " + chapter.getTypeId());
            Log.d(TAG, "code = " + chapter.getCode());
        }
        Log.d(TAG, "################# Chapter #################");
    }

    private void readQuestion() {
        RealmResults<Question> realmResults = mRealm.where(Question.class).findAll();
        Log.d(TAG, "################# Question #################");
        Log.d(TAG, "Question size " + realmResults.size());
        for (int i = 0; i < 10; i++) {
            Question q = realmResults.get(i);
            Log.d(TAG, "id = " + q.getId());
            Log.d(TAG, "charterId = " + q.getChapterId());
            Log.d(TAG, "header = " + q.getHeader());
            Log.d(TAG, "code = " + q.getCode());
            Log.d(TAG, "correctnum = " + q.getCorrectnum());
            Log.d(TAG, "weight = " + q.getWeight());
            Log.d(TAG, "answer:");
            for (Answer a : q.getAnswers()) {
                Log.d(TAG, "Text = " + a.getText());
                Log.d(TAG, "Num = " + a.getNum());
            }
        }
        mRealm.close();
        Log.d(TAG, "################# Question #################");

    }

    public void stop() {
        if (mRealmAsyncTask != null && !mRealmAsyncTask.isCancelled()) {
            mRealmAsyncTask.cancel();
            mRealmAsyncTask = null;
            mRealm.close();
        }
    }


}
