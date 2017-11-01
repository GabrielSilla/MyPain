package com.example.gabriel.mypain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DiagFinal extends AppCompatActivity {
    ArrayList<String> injuriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent lastIntent = getIntent();
        injuriesList = lastIntent.getStringArrayListExtra("Injuries");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diag_final);

        try{
            Intent intentInjuries = getIntent();
            injuriesList = intentInjuries.getStringArrayListExtra("Injuries");

            TextView injuriesTextList = (TextView) findViewById(R.id.injuries_final_txt);
            injuriesTextList.setText(returnInjurieListAsText(injuriesList));
        }
        catch(Exception ex){
            Toast.makeText(this.getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    private String returnInjurieListAsText(ArrayList<String> injuriesList){
        String injurieText = "";

        for(String injurie : injuriesList ){
            injurieText = injurieText + injurie + "\n";
        }

        return injurieText;
    }
}
