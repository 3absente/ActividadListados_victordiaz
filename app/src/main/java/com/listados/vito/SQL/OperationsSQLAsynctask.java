package com.listados.vito.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.listados.vito.actividadlistados_victordiaz.Item;

import java.util.ArrayList;

/**
 * Esta clase se encargara de agregar los datos a la base de datos de forma asíncrona
 *
 * Created by vito on 26/12/2016.
 */

public class OperationsSQLAsynctask extends AsyncTask<ArrayList<Item>, Void, Void> {

    private Context context;
    /**
     * @method: Constructor de la Clase tipo Asynctask
     *
     * @author: Victor
     *
     * @param: Pasamos el Contexto de la actividad en la que usaremos la Base de datos
     */
    public OperationsSQLAsynctask(Context context) {
        this.context = context;
    }

    /**
     * @method: Método onPreExecute
     *
     * @author: Victor
     *
     * @param: No recibe ningún parámetro
     *
     * @return: no devuelve nada
     */
    @Override
    protected void onPreExecute() {
        Log.v("AsyncTask", "ANTES de EMPEZAR la descarga. Hilo PRINCIPAL");

    }

    /**
     * @method: Método doInBackground
     *
     * @author: Victor
     *
     * @param: Recibe un ArrayList<Item>  con los elementos a añadir
     *
     * @return: No devuelve nada
     */
    @Override
    protected Void doInBackground(ArrayList<Item>... values) {

        ArrayList<Item> nuevositems = values[0];
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Item item;
        ContentValues itemvalues;

        for(int i=0; i<nuevositems.size(); i++){

            item = nuevositems.get(i);
            itemvalues = item.toContentValues();

            Log.d("Nuevo item en la BD", item.toString());

            if(bd.isOpen())  Log.d("Base de datos", "La base de datos esta funcionando");
                bd.insert(ItemContract.ItemEntry.TABLE_NAME, null, itemvalues);
            }

        bd.close();

        return null;
    }

    /**
     * @method: Metodo on PostExecute
     *
     * @author: Victor
     *
     * @param: No recibe ningún parámetro
     *
     * @return: No devuelve nada
     */
    @Override
    protected void onPostExecute(Void v) {
        Log.v("OnPostExecute", "Termina de introducir los elementos");
    }

    /**
     * @method: OnCancelled
     *
     * @author: Victor
     *
     * @param: No recibe ningún parámetro
     *
     * @return: no devuelve nada
     */
    @Override
    protected void onCancelled () {
        Log.v("OnCancelled", "DESPUÉS de CANCELAR ");
    }

}