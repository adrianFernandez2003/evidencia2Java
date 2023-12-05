package metodos;

import entidades.Doctor;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;

public class CrudDoctores {
    public static ArrayList<Doctor> leerArchivo() throws RuntimeException {
        ArrayList<Doctor> listaLectura = new ArrayList<>();
        try {
            FileInputStream leer =
                    new FileInputStream("C:\\temp\\listaDoctor.txt");
            ObjectInputStream streamLectura =
                    new ObjectInputStream(leer);
            if (leer.available() > 0) {
                Object o = streamLectura.readObject();
                listaLectura = (ArrayList<Doctor>)o;
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
    public static void escribirArchivo(ArrayList<Doctor> d) {
        try {
            FileOutputStream escribir =
                    new FileOutputStream("C:\\temp\\listaDoctor.txt");
            ObjectOutputStream streamEscritura =
                    new ObjectOutputStream(escribir);
            streamEscritura.writeObject(d);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //crear doctor
    public void agregardoctor(Doctor d){
        ArrayList<Doctor> listaLectura = leerArchivo();

        if (existeDoctorConId(d.getId())) {
            System.out.println("ya existe un doctor con ese id");
            return;
        }
        listaLectura.add(d);
        escribirArchivo(listaLectura);
    }
    //buscar doctor
    public Doctor buscarDoctorPorId(String id) {
        for (Doctor d : leerArchivo()) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }
    //modificar doctor
    public void modificarDoctor(Doctor DoctorModificado) {
        ArrayList<Doctor> listaLectura = leerArchivo();

        for (int i = 0; i < listaLectura.size(); i++) {
            Doctor DoctorActual = listaLectura.get(i);
            if (DoctorActual.getId().equals(DoctorModificado.getId())) {
                listaLectura.set(i, DoctorModificado);
                escribirArchivo(listaLectura);
                return;
            }
        }
        System.out.println("No se encontró ningún doctor con el ID: " + DoctorModificado.getId());
    }
    //eliminar doctor
    public void borrarDoctorPorId(String id) {
        ArrayList<Doctor> listaLectura = leerArchivo();

        Doctor doctorAEliminar = null;
        for (Doctor d : listaLectura) {
            if (d.getId().equals(id)) {
                doctorAEliminar = d;
                break;
            }
        }
        // eliminar doctor en caso de encontrarlo
        if (doctorAEliminar != null) {
            listaLectura.remove(doctorAEliminar);
            escribirArchivo(listaLectura);
            System.out.println("doctor eliminado exitosamente.");
        } else {
            System.out.println("No se encontró ningún doctor con el ID: " + id);
        }
    }
        //limpiar campos
        public String limpiarCampos() {
            return null;
        }
    public boolean camposValidos(Doctor d) {
        return d.getId() != null && !d.getId().isEmpty() &&
                d.getNombre() != null && !d.getNombre().isEmpty() &&
                d.getApellidoP() != null && !d.getApellidoP().isEmpty() &&
                d.getApellidoM() != null && !d.getApellidoM().isEmpty() &&
                d.getCorreo() != null && !d.getCorreo().isEmpty() &&
                d.getNumeroTel() != null && !d.getNumeroTel().isEmpty() &&
                d.getGenero() != null && !d.getGenero().isEmpty() && !d.getGenero().equals("Seleccione el sexo") &&
                d.getEspecialidad() != null && !d.getEspecialidad().isEmpty() && !d.getEspecialidad().equals("Seleccione la especialidad") &&
                d.getFechaNac() != null;
    }
    //verificar si existen doctores
    public boolean existeDoctorConId(String id) {
        ArrayList<Doctor> listaDoctores = leerArchivo();

        for (Doctor doctor : listaDoctores) {
            if (doctor.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }
}

