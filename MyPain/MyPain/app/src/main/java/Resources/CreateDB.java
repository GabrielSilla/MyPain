package Resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gabriel on 22/08/2017.
 */

public class CreateDB extends SQLiteOpenHelper{
    public static final String DB_NAME = "mypain2.db";

    public static final String ID = "_id";
    private static final int VERSION = 1;

    public static final String TABLE = "Injuries";
    public static final String TITLE = "InjurieName";
    public static final String HASLOCAL = "HasLocal";

    public static final String TABLE_PATIENT = "Patient";
    public static final String PATIENT_CPF = "Cpf";
    public static final String PATIENT_NAME = "P_Name";

    public static final String TABLE_DIAG = "Diagnostic";
    public static final String DIAG_DATE = "Date";
    public static final String DIAG_TEXT = "DiagnosticText";


    public static final String TABLE_USER = "MedicUser";
    public static final String USER_CRM = "CRM";
    public static final String USER_NAME = "Name";

    public static final String TABLE_INJURIE_LOCATION = "InjuriesLocation";
    public static final String TITLE_INJURIE_LOCATION = "InjuriesLocationName";

    private static final List<String> injuriesList = Arrays.asList("Fraqueza", "Convulsão (ataque)", "Soluço", "Febre", "Queimação", "Cansaço", "Nariz escorrendo (Coriza)", "Vontade de vomitar (Náusea)", "Tontura (Sensação de desmaio)", "Dor de barriga (Diarreia)", "Intestino preso (Prisão de ventre)",
            "Dificuldade em urinar (Retenção urinária)", "Tremor", "Suor", "Falta de ar (Dispneia)", "Espirros", "Tosse", "Gazes (Flatulência)",
            "Arroto (Eructação)", "Sangramento", "Dificuldade em dormir (Insônia)", "Sonolência", "Palpitação (Arritmia)", "Surdez", "Tremedeira", "Cólica");

    private static final List<String> injuriesListLocation = Arrays.asList("Dor", "Coceira", "Dormência (Sensibilidade)", "Manchas", "Caroços", "Inchaço (Edema)", "Câimbra");

    public CreateDB(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "("
                + ID + " integer primary key autoincrement,"
                + TITLE + " text)";
        db.execSQL(sql);

        String sql_injurie_location = "CREATE TABLE " + TABLE_INJURIE_LOCATION + "("
                + ID + " integer primary key autoincrement,"
                + TITLE_INJURIE_LOCATION + " text)";
        db.execSQL(sql_injurie_location);

        String sql_patient = "CREATE TABLE " + TABLE_PATIENT + "("
                + ID + " integer primary key autoincrement,"
                + PATIENT_CPF + " text,"
                + PATIENT_NAME + " text)";
        db.execSQL(sql_patient);

        String sql_diagnostic = "CREATE TABLE " + TABLE_DIAG + "("
                + ID + " integer primary key autoincrement,"
                + DIAG_DATE + " text,"
                + PATIENT_CPF + " text,"
                + USER_CRM + " text,"
                + USER_NAME + "text,"
                + DIAG_TEXT + " text)";
        db.execSQL(sql_diagnostic);

        String sql_user = "CREATE TABLE " + TABLE_USER + "("
                + USER_CRM + " text,"
                + USER_NAME + " text)";
        db.execSQL(sql_user);

        for(String injurie : injuriesList){

            String insertInjurie = "INSERT INTO " + TABLE + " (InjurieName) VALUES ('" + injurie + "');";

            db.execSQL(insertInjurie);
        }

        for(String injurie : injuriesListLocation){

            String insertInjurieLocation = "INSERT INTO " + TABLE_INJURIE_LOCATION + " (InjuriesLocationName) VALUES ('" + injurie + "');";

            db.execSQL(insertInjurieLocation);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
