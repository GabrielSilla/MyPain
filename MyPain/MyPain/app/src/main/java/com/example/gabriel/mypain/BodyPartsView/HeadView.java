package com.example.gabriel.mypain.BodyPartsView;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gabriel.mypain.R;

public class HeadView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String orientation = extras.getString("orientation");

        Toast.makeText(getBaseContext(), orientation, Toast.LENGTH_SHORT).show();

        ImageView headView = (ImageView) findViewById(R.id.head_view);

        SetButtonsSize();

        if (orientation.equals("Front")) {
            headView.setImageResource(R.drawable.head_front);
        } else {
            headView.setImageResource(R.drawable.head_back);
        }
    }

    public void SetButtonsSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        Button btn_forehead = (Button) findViewById(R.id.button_forehead);
        ViewGroup.LayoutParams params = btn_forehead.getLayoutParams();
        params.width = screenWidth / 2;
        params.height = screenHeight / 12;

        btn_forehead.setLayoutParams(params);

        Button btn_brain = (Button) findViewById(R.id.button_brain);
        ViewGroup.LayoutParams params1 = btn_brain.getLayoutParams();
        params1.width = screenWidth / 3;
        params1.height = screenHeight / 12;

        btn_brain.setLayoutParams(params1);

        Button btn_lf_eye = (Button) findViewById(R.id.button_left_eye);
        ViewGroup.LayoutParams params3 = btn_lf_eye.getLayoutParams();
        params3.width = screenWidth / 9;
        params3.height = screenHeight / 15;

        btn_lf_eye.setLayoutParams(params3);

        Button btn_rh_eye = (Button) findViewById(R.id.button_right_eye);
        ViewGroup.LayoutParams params4 = btn_rh_eye.getLayoutParams();asdasd
        params4.width = screenWidth / 9;
        params4.height = screenHeight / 15;

        btn_rh_eye.setLayoutParams(params4);

        Button btn_nose = (Button) findViewById(R.id.button_nose);
        ViewGroup.LayoutParams params5 = btn_nose.getLayoutParams();
        params5.width = screenWidth / 10;
        params5.height = screenHeight / 7;

        btn_nose.setLayoutParams(params5);

    }
}