package com.example.gabriel.mypain;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Body_full extends AppCompatActivity {
    private String orientation = "Front";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetButtonsSize();

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


    public void SetButtonsSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        Button btn_head = (Button) findViewById(R.id.button_head);
        ViewGroup.LayoutParams params = btn_head.getLayoutParams();
        params.width = screenWidth / 6;
        params.height = screenHeight / 6;

        btn_head.setLayoutParams(params);

        Button btn_torso = (Button) findViewById(R.id.button_torso);
        ViewGroup.LayoutParams params1 = btn_torso.getLayoutParams();
        params1.width = screenWidth / 4;
        params1.height = screenHeight / 5;

        btn_torso.setLayoutParams(params1);

        Button btn_genital = (Button) findViewById(R.id.button_genital);
        ViewGroup.LayoutParams params2 = btn_genital.getLayoutParams();
        params2.width = screenWidth / 4;
        params2.height = screenHeight / 8;

        btn_genital.setLayoutParams(params2);
    }
}
