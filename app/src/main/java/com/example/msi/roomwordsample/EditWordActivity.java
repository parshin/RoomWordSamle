package com.example.msi.roomwordsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class EditWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_WORD = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY_TRANSLATE = "com.example.android.translatelistsql.REPLY";

    private EditText mEditWordView;
    private EditText mEditTranslateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditWordView = findViewById(R.id.edit_word);
        mEditTranslateView = findViewById(R.id.edit_translate);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText()) &&
                        TextUtils.isEmpty(mEditTranslateView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditWordView.getText().toString();
                    String translate = mEditTranslateView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_WORD, word);
                    replyIntent.putExtra(EXTRA_REPLY_TRANSLATE, translate);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
