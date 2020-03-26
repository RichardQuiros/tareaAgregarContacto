package com.example.tareaagregarcontacto;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CrearContacto extends AppCompatActivity {
     public static Context context;

    public static LinearLayout linearLayout = new LinearLayout(context);
    TextView nombre = new TextView(this);
    TextView apellido = new TextView(this);
    TextView numero = new TextView(this);

    public static ArrayList<LinearLayout> arrayList = new ArrayList<>();

     public  CrearContacto(String nombreTv, String apellidoTv, String numeroTv) {
       nombre.setText(nombreTv);
       apellido.setText(apellidoTv);
       numero.setText(numeroTv);
       linearLayout.addView(nombre);
       linearLayout.addView(apellido);
       linearLayout.addView(numero);
    }



    public static LinearLayout agregar(){
      return linearLayout;
    }

}
