package com.alesandro.juegoshambre.dao;

import com.alesandro.juegoshambre.model.*;
import com.db4o.*;
import com.db4o.query.*;

import java.util.*;

public class DaoDistrito {
    public static void insertar(Distrito distrito, ObjectContainer db) {
        try {
            db.store(distrito);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }

    public static Distrito buscar(Distrito distrito, ObjectContainer db) {
        List<Distrito> distritos = db.query(new Predicate<Distrito>() {
            @Override
            public boolean match(Distrito distrito1) {
                return distrito1.equals(distrito);
            }
        });
        if (distritos.isEmpty()) {
            return null;
        }
        return distritos.getFirst();
    }

    public static List<Distrito> obtenerTodos(ObjectContainer db) {
        Query query = db.query();
        query.constrain(Distrito.class);
        return query.execute();
    }

    public static void eliminar(Distrito distrito, ObjectContainer db) {
        try {
            db.delete(distrito);
            db.commit();
        } catch (Exception e) {
            db.rollback();
            System.err.println(e.getMessage());
        }
    }
}
