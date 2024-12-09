package com.alesandro.juegoshambre;

import com.db4o.*;

public class DBManager {
    private static ObjectContainer db = null;

    public static ObjectContainer connect() {
        if (db == null) {
            db = Db4oEmbedded.openFile("juegos_del_hambreAlesandro.db4o");
        }
        return db;
    }

    public static void disconnect() {
        if (db != null) {
            db.close();
            db = null;
        }
    }
}
