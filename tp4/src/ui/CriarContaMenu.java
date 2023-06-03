package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logic.Cliente;
import logic.Conta;
import logic.ContaMultibanco;
import logic.ContaOrdem;
import logic.ContaPoupancaHabitacao;
import logic.ContaPrazo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CriarContaMenu extends JDialog {
	private Conta conta;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField fNameField;
	private JTextField uNameField;
	private JTextField ccField;
	private JTextField depDatField;
	private JLabel tipoLabel;
	private JLabel depDatLabel;
	private JComboBox tipoComboBox;
	private JLabel ccLabel;
	private JLabel uNomeLabel;
	
	private JDialog aux = this;

	/**
	 * Create the dialog.
	 */
	public CriarContaMenu(Cliente cliente) {
		setResizable(false);
		setUndecorated(true);
		setModal(true);
		setTitle("Criar Conta");
		setBounds(100, 100, 450, 283);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(5, 2, 5, 5));
		{
			JLabel fNomeLabel = new JLabel("Primeiro Nome:");
			contentPanel.add(fNomeLabel);
		}
		{
			fNameField = new JTextField();
			contentPanel.add(fNameField);
			fNameField.setColumns(10);
		}
		{
			uNomeLabel = new JLabel("\u00DAltimo Nome:");
			contentPanel.add(uNomeLabel);
		}
		{
			uNameField = new JTextField();
			contentPanel.add(uNameField);
			uNameField.setColumns(10);
		}
		{
			ccLabel = new JLabel("NIF:");
			contentPanel.add(ccLabel);
		}
		{
			ccField = new JTextField();
			contentPanel.add(ccField);
			ccField.setColumns(10);
		}
		{
			tipoLabel = new JLabel("Tipo:");
			contentPanel.add(tipoLabel);
		}
		{
			tipoComboBox = new JComboBox();
			tipoComboBox.setModel(new DefaultComboBoxModel(new String[] {"Conta Multibanco", "Conta \u00E0 Ordem", "Conta a Prazo", "Conta Poupan\u00E7a-Habita\u00E7\u00E3o"}));
			contentPanel.add(tipoComboBox);
		}
		{
			depDatLabel  = new JLabel("Dep\u00F3sito/Data:");
//			switch(tipoLabel.getText()) {
//				case "Conta Multibanco", "Conta à Ordem" -> depDatLabel = new JLabel("Deposito:");
//				case "Conta a Prazo", "Conta Poupança-Habitação" -> depDatLabel = new JLabel("Data:");
//			}
			contentPanel.add(depDatLabel);
		}
		{
			depDatField = new JTextField();
			contentPanel.add(depDatField);
			depDatField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String regex = "\\d{9}";
						if(fNameField.getText().isBlank()) {
							JOptionPane.showMessageDialog(aux, "Primeiro nome n\u00E3o pode estar vazio.", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
						else if(uNameField.getText().isBlank()) {
							JOptionPane.showMessageDialog(aux, "Ultimo nome n\u00E3o pode estar vazio.", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
						else if(!ccField.getText().matches(regex)) {
							JOptionPane.showMessageDialog(aux, "NIF inv\u00E1lido.", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
						else if(depDatField.getText().isBlank()) {
							JOptionPane.showMessageDialog(aux, "Dep\u00F3sito ou Data n\u00E3o pode estar vazio.", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
						else {
							switch (String.valueOf(tipoComboBox.getSelectedItem())) {
								case "Conta Poupan\u00E7a-Habita\u00E7\u00E3o" -> {
									conta = new ContaPoupancaHabitacao(depDatField.getText(), fNameField.getText(), uNameField.getText(), ccField.getText());
									cliente.banco.addConta(cliente, conta);
								}
								case "Conta \u00E0 Ordem" -> {
									conta = new ContaOrdem(depDatField.getText(), fNameField.getText(), uNameField.getText(), ccField.getText());
									cliente.banco.addConta(cliente, conta);
								}
								case "Conta Multibanco" -> {
									conta = new ContaMultibanco(depDatField.getText(), fNameField.getText(), uNameField.getText(), ccField.getText());
									cliente.banco.addConta(cliente, conta);
								}
								case "Conta a Prazo" -> {
									conta = new ContaPrazo(depDatField.getText(), fNameField.getText(), uNameField.getText(), ccField.getText());
									cliente.banco.addConta(cliente, conta);
								}
							}
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

	public Conta getConta() {
		return conta;
	}
}
