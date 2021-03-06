package com.fisheradelakin.drafts;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fisheradelakin.drafts.db.ThoughtsDataSource;
import com.fisheradelakin.drafts.model.Thought;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by temidayo on 1/7/16.
 */
public class DraftsRVAdapter extends RecyclerView.Adapter<DraftsRVAdapter.ViewHolder> {

    private Context mContext;
    private List<Thought> mThoughts;

    public DraftsRVAdapter(Context context, List<Thought> thoughts) {
        mContext = context;
        mThoughts = thoughts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drafts_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Thought thought = mThoughts.get(position);
        holder.thought.setText(thought.getDrafts());

        if (thought.getTitle() != null) {
            holder.thoughtTitle.setText(thought.getTitle());
        }

        long timeAgo = thought.getUpdatedAt();
        if (timeAgo != 0) {

        }
    }

    @Override
    public int getItemCount() {
        return mThoughts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.thought) TextView thought;
        @BindView(R.id.thought_title) TextView thoughtTitle;
        @BindView(R.id.time_ago) TextView timeAgo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, CreateNewDraftActivity.class);
            intent.putExtra("thought", mThoughts.get(getLayoutPosition()));
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO: Switch to share and copy
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Thought", mThoughts.get(getLayoutPosition()).getDrafts());
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(mContext, "Draft copied to clipboard.", Toast.LENGTH_SHORT).show();

            return true;
        }
    }
}
