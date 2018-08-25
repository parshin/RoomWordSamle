package com.example.msi.roomwordsample;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_WORD = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY_TRANSLATE = "com.example.android.translatelistsql.REPLY";
    public static final String EXTRA_REPLY_DEFINITION = "com.example.android.definitionlistsql.REPLY";

    private EditText mEditWordView;
    private EditText mEditTranslateView;
    private TextView mTextDefinition;

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

        final Button get_translate = findViewById(R.id.button_get_translate);
        get_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_translate();
            }
        });

        final Button get_definition = findViewById(R.id.button_get_definition);
        get_definition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_definition();
            }
        });
    }

    private void get_translate(){
        // TODO: get Bearer token () once a day

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
                // TODO show errors
//                mTextView.setText(error.getMessage());
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

    private void get_definition(){
        new CallbackTask().execute(dictionaryEntries());
    }

    private String dictionaryEntries() {
        mEditWordView = findViewById(R.id.edit_new_word);

        final String language = "en";
        final String word = mEditWordView.getText().toString();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

    private class CallbackTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            final String app_id = "d00c02f0";
            final String app_key = "35c0680f58a27f5852356f9c4d842f63";
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            int i, j, k, l;
            String definition = "";

            mTextDefinition = findViewById(R.id.text_definition);
            String lineSep = System.getProperty("line.separator");

            try {
                JSONObject jsondef = new JSONObject(result);
                JSONArray results = jsondef.getJSONArray("results");
                for (i=0; i < results.length(); i++){
                    JSONObject jresult = results.getJSONObject(i);
                    JSONArray lexicalentries = jresult.getJSONArray("lexicalEntries");
                    for (j=0; j < lexicalentries.length(); j++) {
                        JSONObject jolentrie = lexicalentries.getJSONObject(j);
                        JSONArray entries = jolentrie.getJSONArray("entries");
                        for (k=0; k < entries.length(); k++) {
                            JSONObject joentrie = entries.getJSONObject(k);
                            JSONArray senses = joentrie.getJSONArray("senses");
                            for (l=0; l < senses.length(); l++){
                                JSONObject josenses = senses.getJSONObject(l);
                                JSONArray definitions = josenses.getJSONArray("definitions");
                                definition = definition + String.valueOf(l+1) + ". " + definitions.getString(0) + lineSep;
                            }
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            mTextDefinition.setText(definition);
        }
    }

    private void getMiniCard(final String bearer){
//        final TextView mTextView = findViewById(R.id.response_text);
        RequestQueue queue = Volley.newRequestQueue(this);
        mEditWordView = findViewById(R.id.edit_new_word);

        String url = String.format("https://developers.lingvolive.com/api/v1/Minicard?text=%1$s&srcLang=1033&dstLang=1049", mEditWordView.getText());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mEditTranslateView = findViewById(R.id.edit_new_translate);

//                        mTextView.setText("Card is: "+ response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jsonTranslate = jsonObject.getString("Translation");
                            JSONObject jsonObjectTranslate = new JSONObject(jsonTranslate);
                            String translate = jsonObjectTranslate.getString("Translation");
                            mEditTranslateView.setText(translate);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mTextView.setText(error.getMessage());
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