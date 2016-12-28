package com.listados.vito.actividadlistados_victordiaz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Esta Clase se encargará de crear un nuevo elemento para introducir en la lista y guardarlo en la Base de datos
 *
 * Created by vito on 22/12/2016.
 */

public class NuevoItem extends Activity  implements View.OnClickListener {

    private Button btnEnviar, btnCancelar;
    private EditText Nombrenuevo,Subnombrenuevo;
    private Button photoButton;
    private ImageView photoViewer;
    private Bitmap imagen;
    private static final int SELECT_FILE = 1;
    private static final float MAX_IMAGE_SIZE = 50;
    private boolean permisoGaleria;
    private SharedPreferences sharedpreferences;
    int items;

    /**
     * @method: Método onCreate
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
        setContentView(R.layout.nuevoelemento);

        btnEnviar=(Button)findViewById(R.id.btnnuevoItem);
        btnCancelar = (Button) findViewById(R.id.btncancelar);
        Nombrenuevo = (EditText) findViewById( R.id.nombrenuevo);
        Subnombrenuevo = (EditText) findViewById( R.id.subnombrenuevo );
        photoButton = (Button) findViewById(R.id.buttomphoto);
        photoViewer = (ImageView) findViewById(R.id.imagennueva);
        sharedpreferences = getSharedPreferences(Utils.MyPREFERENCES, MODE_PRIVATE);

        permisoGaleria = sharedpreferences.getBoolean("Permisogaleria", false);
        btnEnviar.setOnClickListener(this);
        photoButton.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        this.items = bundle.getInt("items");

    }

    /**
     * @method: Método onClick
     *
     * @author: Victor
     *
     * @param: Recibe un objeto View
     *
     * @return: No devuelve nada
     */

    @Override
    public void onClick(View v) {
        if(v == btnEnviar) {
            try{
                Intent explicit_intent;
                explicit_intent = new Intent(this, MainActivity.class);
                String auxnom = Nombrenuevo.getText().toString();
                String auxsubnom = Subnombrenuevo.getText().toString();
                Bitmap auximg = null;
                if(imagen != null ) auximg = scaleDown(imagen, MAX_IMAGE_SIZE, true);

                explicit_intent.putExtra("nombre", auxnom);
                explicit_intent.putExtra("subnombre", auxsubnom);
                explicit_intent.putExtra("imagen", auximg);

                Log.d("Item nuevo", "nombre: " + auxnom + ", subnombre: " + auxsubnom + ", imagen: " + auximg);

                startActivity(explicit_intent);

            }catch(Exception e){Log.d("ERROR: item nuevo", "Error al enviar el nuevo elemento");}
        }

        if(v == photoButton) {

            if(permisoGaleria) {
                try{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"),
                        SELECT_FILE);
                }catch(Exception e){Log.d("ERROR: item nuevo", "Error al seleccionar la imagen");}
            }else{
                try{
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                    dialogo1.setTitle("Aviso");
                    dialogo1.setMessage("Debe dar permiso a la aplciación para poder acceder a la galeria");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar();
                    }});
                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                            dialogo1.cancel();
                    }});

                    dialogo1.show();

                }catch(Exception e){Log.d("ERROR: item nuevo", "Error al enviar el nuevo elemento");}

            }
        }
        if(v==btnCancelar){
            finalizar(v);
        }
    }

    /**
     * @method: Método aceptar
     *
     * @author: Victor
     *
     * @param: No recibe nada
     *
     * @return: No devuelve nada
     */

    private void aceptar(){

        sharedpreferences.edit().putBoolean("Permisogaleria", true).apply();
        permisoGaleria = true;

    }

    /**
     * @method: Método finalizar
     *
     * @author: Victor
     *
     * @param: Recibe un objeto View
     *
     * @return: No devuelve nada
     */

    public void finalizar(View view) {
        finish();
    }

    /**
     * @method: Método onActivityResult
     *
     * @author: Victor
     *
     * @param: Recibe dos enteros y un Intent
     *
     * @return: No devuelve nada
     */

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();

                    if (requestCode == SELECT_FILE) {

                        if (selectedImage != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            imagen = BitmapFactory.decodeStream(imageStream);

                            ImageView mImg = (ImageView) findViewById(R.id.imagennueva);
                            mImg.setImageBitmap(imagen);

                        }
                    }
                }
                break;
        }
    }

    /**
     * @method: Método scaleDown
     *
     * @author: Victor
     *
     * @param: Recibe un objeto Bitmap, un float y un boolean
     *
     * @return: Devuelve un Bitmap escalado
     */

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newBitmap = null;

        try{
            newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        }catch(Exception e){
            Log.d("ERROR Nuevo Item", "No se pudo escalar la imagen");
        }
        return newBitmap;
    }

}