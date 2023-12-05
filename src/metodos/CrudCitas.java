package metodos;

import entidades.Cita;
import entidades.Pacientes;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CrudCitas {
    public static ArrayList<Cita> leerArchivo() throws RuntimeException {
        ArrayList<Cita> listaLectura = new ArrayList<>();
        try {
            FileInputStream leer =
                    new FileInputStream("C:\\temp\\listaCitas.txt");
            ObjectInputStream streamLectura =
                    new ObjectInputStream(leer);
            if (leer.available() > 0) {
                Object o = streamLectura.readObject();
                listaLectura = (ArrayList<Cita>)o;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EOFException e) {
            System.out.println("archivo vacio");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listaLectura;
    }
    //escribir en archivo
    public static void escribirArchivo(ArrayList<Cita> d) {
        try {
            FileOutputStream escribir =
                    new FileOutputStream("C:\\temp\\listaCitas.txt");
            ObjectOutputStream streamEscritura =
                    new ObjectOutputStream(escribir);
            streamEscritura.writeObject(d);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //crear Cita
    public void agendarCita(Cita c){
        ArrayList<Cita> listaLectura = leerArchivo();

        if (existeCitaConId(c.getIdCita())) {
            System.out.println("ya existe una Cita con ese id");
            return;
        }
        listaLectura.add(c);
        escribirArchivo(listaLectura);
    }
    //buscar Cita
    public Cita buscarCitaPorId(String id) {
        for (Cita c : leerArchivo()) {
            if (c.getIdCita().equals(id)) {
                return c;
            }
        }
        return null;
    }
    //modificar Cita
    public void modificarCita(Cita CitaModificado) {
        ArrayList<Cita> listaLectura = leerArchivo();

        for (int i = 0; i < listaLectura.size(); i++) {
            Cita CitaActual = listaLectura.get(i);
            if (CitaActual.getIdCita().equals(CitaModificado.getIdCita())) {
                listaLectura.set(i, CitaModificado);
                escribirArchivo(listaLectura);
                return;
            }
        }
        System.out.println("No se encontró ninguna cita con el ID: " + CitaModificado.getIdCita());
    }
    //eliminar Cita
    public void borrarCitaPorId(String id) {
        ArrayList<Cita> listaLectura = leerArchivo();

        Cita CitaAEliminar = null;
        for (Cita c : listaLectura) {
            if (c.getIdCita().equals(id)) {
                CitaAEliminar = c;
                break;
            }
        }
        // eliminar Cita en caso de encontrarlo
        if (CitaAEliminar != null) {
            listaLectura.remove(CitaAEliminar);
            escribirArchivo(listaLectura);
            System.out.println("Cita eliminado exitosamente.");
        } else {
            System.out.println("No se encontró ningún Cita con el ID: " + id);
        }
    }
    //limpiar campos
    public String limpiarCampos() {
        return null;
    }
    public boolean camposValidos(Cita c) {
        return c.getNotas() != null && !c.getNotas().isEmpty() &&
                c.getIdCita() != null && !c.getIdCita().isEmpty() &&
                c.getNombreDoctor() != null && !c.getNombreDoctor().isEmpty() &&
                c.getNombrePaciente() != null && !c.getNombrePaciente().isEmpty() &&
                c.getFechaAgenda() != null;
    }
    //verificar si existen Citaes
    public boolean existeCitaConId(String id) {
        ArrayList<Cita> listaCita = leerArchivo();

        for (Cita c : listaCita) {
            if (c.getIdCita().equals(id)) {
                return true; // El Cita con el ID dado ya existe
            }
        }
        return false;
    }
    //validar que el formato de la fecha sea correcto
    public boolean validarFechaCita(String fecha){
        try{
            SimpleDateFormat formatoFecha =
                    new SimpleDateFormat("dd/MM/yyyy 'at' HH");
            formatoFecha.setLenient(false);
            Date fechaCita = formatoFecha.parse(fecha);
            System.out.println(fechaCita);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
