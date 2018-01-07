package com.quicku.translate.ui.home;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.utils.FontManager;

import java.util.ArrayList;

public class RcvLastTranslatedWordsAdapter extends RecyclerView.Adapter<RcvLastTranslatedWordsAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<String> lastTranslatedWordsList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvRcvLastTranslatedWordItem;

        public ViewHolder(View itemView) {
            super(itemView);

            // Define your items in here
            tvRcvLastTranslatedWordItem = (TextView) itemView.findViewById(R.id.tvRcvLastTranslatedWordItem);

        }
    }

    public RcvLastTranslatedWordsAdapter(Activity activity, ArrayList<String> lastTranslatedWordsList) {
        this.activity = activity;
        this.lastTranslatedWordsList = lastTranslatedWordsList;
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
        holder.tvRcvLastTranslatedWordItem.setTypeface(FontManager.getRobotoSlabRegular(activity));
        holder.tvRcvLastTranslatedWordItem.setText(lastTranslatedWordsList.get(position));

    }

    @Override
    public int getItemCount() {
        return lastTranslatedWordsList.size();
    }
}
