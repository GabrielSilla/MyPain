package com.example.gabriel.mypain;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Controllers.DatabaseController;

public class DiagFinal extends AppCompatActivity {
    ArrayList<String> injuriesList;

    private String pacientName;
    private String pacientCpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diag_final);

        try{
            Intent lastIntent = getIntent();
            injuriesList = lastIntent.getStringArrayListExtra("selectedInjuries");
            pacientName = lastIntent.getStringExtra("pacientName");
            pacientCpf = lastIntent.getStringExtra("pacientCpf");

            TextView pacientText = (TextView) findViewById(R.id.pacient_final_txt);
            pacientText.setText("Paciente: " + pacientName + "\nCPF: " + pacientCpf);

            TextView injuriesTextList = (TextView) findViewById(R.id.injuries_final_txt);
            injuriesTextList.setText(returnInjurieListAsText(injuriesList));

            Button btn_discart = (Button) findViewById(R.id.button_discart);
            btn_discart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), InitialScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });

            Button btn_save_diag = (Button) findViewById(R.id.button_save_diag);
            btn_save_diag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseController db = new DatabaseController(v.getContext());
                    Snackbar.make(v, db.saveDiag(pacientCpf,returnInjurieListAsText(injuriesList)), Snackbar.LENGTH_SHORT).show();
                }
            });
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
