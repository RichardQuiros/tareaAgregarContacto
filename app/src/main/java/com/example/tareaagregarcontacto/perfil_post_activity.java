package com.example.tareaagregarcontacto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class perfil_post_activity extends Administrador {
     TextView nombre;
     TextView telefono;
     TextView correo;
     TextView numberLike;
     TextView nota;
     ImageView perfil;
     ImageButton cerrar;
     String id;
    private int likeEncendidos = 0;
     private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_post_activity);
        init();
        close();

        updateText();
    }

    public void init(){
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        correo = findViewById(R.id.correo);
        numberLike = findViewById(R.id.like);
        nota = findViewById(R.id.nota);
        perfil = findViewById(R.id.perfil);
        cerrar= findViewById(R.id.cerrar);
        id = getIntent().getStringExtra("id");
        db = FirebaseFirestore.getInstance();
    }

    public void close(){
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(perfil_post_activity.this,MainActivity.class));
            }
        });
    }

    public void updateText() {
        actualizarLikes();
        db.collection("post").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Map<String,Object> mapa = documentSnapshot.getData();
                            numberLike.setText(mapa.get("like").toString());
                        }
                        else{
                            //crear verificador de like para usuario
                            Toast.makeText(perfil_post_activity.this,"ERROR:"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        db.collection("post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful()){
                  for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                       if(documentSnapshot.getId().equals(id)){
                           Map<String,Object> map =documentSnapshot.getData();

                           nombre.setText(map.get("nombre").toString()+" "+map.get("apellido").toString());
                           telefono.setText(map.get("telefono").toString());
                           correo.setText(map.get("email").toString());
                           numberLike.setText(map.get("like").toString());
                           nota.setText(map.get("comentario").toString());
                           perfil.setImageURI(Uri.parse(map.get("imgurl").toString()));

                       }
                   }
               }
               else{
                   Toast.makeText(perfil_post_activity.this,"Error base de datps:"+task.getException().toString(),Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    public void actualizarLikes(){
        db.collection("like").document(id).get()
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
                                    DocumentReference referenciaPost = db.collection("post").document(id);
                                    referenciaPost.update("like", likeEncendidos);
                                }
                            }

                        }
                        else{
                            //crear verificador de like para usuario
                            Toast.makeText(perfil_post_activity.this,"ERROR:"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
