package Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Resources.CreateDB;

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
}
