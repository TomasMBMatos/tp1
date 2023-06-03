package ui;

import logic.Banco;
import logic.Cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class DepositoMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField depositoField;
	private JDialog aux = this;

	/**
	 * Create the dialog.
	 */
	public DepositoMenu(Cliente cliente) {
		setUndecorated(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 300, 70);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 128, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblNewLabel = new JLabel("Montante do dep\u00F3sito");
			lblNewLabel.setFont(new Font("Sitka Text", Font.BOLD, 11));
			contentPanel.add(lblNewLabel);
		}
		{
			depositoField = new JTextField();
			contentPanel.add(depositoField);
			depositoField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 255, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Depositar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String regex = "\\d+(\\.\\d{1,2})?";
						if(!depositoField.getText().matches(regex)) {
							JOptionPane.showMessageDialog(aux, "Montante inv\u00E1lido", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
						else {
							cliente.banco.depositar(Double.parseDouble(depositoField.getText()), cliente.conta);
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
