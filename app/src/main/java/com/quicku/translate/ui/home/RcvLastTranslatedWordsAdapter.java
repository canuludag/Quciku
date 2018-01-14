package com.quicku.translate.ui.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.utils.FontManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class RcvLastTranslatedWordsAdapter extends RecyclerView.Adapter<RcvLastTranslatedWordsAdapter.ViewHolder> {

    private ArrayList<String> lastTranslatedWordsList;
    private FontManager mFontManager;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRcvLastTranslatedWordItem;
        TextView tvRcvLastTranslatedWordItem2;

        ViewHolder(View itemView) {
            super(itemView);

            // Define your items in here
            tvRcvLastTranslatedWordItem = itemView.findViewById(R.id.tvRcvLastTranslatedWordItem);
            tvRcvLastTranslatedWordItem2 = itemView.findViewById(R.id.tvRcvLastTranslatedWordItem2);

        }
    }

    @Inject
    RcvLastTranslatedWordsAdapter(ArrayList<String> lastTranslatedWordsList, FontManager fontManager) {
        this.lastTranslatedWordsList = lastTranslatedWordsList;
        mFontManager = fontManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_last_translated_words_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // set your items here
        holder.tvRcvLastTranslatedWordItem.setTypeface(mFontManager.getRobotoSlabBold());
        holder.tvRcvLastTranslatedWordItem2.setTypeface(mFontManager.getRobotoSlabRegular());
        String[] wordPair = lastTranslatedWordsList.get(position).split(" = ");
        String word = wordPair[0];
        String wordTranslation = wordPair[1];
        holder.tvRcvLastTranslatedWordItem.setText(word);
        holder.tvRcvLastTranslatedWordItem2.setText(wordTranslation);

    }

    @Override
    public int getItemCount() {
        return lastTranslatedWordsList.size();
    }
}
