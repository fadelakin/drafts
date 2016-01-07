package com.fisheradelakin.drafts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fisheradelakin.drafts.db.ThoughtsDataSource;
import com.fisheradelakin.drafts.model.Thought;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return mThoughts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView thought;

        public ViewHolder(View itemView) {
            super(itemView);

            thought = (TextView) itemView.findViewById(R.id.thought);
        }

        public void updateThought() {
            Thought thought = mThoughts.get(getLayoutPosition());
            ThoughtsDataSource dataSource = new ThoughtsDataSource(mContext);
            dataSource.updateThought(thought);
        }
    }
}
