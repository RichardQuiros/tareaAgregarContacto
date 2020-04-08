package com.example.tareaagregarcontacto;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import model.Post;

public class Administrador extends AppCompatActivity {
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected ArrayList<Post> database = new ArrayList<>();
    public static String userid;
    public static String like;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public static String getLike() {
        return like;
    }

    public static void setLike(String like) {
        Administrador.like = like;
    }
}
