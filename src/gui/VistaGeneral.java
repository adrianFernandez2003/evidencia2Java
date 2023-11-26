package gui;

import entidades.Pacientes;
import metodos.CrudPacientes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton buscarButton;
    private JButton modificarButton;
    private JButton crearButton;
    private JButton eliminarButton;
    private JPanel panelDoctores;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox cmbSexo;
    private JLabel lblSexo;

    public VistaGeneral() {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pacientes p = new Pacientes();
                p.setId((txtId.getText()));
                p.setNombre(txtNombre.getText());
                p.setApellidoP(txtApellidoP.getText());
                p.setApellidoM(txtApellidoM.getText());

                Object selectedItem = cmbSexo.getSelectedItem();
                p.setGenero(cmbSexo.getSelectedItem().toString());



                //METODO CREAR PACIENTE
                try {
                    CrudPacientes crud = new CrudPacientes();

                    //verificar
                    if(crud.camposValidos(p)) {
                        crud.agregarPaciente(p);
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
                    }
                }else{
                    txtNombre.setText(p.getNombre());
                    txtApellidoP.setText(p.getApellidoP());
                    txtApellidoM.setText(p.getApellidoM());
                }
            }
        });
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrudPacientes crud = new CrudPacientes();
                String id = txtId.getText();
                crud.borrarPacientePorId(id);
                JOptionPane.showMessageDialog(VistaGeneral.this, "Paciente borrado con exito");
            }
        });
    }

    public static void main(String[] args) {
        VistaGeneral v = new VistaGeneral();
        v.setContentPane(v.general);
        v.setSize(800,800);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setVisible(true);
    }
}
