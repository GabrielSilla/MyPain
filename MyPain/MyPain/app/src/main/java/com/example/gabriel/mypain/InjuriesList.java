package com.example.gabriel.mypain;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import Controllers.DatabaseController;
import Resources.CreateDB;

public class InjuriesList extends AppCompatActivity {
    private ArrayList<String> selectedInjuries = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injuries_list);

        DatabaseController dbController = new DatabaseController(getBaseContext());

        final ListView injuriesList = (ListView) findViewById(R.id.injurie_list_View);
        injuriesList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        Cursor cursor = dbController.getAllInjuries();

        String[] nomeCampos = new String[] {CreateDB.TITLE};
        int[] idViews = new int[] {R.id.txt_title};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_layout,cursor,nomeCampos,idViews, 0);

        injuriesList.setAdapter(adapter);

        /*injuriesList.setOnItemClickListener(new UsersAdapter().OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "Clicked", Toast.LENGTH_SHORT).show();
                /*TextView item = (TextView) view.findViewById(R.id.rowTextView);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.editCheckbox);

                String selectedInjurie = item.getText().toString();

                if(!selectedInjuries.contains(selectedInjurie)){
                    selectedInjuries.add(selectedInjurie);
                    checkBox.setChecked(true);
                    Toast.makeText(getBaseContext(), selectedInjurie + " - Selecionado", Toast.LENGTH_SHORT).show();
                }
                else{
                    checkBox.setChecked(false);
                    selectedInjuries.remove(selectedInjurie);
                    Toast.makeText(getBaseContext(), selectedInjurie + " - Deselecionado", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button btn_continue_diag = (Button) this.findViewById(R.id.btn_injuries_continue);
        btn_continue_diag.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DiagFinal.class);
                intent.putStringArrayListExtra("Injuries", selectedInjuries);
                startActivity(intent);
            }
        });
        */
    }
}
