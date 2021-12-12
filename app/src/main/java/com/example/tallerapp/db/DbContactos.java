package com.example.tallerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tallerapp.model.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarContacto(String nombre, String apellido, String correo, String telefono) {

        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(table_C_Column_1_Nombre, nombre);
            values.put(table_C_Column_2_Apellido, apellido);
            values.put(table_C_Column_3_Correo, correo);
            values.put(table_C_Column_4_Telefono, telefono);
            id = db.insert(TABLE_CONTACTOS, null, values);
            db.close();
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public ArrayList<Contactos> mostrarContactos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM "+ DbHelper.TABLE_CONTACTOS, null);

        if (cursorContactos.moveToFirst()) {
            do{
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setApellido(cursorContactos.getString(2));
                contacto.setCorreo(cursorContactos.getString(3));
                contacto.setTelefono(cursorContactos.getString(4));
                listaContactos.add(contacto);
            }while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = "+ id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setApellido(cursorContactos.getString(2));
            contacto.setCorreo(cursorContactos.getString(3));
            contacto.setTelefono(cursorContactos.getString(4));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean actualizarContacto(int id, String nombre, String apellido, String correo, String telefono){

        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CONTACTOS
                    +" SET nombre = '" + nombre + "',"
                    + "', apellido = '" + apellido +   "',"
                    + "', correo = '" + correo +   "',"
                    +" telefono = '" + telefono
                    +"' WHERE id='"
                    + id + "' ");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto=false;
        }
        finally {
            db.close();
        }
        return  correcto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }

}

