package com.example.msi.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private LiveData<List<Word>> mAllWords;

    private LiveData<Integer> mWordsCount;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mWordsCount = mRepository.getWordsCount();
    }


    LiveData<List<Word>> getAllWords() { return mAllWords; }

    LiveData<Integer> getWordsCount() { return mWordsCount; }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void delete(Word word) { mRepository.delete(word); }

}
