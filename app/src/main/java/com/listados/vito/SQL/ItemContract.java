package com.listados.vito.SQL;

import android.graphics.Bitmap;
import android.provider.BaseColumns;

/**
 * Clase ItentContract
 *
 * Created by vito on 23/12/2016.
 */

public class ItemContract {

    public static abstract class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String SUBNOMBRE = "subnombre";

        public static final String IMAGEN = "imagen";
    }

}
