package com.example.gabriel.mypain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Body_full extends AppCompatActivity {
    private String orientation = "Front";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.change_rotation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView body = (ImageView) findViewById(R.id.body_picture);

                if(orientation.equals("Front")){
                    body.setImageResource(R.drawable.body_back);
                    orientation = "Back";
                }
                else if(orientation.equals("Back")){
                    body.setImageResource(R.drawable.body_front);
                    orientation = "Front";
                }
            }
        });
    }

}
