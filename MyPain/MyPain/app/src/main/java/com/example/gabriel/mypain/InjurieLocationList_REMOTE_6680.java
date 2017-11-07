package com.example.gabriel.mypain;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Controllers.DatabaseController;
import Resources.CreateDB;

public class InjurieLocationList extends AppCompatActivity {
    private ArrayList<String> selectedInjuries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent lastIntent = getIntent();
        selectedInjuries = lastIntent.getStringArrayListExtra("Injuries");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injurie_location_list);

        DatabaseController dbController = new DatabaseController(getBaseContext());

        final ListView injuriesList = (ListView) findViewById(R.id.injurie_location_list_View);
        injuriesList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        Cursor cursor = dbController.getInjuriesLocation();

        String[] nomeCampos = new String[] {CreateDB.TITLE_INJURIE_LOCATION};
        int[] idViews = new int[] {R.id.txt_title};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_nocheck,cursor,nomeCampos,idViews, 0);

        injuriesList.setAdapter(adapter);

        injuriesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = (TextView) v.findViewById(R.id.txt_title);
                Toast.makeText(getBaseContext(), tx.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}