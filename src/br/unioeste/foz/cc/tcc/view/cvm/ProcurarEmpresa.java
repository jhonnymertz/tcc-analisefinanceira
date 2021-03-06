package br.unioeste.foz.cc.tcc.view.cvm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.unioeste.foz.cc.tcc.controller.ListaEmpresasFrameActionManager;
import br.unioeste.foz.cc.tcc.view.arvore.ArvoreEmpresas;
import br.unioeste.foz.cc.tcc.view.base.ListaEmpresasFrame;

@SuppressWarnings("serial")
public class ProcurarEmpresa extends ListaEmpresasFrame {

	/**
	 * Create the frame.
	 * 
	 * @param arvoreEmpresas
	 */
	public ProcurarEmpresa(ArvoreEmpresas arvoreEmpresas) {
		this.arvoreEmpresas = arvoreEmpresas;
		actionManager = new ListaEmpresasFrameActionManager(this);
		setTitle("Procurar Empresa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 687, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		tbModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Nome", "Cod. CVM" });
		tableResults = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}
		};
		tableResults
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableResults.setModel(tbModel);

		scrollResults = new JScrollPane();
		scrollResults.setViewportView(tableResults);
		tableResults.setAutoCreateRowSorter(true);
		contentPane.add(scrollResults, BorderLayout.CENTER);
		contentPane.add(tableResults.getTableHeader(), BorderLayout.PAGE_START);

		JPanel panelOptions = new JPanel();
		contentPane.add(panelOptions, BorderLayout.SOUTH);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setActionCommand("buscar");
		btnBuscar.addActionListener(actionManager);
		panelOptions.setLayout(new GridLayout(0, 4, 20, 0));
		panelOptions.add(btnBuscar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("cancelar");
		btnCancelar.addActionListener(actionManager);
		panelOptions.add(btnCancelar);

		btnCarregar = new JButton("Carregar");
		btnCarregar.setActionCommand("carregar");
		btnCarregar.addActionListener(actionManager);
		panelOptions.add(btnCarregar);

		btnFechar = new JButton("Fechar");
		btnFechar.setActionCommand("fechar");
		btnFechar.addActionListener(actionManager);
		panelOptions.add(btnFechar);

		JPanel panelInput = new JPanel();
		contentPane.add(panelInput, BorderLayout.NORTH);
		panelInput.setLayout(new BorderLayout(20, 0));

		JLabel lblBuscarPor = new JLabel("Buscar por:");
		panelInput.add(lblBuscarPor, BorderLayout.WEST);

		tfBuscarPor = new JTextField();
		panelInput.add(tfBuscarPor);
		tfBuscarPor.setColumns(10);

	}

}
