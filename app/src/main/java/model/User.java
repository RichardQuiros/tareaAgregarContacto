package model;

public class User {
    String nombre;
    String userid;

    public User(String nombre, String userid) {
        this.nombre = nombre;
        this.userid = userid;
    }

    public User() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
