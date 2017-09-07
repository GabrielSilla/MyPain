package com.example.gabriel.mypain;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.gabriel.mypain.BodyPartsView.HeadView;

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

        Button btn_head = (Button) findViewById(R.id.button_head);
        btn_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HeadView.class);
                intent.putExtra("orientation", orientation);
                startActivity(intent);
            }
        });

        Button btn_torso = (Button) findViewById(R.id.button_torso);
        btn_torso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Torso selected", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_genital = (Button) findViewById(R.id.button_genital);
        btn_genital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Genital selected", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_left_arm = (Button) findViewById(R.id.button_left_arm);
        btn_left_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Left arm selected", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_right_arm = (Button) findViewById(R.id.button_right_arm);
        btn_right_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Right arm selected", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_right_leg = (Button) findViewById(R.id.button_right_leg);
        btn_right_leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Right leg selected", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_left_leg = (Button) findViewById(R.id.button_left_leg);
        btn_left_leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Left leg selected", Toast.LENGTH_SHORT).show();
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
        params2.height = screenHeight / 15;

        btn_genital.setLayoutParams(params2);

        Button leg_spacing = (Button) findViewById(R.id.leg_spacing);
        ViewGroup.LayoutParams params3 = leg_spacing.getLayoutParams();
        params3.width = screenWidth / 20;
        params3.height = screenHeight / 3;

        leg_spacing.setLayoutParams(params3);

        Button btn_left_leg = (Button) findViewById(R.id.button_left_leg);
        ViewGroup.LayoutParams params4 = btn_left_leg.getLayoutParams();
        params4.width = screenWidth / 7;
        params4.height = screenHeight / 3;

        btn_left_leg.setLayoutParams(params4);

        Button btn_right_leg = (Button) findViewById(R.id.button_right_leg);
        ViewGroup.LayoutParams params5 = btn_right_leg.getLayoutParams();
        params5.width = screenWidth / 7;
        params5.height = screenHeight / 3;

        btn_right_leg.setLayoutParams(params5);

        Button btn_left_arm = (Button) findViewById(R.id.button_left_arm);
        ViewGroup.LayoutParams params6 = btn_left_arm.getLayoutParams();
        params6.width = screenWidth / 6;
        params6.height = screenHeight / 4;

        btn_left_arm.setLayoutParams(params6);

        Button btn_right_arm = (Button) findViewById(R.id.button_right_arm);
        ViewGroup.LayoutParams params7 = btn_right_arm.getLayoutParams();
        params7.width = screenWidth / 6;
        params7.height = screenHeight / 4;

        btn_right_arm.setLayoutParams(params7);
    }
}
