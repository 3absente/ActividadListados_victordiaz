package com.listados.vito.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.listados.vito.SQL.ItemContract.ItemEntry;
import com.listados.vito.actividadlistados_victordiaz.Item;

import java.util.ArrayList;

/**
 * Created by vito on 23/12/2016.
 */

public class OperationsSQL extends AppCompatActivity {

    Context context;
    OperationsSQLAsynctask operacionesAsync;

    /**
     * @method: Constructor de la clase OperationSQL
     *
     * @author: Victor
     *
     * @param: Pasamos el Contexto de la actividad en la que usaremos la Base de datos
     */

    public OperationsSQL(Context context) {
        this.context = context;
    }

    /**
     * @method: Método para agregar un nuevo item
     *
     * @author: Victor
     *
     * @param: Recibe el item a introducir
     *
     * @return: no devuelve nada
     */
    public void alta(Item item) {

        ArrayList<Item> itemsbd = new ArrayList<Item>();
        operacionesAsync = new OperationsSQLAsynctask(this.context);
        itemsbd.add(item);
        operacionesAsync.execute(itemsbd);
        Log.d("Insersión BD", "inserta en la base de datos");
    }


    /**
     * @method: Método para agregar varios items nuevos
     *
     * @author: Victor
     *
     * @param: Recibe lista de items a introducir
     *
     * @return: No devuelve nada
     */
    public void altaVarios(ArrayList<Item> items) {
        operacionesAsync = new OperationsSQLAsynctask(this.context);
        operacionesAsync.execute(items);
        Log.d("Insersión BD", "inserta en la base de datos varios items");
    }

    /**
     * @method: Método que devuelve una lista con todos los elementos de la Base de Datos
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un ArrayList<Item> con todos los items de la Base de Datos
     */

    public ArrayList<Item> ListaItem() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String[] camposItems = new String[]{"id", "nombre", "subnombre", "imagen"};
        String ordenItems = "id";

        ArrayList<Item> itemsbd = new ArrayList<Item>();

        Cursor c = bd.query(ItemEntry.TABLE_NAME, camposItems, null, null, null, null, ordenItems);
        Long id;
        String nombre, subnombre;
        byte[] imagenbyte;
        Bitmap imagen;

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            Log.d("listaItems", "Hay items en la base de datos");
            do {
                id = c.getLong(0);
                nombre = c.getString(1);
                subnombre = c.getString(2);
                imagenbyte = c.getBlob(3);
                if(imagenbyte!= null) imagen = BitmapFactory.decodeByteArray(imagenbyte, 0, imagenbyte.length);
                else imagen = null;
                itemsbd.add(new Item(id, nombre, subnombre, imagen));
            } while (c.moveToNext());
        }else Log.d("ListaItems", "no hay items en la base de datos");

        return itemsbd;
    }

    /**
     * @method: Método que devuelve un booleano indicando si la Base de Datos esta vacía
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un Boolean
     */

    public boolean isEmpty() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String[] camposItems = new String[]{"id", "nombre", "subnombre", "imagen"};
        String ordenItems = "id";

        Cursor c = bd.query(ItemEntry.TABLE_NAME, camposItems, null, null, null, null, ordenItems);
        Log.d("Entra en isEmpy", "aqui entra y comprueba la base de datos");

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            bd.close();
            return false;

        } else {
            bd.close();
            return true;
        }
    }

    /**
     * @method: Método que vacía de registros la Base de Datos
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: No devuelve nada
     */

    public void deleteAll() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase bd = admin.getWritableDatabase();


        int c = bd.delete(ItemEntry.TABLE_NAME, null, null);
        Log.d("Entra en DeleteAll", "aqui entra y resetea la base de datos");

    }

}
