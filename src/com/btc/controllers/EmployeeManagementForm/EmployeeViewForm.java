package com.btc.controllers.EmployeeManagementForm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.btc.model.BecchuuType;
import com.btc.model.Employee;
import com.btc.repositoty.CommonRepository;
import com.btc.viewModel.BukkenTableModel;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class EmployeeViewForm extends JFrame {

	private JPanel contentPane;
	private JTable employeeTable;
	private JTextField txtShainBangou;
	private JTextField txtName;
	TableRowSorter<TableModel> rowSorter;
	private JComboBox cbGroup;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeViewForm frame = new EmployeeViewForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private void createAndSetupGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		employeeTable = new JTable();
		scrollPane.setViewportView(employeeTable);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("社員番号：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		txtShainBangou = new JTextField();
		GridBagConstraints gbc_txtShainBangou = new GridBagConstraints();
		gbc_txtShainBangou.insets = new Insets(0, 0, 5, 0);
		gbc_txtShainBangou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtShainBangou.gridx = 1;
		gbc_txtShainBangou.gridy = 0;
		panel_1.add(txtShainBangou, gbc_txtShainBangou);
		txtShainBangou.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("名前：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 1;
		panel_1.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		JButton btnSave = new JButton("追加");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String groupID = cbGroup.getSelectedIndex() == 0 ? "XV" : "SM";
				Employee employee = new Employee(txtShainBangou.getText(), txtName.getText(), groupID);
				EmployeeViewTableModel employeeViewTableModel = (EmployeeViewTableModel)employeeTable.getModel();
				employeeViewTableModel.insertEmployee(employee);
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("所属班：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		cbGroup = new JComboBox();
		GridBagConstraints gbc_cbGroup = new GridBagConstraints();
		gbc_cbGroup.insets = new Insets(0, 0, 5, 0);
		gbc_cbGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGroup.gridx = 1;
		gbc_cbGroup.gridy = 2;
		panel_1.add(cbGroup, gbc_cbGroup);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.EAST;
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 3;
		panel_1.add(btnSave, gbc_btnSave);
	}
	
	private void loadComboboxGroup() {
		  DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();	    
	      model.addElement("XEVO");
	      model.addElement("Σ");
	      cbGroup.setModel(model);
	      cbGroup.setSelectedIndex(0);
	}
	
	private void setupTable() {
		EmployeeViewTableModel employeeViewTableModel = new EmployeeViewTableModel();
	      employeeTable.setModel(employeeViewTableModel);
	      //rowSorter = new TableRowSorter<TableModel>(employeeTable.getModel());
	      //employeeTable.setRowSorter(rowSorter);
	      employeeTable.setRowHeight(25);
	}

	public EmployeeViewForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 682, 434);
		
		createAndSetupGUI();
		loadComboboxGroup();
		setupTable();
	}

}
