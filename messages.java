package com.example.samuelhimself.agent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class messages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

//        /---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.messagetool);
        setSupportActionBar(toolbar);

    }
}
