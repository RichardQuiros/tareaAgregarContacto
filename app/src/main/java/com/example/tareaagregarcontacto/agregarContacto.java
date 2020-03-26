package com.example.tareaagregarcontacto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class agregarContacto extends AppCompatActivity {
    TextView nombreTv;
    TextView apellidoTv;
    TextView numeroTv;
    Button agregarBt;
    String stringDatos;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        init();
    }


    //metodo para concultar contactos

    public void init(){
            nombreTv = findViewById(R.id.agregarNombreTv);
            apellidoTv = findViewById(R.id.agregarApellidoTv);
            numeroTv = findViewById(R.id.agregarNumeroTv);
            agregarBt = findViewById(R.id.agregarAgregarBt);
    }



    //agregar contacto con intent
public void agregarContactoConIntent(View view){
        if(nombreTv.getText().toString().isEmpty()&&apellidoTv.getText().toString().isEmpty()&&numeroTv.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"llene todos los campos",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            stringDatos = nombreTv.getText().toString()+" "+apellidoTv.getText().toString()+" "+numeroTv.getText().toString()+"\n\n";
            intent.putExtra("dato", stringDatos);

            //Agregar lista

            Administrador.añadirContactoCreado(new CrearContacto(nombreTv.getText().toString(),apellidoTv.getText().toString(),numeroTv.getText().toString()).agregar());
           // new debug(5);
            startActivity(intent);

        }

}

}
