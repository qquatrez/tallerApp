package com.example.tallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tallerapp.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    Button logInButton, registrarButton ;
    EditText usuario, password ;
    String usuarioHolder, passwordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DbHelper dbHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String usuarioLogin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = (Button)findViewById(R.id.buttonLogin);

        registrarButton = (Button)findViewById(R.id.buttonRegistrar);

        usuario = (EditText)findViewById(R.id.editUsuario);
        password = (EditText)findViewById(R.id.editPassword);

        dbHelper = new DbHelper(this);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chequeo estado cajas de texto
                CheckEditTextStatus();

                //Calling login method.
                LoginUsuario();
                Limpiar();

            }
        });

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegistrarUsuario.class);
                startActivity(intent);

            }
        });




    }//cierro on create

    // Login function starts from here.
    public void LoginUsuario(){

        if(EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = dbHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(DbHelper.TABLE_USUARIOS, null, " " + DbHelper.table_U_Column_1_Usuario + "=?", new String[]{usuarioHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(DbHelper.table_U_Column_3_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();

        }
        else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(MainActivity.this,"Campos Usuario o Password Vacios.",Toast.LENGTH_LONG).show();

        }

    }



    //Validacion Campos vacios.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        usuarioHolder = usuario.getText().toString();
        passwordHolder = password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(usuarioHolder) || TextUtils.isEmpty(passwordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(passwordHolder))
        {

            Toast.makeText(MainActivity.this,"Login Correcto",Toast.LENGTH_LONG).show();

            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(MainActivity.this, Principal.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(usuarioLogin, usuarioHolder);

            startActivity(intent);


        }
        else {

            Toast.makeText(MainActivity.this,"Usuario o Password Incorrecto.",Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND" ;

    }

    public void Limpiar(){
        usuario.setText("");
        password.setText("");

    }



}