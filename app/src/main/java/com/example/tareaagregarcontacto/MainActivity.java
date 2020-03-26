package com.example.tareaagregarcontacto;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import controller.Myadptador;
import model.Usuario;

public class MainActivity extends Administrador {


    private ListView lista;
    private ListView listView;
    private ArrayList<Usuario> database = new ArrayList<>();
    private ArrayList<String> data1 = new ArrayList<>();
    private ArrayAdapter<Usuario> adapter;
    private ArrayAdapter<String> adapter1;

    Myadptador myadptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.mainListaContactos);
        mostrarLista();
        addUser();
      //  adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,database);
      //  adapter1 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,data1);
        myadptador = new Myadptador(database,MainActivity.this,R.layout.celda1);
        lista.setAdapter(myadptador);

    }

    public void agregar(View view) {
        startActivity(new Intent(MainActivity.this, agregarContacto.class));
    }

    public void mostrarLista() {
        Intent intent = getIntent();
        String dato = intent.getStringExtra("dato");
        if (dato == null) {
        } else {
        }
    }

    private void addUser() {
        database.add(new Usuario("Richard","Quiros","3453534","mail.@mail.com",R.drawable.shape_circle_blue_gradient));
        database.add(new Usuario("Juan","Pineda","32431","mail.@mail.com",R.drawable.shape_circle_blue_gradient));
        database.add(new Usuario("Alturo","Jhair","4355","mail.@mail.com",R.drawable.shape_circle_blue_gradient));

        data1.add("1Richard");
        data1.add("2Richard");
        data1.add("3Richard");

    }
}


