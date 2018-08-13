package com.example.msi.roomwordsample;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_WORD = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY_TRANSLATE = "com.example.android.translatelistsql.REPLY";

    private EditText mEditWordView;
    private EditText mEditTranslateView;

    private WordListAdapter mWordListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_new_word);
        mEditTranslateView = findViewById(R.id.edit_new_translate);

        Intent intent = getIntent();
        Word word = (Word)intent.getSerializableExtra("word_object");
        if (word != null) {
            mEditWordView.setText(word.getWord());
            mEditTranslateView.setText(word.getTranslate());
        }

        FloatingActionButton fab_save = findViewById(R.id.fab_save);
        fab_save.setOnClickListener(new View.OnClickListener() {
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
//        final Button button = findViewById(R.id.button_save);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent replyIntent = new Intent();
//                if (TextUtils.isEmpty(mEditWordView.getText()) &&
//                            TextUtils.isEmpty(mEditTranslateView.getText())) {
//                    setResult(RESULT_CANCELED, replyIntent);
//                } else {
//                    String word = mEditWordView.getText().toString();
//                    String translate = mEditTranslateView.getText().toString();
//                    replyIntent.putExtra(EXTRA_REPLY_WORD, word);
//                    replyIntent.putExtra(EXTRA_REPLY_TRANSLATE, translate);
//                    setResult(RESULT_OK, replyIntent);
//                }
//                finish();
//            }
//        });

//        final Button gtranslate_btn = findViewById(R.id.button_google_translate);
//        gtranslate_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}