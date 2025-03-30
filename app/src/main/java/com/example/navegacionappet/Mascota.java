package com.example.navegacionappet;

public class Mascota {
    private String nombre;
    private String raza;
    private String descripcion;
    private int foto;
    private int edad;
    private String sexo;

    public static final Mascota[] mascotas = {
            new Mascota("Maximo", "Criollo", "Amarillo", R.drawable.maximo, 15, "Macho"),
            new Mascota("Ozzy", "Criollo", "Negro", R.drawable.ozzy, 2, "Macho"),
            new Mascota("Luna", "Criollo", "Blanco", R.drawable.luna, 3, "Hembra")
    };
    public Mascota(String nombre, String raza, String descripcion, int foto, int edad, String sexo) {
        this.nombre = nombre;
        this.raza = raza;
        this.descripcion = descripcion;
        this.foto = foto;
        this.edad = edad;
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
