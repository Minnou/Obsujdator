package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddReplyActivity extends AppCompatActivity {
    TextView title;
    TextView text;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply);
        title = findViewById(R.id.title_text);
        text = findViewById(R.id.text);
        Bundle extras = getIntent().getExtras();
        id = "";
        if (extras != null)
        {
            id = extras.getString("id");
        }
    }

    public void postReply(View view){
        NetClient n = new NetClient(getResources().getString(R.string.server_ip),getResources().getInteger(R.integer.server_port));
        if(text.getText().toString().equals(""))
            return;
        if(title.getText().toString().equals(""))
            return;
        Thread receiveThread = new Thread(() -> {
            n.postReply(id,title.getText().toString(), text.getText().toString());
        });
        receiveThread.start();
        try {
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, OpenDiscussion.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}