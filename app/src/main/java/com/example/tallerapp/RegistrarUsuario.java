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
import com.example.tallerapp.db.DbUsuarios;

public class RegistrarUsuario extends AppCompatActivity {

    EditText txtUsuario, txtCorreo, txtPassword;
    Button btnRegistrar;
    String usuario, correo, password;
    Boolean estadoCampos;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        btnRegistrar = (Button)findViewById(R.id.btnRegistrarUsuario);
        txtUsuario = (EditText)findViewById(R.id.editUsuario);
        txtCorreo = (EditText)findViewById(R.id.editCorreo);
        txtPassword = (EditText)findViewById(R.id.editPassword);

        dbHelper = new DbHelper(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Controlo campos ingreso de todos los campos
                estadoCampos=ControlarCampos();

                if(estadoCampos){
                    RegistrarUsuario();
                    LimpiarCampos();
                }
                else{
                    Toast.makeText(RegistrarUsuario.this,"Se deben completar todos los campos.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Metodo Controlar cajas no vacias
    public boolean ControlarCampos(){
        boolean estado=false;

        usuario = txtUsuario.getText().toString() ;
        correo = txtCorreo.getText().toString();
        password = txtPassword.getText().toString();

        if(TextUtils.isEmpty(usuario) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(password)){
            estado = false ;
        }
        else {
            estado = true ;
        }
        return  estado;
    }

    public void RegistrarUsuario(){
        boolean correoExiste=false;
        boolean usuarioExiste=false;
        DbUsuarios dbUsuarios = new DbUsuarios(RegistrarUsuario.this);
        correoExiste= dbUsuarios.ExisteCorreo(correo);
        usuarioExiste=dbUsuarios.ExisteUsuario(usuario);

        if(correoExiste || usuarioExiste){
            Toast.makeText(RegistrarUsuario.this,"Usuario o Correo ya registrado",Toast.LENGTH_LONG).show();
        }
        else{
            GuardarUsuario();
        }
    }

    //Metodo para Agregar Usuarios a base SqlLite
    public void GuardarUsuario(){
        DbUsuarios dbUsuarios = new DbUsuarios(RegistrarUsuario.this);
        long id=dbUsuarios.agregarUsuario(usuario, correo, password);

        if(id>0){
            Toast.makeText(RegistrarUsuario.this,"Usuario Registrado", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(RegistrarUsuario.this,"Error al registrar Usuario", Toast.LENGTH_LONG).show();
        }
    }

    // Limpiar cajas
    public void LimpiarCampos(){
        txtUsuario.getText().clear();
        txtCorreo.getText().clear();
        txtPassword.getText().clear();
    }

}
