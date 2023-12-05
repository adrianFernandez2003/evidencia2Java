package gui;

import entidades.Cita;
import entidades.Doctor;
import entidades.Pacientes;
import metodos.CrudCitas;
import metodos.CrudDoctores;
import metodos.CrudPacientes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private JComboBox cmbAnio;
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
    private JComboBox cmbDiaDoctor;
    private JComboBox cmbMesDoctor;
    private JComboBox cmbAnioDoctor;
    private JLabel lblFechaNacDoctor;
    private JButton btnAgendarCita;
    private JButton btnCancelarCita;
    private JTextArea txtaNotas;
    private JLabel lblNotas;
    private JTextField txtIdCita;
    private JLabel lblIdCita;
    private JComboBox cmbHoraCita;
    private JComboBox cmbDiaCita;
    private JComboBox cmbMesCita;
    private JComboBox cmbAnioCita;
    private JButton btnBuscarCita;
    private JButton btnModificarCita;
    private JButton btnCerrarSesion;
    private JButton btnCerrarSesion2;
    private JButton btnCerrarSesion3;
    private JList<String>  liPacientes;
    private JList<String>  liDoctores;
    private JList<String>  liCitas;
    private DefaultListModel<String> pacientesListModel;
    private DefaultListModel<String> doctoresListModel;
    private DefaultListModel<String> citasListModel;


    //general validar fecha
    public boolean validarFecha(String fecha){
        try{
            SimpleDateFormat formatoFecha =
                    new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            Date fechaNacimiento = formatoFecha.parse(fecha);
            System.out.println(fechaNacimiento);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public VistaGeneral() {

        pacientesListModel = new DefaultListModel<>();
        liPacientes.setModel(pacientesListModel);
        doctoresListModel = new DefaultListModel<>();
        liDoctores.setModel(doctoresListModel);
        citasListModel = new DefaultListModel<>();
        liCitas.setModel(citasListModel);

        cargarPacientes();
        cargarDoctores();
        cargarCitas();

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
                String fechaNacimiento = cmbDia.getSelectedItem().toString() + "/" + cmbMes.getSelectedItem().toString() + "/" + cmbAnio.getSelectedItem().toString();
                boolean resultado = validarFecha(fechaNacimiento);
                if(resultado){
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    formatoFecha.setLenient(false);
                    Date fechaNacPacientes = null;
                    try {
                        fechaNacPacientes = formatoFecha.parse(fechaNacimiento);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    p.setFechaNac(fechaNacPacientes);
                }else{
                    cmbDia.requestFocus();
                    cmbMes.requestFocus();
                    cmbAnio.requestFocus();
                }

                try {
                    CrudPacientes crud = new CrudPacientes();

                    //verificar
                    if(crud.camposValidos(p)) {
                        if (crud.existePacienteConId(p.getId())) {
                            JOptionPane.showMessageDialog(VistaGeneral.this, "Un paciente con ese id ya ha sido creado");
                            return;
                        }
                        crud.agregarPaciente(p);
                        cargarPacientes();
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
                    int respuesta = JOptionPane.showConfirmDialog(miPanel, "No fue posible encontrar al paciente con id " + id + "\n¿Desea dar de alta?" ,"Paciente",JOptionPane.YES_NO_OPTION);
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
                        String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                        cmbDia.setModel(new DefaultComboBoxModel<>(dia));
                        String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                        cmbMes.setModel(new DefaultComboBoxModel<>(mes));
                        String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                        cmbAnio.setModel(new DefaultComboBoxModel<>(anio));
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
                    String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                    cmbDia.setModel(new DefaultComboBoxModel<>(dia));
                    String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                    cmbMes.setModel(new DefaultComboBoxModel<>(mes));
                    String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                    cmbAnio.setModel(new DefaultComboBoxModel<>(anio));

                    if (p.getFechaNac() != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(p.getFechaNac());

                        cmbDia.setSelectedItem(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
                        cmbMes.setSelectedItem(String.format("%02d", cal.get(Calendar.MONTH) + 1));
                        cmbAnio.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
                    }
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
                String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                cmbDia.setModel(new DefaultComboBoxModel<>(dia));
                String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                cmbMes.setModel(new DefaultComboBoxModel<>(mes));
                String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                cmbAnio.setModel(new DefaultComboBoxModel<>(anio));
                cargarPacientes();
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
                String fecha = cmbDia.getSelectedItem().toString() + "/" + cmbMes.getSelectedItem().toString() + "/" + cmbAnio.getSelectedItem().toString();
                boolean resultado = validarFecha(fecha);

                if (resultado) {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        p.setFechaNac(formatoFecha.parse(fecha));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    cmbDia.requestFocus();
                    cmbMes.requestFocus();
                    cmbAnio.requestFocus();
                }
                try {
                    CrudPacientes crud = new CrudPacientes();

                    if (crud.camposValidos(p)) {
                        crud.modificarPaciente(p);
                        cargarPacientes();
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

                String fechaNacimiento = cmbDiaDoctor.getSelectedItem().toString() + "/" + cmbMesDoctor.getSelectedItem().toString() + "/" + cmbAnioDoctor.getSelectedItem().toString();
                boolean resultado = validarFecha(fechaNacimiento);
                if(resultado){
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    formatoFecha.setLenient(false);
                    Date fechaNacDoctores = null;
                    try {
                        fechaNacDoctores = formatoFecha.parse(fechaNacimiento);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    d.setFechaNac(fechaNacDoctores);
                }else{
                    cmbDia.requestFocus();
                    cmbMes.requestFocus();
                    cmbAnio.requestFocus();
                }

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
                        cargarDoctores();
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
                        String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                        cmbDiaDoctor.setModel(new DefaultComboBoxModel<>(dia));
                        String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                        cmbMesDoctor.setModel(new DefaultComboBoxModel<>(mes));
                        String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                        cmbAnioDoctor.setModel(new DefaultComboBoxModel<>(anio));
                    }
                }else{
                    txtNombreDoctores.setText(d.getNombre());
                    txtApellidoPDoctores.setText(d.getApellidoP());
                    txtApellidoMDoctores.setText(d.getApellidoM());
                    txtCorreoDoctores.setText(d.getCorreo());
                    txtTelefonoDoctores.setText(d.getNumeroTel());

                    String[] generos = {"Seleccione el sexo", "Masculino", "Femenino"};
                    cmbSexoDoctores.setModel(new DefaultComboBoxModel<>(generos));
                    cmbSexoDoctores.setSelectedItem(d.getGenero());
                    String[] especialidades = {"Seleccione la especialidad", "Pediatria", "Cirugía",
                            "Psiquiatría", "Cardiología", "Dermatología", "Oftalmología", "Otorrinolaringología",
                            "Neumología", "Neurología", "Radiología", "Anestesiología", "rehabilitación"};
                    cmbEspecialidad.setModel(new DefaultComboBoxModel<>(especialidades));
                    cmbEspecialidad.setSelectedItem(d.getEspecialidad());
                    String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                    cmbDiaDoctor.setModel(new DefaultComboBoxModel<>(dia));
                    String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                    cmbMesDoctor.setModel(new DefaultComboBoxModel<>(mes));
                    String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                    cmbAnioDoctor.setModel(new DefaultComboBoxModel<>(anio));

                    if (d.getFechaNac() != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d.getFechaNac());

                        cmbDiaDoctor.setSelectedItem(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
                        cmbMesDoctor.setSelectedItem(String.format("%02d", cal.get(Calendar.MONTH) + 1));
                        cmbAnioDoctor.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
                    }


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
                String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                cmbDiaDoctor.setModel(new DefaultComboBoxModel<>(dia));
                String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                cmbMesDoctor.setModel(new DefaultComboBoxModel<>(mes));
                String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                cmbAnioDoctor.setModel(new DefaultComboBoxModel<>(anio));
                cargarDoctores();
                actualizarListaDoctores();
                JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente borrado con exito");

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
                String fecha = cmbDiaDoctor.getSelectedItem().toString() + "/" + cmbMesDoctor.getSelectedItem().toString() + "/" + cmbAnioDoctor.getSelectedItem().toString();
                boolean resultado = validarFecha(fecha);

                if (resultado) {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        d.setFechaNac(formatoFecha.parse(fecha));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    cmbDia.requestFocus();
                    cmbMes.requestFocus();
                    cmbAnio.requestFocus();
                }
                try {
                    CrudDoctores crud = new CrudDoctores();

                    if (crud.camposValidos(d)) {
                        crud.modificarDoctor(d);
                        cargarDoctores();
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
        //Citas

        //Crear cita
        btnAgendarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cita c = new Cita();
                CrudCitas crud = new CrudCitas();
                c.setIdCita((txtIdCita.getText()));
                c.setNotas(txtaNotas.getText());
                Object selectedItem = cmbListaPacientes.getSelectedItem();
                c.setNombrePaciente(cmbListaPacientes.getSelectedItem().toString());
                Object selectedItem2 = cmbListaDoctores.getSelectedItem();
                c.setNombreDoctor(cmbListaDoctores.getSelectedItem().toString());

                String fechaCita = cmbDiaCita.getSelectedItem().toString() + "/" + cmbMesCita.getSelectedItem().toString() +
                        "/" + cmbAnioCita.getSelectedItem().toString() + " at " + cmbHoraCita.getSelectedItem().toString();
                boolean resultado = crud.validarFechaCita(fechaCita);
                if(resultado){
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy 'at' HH");
                    formatoFecha.setLenient(false);
                    Date fechaCitas = null;
                    try {
                        fechaCitas = formatoFecha.parse(fechaCita);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    c.setFechaAgenda(fechaCitas);
                }else{
                    cmbDiaCita.requestFocus();
                    cmbMesCita.requestFocus();
                    cmbAnioCita.requestFocus();
                    cmbHoraCita.requestFocus();
                }
                try {


                    //verificar
                    if(crud.camposValidos(c)) {
                        if (crud.existeCitaConId(c.getIdCita())) {
                            JOptionPane.showMessageDialog(VistaGeneral.this, "Una cita con ese id ya ha sido creada");
                            return;
                        }
                        crud.agendarCita(c);
                        cargarCitas();
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Cita creada exitosamente");

                    }else {
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Campos obligatorios no pueden estar vacíos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VistaGeneral.this, "Error al crear la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancelarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrudCitas crud = new CrudCitas();
                String id = txtIdCita.getText();
                crud.borrarCitaPorId(id);
                txtIdCita.setText(crud.limpiarCampos());
                txtaNotas.setText(crud.limpiarCampos());
                String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                cmbDiaCita.setModel(new DefaultComboBoxModel<>(dia));
                String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                cmbMesCita.setModel(new DefaultComboBoxModel<>(mes));
                String[] anio = {"Año", "2023", "2024", "2025", "2026", "2027", "2028"};
                cmbAnioCita.setModel(new DefaultComboBoxModel<>(anio));
                String[] hora = {"Hora", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
                cmbHoraCita.setModel(new DefaultComboBoxModel<>(hora));
                cargarCitas();
                JOptionPane.showMessageDialog(VistaGeneral.this, "Cita cancelada con exito");
            }
        });
        btnBuscarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrudCitas crud = new CrudCitas();
                String id = txtIdCita.getText();
                Cita c = crud.buscarCitaPorId(id);
                if (c == null) {
                    int respuesta = JOptionPane.showConfirmDialog(miPanel, "No fue posible encontrar al paciente con id " + id + "\n¿Desea crear cita?" ,"Cita",JOptionPane.YES_NO_OPTION);
                    if (respuesta == 0){

                    }else if(respuesta == 1){
                        txtaNotas.setText(crud.limpiarCampos());
                        String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                        cmbDiaCita.setModel(new DefaultComboBoxModel<>(dia));
                        String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                        cmbMesCita.setModel(new DefaultComboBoxModel<>(mes));
                        String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                        cmbAnioCita.setModel(new DefaultComboBoxModel<>(anio));
                        String[] hora = {"Hora", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
                        cmbHoraCita.setModel(new DefaultComboBoxModel<>(hora));
                    }
                }else{
                    txtIdCita.setText(c.getIdCita());
                    txtaNotas.setText(c.getNotas());

                    String[] dia = {"Dia", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                    cmbDiaCita.setModel(new DefaultComboBoxModel<>(dia));
                    String[] mes = {"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                    cmbMesCita.setModel(new DefaultComboBoxModel<>(mes));
                    String[] anio = {"Año", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
                    cmbAnioCita.setModel(new DefaultComboBoxModel<>(anio));
                    String[] hora = {"Hora", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
                    cmbHoraCita.setModel(new DefaultComboBoxModel<>(hora));
                    cmbListaPacientes.setSelectedItem(c.getNombrePaciente());
                    cmbListaDoctores.setSelectedItem(c.getNombreDoctor());
                    if (c.getFechaAgenda() != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(c.getFechaAgenda());

                        cmbDiaCita.setSelectedItem(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
                        cmbMesCita.setSelectedItem(String.format("%02d", cal.get(Calendar.MONTH) + 1));
                        cmbAnioCita.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
                        cmbHoraCita.setSelectedItem(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
                    }
                }
            }
        });
        btnModificarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cita c = new Cita();
                CrudCitas crud = new CrudCitas();
                c.setIdCita(txtIdCita.getText());
                c.setNotas(txtaNotas.getText());

                Object selectedItem = cmbListaDoctores.getSelectedItem();
                c.setNombreDoctor(cmbListaDoctores.getSelectedItem().toString());
                Object selectedItem2 = cmbEspecialidad.getSelectedItem();
                c.setNombrePaciente(cmbListaPacientes.getSelectedItem().toString());
                String fecha = cmbDiaCita.getSelectedItem().toString() + "/" + cmbMesCita.getSelectedItem().toString() +
                        "/" + cmbAnioCita.getSelectedItem().toString() + " at " + cmbHoraCita.getSelectedItem().toString();
                boolean resultado = crud.validarFechaCita(fecha);

                if (resultado) {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy 'at' HH");
                    try {
                        c.setFechaAgenda(formatoFecha.parse(fecha));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    cmbDia.requestFocus();
                    cmbMes.requestFocus();
                    cmbAnio.requestFocus();
                }
                try {

                    if (crud.camposValidos(c)) {
                        crud.modificarCita(c);
                        cargarCitas();
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Cita modificada exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(VistaGeneral.this, "Los campos no pueden estar vacios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VistaGeneral.this, "Error al modificar el Cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        btnCerrarSesion2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        btnCerrarSesion3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
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

    private void cerrarSesion() {
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
        VistaGeneral v = new VistaGeneral();
        v.setContentPane(v.general);
        v.setLocationRelativeTo(null);
        v.setSize(800,500);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setVisible(true);
    }
}
