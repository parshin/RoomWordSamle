package com.example.msi.roomwordsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words
    private OnItemClicked onClick;
    private Context mContext;

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }



    public interface OnItemClicked {
//        void onItemClick(int position);
    }

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // Create new views
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClick.onItemClick(position);

                Intent intent = new Intent(mContext, NewWordActivity.class);
//                intent.putExtra("word", mWords.get(position).getWord());
//                intent.putExtra("translate", mWords.get(position).getTranslate());
                Word current = getWord(position);
                intent.putExtra("word_object", current);
                mContext.startActivity(intent);
            }

        });

        if (mWords != null) {
//            Word current = mWords.get(position);
            Word current = getWord(position);
            holder.wordItemView.setText(current.getWord() + " - " + current.getTranslate());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    public Word getWord(int position){

//        if (mWords != null) {
            Word current = mWords.get(position);
            return current;
//        } else {
//            return ;
////            Toast.makeText(
////                    this,
////                    R.string.empty_not_saved,
////                    Toast.LENGTH_LONG).show();
//        }

    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}