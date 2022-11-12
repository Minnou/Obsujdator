package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ReplyBinding;

import java.util.List;

public class ReplyViewAdapter extends RecyclerView.Adapter<ReplyViewAdapter.ViewHolder> {

    private final List<ReplyClass> mValues;


    public ReplyViewAdapter(List<ReplyClass> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ReplyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //В методе адаптера onBindViewHolder() связываем используемые текстовые метки с данными
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ReplyClass reply = mValues.get(position);
        holder.mTitle.setText(reply.getTitle());
        holder.mText.setText(reply.getText());

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

        public final TextView mTitle, mText;

        public ViewHolder(ReplyBinding binding) {
            super(binding.getRoot());
            mTitle = binding.title;
            mText = binding.text;
        }
    }
}

