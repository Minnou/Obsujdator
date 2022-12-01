package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

public class OpenDiscussion extends AppCompatActivity {
    String id;
    ReplyClass originalPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_discussion);
        Bundle extras = getIntent().getExtras();
        id = "";
        if (extras != null)
        {
            id = extras.getString("id");
            String OPtitle = extras.getString("title");
            String OPtext = extras.getString("text");
            originalPost = new ReplyClass(id,OPtitle,OPtext);
        }
        final String fId = id;
        ArrayList<ReplyClass> replies = new ArrayList<>();
        NetClient n = new NetClient(getResources().getString(R.string.server_ip),getResources().getInteger(R.integer.server_port));
        Thread receiveThread = new Thread(() -> {
            ArrayList<ReplyClass> temp = n.receiveRepliesFromServer(fId);
            int length = temp.size();
            replies.add(originalPost);
            for (int i = 0; i < length; i++)
                replies.add(temp.remove(0));
        });
        receiveThread.start();
        try {
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecyclerView rv = findViewById(R.id.rv);
        rv.setAdapter(new ReplyViewAdapter(replies));
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetClient n = new NetClient(getResources().getString(R.string.server_ip),getResources().getInteger(R.integer.server_port));
                Thread receiveThread = new Thread(() -> {
                    replies.clear();
                    ArrayList<ReplyClass> temp = n.receiveRepliesFromServer(fId);
                    int length = temp.size();
                    replies.add(originalPost);
                    for (int i = 0; i < length; i++)
                        replies.add(temp.remove(0));
                });
                receiveThread.start();
                try {
                    receiveThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rv.setAdapter(new ReplyViewAdapter(replies));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void addReply(View view){
        Intent intent = new Intent(this, AddReplyActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}