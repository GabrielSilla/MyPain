package com.example.gabriel.mypain;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import Controllers.DatabaseController;

public class InitialScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AlertDialog alerta;
    Boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseController dbController = new DatabaseController(getBaseContext());
        Cursor user = dbController.getCurrentUser();
        if(user.getCount() > 0){
            isLogged = true;
            TextView txt_user = (TextView) findViewById(R.id.txt_user_welcome);
            txt_user.setText(user.getString(user.getColumnIndex("Name")).split(" ")[0] + ", o que deseja fazer?");
        }else{
            isLogged = false;
            createAlertDialogLogIn();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton logout = (FloatingActionButton) this.findViewById(R.id.logout_btn);
        FloatingActionButton registerPatient = (FloatingActionButton) this.findViewById(R.id.btnRegisterPatient);
        FloatingActionButton fabNewDiag = (FloatingActionButton) this.findViewById(R.id.new_diag);
        fabNewDiag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isLogged){
                    Intent intent = new Intent(v.getContext(),InjuriesList.class);
                    startActivity(intent);
                }else{
                    createAlertDialogLogIn();
                }
            }
        });
        registerPatient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isLogged){
                    Intent intent = new Intent(v.getContext(), PatientRegister.class);
                    startActivity(intent);
                }else{
                    createAlertDialogLogIn();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogged){
                    isLogged = false;
                    dbController.userLogOut();
                    createAlertDialogLogIn();
                    TextView txt_user = (TextView) findViewById(R.id.txt_user_welcome);
                    txt_user.setText("O que deseja fazer?");
                }
                else{
                    Snackbar.make(v, "Você precisa estar identificado para fazer isto!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.initial_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            Intent intent = new Intent(this, InjuriesList.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createAlertDialogLogIn(){
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        final View view = li.inflate(R.layout.alertdialog_login, null);
        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //exibe um Toast informativo.
                DatabaseController dbController = new DatabaseController(getBaseContext());
                EditText name = (EditText) alerta.findViewById(R.id.name_text);
                EditText crm = (EditText) alerta.findViewById(R.id.crm_text);

                if(crm.getText().toString().isEmpty() || name.getText().toString().isEmpty()){
                    Toast.makeText(InitialScreen.this, "Preencha os dois campos!", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbController.registerNewUser(name.getText().toString(), crm.getText().toString());
                    Toast.makeText(InitialScreen.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    isLogged = true;
                    TextView txt_user = (TextView) findViewById(R.id.txt_user_welcome);
                    txt_user.setText(name.getText().toString().split(" ")[0] + ", o que deseja fazer?");
                    alerta.dismiss();
                }
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Identifique-se para realizar seus diagnósticos!");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }
}
