package com.example.msi.roomwordsample;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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
        final Button button_get_translate = findViewById(R.id.button_get_translate);
        button_get_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_translate();
            }
        });
    }

    private void get_translate(){
        // TODO: get Bearer token () once a day
        final TextView mTextView = findViewById(R.id.response_text);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://developers.lingvolive.com/api/v1.1/authenticate";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getMiniCard(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic OTkwMjllNzMtNjU5Mi00NDQwLTllNDYtYmIzZjI3NmUxZDI0OjQ5YjNhOWI1NTdhNzQzZTk4YmE1NjliNzMwYTM2NmQ3");
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void getMiniCard(final String bearer){
        final TextView mTextView = findViewById(R.id.response_text);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://developers.lingvolive.com/api/v1/Minicard?text=text&srcLang=1033&dstLang=1049";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTextView.setText("Card is: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + bearer);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}