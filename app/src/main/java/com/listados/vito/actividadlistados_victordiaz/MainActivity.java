package com.listados.vito.actividadlistados_victordiaz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

import com.listados.vito.SQL.OperationsSQL;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView lv;
    private ArrayList<Item> items;
    private ItemAdapter adapter;
    private OperationsSQL operaciones;

    /**
     * @method: onCreate del MainActivity
     *
     * @author: Victor
     *
     * @param: Recibe un Bundle
     *
     * @return: No devuelve nada
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        lv = (ListView) findViewById(R.id.lista);
        operaciones = new OperationsSQL(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        items = new ArrayList<Item>();
        items = obtenerItems();


        if (!operaciones.isEmpty())
            cargarItemsBD();


        if (extras != null) {
            String nombre = (String) extras.get("nombre");
            String subnombre = (String) extras.get("subnombre");
            Bitmap imagen = (Bitmap) extras.get("imagen");
            insertaritem(nombre, subnombre, imagen);
        }
        adapter = new ItemAdapter(this, items);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Toast.makeText(MainActivity.this, "Es el elemento primero", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, NuevoItem.class);
                    i.putExtra("items", adapter.getCount() - 1);
                    startActivity(i);
                }
            }
        });

    }

    /**
     * @method: Método initToolbar del MainActivity
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: No devuelve nada
     */

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbarmenu);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

        setSupportActionBar(mToolbar);

    }

    /**
     * @method: Método insertaritem del MainActivity
     *
     * @author: Victor
     *
     * @param: Recibe dos String y un Bitmap
     *
     * @return: No devuelve nada
     */

    public void insertaritem(String nombre, String subnombre, Bitmap imagen) {

        int contador = items.size();
        items.remove(items.size()-1);

        Item item = new Item(contador, nombre, subnombre, imagen);

        items.add(item);

        operaciones.alta(item);

        items.add(new Item(contador, UltimaFila()));
        adapter = new ItemAdapter(this, items);
        lv.setAdapter(adapter);
        lv.deferNotifyDataSetChanged();
        Log.d("Elementos Lista", "Hay: " + contador + " elementos");


    }

    /**
     * @method: Método cargarItemsBD del MainActivity
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: No devuelve nada
     */

    public void cargarItemsBD() {
        items.remove(items.size() - 1);
        Log.d("CargarItemsBD", "entra en la función");
        ArrayList<Item> itemsList = operaciones.ListaItem();
        items.addAll(itemsList);
        for(int i=1;i<items.size();i++) Log.d("Items pantalla",items.toString());

        items.add(new Item(items.size(), UltimaFila()));

        Log.d("Elementos Lista", "Hay: " + items.size() + " elementos");
    }

    /**
     * @method: Método onCreateOptionsMenu del MainActivity
     *
     * @author: Victor
     *
     * @param: Recibe un objeto Menu
     *
     * @return: No devuelve nada
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menulistado, menu);
        return true;
    }

    /**
     * @method: Método onOptionsItemSelected del MainActivity
     *
     * @author: Victor
     *
     * @param: Recibe un objeto MenuItem
     *
     * @return: devuelve un booleano
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
         if (id == R.id.add_10) {
            AddVariositems(10);
         }
         if (id == R.id.delete) {
            EliminarTodosItems();
         }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @method: Método AddVariositems del MainActivity
     *
     * @author: Victor
     *
     * @param: Recibe entero
     *
     * @return: No devuelve nada
     */

    private void AddVariositems(int limite) {
        Log.d("Numero Items addvarios1", "Hay: "+items.size()+" items");
        items.remove(items.size() - 1);
        Log.d("Numero Items addvarios2", "Hay: "+items.size()+" items");
        ArrayList<Item> itemsaux = new ArrayList<Item>();
        Item itemaux;
        int contador = items.size();
        for (int i = 1; i <=limite; i++) {

            itemaux = new Item(contador, "elemento " + contador, "elemento");
            itemsaux.add(itemaux);

            contador++;
        }

        operaciones.altaVarios(itemsaux);
        Log.d("Numero Items addvarios3", "Hay: "+(items.size()+itemsaux.size())+" items");
        items.addAll(itemsaux);

        items.add(new Item(items.size(), UltimaFila()));
        adapter = new ItemAdapter(this, items);
        lv.setAdapter(adapter);
        lv.deferNotifyDataSetChanged();
        Log.d("Elementos Lista", "Hay: " + contador + " elementos");


    }

    /**
     * @method: Método EliminarTodosItems del MainActivity
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: No devuelve nada
     */

    private void EliminarTodosItems() {
        items.removeAll(items);
        items = obtenerItems();
        operaciones.deleteAll();
        Log.d("Numero Items ETI", "Hay: "+items.size()+" items");
        adapter = new ItemAdapter(this, items);
        lv.setAdapter(adapter);
        lv.deferNotifyDataSetChanged();
        Log.d("Elementos Lista", "Elementos eliminados");


    }

    /**
     * @method: Método obtenerItems del MainActivity
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: devuelve ArrayList<Item>
     */

    private ArrayList<Item> obtenerItems() {
        ArrayList<Item> items = new ArrayList<Item>();

        items.add(new Item(1, "Introducir nuevo elemento"));
        items.add(new Item(4, UltimaFila()));

        return items;
    }

    /**
     * @method: Método UltimaFila del MainActivity
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un String
     */

    private String UltimaFila() {
        Date horaActual = new Date();
        String hora;
        if (horaActual.getMinutes() < 10)
            hora = horaActual.getHours()  + ":0" + horaActual.getMinutes();
        else hora = horaActual.getHours() + ":" + horaActual.getMinutes();
        Log.d("Numero Items ULTIMA", "Hay: "+items.size()+" items");
        String mes = MesAnho(horaActual.getMonth());
        String fecha = horaActual.getDate() + " de " + mes;
        int totalitems = 0;
        if(items.size()>2) totalitems=items.size()-1;
        return "\"Son las: " + hora + " del " + fecha + ", hay: " + totalitems + " elementos.\"";


    }

    /**
     * @method: Método MesAnho del MainActivity
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un String
     */

    private String MesAnho(int i) {
        String[] nombremeses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};

        if (i >= 0 && i < 12) return nombremeses[i];
        else return "mes incorrecto";
    }



}
