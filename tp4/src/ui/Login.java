package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logic.Cliente;
import java.awt.Toolkit;

public class Login extends Cliente implements ActionListener {

	private JFrame frmCaixaGeralDe;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JButton loginBtn;
	private JButton registarBtn;
	private JLabel warning;
	
	public boolean success = false;

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCaixaGeralDe = new JFrame();
		frmCaixaGeralDe.setTitle("Caixa Geral de Levantamentos");
		frmCaixaGeralDe.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\tomas\\Documents\\College\\LEIM\\MoP\\tp4\\images\\CGD-logo-213C7E1FF6-seeklogo.com.png"));
		frmCaixaGeralDe.setResizable(false);
		frmCaixaGeralDe.getContentPane().setForeground(new Color(255, 255, 255));
		frmCaixaGeralDe.setBounds(100, 100, 520, 440);
		frmCaixaGeralDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setForeground(new Color(255, 255, 255));
		frmCaixaGeralDe.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(198, 213, 89, 23);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
		
		registarBtn = new JButton("Registar");
		registarBtn.setBounds(198, 285, 89, 23);
		registarBtn.addActionListener(this);
		panel.add(registarBtn);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(198, 140, 89, 20);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(198, 171, 89, 20);
		panel.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(108, 143, 80, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(108, 174, 80, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("N\u00E3o tem conta?");
		lblNewLabel_2.setBounds(198, 260, 117, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("LOGIN");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 25));
		lblNewLabel_3.setBounds(198, 61, 102, 50);
		panel.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 192));
		panel_1.setBounds(0, 0, 504, 50);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Bem Vindo ao seu banco");
		lblNewLabel_4.setBounds(157, 11, 196, 24);
		panel_1.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\tomas\\Documents\\College\\LEIM\\MoP\\tp4\\images\\clouds.png"));
		lblNewLabel_5.setBounds(0, 0, 504, 50);
		panel_1.add(lblNewLabel_5);
		
		warning = new JLabel("Utilizador ou password incorretos!");
		warning.setForeground(new Color(255, 255, 255));
		warning.setBackground(new Color(255, 255, 255));
		warning.setFont(new Font("Tahoma", Font.PLAIN, 16));
		warning.setBounds(121, 102, 249, 35);
		panel.add(warning);
		frmCaixaGeralDe.setLocationRelativeTo(null);
		frmCaixaGeralDe.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		username = txtUsername.getText();
		password = String.valueOf(passwordField.getPassword());
		if(e.getSource() == loginBtn) {
			if(banco.login(this)) {
				frmCaixaGeralDe.dispose();
				success = true;
			}
			else {
				warning.setForeground(Color.red);
			}
		}
		else if(e.getSource() == registarBtn) {
			if(txtUsername.getText().isBlank() || passwordField.getPassword().toString().isBlank()) {
				warning.setText("Preencha o utilizador e password");
				warning.setForeground(Color.red);
			}
			else {
				banco.registar(this);
				JOptionPane.showMessageDialog(null, "Conta criada com sucesso!");
				frmCaixaGeralDe.dispose();
				success = true;
			}
		}
		
	}
}
