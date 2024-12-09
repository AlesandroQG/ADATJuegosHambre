package com.alesandro.juegoshambre.model;

import java.util.*;

public class Juego {
    private int id;
    private int anio;
    private List<Tributo> tributos = new ArrayList<Tributo>();
    private Tributo ganador;

    public Juego(int id, int anio, List<Tributo> tributos, Tributo ganador) {
        this.id = id;
        this.anio = anio;
        this.tributos = tributos;
        this.ganador = ganador;
    }

    public Juego(int id) {
        this.id = id;
    }

    public Juego() {}

    @Override
    public String toString() {
        return "Juego del " + anio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<Tributo> getTributos() {
        return tributos;
    }

    public void setTributos(List<Tributo> tributos) {
        this.tributos = tributos;
    }

    public Tributo getGanador() {
        return ganador;
    }

    public void setGanador(Tributo ganador) {
        this.ganador = ganador;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Juego juego = (Juego) o;
        return id == juego.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
