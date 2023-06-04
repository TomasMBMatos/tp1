package ui;

import logic.Conta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.util.Objects;

public class ContaInfoPopup extends JDialog {

	/**
	 * Create the dialog.
	 */
	public ContaInfoPopup(Conta conta) {
		setUndecorated(true);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(7, 2, 5, 5));
		{
			JLabel nomeLabel = new JLabel("Nome:");
			contentPanel.add(nomeLabel);
		}
		{
			JLabel nomeLabel1 = new JLabel(conta.getNome());
			contentPanel.add(nomeLabel1);
		}
		{
			JLabel apelidoLbl = new JLabel("Apelido:");
			contentPanel.add(apelidoLbl);
		}
		{
			JLabel apelidoLabel1 = new JLabel(conta.getApelido());
			contentPanel.add(apelidoLabel1);
		}
		{
			JLabel ccLabel = new JLabel("NIF:");
			contentPanel.add(ccLabel);
		}
		{
			JLabel ccLabel1 = new JLabel(conta.getCc());
			contentPanel.add(ccLabel1);
		}
		{
			JLabel numeroLabel = new JLabel("Número da Conta:");
			contentPanel.add(numeroLabel);
		}
		{
			JLabel numeroLabel1 = new JLabel(String.valueOf(conta.getNumConta()));
			contentPanel.add(numeroLabel1);
		}
		{
			JLabel tipoLabel = new JLabel("Tipo da Conta:");
			contentPanel.add(tipoLabel);
		}
		{
			JLabel tipoLabel1 = new JLabel(conta.getTipo());
			contentPanel.add(tipoLabel1);
		}
		{
			JLabel saldoLabel = new JLabel("Saldo:");
			contentPanel.add(saldoLabel);
		}
		{
			JLabel saldoLabel1 = new JLabel(String.valueOf(conta.getSaldo()));
			contentPanel.add(saldoLabel1);
		}
		{
			JLabel dataLabel = new JLabel("Data:");
			contentPanel.add(dataLabel);
		}
		{
			JLabel dataLabel1 = new JLabel("-------");
			if(Objects.equals(conta.getTipo(), "Prazo") ||
				Objects.equals(conta.getTipo(), "PoupancaHabitacao"))
						dataLabel1 = new JLabel(conta.getData());
			contentPanel.add(dataLabel1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(e -> dispose());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
