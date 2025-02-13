package com.alesandro.juegoshambre;

import com.alesandro.juegoshambre.dao.*;
import com.alesandro.juegoshambre.model.*;
import com.db4o.*;

import java.io.*;
import java.util.*;

/**
 * Gestión de Los Juegos del Hambre
 *
 * @author Alesandro Quirós Gobbato
 */
public class GestionJuegosHambre {
    public static void importarCSV() {
        File f = new File("juegos_del_hambreAlesandro.db4o");
        if (f.exists()) {
            System.out.println("Eliminando fichero...");
            f.delete();
        }
        ObjectContainer db = DBManager.connect();
        // Empezar con Distritos
        try (BufferedReader br = new BufferedReader(new FileReader("csv/distritos.csv"))) {
            String linea = br.readLine();
            linea = br.readLine();
            while (linea != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                Distrito distrito = new Distrito(id, nombre);
                if (DaoDistrito.buscar(distrito, db) == null) {
                    DaoDistrito.insertar(distrito, db);
                }
                linea = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        // Continuar con Habitantes
        try (BufferedReader br = new BufferedReader(new FileReader("csv/habitantes.csv"))) {
            String linea = br.readLine();
            linea = br.readLine();
            while (linea != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                int edad = Integer.parseInt(partes[2]);
                Distrito distrito = DaoDistrito.buscar(new Distrito(Integer.parseInt(partes[4])), db);
                Habitante habitante = new Habitante(id, nombre, edad);
                if (DaoHabitante.buscar(habitante, db) == null) {
                    DaoHabitante.insertar(habitante, db);
                    if (partes[3].equals("True")) {
                        String habilidades = partes[5];
                        int puntaje = Integer.parseInt(partes[6]);
                        Tributo tributo = new Tributo(habitante, puntaje, habilidades);
                        DaoTributo.insertar(tributo, db);
                    }
                }
                List<Habitante> habitantes = new ArrayList<Habitante>();
                habitantes.addAll(distrito.getHabitantes());
                habitantes.add(habitante);
                distrito.setHabitantes(habitantes);
                DaoDistrito.insertar(distrito, db);
                linea = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        // Terminar con Juegos
        try (BufferedReader br = new BufferedReader(new FileReader("csv/juegos.csv"))) {
            String linea = br.readLine();
            linea = br.readLine();
            while (linea != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                int anio = Integer.parseInt(partes[1]);
                String[] tributos_ids = partes[2].split(";");
                List<Tributo> tributos = new ArrayList<Tributo>();
                Tributo ganador = new Tributo(new Habitante(Integer.parseInt(partes[3])));
                for (String tributo_id:tributos_ids) {
                    Tributo t = new Tributo(new Habitante(Integer.parseInt(tributo_id)));
                    Tributo tributo = DaoTributo.buscar(t, db);
                    if (tributo != null) {
                        tributos.add(tributo);
                        if (tributo.equals(ganador)) {
                            ganador = tributo;
                        }
                    }
                }
                Juego juego = new Juego(id, anio, tributos, ganador);
                if (DaoJuego.buscar(juego, db) == null) {
                    DaoJuego.insertar(juego, db);
                }
                linea = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Base de datos importada correctamente");
        DBManager.disconnect();
    }

    public static void obtenerDistritos() {
        ObjectContainer db = DBManager.connect();
        List<Distrito> distritos = DaoDistrito.obtenerTodos(db);
        System.out.println("Todos distritos:");
        for (Distrito distrito:distritos) {
            System.out.println("\t- " + distrito.getNombre());
        }
        DBManager.disconnect();
    }

    public static void obtenerTributos() {
        ObjectContainer db = DBManager.connect();
        List<Tributo> tributos = DaoTributo.obtenerMayoresDe16(db);
        System.out.println("Tributos mayores de 16:");
        for (Tributo tributo:tributos) {
            System.out.println("\t- " + tributo.getHabitante().getNombre());
        }
        DBManager.disconnect();
    }

    public static void obtenerJuegosDeTributo() {
        ObjectContainer db = DBManager.connect();
        List<Tributo> tributos = DaoTributo.obtenerTodos(db);
        String seleccion_tributo = "Selecciona un tributo:";
        for (int i = 1;i<=tributos.size();i++) {
            seleccion_tributo += "\n" + i + ". " + tributos.get(i-1).getHabitante().getNombre();
        }
        System.out.println(seleccion_tributo);
        int tributo_seleccionado = Consola.leeInt();
        Tributo tributo = tributos.get(tributo_seleccionado - 1);
        List<Juego> juegos = DaoJuego.obtenerJuegosDeTributo(tributo, db);
        System.out.println("Juegos del tributo " + tributo.getHabitante().getNombre() + ":");
        for (Juego juego:juegos) {
            System.out.println("\t- Juego del " + juego.getAnio());
        }
        DBManager.disconnect();
    }

    public static void modificarHabitante() {
        ObjectContainer db = DBManager.connect();
        List<Habitante> habitantes = DaoHabitante.obtenerTodos(db);
        String seleccion_habitante = "Selecciona un habitante a modificar:";
        for (int i = 1;i<=habitantes.size();i++) {
            seleccion_habitante += "\n" + i + ". " + habitantes.get(i-1).getNombre();
        }
        System.out.println(seleccion_habitante);
        int habitante_seleccionado = Consola.leeInt();
        Habitante habitante = habitantes.get(habitante_seleccionado - 1);
        System.out.println("Introduce un nuevo nombre (actual: " + habitante.getNombre() + "):");
        String nombre = Consola.leeString();
        System.out.println("Introduce una nueva edad (actual: " + habitante.getEdad() + "):");
        int edad = Consola.leeInt();
        habitante.setNombre(nombre);
        habitante.setEdad(edad);
        DaoHabitante.insertar(habitante, db);
        System.out.println("Habitante actualizado");
        DBManager.disconnect();
    }

    public static void cambiarGanadorDeJuego() {
        ObjectContainer db = DBManager.connect();
        List<Juego> juegos = DaoJuego.obtenerTodos(db);
        String seleccion_juego = "Selecciona un juego a modificar:";
        for (int i = 1;i<=juegos.size();i++) {
            seleccion_juego += "\n" + i + ". Juego del " + juegos.get(i-1).getAnio();
        }
        System.out.println(seleccion_juego);
        int juego_seleccionado = Consola.leeInt();
        Juego juego = juegos.get(juego_seleccionado - 1);
        String seleccion_ganador = "Selecciona un participante como ganador (actual: " + juego.getGanador().getHabitante().getNombre() + "):";
        for (int i=1;i<=juego.getTributos().size();i++) {
            seleccion_ganador += "\n" + i + ". " + juego.getTributos().get(i-1).getHabitante().getNombre();
        }
        System.out.println(seleccion_ganador);
        int ganador_seleccionado = Consola.leeInt();
        Tributo tributo = juego.getTributos().get(ganador_seleccionado-1);
        juego.setGanador(tributo);
        DaoJuego.insertar(juego, db);
        System.out.println("Ganador actualizado");
        DBManager.disconnect();
    }

    public static void agregar() {
        ObjectContainer db = DBManager.connect();
        String menu = "AGREGAR\n" +
                "1. Tributo\n" +
                "2. Habitante\n" +
                "3. Distrito\n" +
                "\n" +
                "0. Cancelar";
        System.out.println(menu);
        int opcion = Consola.leeInt();
        switch (opcion) {
            case 1:
                List<Habitante> habitantes = DaoHabitante.obtenerTodos(db);
                int habitante_seleccionado = menuOpcion(habitantes);
                Habitante habitante = habitantes.get(habitante_seleccionado);
                System.out.println("Introduce puntaje del tributo");
                int puntaje = Consola.leeInt();
                System.out.println("Introduce habilidades especiales del tributo (si es que tiene)");
                String habilidades = Consola.leeString();
                Tributo tributo = new Tributo(habitante, puntaje, habilidades);
                DaoTributo.insertar(tributo, db);
                System.out.println("Tributo añadido correctamente");
                break;
            case 2:
                System.out.println("Introduce id del habitante");
                int id = Consola.leeInt();
                System.out.println("Introduce nombre del habitante");
                String nombre = Consola.leeString();
                System.out.println("Introduce edad del habitante");
                int edad = Consola.leeInt();
                Habitante habitante1 = new Habitante(id, nombre, edad);
                DaoHabitante.insertar(habitante1, db);
                System.out.println("Habitante añadido correctamente");
                break;
            case 3:
                System.out.println("Introduce id del distrito");
                int id1 = Consola.leeInt();
                System.out.println("Introduce nombre del distrito");
                String nombre1 = Consola.leeString();
                Distrito distrito = new Distrito(id1, nombre1);
                DaoDistrito.insertar(distrito, db);
                System.out.println("Distrito añadido correctamente");
                break;
        }
        DBManager.disconnect();
    }

    public static void eliminar() {
        ObjectContainer db = DBManager.connect();
        String menu = "ELIMINAR\n" +
                "1. Distrito\n" +
                "2. Habitante\n" +
                "3. Juego\n" +
                "\n" +
                "0. Cancelar";
        System.out.println(menu);
        int opcion = Consola.leeInt();
        switch (opcion) {
            case 1:
                List<Distrito> distritos = DaoDistrito.obtenerTodos(db);
                int distrito_seleccionado = menuOpcion(distritos);
                Distrito distrito = distritos.get(distrito_seleccionado);
                DaoDistrito.eliminar(distrito, db);
                System.out.println("Distrito eliminado correctamente");
                break;
            case 2:
                List<Habitante> habitantes = DaoHabitante.obtenerTodos(db);
                int habitante_seleccionado = menuOpcion(habitantes);
                Habitante habitante = habitantes.get(habitante_seleccionado);
                DaoHabitante.eliminar(habitante, db);
                System.out.println("Habitante eliminado correctamente");
                break;
            case 3:
                List<Juego> juegos = DaoJuego.obtenerTodos(db);
                int juego_seleccionado = menuOpcion(juegos);
                Juego juego = juegos.get(juego_seleccionado);
                DaoJuego.eliminar(juego, db);
                System.out.println("Juego eliminado correctamente");
                break;
        }
        DBManager.disconnect();
    }

    /**
     * Función para hacer la búsqueda de una lista por números
     * Realize esta función para el final del examen, por lo que solo lo use para agregar y eliminar
     *
     * @param lista lista de objetos
     * @return selección del usuario como índice de la lista
     */
    private static int menuOpcion(List lista) {
        String menu = "Seleccione una opción: ";
        for (int i=1;i<=lista.size();i++) {
            menu += "\n" + i + ". " + lista.get(i-1);
        }
        System.out.println(menu);
        int seleccion = Consola.leeInt();
        return seleccion - 1;
    }

    public static void main(String[] args) {
        String menu = "GESTIÓN DE LOS JUEGOS DEL HAMBRE\n" +
                "1. Importar csv\n" +
                "Consultas\n" +
                "2. Obtener todos distritos\n" +
                "3. Obtener todos tributos mayores de 16 años\n" +
                "4. Obtener juegos de un tributo en especifico\n" +
                "Actualizaciones\n" +
                "5. Modificar datos de un habitante\n" +
                "6. Cambiar ganador de un juego\n" +
                "Añadir elementos\n" +
                "7. Añadir\n" +
                "Eliminar elementos\n" +
                "8. Eliminar\n" +
                "\n" +
                "0. Cerrar";
        System.out.println(menu);
        int opcion = Consola.leeInt();
        while (opcion != 0) {
            switch (opcion) {
                case 1:
                    importarCSV();
                    break;
                case 2:
                    obtenerDistritos();
                    break;
                case 3:
                    obtenerTributos();
                    break;
                case 4:
                    obtenerJuegosDeTributo();
                    break;
                case 5:
                    modificarHabitante();
                    break;
                case 6:
                    cambiarGanadorDeJuego();
                    break;
                case 7:
                    agregar();
                    break;
                case 8:
                    eliminar();
                    break;
            }
            System.out.println(menu);
            opcion = Consola.leeInt();
        }
    }
}
