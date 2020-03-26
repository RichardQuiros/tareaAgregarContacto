package com.example.tareaagregarcontacto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Administrador {


TextView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.mainListaTv);
        mostrarLista();

    }

    public void agregar(View view){
        text = lista.getText().toString();
        startActivity(new Intent(MainActivity.this,agregarContacto.class));
    }

    public void mostrarLista(){
        Intent intent = getIntent();
        String dato = intent.getStringExtra("dato");
        System.out.println("El dato dice "+dato);
        if(dato == null){}

        else{
            lista.setText(text+dato);
        }
    }


}
