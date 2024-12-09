package com.alesandro.juegoshambre.model;

import java.util.*;

public class Distrito {
    private int id;
    private String nombre;
    private List<Habitante> habitantes = new ArrayList<Habitante>();

    public Distrito(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Distrito(int id) {
        this.id = id;
    }

    public Distrito() {}

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

    public List<Habitante> getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(List<Habitante> habitantes) {
        this.habitantes = habitantes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Distrito distrito = (Distrito) o;
        return id == distrito.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
