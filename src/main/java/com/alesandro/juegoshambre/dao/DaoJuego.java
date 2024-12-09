package com.alesandro.juegoshambre.dao;

import com.alesandro.juegoshambre.model.*;
import com.db4o.*;
import com.db4o.query.*;

import java.util.*;

public class DaoJuego {
    public static void insertar(Juego juego, ObjectContainer db) {
        try {
            db.store(juego);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }

    public static Juego buscar(Juego juego, ObjectContainer db) {
        List<Juego> juegos = db.query(new Predicate<Juego>() {
            @Override
            public boolean match(Juego juego1) {
                return juego1.equals(juego);
            }
        });
        if (juegos.isEmpty()) {
            return null;
        }
        return juegos.getFirst();
    }

    public static List<Juego> obtenerTodos(ObjectContainer db) {
        Query query = db.query();
        query.constrain(Juego.class);
        return query.execute();
    }

    public static List<Juego> obtenerJuegosDeTributo(Tributo tributo, ObjectContainer db) {
        return db.query(new Predicate<Juego>() {
            @Override
            public boolean match(Juego juego) {
                for (Tributo tributo1:juego.getTributos()) {
                    if (tributo1.equals(tributo)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public static void eliminar(Juego juego, ObjectContainer db) {
        try {
            db.delete(juego);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }
}
