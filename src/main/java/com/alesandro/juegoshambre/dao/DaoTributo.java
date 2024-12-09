package com.alesandro.juegoshambre.dao;

import com.alesandro.juegoshambre.model.*;
import com.db4o.*;
import com.db4o.query.*;

import java.util.*;

public class DaoTributo {
    public static void insertar(Tributo tributo, ObjectContainer db) {
        try {
            db.store(tributo);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }

    public static Tributo buscar(Tributo tributo, ObjectContainer db) {
        List<Tributo> tributos = db.query(new Predicate<Tributo>() {
            @Override
            public boolean match(Tributo tributo1) {
                return tributo1.equals(tributo);
            }
        });
        if (tributos.isEmpty()) {
            return null;
        }
        return tributos.getFirst();
    }

    public static List<Tributo> obtenerTodos(ObjectContainer db) {
        Query query = db.query();
        query.constrain(Tributo.class);
        return query.execute();
    }

    public static List<Tributo> obtenerMayoresDe16(ObjectContainer db) {
        return db.query(new Predicate<Tributo>() {
            @Override
            public boolean match(Tributo tributo) {
                return tributo.getHabitante().getEdad() >= 16;
            }
        });
    }
}
