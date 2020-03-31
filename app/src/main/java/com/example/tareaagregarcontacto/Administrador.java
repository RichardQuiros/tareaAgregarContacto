package com.example.tareaagregarcontacto;

import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import controller.Myadptador;
import model.Usuario;

public class Administrador extends AppCompatActivity {
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected ArrayList<Usuario> database = new ArrayList<>();
}
