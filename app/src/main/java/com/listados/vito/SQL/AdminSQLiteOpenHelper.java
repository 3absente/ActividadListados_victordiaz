package com.listados.vito.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Esta clase se encarga de crear y administrar la base de datos
 *
 * Created by vito on 23/12/2016.
 */

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "items.db";
    public SQLiteDatabase db;
    private Context context;

    /**
     * @method: Constructor de la Clase AdminSQLiteOpenHelper
     *
     * @author: Victor
     *
     * @param: Pasamos el Contexto de la actividad en la que usaremos la Base de datos
     */

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        Log.d("SQL helper", "Entra en el constructor de la base de datos");
    }

    /**
     * @method: Método onCreate
     *
     * @author: Victor
     *
     * @param: Recibe un objeto SQLiteDatabase
     *
     * @return: no devuelve nada
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE "+ ItemContract.ItemEntry.TABLE_NAME + "("
                                      + ItemContract.ItemEntry.ID + " INTEGER PRIMARY KEY, "
                                      + ItemContract.ItemEntry.NOMBRE+ " TEXT NOT NULL, "
                                      + ItemContract.ItemEntry.SUBNOMBRE+ " TEXT, "
                                      + ItemContract.ItemEntry.IMAGEN+ " BLOB) ;");
            Log.d("SQL helper", "crea la base de datos");
        }catch (SQLException e){
            Log.d("ERROR: SQL helper", "No crea la base de datos");
        }

    }

    /**
     * @method: Método onUpgrade
     *
     * @author: Victor
     *
     * @param: Recibe un objeto SQLiteDatabase, el int de la version anterior y el int de la nueva version de la Base de Datos
     *
     * @return: no devuelve nada
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}