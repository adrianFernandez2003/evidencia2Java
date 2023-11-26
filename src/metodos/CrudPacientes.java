package metodos;

import entidades.Pacientes;

import java.io.*;
import java.util.ArrayList;

public class CrudPacientes {
    public static ArrayList<Pacientes> leerArchivo() throws RuntimeException {
        ArrayList<Pacientes> listaLectura = new ArrayList<>();
        try {
            FileInputStream leer =
                    new FileInputStream("C:\\temp\\listaPacientes.txt");
            ObjectInputStream streamLectura =
                    new ObjectInputStream(leer);
            if (leer.available() > 0) {
                Object o = streamLectura.readObject();
                listaLectura = (ArrayList<Pacientes>)o;
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
    public static void escribirArchivo(ArrayList<Pacientes> p) {
        try {
            FileOutputStream escribir =
                    new FileOutputStream("C:\\temp\\listaPacientes.txt");
            ObjectOutputStream streamEscritura =
                    new ObjectOutputStream(escribir);
            streamEscritura.writeObject(p);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Añadir Paciente
    public void agregarPaciente(Pacientes p){
        ArrayList<Pacientes> listaLectura = leerArchivo();
        listaLectura.add(p);
        escribirArchivo(listaLectura);
    }

    //Buscar Paciente
    public Pacientes buscarPacientePorId(String id) {
        for (Pacientes p : leerArchivo()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    //modificar

    //borrar paciente
    public void borrarPacientePorId(String id) {
        ArrayList<Pacientes> listaLectura = leerArchivo();

        // Iterar sobre la lista para encontrar el paciente con el ID dado
        Pacientes pacienteAEliminar = null;
        for (Pacientes p : listaLectura) {
            if (p.getId().equals(id)) {
                pacienteAEliminar = p;
                break;
            }
        }
        // Si se encontró al paciente, eliminarlo de la lista
        if (pacienteAEliminar != null) {
            listaLectura.remove(pacienteAEliminar);
            escribirArchivo(listaLectura);
            System.out.println("Paciente eliminado exitosamente.");
        } else {
            System.out.println("No se encontró ningún paciente con el ID: " + id);
        }
    }

    //limpiar campos especificos de la ventana pacientes
    public String limpiarCampos() {
        return null;
    }

    //verificar estado de campos
    public boolean camposValidos(Pacientes p) {
        // Verificar que los campos obligatorios no estén vacíos
        return p.getId() != null && !p.getId().isEmpty() &&
                p.getNombre() != null && !p.getNombre().isEmpty() &&
                p.getApellidoP() != null && !p.getApellidoP().isEmpty() &&
                p.getApellidoM() != null && !p.getApellidoM().isEmpty();
    }
}
