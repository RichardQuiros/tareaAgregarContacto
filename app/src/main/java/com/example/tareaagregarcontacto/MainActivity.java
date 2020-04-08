package com.example.tareaagregarcontacto;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import controller.Myadptador;
import model.Post;

public class MainActivity extends Administrador {


    private ListView lista;
    private Myadptador myadptador;
   // private Dialog dialog;
    private boolean likeUser;
    private int likeEncendidos = 0;
    private boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mostrarLista();
        init();
        itemclic();


    }

    public void cambiarAViewAgregar(View view) {
        startActivity(new Intent(MainActivity.this, agregarContacto.class));
    }

    //Muestra la lista desde la nube

    public void mostrarLista() {
    db.collection("post")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
          if(task.isSuccessful()){
              for(QueryDocumentSnapshot i: task.getResult()){
                  Map<String,Object> map = i.getData();
                  Post nuevoPost = new Post();
                  nuevoPost.setNombre(map.get("nombre").toString());
                  nuevoPost.setApellido(map.get("apellido").toString());
                  nuevoPost.setTelefono(map.get("telefono").toString());
                  nuevoPost.setEmail(map.get("email").toString());
                  nuevoPost.setNombre(map.get("nombre").toString());
                  nuevoPost.setLike(map.get("like").toString());
                  nuevoPost.setImgurl(map.get("imgurl").toString());
                  nuevoPost.setId(map.get("id").toString());
                  database.add(nuevoPost);
                  Myadptador adapter  = new Myadptador(database,MainActivity.this,R.layout.celda1);
                  lista.setAdapter(adapter);
              }
          }
          else{

          }
        }
    });
    }


    //item¿n clic

    public void itemclic(){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //verificador de like
                checkCollectionLike(position);
                actualizarLikes(position);
                consultaDeLike(position);

                final TextView nombre;
                final TextView numberlike;
                ImageButton perfil;
                ImageButton telefono;
                final ImageButton correo;
                final ImageButton like;
                final Dialog dialog = new  Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_contacto);
                nombre = dialog.findViewById(R.id.nombre);
                numberlike = dialog.findViewById(R.id.numberlike);
                perfil = dialog.findViewById(R.id.perfil);
                telefono = dialog.findViewById(R.id.telefono);
                correo = dialog.findViewById(R.id.correo);
                like = dialog.findViewById(R.id.like);
                //set text cantidad de like
                  numberlike.setText(getLike());
                //boton strella apagado o encendido
                if(likeUser){
                    like.setImageResource(android.R.drawable.btn_star_big_on);
                }
                else{
                    like.setImageResource(android.R.drawable.btn_star_big_off);
                }

                nombre.setText(database.get(position).getNombre()+" "+database.get(position).getApellido().toString());
                perfil.setImageURI(Uri.parse(database.get(position).getImgurl()));

                perfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,perfil_post_activity.class).putExtra("id",database.get(position).getId()));
                    }
                });

                telefono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,database.get(position).getTelefono(),Toast.LENGTH_SHORT).show();
                    }
                });

                correo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,database.get(position).getEmail(),Toast.LENGTH_SHORT).show();
                    }
                });

                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localUpdateLike(position);
                        if(likeUser){
                            like.setImageResource(android.R.drawable.btn_star_big_off);
                            Map<String, Object> map = new HashMap<>();
                            map.put(getUserid(),false);
                            db.collection("like").document(database.get(position).getId()).set(map);
                            actualizarLikes(position);
                            consultaDeLike(position);
                            numberlike.setText(getLike());
                            nombre.setText("false"+getLike());
                        }
                        else{
                            like.setImageResource(android.R.drawable.btn_star_big_on);
                            Map<String, Object> map = new HashMap<>();
                            map.put(getUserid(),true);
                            db.collection("like").document(database.get(position).getId()).set(map);
                            actualizarLikes(position);
                            consultaDeLike(position);
                            numberlike.setText(getLike());
                            nombre.setText("true"+getLike());

                        }
                    }
                });

                dialog.show();
            }
        });
    }


    public void init(){
        lista = findViewById(R.id.mainListaContactos);

    }

    public void actualizarLikes(final int position){
        db.collection("like").document(database.get(position).getId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                likeEncendidos = 0;
                                Map<String,Object> map = documentSnapshot.getData();
                                for(Map.Entry<String,Object> entry : map.entrySet()){
                                    if(Boolean.valueOf(entry.getValue().toString())){
                                        likeEncendidos = likeEncendidos +1;
                                    }
                                    DocumentReference referenciaPost = db.collection("post").document(database.get(position).getId());
                                    referenciaPost.update("like", likeEncendidos);
                                }
                            }

                        }
                        else{
                            //crear verificador de like para usuario
                            Toast.makeText(MainActivity.this,"ERROR:"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void consultaDeLike(final int position){
        db.collection("post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getId().equals(database.get(position).getId())){
                            Map<String,Object> map =documentSnapshot.getData();

                            setLike(map.get("like").toString());
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Error base de datps:"+task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkCollectionLike(final int position){
         final DocumentReference reference =   db.collection("like").document(database.get(position).getId());
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists()) {
                            Map<String, Object> map = documentSnapshot.getData();
                            if( map.containsKey(getUserid())){
                                likeUser =Boolean.valueOf(map.get(getUserid()).toString());
                            }
                            else {
                                map.put(getUserid(),false);
                                db.collection("like").document(database.get(position).getId()).set(map);
                                likeUser = false;
                            }
                        }
                        else{
                            Map<String, Object> map = new HashMap<>();
                            map.put(getUserid(),false);
                            db.collection("like").document(database.get(position).getId()).set(map);
                            likeUser = false;
                        }
                    }
                    else {
                        Map<String, Object> map = new HashMap<>();
                        map.put(getUserid(),false);
                        db.collection("like").document(database.get(position).getId()).set(map);
                        likeUser = false;
                    }
                }
            });

    }

    public void localUpdateLike(int position){
        db.collection("like").document(database.get(position).getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null){;
                    return;}
                if(documentSnapshot != null && documentSnapshot.exists()){
                    Toast.makeText(MainActivity.this,"Actualizando boton like",Toast.LENGTH_SHORT).show();
                    Map<String, Object> map = documentSnapshot.getData();
                    likeUser =Boolean.valueOf(map.get(getUserid()).toString());
                }
                else{
                    Toast.makeText(MainActivity.this,"prueva fallida",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}


