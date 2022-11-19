package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiscussionViewAdapter extends RecyclerView.Adapter<DiscussionViewAdapter.ViewHolder> {

    private final List<DiscussionClass> mValues;
    private Context mContext;

    public DiscussionViewAdapter(Context context,List<DiscussionClass> items) {
        mContext = context;
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //В методе адаптера onBindViewHolder() связываем используемые текстовые метки с данными
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DiscussionClass discussion = mValues.get(position);
        holder.mTitle.setText(discussion.getTitle());
        holder.mText.setText(discussion.getText());
        holder.mId.setText(discussion.getId());
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OpenDiscussion.class);
                intent.putExtra("id", discussion.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * @return количество элементов списка.
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //Класс MyViewHolder на основе ViewHolder служит для оптимизации ресурсов.
    // Он служит своеобразным контейнером для всех компонентов, которые входят в элемент списка.
    // При этом RecyclerView создаёт ровно столько контейнеров, сколько нужно для отображения на экране.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTitle, mText, mId;
        public final Button mButton;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mText = itemView.findViewById(R.id.text);
            mButton = itemView.findViewById(R.id.discuss_button);
            mId = itemView.findViewById(R.id.threadId);
        }
    }
}

