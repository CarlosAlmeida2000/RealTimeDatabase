package com.example.realtimedatabase.Adaptadores;

public class Persona {
    private String id;
    private String nombres;
    private String apellidos;
    private String cedula;

    public Persona() {
    }

    public Persona(String id, String nombres, String apellidos, String cedula) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
