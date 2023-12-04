package metodos;

import entidades.Doctor;
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

        if (existePacienteConId(p.getId())) {
            System.out.println("ya existe un paciente con ese id");
            return;
        }
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
    public void modificarPaciente(Pacientes pacienteModificado) {
        ArrayList<Pacientes> listaLectura = leerArchivo();

        for (int i = 0; i < listaLectura.size(); i++) {
            Pacientes pacienteActual = listaLectura.get(i);
            if (pacienteActual.getId().equals(pacienteModificado.getId())) {
                listaLectura.set(i, pacienteModificado);
                escribirArchivo(listaLectura);
                return;
            }
        }
        System.out.println("No se encontró ningún paciente con el ID: " + pacienteModificado.getId());
    }
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
        return p.getId() != null && !p.getId().isEmpty() &&
                p.getNombre() != null && !p.getNombre().isEmpty() &&
                p.getApellidoP() != null && !p.getApellidoP().isEmpty() &&
                p.getApellidoM() != null && !p.getApellidoM().isEmpty() &&
                p.getCorreo() != null && !p.getCorreo().isEmpty() &&
                p.getNumeroTel() != null && !p.getNumeroTel().isEmpty() &&
                p.getGenero() != null && !p.getGenero().isEmpty() && !p.getGenero().equals("Seleccione el sexo");
    }
    //verificar si existen doctores
    public boolean existePacienteConId(String id) {
        ArrayList<Pacientes> listaDoctores = leerArchivo();

        for (Pacientes p : listaDoctores) {
            if (p.getId().equals(id)) {
                return true; // El doctor con el ID dado ya existe
            }
        }

        return false; // No hay doctor con el ID dado
    }
}
