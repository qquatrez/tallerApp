package com.example.tallerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    //Base de datos
    private static final String DATABASE_NOMBRE="taller.db";

    //Usuarios
    public static final String TABLE_USUARIOS ="t_usuarios";
    //columnas
    public static final String table_U_Column_ID="id";
    public static final String table_U_Column_1_Usuario="usuario";
    public static final String table_U_Column_2_Correo="correo";
    public static final String table_U_Column_3_Password="password";


    //tabla Contactos
    public static final String TABLE_CONTACTOS ="t_contactos";
    //columnas
    public static final String table_C_Column_ID="id";
    public static final String table_C_Column_1_Nombre="nombre";
    public static final String table_C_Column_2_Apellido="apellido";
    public static final String table_C_Column_3_Correo="correo";
    public static final String table_C_Column_4_Telefono="telefono";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USUARIOS="CREATE TABLE IF NOT EXISTS "+TABLE_USUARIOS+
                " ("+table_U_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                ""+table_U_Column_1_Usuario+" TEXT, " +
                ""+table_U_Column_2_Correo+" TEXT, " +
                ""+table_U_Column_3_Password+" TEXT)";

        String CREATE_TABLE_CONTACTOS="CREATE TABLE IF NOT EXISTS "+TABLE_CONTACTOS+
                "("+table_C_Column_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                ""+table_C_Column_1_Nombre+" TEXT, " +
                ""+table_C_Column_2_Apellido+" TEXT, "+
                ""+table_C_Column_3_Correo+" TEXT,"+
                ""+table_C_Column_4_Telefono+" TEXT)";

        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE "+TABLE_USUARIOS);
        db.execSQL("DROP TABLE "+TABLE_CONTACTOS);
        onCreate(db);

        Log.e("oncreate","oncreate");
    }

    //Metodos para Usuarios
    //agregar usuario
    public void agregarUsuario(String usuario, String correo,String  password){
        long id = 0;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(table_U_Column_1_Usuario, usuario);
            values.put(table_U_Column_2_Correo, correo);
            values.put(table_U_Column_3_Password, password);
            id=db.insert(TABLE_USUARIOS,null, values );
            db.close();
        }catch (Exception ex){
            ex.toString();
        }

    }


}//cierro llave principal

