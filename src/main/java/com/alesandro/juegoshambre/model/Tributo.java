package com.alesandro.juegoshambre.model;

import java.util.*;

public class Tributo {
    private Habitante habitante;
    private int puntaje;
    private String habilidades;

    public Tributo(Habitante habitante, int puntaje, String habilidades) {
        this.habitante = habitante;
        this.puntaje = puntaje;
        this.habilidades = habilidades;
    }

    public Tributo(Habitante habitante) {
        this.habitante = habitante;
    }

    public Tributo() {}

    @Override
    public String toString() {
        return habitante.getNombre();
    }

    public Habitante getHabitante() {
        return habitante;
    }

    public void setHabitante(Habitante habitante) {
        this.habitante = habitante;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tributo tributo = (Tributo) o;
        return Objects.equals(habitante, tributo.habitante);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(habitante);
    }
}
