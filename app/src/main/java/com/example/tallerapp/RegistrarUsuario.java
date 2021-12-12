package com.example.tallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tallerapp.db.DbHelper;

public class RegistrarUsuario extends AppCompatActivity {

    EditText usuario, correo, password;
    Button registrar;
    String usuarioHolder, correoHolder, passwordHolder;
    Boolean editTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    DbHelper dbHelper;
    Cursor cursor;
    String F_Result = "Not_Found";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        registrar = (Button)findViewById(R.id.btnRegistrarUsuario);
        usuario = (EditText)findViewById(R.id.editUsuario);
        correo = (EditText)findViewById(R.id.editCorreo);
        password = (EditText)findViewById(R.id.editPassword);

        dbHelper = new DbHelper(this);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();

            }
        });

    }

    // Limpiar cajas
    public void EmptyEditTextAfterDataInsert(){

        usuario.getText().clear();
        correo.getText().clear();
        password.getText().clear();

    }




    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){

        // If editText is not empty then this block will executed.
        if(editTextEmptyHolder)
        {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+DbHelper.TABLE_USUARIOS
                    +" (usuario,correo,password)" +
                    " VALUES('"+usuarioHolder+"', '"+correoHolder+"', '"+passwordHolder+"');";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(RegistrarUsuario.this,"Usuario Registrado", Toast.LENGTH_LONG).show();
            finish();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(RegistrarUsuario.this,"Se deben completar todos los campos.", Toast.LENGTH_LONG).show();

        }

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        usuarioHolder = usuario.getText().toString() ;
        correoHolder = correo.getText().toString();
        passwordHolder = password.getText().toString();

        if(TextUtils.isEmpty(usuarioHolder) || TextUtils.isEmpty(correoHolder) || TextUtils.isEmpty(passwordHolder)){

            editTextEmptyHolder = false ;

        }
        else {

            editTextEmptyHolder = true ;
        }
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = dbHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(DbHelper.TABLE_USUARIOS,
                null,
                " " + DbHelper.table_U_Column_1_Usuario + "=?", new String[]{usuarioHolder},
                null,
                null,
                null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Usuario Encontrado";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }

    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Usuario Encontrado"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(RegistrarUsuario.this,"Usuario ya registrado",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "No Existe" ;

    }

}
