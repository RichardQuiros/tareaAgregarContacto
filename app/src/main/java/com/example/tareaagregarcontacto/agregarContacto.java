package com.example.tareaagregarcontacto;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import model.Usuario;

public class agregarContacto extends Administrador {
    TextView nombreTv;
    TextView apellidoTv;
    TextView numeroTv;
    Button agregarBt;
    ImageButton agregarPerfil;
    Intent intent;
    Switch switchAgregar;
    Dialog dialog;
    LinearLayout agregarLayoutParametros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        init();
        escuchadorSwicht();
    }


    //metodo para concultar contactos

    public void init(){
            setContentView(R.layout.activity_agregar_contacto);
            nombreTv = findViewById(R.id.agregarNombreTv);
            apellidoTv = findViewById(R.id.agregarApellidoTv);
            numeroTv = findViewById(R.id.agregarTelefonoTv);
            agregarBt = findViewById(R.id.agregarAgregarBt);
            agregarPerfil = findViewById(R.id.agregarPerfilBt);
            switchAgregar = findViewById(R.id.agregarMasSw);
            dialog = new Dialog(this);
            agregarLayoutParametros = findViewById(R.id.agregarLayoutParametros);
            intent = new Intent(this,MainActivity.class);
    }


    //escuchador del switch para mostrar mas parametros
    public void escuchadorSwicht(){
        switchAgregar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    agregarLayoutParametros.setVisibility(View.VISIBLE);
                }
                else{
                    agregarLayoutParametros.setVisibility(View.GONE);
                }
            }
        });
    }

    //PopUp de agregar perfil
    public void popupPerfil(){
        agregarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton close;
                ImageButton camara;
                ImageButton galleria;
                ImageButton color;
               // dialog.setContentView(R.layout);
            }
        });
    }


    //agregar contacto con intent
public void agregarContactoConIntent(View view){
        if(nombreTv.getText().toString().isEmpty()||apellidoTv.getText().toString().isEmpty()||numeroTv.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"llene todos los campos",Toast.LENGTH_SHORT).show();
        }
        else {
            System.out.println("se precio");
            db.collection("user").add(new Usuario(nombreTv.getText().toString(),apellidoTv.getText().toString()
                    ,numeroTv.getText().toString(),"mail@mail.com",R.drawable.shape_circle_blue_gradient))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                      Toast.makeText(getApplicationContext(),"Fallo Data Base,Razon:"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            });

        }

}

}

