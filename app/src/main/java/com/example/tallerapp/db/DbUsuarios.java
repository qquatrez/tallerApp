package com.example.tallerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbUsuarios extends DbHelper {
    Context context;


    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    //Registrar Usuario
    public long agregarUsuario(String usuario, String correo, String password){
        long id = 0;

        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(table_U_Column_1_Usuario, usuario);
            values.put(table_U_Column_2_Correo, correo);
            values.put(table_U_Column_3_Password, password);
            id=db.insert(TABLE_USUARIOS, null, values);
            db.close();
        }
        catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public boolean ExisteUsuario(String usuario){
        boolean existe=false;
        Cursor cursor;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            cursor= db.query(TABLE_USUARIOS,
                    null,
                    "" + table_U_Column_1_Usuario +"=?"+ usuario,
                    null,
                    null,
                    null,
                    null);
            while(cursor.moveToNext()){
                if(cursor.isFirst()){
                    cursor.moveToFirst();
                    existe=true;
                    cursor.close();
                }
            }
        }
        catch (Exception ex){
            ex.toString();
        }
        return existe;
    }

    public boolean ExisteCorreo(String correo){
        boolean existe=false;
        Cursor cursor;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            cursor= db.query(TABLE_USUARIOS,
                    null,
                    "" + table_U_Column_2_Correo +"=?"+ correo,
                    null,
                    null,
                    null,
                    null);
            while(cursor.moveToNext()){
                if(cursor.isFirst()){
                    cursor.moveToFirst();
                    existe=true;
                    cursor.close();
                }
            }
        }
        catch (Exception ex){
            ex.toString();
        }
        return existe;
    }





}
