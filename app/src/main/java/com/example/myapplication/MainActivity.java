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
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView rv = findViewById(R.id.rv);
        ArrayList<DiscussionClass> discussions = new ArrayList<>();
        NetClient n = new NetClient(getResources().getString(R.string.server_ip),getResources().getInteger(R.integer.server_port));
        Thread receiveThread = new Thread(() -> {
            ArrayList<DiscussionClass> temp = n.receiveDiscussionsFromServer();
            int length = temp.size();
            for (int i = 0; i < length; i++)
                discussions.add(temp.remove(0));
        });
        receiveThread.start();
        try {
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //discussions.add(new DiscussionClass("22222","Меня хотят съесть пришельцы", "Помогите меня хотят съесть пришельцы"));
        //discussions.add(new DiscussionClass("22222","Я очень вкусно поел", "А вы?"));
        rv.setAdapter(new DiscussionViewAdapter(mContext,discussions));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final String[] title = new String[1];
                NetClient n = new NetClient(getResources().getString(R.string.server_ip),getResources().getInteger(R.integer.server_port));
                Thread receiveThread = new Thread(() -> {
                    discussions.clear();
                    ArrayList<DiscussionClass> temp = n.receiveDiscussionsFromServer();
                    int length = temp.size();
                    for (int i = 0; i < length; i++)
                        discussions.add(temp.remove(0));
                });
                receiveThread.start();
                try {
                    receiveThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rv.setAdapter(new DiscussionViewAdapter(mContext,discussions));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void addDiscussion(View view) {
        Intent intent = new Intent(this, AddDiscussionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}