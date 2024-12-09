package com.alesandro.juegoshambre.dao;

import com.alesandro.juegoshambre.model.*;
import com.db4o.*;
import com.db4o.query.*;

import java.util.*;

public class DaoHabitante {
    public static void insertar(Habitante habitante, ObjectContainer db) {
        try {
            db.store(habitante);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }

    public static Habitante buscar(Habitante habitante, ObjectContainer db) {
        List<Habitante> habitantes = db.query(new Predicate<Habitante>() {
            @Override
            public boolean match(Habitante habitante1) {
                return habitante1.equals(habitante);
            }
        });
        if (habitantes.isEmpty()) {
            return null;
        }
        return habitantes.getFirst();
    }

    public static List<Habitante> obtenerTodos(ObjectContainer db) {
        Query query = db.query();
        query.constrain(Habitante.class);
        return query.execute();
    }

    public static void eliminar(Habitante habitante, ObjectContainer db) {
        try {
            db.delete(habitante);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }
}
