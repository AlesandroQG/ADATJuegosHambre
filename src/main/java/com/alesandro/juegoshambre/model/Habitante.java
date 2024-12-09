package com.alesandro.juegoshambre.model;

import java.util.*;

public class Habitante {
    private int id;
    private String nombre;
    private int edad;

    public Habitante(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Habitante(int id) {
        this.id = id;
    }

    public Habitante() {}

    @Override
    public String toString() {
        return nombre;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Habitante habitante = (Habitante) o;
        return id == habitante.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
