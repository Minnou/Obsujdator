package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        ArrayList<DiscussionClass> discussions = new ArrayList<>();
        discussions.add(new DiscussionClass("Меня хотят съесть пришельцы", "Помогите меня хотят съесть пришельцы"));
        discussions.add(new DiscussionClass("Я очень вкусно поел", "А вы?"));
        RecyclerView rv = findViewById(R.id.rv);
        //rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new DiscussionViewAdapter(mContext,discussions));
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setAdapter(new DiscussionViewAdapter(mContext,discussions));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void addDiscussion(View view) {
        Intent intent = new Intent(this, AddDiscussionActivity.class);
        startActivity(intent);
    }

}