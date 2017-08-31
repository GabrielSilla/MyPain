package Controllers;

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
}
