package com.listados.vito.actividadlistados_victordiaz;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.util.Log;

import com.listados.vito.SQL.ItemContract;

import java.io.ByteArrayOutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import static com.listados.vito.SQL.ItemContract.ItemEntry.IMAGEN;

/**
 * Clase Item
 *
 * Created by vito on 19/12/2016.
 */

public class Item{
    protected long id;
    protected String nombre;
    protected String subnombre;
    protected Bitmap imagen;

    /**
     * @method: Constructor de la Clase Item
     *
     * @author: Victor
     *
     * @param: No recibe parámetros
     */

    public Item() {
        this.id=-1;
        this.nombre = "";
        this.subnombre="";
        this.imagen = null;
    }

    /**
     * @method: Constructor de la Clase Item
     *
     * @author: Victor
     *
     * @param: Recibe Id y nombre del nuevo item
     */

    public Item(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = null;
        this.subnombre = null;
    }

    /**
     * @method: Constructor de la Clase Item
     *
     * @author: Victor
     *
     * @param: Recibe Id, nombre y subnombre del nuevo item
     */

    public Item(long id, String nombre, String subnombre) {
        this.id = id;
        this.nombre = nombre;
        this.subnombre=subnombre;
        this.imagen = null;
    }

    /**
     * @method: Constructor de la Clase Item
     *
     * @author: Victor
     *
     * @param: Recibe id, nombre, subnombre e imagen del nuevo item
     */


    public Item(long id, String nombre, String subnombre, Bitmap Imagen) {
        this.id = id;
        this.nombre = nombre;
        this.subnombre = subnombre;
        if(Imagen==null) this.imagen = null;
        else this.imagen = Imagen;
    }

    //Métodos SET de la clase Item

    public void setId(long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setSubnombre(String subnombre) {this.subnombre = subnombre;}
    public void setImagenMap(Bitmap imagen){
        this.imagen = imagen;
    }

    //Métodos GET de la clase Item

    public String getNombre() {
        return nombre;
    }
    public long   getId() {return id;}
    public String getSubnombre(){ return subnombre; }
    public Bitmap getImagenMap(){
        if(imagen!=null) return imagen;
        else return null;
    }

    /**
     * @method: Metodo para convertir un item en un ContentValues
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un ContentValues
     */

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        try {
            values.put(ItemContract.ItemEntry.ID, id);
            values.put(ItemContract.ItemEntry.NOMBRE, nombre);
            values.put(ItemContract.ItemEntry.SUBNOMBRE, subnombre);

            if (imagen != null) {
                byte[] imagenArray = getArrayByte();
                values.put(ItemContract.ItemEntry.IMAGEN, imagenArray);
            }
        }catch(Exception e){
            Log.d("ERROR ITEM", "No se pudo convertir el item en un toContentValues");
        }
        return values;
    }

    /**
     * @method: Metodo para convertir la imagen Bitmap del item en un objeto Byte[]
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un byte[]
     */

    public byte[] getArrayByte(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] imagenArray=null;
        try{
            imagen.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagenArray = stream.toByteArray();
        }catch (Exception e){
            Log.d("ERROR ITEM", "No se pudo convertir la imagen a byte[]");
        }
        return imagenArray;
    }

    /**
     * @method: Metodo sobrecargado toString para imprimir datos del item en pantalla
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: Devuelve un String
     */

    public String toString(){

        if(imagen!=null) return "Id: "+id+", Nombre: "+nombre+", subnombre: "+subnombre+", imagen: "+imagen.toString();
        else return "Id: "+id+", Nombre: "+nombre+", subnombre: "+subnombre;
    }
}