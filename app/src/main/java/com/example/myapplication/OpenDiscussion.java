package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

public class OpenDiscussion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_discussion);
        ArrayList<ReplyClass> replies = new ArrayList<>();
        replies.add(new ReplyClass("Ты че тут забыл", "ЧЁРТ?"));
        replies.add(new ReplyClass("И?", "Ну и чё ты сказать-то хотел?"));
        RecyclerView rv = findViewById(R.id.rv);
        //rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new ReplyViewAdapter(replies));
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setAdapter(new ReplyViewAdapter(replies));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}