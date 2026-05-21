package com.cuidados.paliativos.modelo;

public class Voluntario {
    private Long id;
    private String nombre;
    private String apellido;
    private Area area;
    private String telefono;
    private Usuario usuario;

    public Voluntario() {}

    public Voluntario(Long id, String nombre, String apellido, Area area,
                       String telefono, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.area = area;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Area getArea() { return area; }

    public void setArea(Area area) { this.area = area; }
}
