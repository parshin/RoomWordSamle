package com.example.msi.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    private LiveData<Integer> mWordsCount;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
        mWordsCount = mWordDao.getDataCount();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    LiveData<Integer> getWordsCount() { return mWordsCount; }

    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void delete (Word word) { new insertAsyncTask(mWordDao).execute(word); }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
