package Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Resources.CreateDB;

import static java.util.Calendar.DATE;

/**
 * Created by Gabriel on 22/08/2017.
 */

public class DatabaseController {

    private SQLiteDatabase db;
    private CreateDB database;

    public DatabaseController(Context context){
        database = new CreateDB(context);
    }

    public Cursor getAllInjuries(){
        Cursor cursor;
        String[] fields = {database.ID,database.TITLE};
        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE, fields, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getInjuriesNonLocation(){
        Cursor cursor;
        String[] fields = {database.ID,database.TITLE};
        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE, fields, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getInjuriesLocation(){
        Cursor cursor;
        String[] fields = {database.ID,database.TITLE_INJURIE_LOCATION};
        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE_INJURIE_LOCATION, fields, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getCurrentUser(){
        Cursor cursor;
        String[] fields = {database.USER_NAME, database.USER_CRM};
        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE_USER, fields, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String registerNewUser(String name, String crm){
        db = database.getWritableDatabase();
        db.execSQL("delete from " + CreateDB.TABLE_USER);

        ContentValues values;
        long result;

        values = new ContentValues();
        values.put(CreateDB.USER_NAME, name);
        values.put(CreateDB.USER_CRM, crm);

        result = db.insert(CreateDB.TABLE_USER, null, values);
        db.close();

        if(result ==-1){
            return "Erro ao tentar inserir registro";
        }
        else{
            return "Registro Inserido com sucesso";
        }
    }

    public void userLogOut(){
        db = database.getWritableDatabase();
        db.execSQL("delete from " + CreateDB.TABLE_USER);

        db.close();
    }

    public String registerPacienteIntoDB(String name, String cpf){
        ContentValues values;
        long result;

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(CreateDB.PATIENT_NAME, name);
        values.put(CreateDB.PATIENT_CPF, cpf);

        result = db.insert(CreateDB.TABLE_PATIENT, null, values);
        db.close();

        if(result ==-1){
            return "Erro ao tentar inserir registro";
        }
        else{
            return "Registro Inserido com sucesso";
        }
    }

    public Cursor getAllPacient(){
        Cursor cursor;
        String[] fields = {database.ID, database.PATIENT_NAME, database.PATIENT_CPF};
        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE_PATIENT, fields,null, null, null, null, null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getPacientByCpf(String cpf){
        Cursor cursor;
        String[] fields = {database.ID, database.PATIENT_NAME, database.PATIENT_CPF};

        String where = CreateDB.PATIENT_CPF + "=" + cpf;

        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE_PATIENT, fields, where, null, null, null, null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String saveDiag(String patientCpf, String diagText){
        ContentValues values;
        long result;

        Cursor cursor = getCurrentUser();

        SimpleDateFormat hour_pattern = new SimpleDateFormat("HH:mm");

        Date date = new Date();
        Calendar  cal = Calendar.getInstance();
        cal.setTime(date);
        Date today = cal.getTime();
        String now = hour_pattern.format(today);

        String formattedDate = DateFormat.getDateInstance().format(new Date()) + " " + now;

        String userName = cursor.getString(cursor.getColumnIndex("Name"));
        String userCrm = cursor.getString(cursor.getColumnIndex("CRM"));

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(CreateDB.DIAG_DATE, formattedDate);
        values.put(CreateDB.PATIENT_CPF, patientCpf);
        values.put(CreateDB.USER_NAME, userName);
        values.put(CreateDB.USER_CRM, userCrm);
        values.put(CreateDB.DIAG_TEXT, diagText);

        result = db.insert(CreateDB.TABLE_DIAG, null, values);
        db.close();

        if(result ==-1){
            return "Erro ao tentar inserir registro";
        }
        else{
            return "Registro Inserido com sucesso";
        }
    }
}
