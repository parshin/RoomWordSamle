package com.example.msi.roomwordsample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;
    public String getWord() {return this.mWord;}

    @ColumnInfo(name = "translate")
    private String mTranslate;
    public String getTranslate() {return this.mTranslate;}

    public Word(@NonNull String word, String translate) {this.mWord = word; this.mTranslate = translate;}

}
