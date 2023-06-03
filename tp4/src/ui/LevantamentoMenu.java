package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import logic.Cliente;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class LevantamentoMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField montanteField;
	private JDialog aux = this;

	/**
	 * Create the dialog.
	 */
	public LevantamentoMenu(Cliente cliente) {
		setBackground(new Color(255, 255, 255));
		setResizable(false);
		setUndecorated(true);
		setModal(true);
		setBounds(100, 100, 305, 71);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 128, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 2, 5, 5));
		{
			JLabel lblNewLabel = new JLabel("Montante do levantamento");
			lblNewLabel.setFont(new Font("Sitka Text", Font.BOLD, 11));
			contentPanel.add(lblNewLabel);
		}
		{
			montanteField = new JTextField();
			contentPanel.add(montanteField);
			montanteField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 255, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Levantar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String regex = "\\d+(\\.\\d{1,2})?";
						if(!montanteField.getText().matches(regex)) {
							JOptionPane.showMessageDialog(aux, "Montante inv\u00E1lido", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
						else {
							cliente.banco.levantar(Double.parseDouble(montanteField.getText()), cliente.conta);
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
