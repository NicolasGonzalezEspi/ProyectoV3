package com.vedruna.gonzalezespinosa01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class AdminSQliteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "administracion";
    private static final int DATABASE_VERSION = 2;

    public AdminSQliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creamos la tabla "articulos" con los campos "codigo", "nombre", "descripcion" y "precio"
        db.execSQL("CREATE TABLE articulos (codigo INTEGER PRIMARY KEY, nombre TEXT, descripcion TEXT, precio REAL)");
        db.execSQL("CREATE TABLE usuarios (username TEXT PRIMARY KEY, password TEXT)");

        // Insertamos un usuario inicial
        db.execSQL("INSERT INTO usuarios (username, password) VALUES ('Nicolas', '1234')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Agrega las sentencias SQL necesarias para actualizar la base de datos a la versión 2.
            db.execSQL("CREATE TABLE usuarios (username TEXT PRIMARY KEY, password TEXT)");
            db.execSQL("INSERT INTO usuarios (username, password) VALUES ('Nicolas', '1234')");
        }
    }

    /*
        public void eliminarProducto(int codigoProducto) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("articulos", "codigo=?", new String[] { String.valueOf(codigoProducto) });
        db.close();
    }
     */

    public int eliminarProducto(int codigoProducto) {
        SQLiteDatabase db = this.getWritableDatabase();
        int cantidadEliminada = db.delete("articulos", "codigo=?", new String[] { String.valueOf(codigoProducto) });
        db.close();
        return cantidadEliminada;
    }
    public int modificarProducto(int codigoProducto, String nuevoNombre, String nuevaDescripcion, double nuevoPrecio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nuevoNombre);
        valores.put("descripcion", nuevaDescripcion);
        valores.put("precio", nuevoPrecio);

        int cantidadModificada = db.update("articulos", valores, "codigo=?", new String[]{String.valueOf(codigoProducto)});
        db.close();

        return cantidadModificada;
    }
    public boolean verificarCredenciales(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean crearUsuario(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long resultado = db.insert("usuarios", null, values);
        db.close();
        return resultado != -1;
    }

}
