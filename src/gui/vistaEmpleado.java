package gui;

import javax.swing.*;

import entidades.Cita;
import entidades.Doctor;
import entidades.Pacientes;
import metodos.CrudCitas;
import metodos.CrudPacientes;
import metodos.CrudDoctores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class vistaEmpleado extends JFrame{
    private JPanel miPanel;
    private JTabbedPane tbdPestañas;
    private JList<String> liPacientes;
    private JList<String> liDoctores;
    private JList<String> liCitas;
    private JButton btnCerrarSesion;
    private DefaultListModel<String> pacientesListModel;
    private DefaultListModel<String> doctoresListModel;
    private DefaultListModel<String> citasListModel;
    public vistaEmpleado() {

        pacientesListModel = new DefaultListModel<>();
        liPacientes.setModel(pacientesListModel);
        doctoresListModel = new DefaultListModel<>();
        liDoctores.setModel(doctoresListModel);
        citasListModel = new DefaultListModel<>();
        liCitas.setModel(citasListModel);

        cargarPacientes();
        cargarDoctores();
        cargarCitas();
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int respuesta = JOptionPane.showConfirmDialog(miPanel, "\n¿Desea cerrar sesion?" ,"Cerrar sesion",JOptionPane.YES_NO_OPTION);
                if(respuesta == 0) {
                    dispose();
                    JOptionPane.showMessageDialog(miPanel, "Sesion cerrada");
                    String[] tipoUsuario = {"admin"};
                    login.main(tipoUsuario);
                }else {
                    System.out.println("cancelando");
                }
            }
        });
    }

    // ciclar pacientes en lista
    private void cargarPacientes() {
        CrudPacientes crud = new CrudPacientes();
        ArrayList<Pacientes> pacientes = crud.leerArchivo();

        pacientesListModel.clear();

        for (Pacientes p : pacientes) {
            pacientesListModel.addElement(p.getId() + ": " + p.getNombre() + " " + p.getApellidoP() + " " + p.getApellidoM());
        }
    }
    private void cargarDoctores() {
        CrudDoctores crud = new CrudDoctores();
        ArrayList<Doctor> doctores = crud.leerArchivo();

        doctoresListModel.clear();

        for (Doctor d : doctores) {
            doctoresListModel.addElement(d.getId() + ": " + d.getNombre() + " " + d.getApellidoP() + " " + d.getApellidoM());
        }
    }
    private void cargarCitas() {
        CrudCitas crud = new CrudCitas();
        ArrayList<Cita> citas = crud.leerArchivo();

        citasListModel.clear();

        for (Cita c : citas) {
            citasListModel.addElement(c.getIdCita() + ": paciente - " + c.getNombrePaciente() + " | doctor - "
                    + c.getNombreDoctor() + " | " + c.getFechaAgenda());
        }
    }
    public static void main(String[] args) {
        vistaEmpleado e = new vistaEmpleado();
        e.setContentPane(e.miPanel);
        e.setLocationRelativeTo(null);
        e.setSize(850,600);
        e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        e.setVisible(true);
    }
}
