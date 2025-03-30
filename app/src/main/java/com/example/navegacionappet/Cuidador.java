package com.example.navegacionappet;

public class Cuidador {

    private String nombre;

    private String sexo;

    private String correo;

    private String telefono;

    private int foto;

    public static final Cuidador[] cuidadores = {
            new Cuidador(R.drawable.ilenia, "6543219", "maria@example.com", "Femenino", "Maria Camila Villanueva"),
            new Cuidador(R.drawable.carlos, "1456788", "carlos@example.com", "Masculino", "Carlos Villagran"),
            new Cuidador(R.drawable.julian, "7412588", "julian@example.com", "Masculino", "Julián David Nova")
    };

    public Cuidador(int foto, String telefono, String correo, String sexo, String nombre) {
        this.foto = foto;
        this.telefono = telefono;
        this.correo = correo;
        this.sexo = sexo;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
