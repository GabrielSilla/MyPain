package com.example.gabriel.mypain;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import Controllers.DatabaseController;
import Resources.CreateDB;

public class PacientSelect extends AppCompatActivity {

    AlertDialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient_select);

        final DatabaseController dbController = new DatabaseController(getBaseContext());

        final ListView pacientList = (ListView) findViewById(R.id.pacient_select_list);
        pacientList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        final Cursor cursor = dbController.getAllPacient();

        String[] nomeCampos = new String[] {CreateDB.PATIENT_NAME, CreateDB.PATIENT_CPF};
        int[] idViews = new int[] {R.id.txt_name_list, R.id.txt_cpf_list};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_two_text,cursor,nomeCampos,idViews, 0);

        pacientList.setAdapter(adapter);

        ImageButton button_search = (ImageButton) findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_pacient_cpf = (EditText) findViewById(R.id.txt_pacient_cpf);
                if (txt_pacient_cpf.getText().toString().isEmpty()) {
                    Snackbar.make(v, "Digite um CPF para buscar!", Snackbar.LENGTH_SHORT).show();
                } else {
                    Cursor newcursor = dbController.getPacientByCpf(txt_pacient_cpf.getText().toString());

                    String[] nomeCampos = new String[]{CreateDB.PATIENT_NAME, CreateDB.PATIENT_CPF};
                    int[] idViews = new int[]{R.id.txt_name_list, R.id.txt_cpf_list};

                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                            R.layout.listview_two_text, newcursor, nomeCampos, idViews, 0);

                    pacientList.setAdapter(adapter);
                }
            }
        });

        pacientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView pacientName = (TextView) view.findViewById(R.id.txt_name_list);
                final TextView pacientCpf = (TextView) view.findViewById(R.id.txt_cpf_list);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmar Paciente");
                builder.setMessage("\n Deseja confirmar o Paciente? \n\n\n Nome: " + pacientName.getText().toString()
                        + " \n CPF: " + pacientCpf.getText().toString());

                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alerta.cancel();
                    }
                });
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), InjuriesList.class);
                        intent.putExtra("pacientName", pacientName.getText().toString());
                        intent.putExtra("pacientCpf", pacientCpf.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });

                alerta = builder.create();
                alerta.show();
            }
        });
    }
}
