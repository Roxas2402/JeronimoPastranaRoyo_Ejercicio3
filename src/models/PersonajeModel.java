package models;

import java.io.Serializable;

public class PersonajeModel implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean sobrevive;

    public PersonajeModel() {
    }

    public PersonajeModel(int id, String nombre, String descripcion, boolean sobrevive) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sobrevive = sobrevive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSobrevive() {
        return sobrevive;
    }

    public void setSobrevive(boolean sobrevive) {
        this.sobrevive = sobrevive;
    }

    @Override
    public String toString() {
        return "PersonajeModel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", sobrevive=" + sobrevive +
                '}';
    }
}
