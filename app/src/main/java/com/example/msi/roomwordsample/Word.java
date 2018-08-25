package com.example.msi.roomwordsample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "word_table")
public class Word implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;
    public String getWord() {
        return this.mWord;
    }

    @ColumnInfo(name = "translate")
    private String mTranslate;
    public String getTranslate() {
        return this.mTranslate;
    }

    @ColumnInfo(name = "definition")
    private String mDefinition;

    public String getmDefinition() {
        return this.mDefinition;
    }

    public Word(@NonNull String word, String translate, String definition) {
        this.mWord = word;
        this.mTranslate = translate;
        this.mDefinition = definition;
    }

    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

}
