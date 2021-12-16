package com.example.tallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tallerapp.db.DbHelper;
import com.example.tallerapp.db.DbUsuarios;

public class LoginUsuario extends AppCompatActivity {

    Button btnLogin, btnRegistrar;
    EditText txtUsuario, txtPassword ;
    String usuario, password;
    Boolean estadoCampos;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnRegistrar = (Button)findViewById(R.id.buttonRegistrar);

        txtUsuario = (EditText)findViewById(R.id.editUsuario);
        txtPassword = (EditText)findViewById(R.id.editPassword);

        dbHelper = new DbHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoCampos=ControlarCamposLogin();

                if(estadoCampos){
                    LoginUsuario();
                    LimpiarCampos();
                }
                else{
                    Toast.makeText(LoginUsuario.this,"Se deben completar todos los campos.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUsuario.this, RegistrarUsuario.class);
                startActivity(intent);
            }
        });
    }

    //Validar Campos vacios.
    public boolean ControlarCamposLogin(){
        boolean estado=false;
        usuario = txtUsuario.getText().toString();
        password = txtPassword.getText().toString();

        if( TextUtils.isEmpty(usuario) || TextUtils.isEmpty(password)){
            estado = false ;
        }
        else {
            estado = true ;
        }
        return estado;
    }

    public void LoginUsuario(){
        Boolean usuarioLogin=false;
        Boolean passwordLogin=false;
        DbUsuarios dbUsuarios = new DbUsuarios(LoginUsuario.this);
        usuarioLogin=dbUsuarios.BuscarUsuario(usuario);
        passwordLogin=dbUsuarios.BuscarPassword(password);

        if(usuarioLogin && passwordLogin){
            Toast.makeText(LoginUsuario.this,"Login Usuario Correcto",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginUsuario.this, Principal.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(LoginUsuario.this,"Usuario o Password Incorrecto.",Toast.LENGTH_LONG).show();
        }

    }

    public void LimpiarCampos(){
        txtUsuario.setText("");
        txtPassword.setText("");
    }
}