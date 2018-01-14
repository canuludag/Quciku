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

        ViewHolder(View itemView) {
            super(itemView);

            // Define your items in here
            tvRcvLastTranslatedWordItem = itemView.findViewById(R.id.tvRcvLastTranslatedWordItem);

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
        holder.tvRcvLastTranslatedWordItem.setTypeface(mFontManager.getRobotoSlabRegular());
        holder.tvRcvLastTranslatedWordItem.setText(lastTranslatedWordsList.get(position));

    }

    @Override
    public int getItemCount() {
        return lastTranslatedWordsList.size();
    }
}
