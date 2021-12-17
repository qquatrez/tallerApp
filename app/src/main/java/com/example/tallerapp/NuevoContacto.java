package com.example.tallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tallerapp.db.DbContactos;

public class NuevoContacto extends AppCompatActivity {
    EditText txtNombre, txtApellido, txtCorreo, txtTelefono;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);

        btnGuardar= (Button)findViewById(R.id.btnGuardar);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtApellido =(EditText)findViewById(R.id.txtApellido);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContactos dbContactos = new DbContactos(NuevoContacto.this);
                long id = dbContactos.AgregarContacto(txtNombre.getText().toString(), txtApellido.getText().toString(),txtCorreo.getText().toString(), txtTelefono.getText().toString());
                if(id>0){
                    Toast.makeText(NuevoContacto.this, "Registro Guardado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                else{
                    Toast.makeText(NuevoContacto.this, "Error al Guardar", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void limpiarCajas(){
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }
}