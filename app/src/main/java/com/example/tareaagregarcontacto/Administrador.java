package com.example.tareaagregarcontacto;

import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Administrador extends AppCompatActivity {

    public static  String text = "";
    public static ArrayList<LinearLayout> arrayList = new ArrayList<>();

    public static void añadirContactoCreado(LinearLayout linearLayout){
        arrayList.add(linearLayout);
    }
}
