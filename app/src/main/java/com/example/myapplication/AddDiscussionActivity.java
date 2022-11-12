package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AddDiscussionActivity extends AppCompatActivity {
    TextView title;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        title = findViewById(R.id.title_text);
        text = findViewById(R.id.text);
    }
    public void createDiscussion(){

    }

}