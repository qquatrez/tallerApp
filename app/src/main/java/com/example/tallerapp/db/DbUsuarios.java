package com.example.tallerapp.db;

import android.content.ContentValues;
import android.content.Context;
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
            values.put("usuario", usuario);
            values.put("correo", correo);
            values.put("password", password);
            id=db.insert(TABLE_USUARIOS, null, values);
            db.close();
        }
        catch (Exception ex){
            ex.toString();
        }

        return id;
    }

}
