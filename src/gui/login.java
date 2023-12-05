package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame{
    private JPanel miPanel;
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JButton btnLogin;
    private JButton btnCancelar;

    public login() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hardcodear
                String contraseña = String.valueOf(txtContraseña.getPassword());
                if(txtUsuario.getText().equals("admin") && contraseña.equals("0396")){
                    dispose();
                    JOptionPane.showMessageDialog(miPanel, "Bienvenido a la plataforma admin");
                    String[] tipoUsuario = {"admin"};
                    VistaGeneral.main(tipoUsuario);
                }else if(txtUsuario.getText().equals("empleado") && contraseña.equals("hola")) {
                    dispose();
                    JOptionPane.showMessageDialog(miPanel, "Bienvenido a la plataforma empleado");
                    String[] tipoUsuario = {"empleado"};
                    vistaEmpleado.main(tipoUsuario);
                }else {
                    JOptionPane.showMessageDialog(miPanel, "Usuario o contraseña incorrectos", "login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        login vLogin = new login();
        vLogin.setContentPane(vLogin.miPanel);
        vLogin.setLocationRelativeTo(null);
        vLogin.setSize(300, 300);
        vLogin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vLogin.setVisible(true);
    }
}
