package com.example.tareaagregarcontacto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import model.User;

public class inicio_Activity extends Administrador {
Button regis;
Button log;
Dialog dialog;
User userr;
//Firebase
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_);
        init();
        regis();
        log();
        // Check if user is signed in (non-null) and update UI accordingly.
     //   FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void init(){
        regis = findViewById(R.id.registrobt);
        log = findViewById(R.id.loggeobt);
        dialog = new Dialog(inicio_Activity.this);
         //FireBase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this,currentUser.toString(),Toast.LENGTH_LONG).show();
        userr = new User();
    }

    //registro y validacion
    public void regis(){
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(inicio_Activity.this,"boton precionado",Toast.LENGTH_SHORT).show();
                ImageButton close;
                final Button aceptar;
                final EditText nombre;
                final EditText correo;
                final EditText contraseña;
                dialog.setContentView(R.layout.celda_registro);
                close = dialog.findViewById(R.id.celdaCancelarBt);
               aceptar = dialog.findViewById(R.id.aceptar);
               nombre = dialog.findViewById(R.id.nombre);
               correo = dialog.findViewById(R.id.correo);
               contraseña = dialog.findViewById(R.id.contraseña);

               //Accion boton cerrar
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //accion validar registro/ registrar
                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(nombre.getText().toString().isEmpty()||correo.getText().toString().isEmpty()
                        ||contraseña.getText().toString().isEmpty()){
                            Toast.makeText(inicio_Activity.this,"Debe llenar todos los parametros",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                                    .addOnCompleteListener(inicio_Activity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                           if(task.isSuccessful()){

                                               Toast.makeText(inicio_Activity.this,"registro completado",Toast.LENGTH_SHORT).show();
                                               db.collection("user").document(task.getResult().getUser().getUid()).set(new User(nombre.getText().toString(),task.getResult().getUser().getUid()));
                                               setUserid(task.getResult().getUser().getUid());
                                               dialog.dismiss();
                                               startActivity(new Intent(inicio_Activity.this,MainActivity.class));
                                           }
                                           else{
                                               Toast.makeText(inicio_Activity.this,"Fallo:"+task.getException().toString(),Toast.LENGTH_SHORT).show();
                                           }
                                        }
                                    });
                        }
                    }
                });
           dialog.show(); }
        });
    }

    //logeo de usuarios
    public void log(){
        //popUp
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton close;
                Button aceptar;
                final EditText correo;
                final EditText contraseña;
                dialog.setContentView(R.layout.celda_login);
                close = dialog.findViewById(R.id.celdaCancelarBt);
                aceptar = dialog.findViewById(R.id.aceptar);
                correo = dialog.findViewById(R.id.correo);
                contraseña = dialog.findViewById(R.id.contraseña);
                //Accion boton cerrar
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //accion validar logeo/ logeo
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(correo.getText().toString().isEmpty()
                                ||contraseña.getText().toString().isEmpty()){
                            Toast.makeText(inicio_Activity.this,"Debe llenar todos los campos",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mAuth.signInWithEmailAndPassword(correo.getText().toString(),contraseña.getText().toString())
                                    .addOnCompleteListener(inicio_Activity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                setUserid(task.getResult().getUser().getUid());
                                                Toast.makeText(inicio_Activity.this,"logeo completado:"+getUserid(),Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                                startActivity(new Intent(inicio_Activity.this,MainActivity.class));
                                            }
                                            else{
                                                Toast.makeText(inicio_Activity.this,"error:"+task.getException(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                    }
                });
        dialog.show();    }
        });
    }



}
