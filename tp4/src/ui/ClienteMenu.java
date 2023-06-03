package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.Cliente;
import logic.Conta;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class ClienteMenu extends Cliente {

	private JFrame frmAplicaoBanco;
	private JTable accountsTable = new JTable() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JButton btnCriarConta;
	private JButton btnRemConta;
	private JButton btnDepConta;
	private JButton btnLevConta;
	private Cliente aux = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Login login = new Login();
		
		while(!login.success) {
			try {
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {
			}
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteMenu window = new ClienteMenu(login.getUsername());
					window.frmAplicaoBanco.setLocationRelativeTo(null);
					window.frmAplicaoBanco.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClienteMenu(String username) {
		this.username = username;
		this.conta = banco.getConta(this, 0);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frmAplicaoBanco = new JFrame();
		frmAplicaoBanco.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\tomas\\Documents\\College\\LEIM\\MoP\\tp4\\images\\CGD-logo-213C7E1FF6-seeklogo.com.png"));
		frmAplicaoBanco.setResizable(false);
		frmAplicaoBanco.setTitle("Caixa Geral de Levantamentos");
		frmAplicaoBanco.setBounds(100, 100, 1024, 576);
		frmAplicaoBanco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		frmAplicaoBanco.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 192));
		panel_1.setBounds(0, 0, 1008, 63);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		
		String bv = String.format("Bem Vindo %s", this.username);
		JLabel lblNewLabel_4 = new JLabel(bv);
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblNewLabel_4.setBounds(415, 11, 380, 36);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\tomas\\Documents\\College\\LEIM\\MoP\\tp4\\images\\clouds.png"));
		lblNewLabel.setBounds(0, 0, 1009, 61);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 166, 988, 360);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 988, 360);
		panel_2.add(scrollPane_1);
		scrollPane_1.setViewportView(accountsTable);
		accountsTable.setGridColor(new Color(123,178,221));
		accountsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRemConta.setEnabled(true);
				btnDepConta.setEnabled(true);
				btnLevConta.setEnabled(true);

				btnRemConta.setForeground(new Color(0,0,0));
				btnDepConta.setForeground(new Color(0,0,0));
				btnLevConta.setForeground(new Color(0,0,0));
				
				if(e.getClickCount() == 2) {
					getSelectedConta(accountsTable.getSelectedRow());
				}
			}
		});
		accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountsTable.setSurrendersFocusOnKeystroke(true);
		accountsTable.setBackground(SystemColor.text);
		accountsTable.setToolTipText("\r\n");
		accountsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Apelido", "N\u00FAmero da Conta", "Saldo"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Long.class, Float.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		accountsTable.getColumnModel().getColumn(0).setResizable(false);
		accountsTable.getColumnModel().getColumn(1).setResizable(false);
		accountsTable.getColumnModel().getColumn(2).setResizable(false);
		accountsTable.getColumnModel().getColumn(3).setResizable(false);
		initTable();
		
		btnCriarConta = new JButton("Criar Conta");
		btnCriarConta.setForeground(new Color(0, 0, 0));
		btnCriarConta.setBackground(new Color(0, 94, 138));
		btnCriarConta.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 20));
		btnCriarConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriarContaMenu menu = new CriarContaMenu(aux);
				menu.setVisible(true);
				if(menu.getConta() != null) {
					conta = menu.getConta();
					addContaToTable(menu.getConta());
				}
			}
		});
		btnCriarConta.setBounds(10, 77, 167, 71);
		panel.add(btnCriarConta);
		
		btnRemConta = new JButton("Remover Conta");
		btnRemConta.setEnabled(false);
		btnRemConta.setBackground(new Color(0, 94, 138));
		btnRemConta.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 20));
		btnRemConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				banco.removeConta(conta);
				removeContaFromTable(accountsTable.getSelectedRow());
			}
		});
		btnRemConta.setBounds(206, 77, 167, 71);
		panel.add(btnRemConta);
		
		btnDepConta = new JButton("Depositar");
		btnDepConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DepositoMenu menu = new DepositoMenu(aux);
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
				updateTable(accountsTable.getSelectedRow());
			}
		});
		btnDepConta.setEnabled(false);
		btnDepConta.setBackground(new Color(0, 94, 138));
		btnDepConta.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 20));
		btnDepConta.setBounds(652, 77, 167, 71);
		panel.add(btnDepConta);
		
		btnLevConta = new JButton("Levantar");
		btnLevConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LevantamentoMenu menu = new LevantamentoMenu(aux);
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
				updateTable(accountsTable.getSelectedRow());
			}
		});
		btnLevConta.setEnabled(false);
		btnLevConta.setBackground(new Color(0, 94, 138));
		btnLevConta.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 20));

		btnLevConta.setBounds(843, 74, 155, 71);
		panel.add(btnLevConta);
		
		JButton btnPremio = new JButton("PR\u00C9MIO!");
		btnPremio.setBackground(new Color(179, 166, 15));
		btnPremio.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
		btnPremio.setBounds(425, 77, 167, 71);
		panel.add(btnPremio);
	}
	
	
	
	private void getSelectedConta(int selectedRow) {
		for(int i=0;i<banco.getContas(this).getLength();i++) {
            contas.add(banco.getConta(this, i));
        }
		this.conta = contas.get(selectedRow);
	}

	private void addContaToTable(Conta conta) {
		DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();
		model.addRow(new Object[] { conta.getNome(), conta.getApelido(), conta.getNumConta(), banco.getSaldo(conta)});

	}

	private void removeContaFromTable(int row) {
		DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();
		model.removeRow(row);
	}

	private void updateTable(int selectedRow) {
		DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();
		model.setValueAt(conta.getNome(), selectedRow, 0);
		model.setValueAt(conta.getApelido(), selectedRow, 1);
		model.setValueAt(conta.getNumConta(), selectedRow, 2);
		model.setValueAt(banco.getSaldo(conta), selectedRow, 3);
	}

	private void initTable() {
		for(int i=0;i<banco.getContas(this).getLength();i++) {
			contas.add(banco.getConta(this, i));
			addContaToTable(banco.getConta(this, i));
		}
	}
}
