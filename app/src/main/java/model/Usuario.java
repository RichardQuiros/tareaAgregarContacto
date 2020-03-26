package model;

public class Usuario {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private int imgurl;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String telefono, String email, int imgurl) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.imgurl = imgurl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }
}
