package com.example.gabriel.mypain;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

        Cursor cursor = dbController.getInjuriesNonLocation();

        String[] nomeCampos = new String[] {CreateDB.TITLE};
        int[] idViews = new int[] {R.id.txt_title};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_layout,cursor,nomeCampos,idViews, 0);

        injuriesList.setAdapter(adapter);

        injuriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView item = (TextView) view.findViewById(R.id.txt_title);

                String selectedInjurie = item.getText().toString();

                if(!selectedInjuries.contains(selectedInjurie)){
                    selectedInjuries.add(selectedInjurie);
                }
                else{
                    selectedInjuries.remove(selectedInjurie);
                }
            }
        });

        Button btn_continue_diag = (Button) this.findViewById(R.id.btn_injuries_continue);
        btn_continue_diag.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InjurieLocationList.class);
                intent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                startActivity(intent);
                finish();
            }
        });
    }
}
