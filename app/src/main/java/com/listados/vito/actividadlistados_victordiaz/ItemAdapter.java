package com.listados.vito.actividadlistados_victordiaz;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Esta clase es la encargada de Adaptar los items a la ListView del MainActivity
 *
 * Created by vito on 19/12/2016.
 */

public class ItemAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Item> items;
    LayoutInflater inflater;

    /**
     * @method: Constructor de la Clase ItemAdapter
     *
     * @author: Victor
     *
     * @param: Pasamos Activity donde realizaremos la adaptacion y lista de items a adaptar
     */

    public ItemAdapter(Activity activity, ArrayList<Item> items) {
        this.activity = activity;
        this.items = items;
    }

    /**
     * @method: Método getCount
     *
     * @author: Victor
     *
     * @param: No recibe parámetros
     *
     * @return: Devuelve Número de los items
     */

    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * @method: Método getCount
     *
     * @author: Victor
     *
     * @param: Recibe posición del elemento de la lista
     *
     * @return: Devuelve elemento correspondiente a la posición
     */

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /**
     * @method: Método getCount
     *
     * @author: Victor
     *
     * @param: Recibe posición del elemento de la lista
     *
     * @return: Devuelve el Id del item correspondiente a la posición recibida
     */

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    /**
     * @method: Método getView
     *
     * @author: Victor
     *
     * @param: Recibe posición del emento a introducir en la lista, tambien dos objetos un View y un ViewGroup
     *
     * @return: devuelve vista para introducir en la lista que la solicito
     */

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View vi = contentView;

        if (contentView == null) {
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        Item item = items.get(position);

        //Log.d("Items", "posicion: " + position + ", Nombre: " + item.getNombre() + ", Subnombre: " + item.getSubnombre() + ", Imagen: " + item.getImagenMap());

        try{
          if (position == 0) {
            vi = inflater.inflate(R.layout.primeritem, null);
            TextView tituloprimero = (TextView) vi.findViewById(R.id.tituloprimeritem);
            tituloprimero.setText(item.getNombre());
          } else {
            if (position == getCount() - 1) {

                vi = inflater.inflate(R.layout.ultimoitem, null);
                TextView tituloultimo = (TextView) vi.findViewById(R.id.tituloultimoitem);
                tituloultimo.setText(item.getNombre());
            } else {
                vi = inflater.inflate(R.layout.list_item, null);

                TextView nombre = (TextView) vi.findViewById(R.id.nombre);
                nombre.setText(item.getNombre());

                ImageView image = (ImageView) vi.findViewById(R.id.imagen);
                if (item.getImagenMap()==null) {

                    int imageResource = activity.getResources()
                            .getIdentifier("mipmap/ic_launcher", null,
                                    activity.getPackageName());
                    image.setImageDrawable(activity.getResources().getDrawable(
                            imageResource));
                }else{
                    image.setImageBitmap(item.getImagenMap());
                }

                if (item.getSubnombre() != null) {

                    TextView subnombre = (TextView) vi.findViewById(R.id.subnombre);
                    subnombre.setText(item.getSubnombre());
                }
            }
          }
        }catch (Exception e){
            Log.d("ERROR: Items", "No se han podido adaptar los items a la lista");}
            return vi;
        }
    }

