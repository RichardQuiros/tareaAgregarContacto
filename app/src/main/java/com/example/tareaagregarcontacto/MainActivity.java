package com.example.tareaagregarcontacto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import controller.Myadptador;
import model.Usuario;

public class MainActivity extends Administrador {


    private ListView lista;
    private Myadptador myadptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mostrarLista();
        init();


    }

    public void cambiarAViewAgregar(View view) {
        startActivity(new Intent(MainActivity.this, agregarContacto.class));
    }

    //Muestra la lista desde la nube

    public void mostrarLista() {
    db.collection("user")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
          if(task.isSuccessful()){
              for(QueryDocumentSnapshot i: task.getResult()){
                  Map<String,Object> map = i.getData();
                  Usuario usuario = new Usuario();
                  usuario.setNombre(map.get("nombre").toString());
                  usuario.setApellido(map.get("apellido").toString());
                  usuario.setTelefono(map.get("telefono").toString());
                  usuario.setEmail(map.get("email").toString());
                  usuario.setNombre(map.get("nombre").toString());
                  usuario.setImgurl(Integer.parseInt(map.get("imgurl").toString()));
                  database.add(usuario);
                  Myadptador adapter  = new Myadptador(database,MainActivity.this,R.layout.celda1);
                  lista.setAdapter(adapter);
              }
          }
          else{

          }
        }
    });
    }


    public void init(){
        lista = findViewById(R.id.mainListaContactos);
    }
}


