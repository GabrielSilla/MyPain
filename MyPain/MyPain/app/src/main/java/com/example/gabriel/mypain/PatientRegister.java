package com.example.gabriel.mypain;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import Controllers.DatabaseController;

public class PatientRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);



        Button backButton = (Button) findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener(){
            TextView name = (TextView) findViewById(R.id.txtNome);
            TextView cpf = (TextView) findViewById(R.id.txtCpf);
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText()) && TextUtils.isEmpty(cpf.getText())){
                    Toast.makeText(v.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseController db = new DatabaseController(getBaseContext());
                    String result = db.registerPacienteIntoDB(name.getText().toString(), cpf.getText().toString());

                    Snackbar.make(v, result, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
