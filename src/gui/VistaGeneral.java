package gui;

import entidades.Doctor;
import entidades.Pacientes;
import metodos.CrudDoctores;
import metodos.CrudPacientes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaGeneral extends JFrame{
    private JPanel miPanel;
    private JLabel lblId;
    private JLabel lblApellidoP;
    private JTextField txtApellidoP;
    private JLabel lblApellidoM;
    private JTextField txtApellidoM;
    private JTextField txtId;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblFechaNac;
    private JComboBox cmbDia;
    private JComboBox cmbAño;
    private JComboBox cmbMes;
    private JButton btnBuscar;
    private JButton btnBorrar;
    private JButton btnModificar;
    private JButton btnCrear;
    private JPanel jPanel;
    private JTabbedPane general;
    private JButton btnbuscarDoctores;
    private JButton btnModificarDoctores;
    private JButton btnCrearDoctores;
    private JButton btnEliminarDoctores;
    private JPanel panelDoctores;
    private JTextField txtIdDoctores;
    private JTextField txtNombreDoctores;
    private JTextField txtApellidoPDoctores;
    private JTextField txtApellidoMDoctores;
    private JComboBox cmbSexoDoctores;
    private JTextField txtTelefonoDoctores;
    private JTextField txtCorreoDoctores;
    private JComboBox cmbSexo;
    private JLabel lblSexo;
    private JLabel lblIdDoctores;
    private JLabel lblNombreDoctores;
    private JLabel lblApPDoctores;
    private JLabel lblApellidoMDoctores;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JLabel lblTelefono;
    private JLabel lblSexoDoctores;
    private JPanel lblTelefonoDoctores;
    private JLabel lblCorreoDoctores;
    private JComboBox cmbListaPacientes;
    private JComboBox cmbListaDoctores;
    private JLabel lblPacientes;
    private JLabel lblDoctores;
    private JComboBox cmbEspecialidad;
    private JLabel lblEspecialidad;

    public VistaGeneral() {
        //pacientes
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pacientes p = new Pacientes();
                p.setId((txtId.getText()));
                p.setNombre(txtNombre.getText());
                p.setApellidoP(txtApellidoP.getText());
                p.setApellidoM(txtApellidoM.getText());
                p.setCorreo(txtCorreo.getText());
                p.setNumeroTel(txtTelefono.getText());

                Object selectedItem = cmbSexo.getSelectedItem();
                p.setGenero(cmbSexo.getSelectedItem().toString());



                //crear paciente
                try {
                    CrudPacientes crud = new CrudPacientes();

                    //verificar
                    if(crud.camposValidos(p)) {
                        if (crud.existePacienteConId(p.getId())) {
                            JOptionPane.showMessageDialog(VistaGeneral.this, "Un paciente con ese id ya ha sido creado");
                            return;
                        }
                        crud.agregarPaciente(p);
                        actualizarListaPacientes();
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente creado exitosamente");

                    }else {
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Campos obligatorios no pueden estar vacíos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VistaGeneral.this, "Error al crear el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                CrudPacientes crud = new CrudPacientes();
                String id = txtId.getText();
                Pacientes p = crud.buscarPacientePorId(id);
                if (p == null) {
                    int respuesta = JOptionPane.showConfirmDialog(miPanel, "No fue posible encontrar al paciente con id " + id + "\n¿Desea dar de alta?" ,"Alumno",JOptionPane.YES_NO_OPTION);
                    if (respuesta == 0){
                        btnCrear.setEnabled(true);
                        txtNombre.requestFocus();
                    }else if(respuesta == 1){
                        txtNombre.setText(crud.limpiarCampos());
                        txtApellidoP.setText(crud.limpiarCampos());
                        txtApellidoM.setText(crud.limpiarCampos());
                        txtCorreo.setText(crud.limpiarCampos());
                        txtTelefono.setText(crud.limpiarCampos());
                        String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                        cmbSexo.setModel(new DefaultComboBoxModel<>(generos));
                    }
                }else{
                    txtNombre.setText(p.getNombre());
                    txtApellidoP.setText(p.getApellidoP());
                    txtApellidoM.setText(p.getApellidoM());
                    txtCorreo.setText(p.getCorreo());
                    txtTelefono.setText(p.getNumeroTel());

                    String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                    cmbSexo.setModel(new DefaultComboBoxModel<>(generos));
                    cmbSexo.setSelectedItem(p.getGenero());
                }
            }
        });
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrudPacientes crud = new CrudPacientes();
                String id = txtId.getText();
                crud.borrarPacientePorId(id);
                txtNombre.setText(crud.limpiarCampos());
                txtApellidoP.setText(crud.limpiarCampos());
                txtApellidoM.setText(crud.limpiarCampos());
                txtCorreo.setText(crud.limpiarCampos());
                txtTelefono.setText(crud.limpiarCampos());
                String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                cmbSexo.setModel(new DefaultComboBoxModel<>(generos));
                actualizarListaPacientes();
                JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente borrado con exito");
            }
        });
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pacientes p = new Pacientes();
                p.setId(txtId.getText());
                p.setNombre(txtNombre.getText());
                p.setApellidoP(txtApellidoP.getText());
                p.setApellidoM(txtApellidoM.getText());
                p.setCorreo(txtCorreo.getText());
                p.setNumeroTel(txtTelefono.getText());

                Object selectedItem = cmbSexo.getSelectedItem();
                p.setGenero(cmbSexo.getSelectedItem().toString());

                try {
                    CrudPacientes crud = new CrudPacientes();

                    if (crud.camposValidos(p)) {
                        crud.modificarPaciente(p);
                        actualizarListaPacientes();
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente modificado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Los campos no pueden estar vacios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VistaGeneral.this, "Error al modificar el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //doctores
        btnCrearDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor d = new Doctor();
                d.setId((txtIdDoctores.getText()));
                d.setNombre(txtNombreDoctores.getText());
                d.setApellidoP(txtApellidoPDoctores.getText());
                d.setApellidoM(txtApellidoMDoctores.getText());
                d.setCorreo(txtCorreoDoctores.getText());
                d.setNumeroTel(txtTelefonoDoctores.getText());

                Object selectedItem = cmbSexoDoctores.getSelectedItem();
                d.setGenero(cmbSexoDoctores.getSelectedItem().toString());
                Object selectedItem2 = cmbEspecialidad.getSelectedItem();
                d.setEspecialidad(cmbEspecialidad.getSelectedItem().toString());


                //crear doctor
                try {
                    CrudDoctores crud = new CrudDoctores();

                    //verificar
                    if(crud.camposValidos(d)) {
                        if (crud.existeDoctorConId(d.getId())) {
                            JOptionPane.showMessageDialog(VistaGeneral.this, "ya existe un paciente con ese id");
                            return;
                        }
                        crud.agregardoctor(d);
                        actualizarListaDoctores();
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente creado exitosamente");

                    }else {
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Campos obligatorios no pueden estar vacíos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VistaGeneral.this, "Error al crear el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnbuscarDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrudDoctores crud = new CrudDoctores();
                String id = txtIdDoctores.getText();
                Doctor d = crud.buscarDoctorPorId(id);
                if (d == null) {
                    int respuesta = JOptionPane.showConfirmDialog(miPanel, "No fue posible encontrar al doctor con id " + id + "\n¿Desea dar de alta?" ,"Doctor",JOptionPane.YES_NO_OPTION);
                    if (respuesta == 0){
                        btnCrearDoctores.setEnabled(true);
                        txtNombreDoctores.requestFocus();
                    }else if(respuesta == 1){
                        txtNombreDoctores.setText(crud.limpiarCampos());
                        txtApellidoPDoctores.setText(crud.limpiarCampos());
                        txtApellidoMDoctores.setText(crud.limpiarCampos());
                        txtCorreoDoctores.setText(crud.limpiarCampos());
                        txtTelefonoDoctores.setText(crud.limpiarCampos());
                        String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                        String[] especialidades = {"Seleccione la especialidad", "Pediatria", "Cirugía",
                                "Psiquiatría", "Cardiología", "Dermatología", "Oftalmología", "Otorrinolaringología",
                                "Neumología", "Neurología", "Radiología", "Anestesiología", "rehabilitación"};
                        cmbSexoDoctores.setModel(new DefaultComboBoxModel<>(generos));
                        cmbEspecialidad.setModel(new DefaultComboBoxModel<>(especialidades));
                    }
                }else{
                    txtNombreDoctores.setText(d.getNombre());
                    txtApellidoPDoctores.setText(d.getApellidoP());
                    txtApellidoMDoctores.setText(d.getApellidoM());
                    txtCorreoDoctores.setText(d.getCorreo());
                    txtTelefonoDoctores.setText(d.getNumeroTel());

                    String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                    String[] especialidades = {"Seleccione la especialidad", "Pediatria", "Cirugía",
                            "Psiquiatría", "Cardiología", "Dermatología", "Oftalmología", "Otorrinolaringología",
                            "Neumología", "Neurología", "Radiología", "Anestesiología", "rehabilitación"};
                    cmbSexoDoctores.setModel(new DefaultComboBoxModel<>(generos));
                    cmbSexoDoctores.setSelectedItem(d.getGenero());
                    cmbEspecialidad.setModel(new DefaultComboBoxModel<>(especialidades));
                    cmbEspecialidad.setSelectedItem(d.getEspecialidad());


                }
            }
        });
        //borrar doctores
        btnEliminarDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrudDoctores crud = new CrudDoctores();
                String id = txtIdDoctores.getText();
                crud.borrarDoctorPorId(id);
                txtNombreDoctores.setText(crud.limpiarCampos());
                txtApellidoPDoctores.setText(crud.limpiarCampos());
                txtApellidoMDoctores.setText(crud.limpiarCampos());
                txtCorreoDoctores.setText(crud.limpiarCampos());
                txtTelefonoDoctores.setText(crud.limpiarCampos());
                String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                String[] especialidades = {"Seleccione la especialidad", "Pediatria", "Cirugía",
                        "Psiquiatría", "Cardiología", "Dermatología", "Oftalmología", "Otorrinolaringología",
                        "Neumología", "Neurología", "Radiología", "Anestesiología", "rehabilitación"};
                cmbSexoDoctores.setModel(new DefaultComboBoxModel<>(generos));
                cmbEspecialidad.setModel(new DefaultComboBoxModel<>(especialidades));
                actualizarListaDoctores();
                JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente borrado con exito");

                cmbSexo.setModel(new DefaultComboBoxModel<>(generos));

            }
        });
        //citas
        actualizarListaPacientes();
        actualizarListaDoctores();
        btnModificarDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor d = new Doctor();
                d.setId(txtIdDoctores.getText());
                d.setNombre(txtNombreDoctores.getText());
                d.setApellidoP(txtApellidoPDoctores.getText());
                d.setApellidoM(txtApellidoMDoctores.getText());
                d.setCorreo(txtCorreoDoctores.getText());
                d.setNumeroTel(txtTelefonoDoctores.getText());

                Object selectedItem = cmbSexoDoctores.getSelectedItem();
                d.setGenero(cmbSexoDoctores.getSelectedItem().toString());
                Object selectedItem2 = cmbEspecialidad.getSelectedItem();
                d.setEspecialidad(cmbEspecialidad.getSelectedItem().toString());

                try {
                    CrudDoctores crud = new CrudDoctores();

                    if (crud.camposValidos(d)) {
                        crud.modificarDoctor(d);
                        actualizarListaDoctores();
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Doctor modificado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Los campos no pueden estar vacios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VistaGeneral.this, "Error al modificar el Doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    // metodos cita
    private void actualizarListaPacientes() {
        CrudPacientes crud = new CrudPacientes();
        ArrayList<Pacientes> listaPacientes = crud.leerArchivo();

        // Limpiar cmbListaPacientes
        cmbListaPacientes.removeAllItems();

        // actualizar cmbListaPacientes
        for (Pacientes paciente : listaPacientes) {
            cmbListaPacientes.addItem(paciente.getNombre() + " " + paciente.getApellidoP() + " " + paciente.getApellidoM()
                    + ": " + paciente.getId());
        }
    }

    private void actualizarListaDoctores() {
        CrudDoctores crud = new CrudDoctores();
        ArrayList<Doctor> listaDoctor = crud.leerArchivo();

        // Limpiar cmbListaDoctores
        cmbListaDoctores.removeAllItems();

        // actualizar cmbListaDoctores
        for (Doctor d : listaDoctor) {
            cmbListaDoctores.addItem(d.getNombre() + " " + d.getApellidoP() + " " + d.getApellidoM()
                    + ": " + d.getId());
        }
    }

    public static void main(String[] args) {
        VistaGeneral v = new VistaGeneral();
        v.setContentPane(v.general);
        v.setLocationRelativeTo(null);
        v.setSize(800,300);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setVisible(true);
    }
}
