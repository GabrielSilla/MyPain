package Resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gabriel on 22/08/2017.
 */

public class CreateDB extends SQLiteOpenHelper{
    public static final String DB_NAME = "mypain.db";
    public static final String TABLE = "Injuries";
    public static final String ID = "_id";
    public static final String TITLE = "InjurieName";
    private static final int VERSION = 1;

    private static final List<String> injuriesList = Arrays.asList("Dor", "Fraqueza", "Sensibilidade (dormência)", "Convulsão (ataque)", "Soluço", "Febre", "Queimação", "Coceira",
            "Cansaço", "Coriza (nariz escorrendo)", "Náusea (vontade de vomitar)", "Tontura (sensação de desmaio)", "Câimbra", "Diarreia (dor de barriga)", "Prisão de ventre (intestino preso)",
            "Retenção urinária (dificuldade em urinar)", "Tremor", "Suor", "Manchas", "Sangramento", "Dispneia (falta de ar )", "Espirros", "Tosse", "Flatulência (gazes)", "Inchaço (edema)",
            "Eructação (arroto)", "Sangramento", "Insônia (dificuldade em dormir)", "Sonolência", "Palpitação (arritmia)", "Surdez", "Caroços", "Tremedeira", "Soluços", "Cólica");

    public CreateDB(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "("
                + ID + " integer primary key autoincrement,"
                + TITLE + " text)";
        db.execSQL(sql);

        for(String injurie : injuriesList){
            String insertInjurie = "INSERT INTO " + TABLE + " (InjurieName) VALUES ('" + injurie + "');";
            db.execSQL(insertInjurie);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
