package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.viewModel.BecchuuTableModel;
import com.btc.viewModel.BukkenTableModel;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Desktop.Action;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainForm extends JFrame implements BukkenDetailsFormDelegate {

	private JPanel contentPane;
	private JTable bukkenTable;
	private JTable becchuuTable;
	private JLabel lblWelcome;

	private BukkenRepository bukkenRepository;
	private JTextField txtBukkenSearch;
	TableRowSorter<TableModel> rowSorter;
	private JComboBox cbType;
	
	private void createAndSetupGUI(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("ファイル");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("\u7D42\u4E86");
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("\u30D8\u30EB\u30D7");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("\u30BD\u30D5\u30C8\u30A6\u30A7\u30A2\u306B\u3064\u3044\u3066");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setDividerSize(2);
		mainSplitPane.setDividerLocation(350);

		contentPane.add(mainSplitPane, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel();
		mainSplitPane.setLeftComponent(leftPanel);

		bukkenTable = new JTable();
		bukkenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		leftPanel.setLayout(new BorderLayout(0, 0));


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bukkenTable);
		leftPanel.add(scrollPane);	

		JPanel leftButtonPanel = new JPanel();
		leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);
		leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));
		
				JButton btnAddGroup = new JButton();
				leftButtonPanel.add(btnAddGroup);
				btnAddGroup.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						btnAddBukkenActionPerformed(event);
					}
				});
				
					btnAddGroup.setIcon(new ImageIcon(MainForm.class.getResource("/resources/icon/icon_plus.png")));
					
							JButton btnDeleteGroup = new JButton("");
							leftButtonPanel.add(btnDeleteGroup);
							btnDeleteGroup.setIcon(new ImageIcon(MainForm.class.getResource("/resources/icon/icon_trash.png")));
		
		JPanel searchBukkenPanel = new JPanel();
		leftPanel.add(searchBukkenPanel, BorderLayout.NORTH);
		
		txtBukkenSearch = new JTextField();
		txtBukkenSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtBukkenSearchKeyReleased(e);
			}
		});
		searchBukkenPanel.setLayout(new BorderLayout(0, 0));
		searchBukkenPanel.add(txtBukkenSearch);
		txtBukkenSearch.setColumns(10);
		
		cbType = new JComboBox();
		cbType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbTypeitemStateChanged(e);
			}
		});
		searchBukkenPanel.add(cbType, BorderLayout.EAST);


		JPanel rightPanel = new JPanel();
		mainSplitPane.setRightComponent(rightPanel);

		becchuuTable = new JTable();

		becchuuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] columnNames1 = {"Title", "User Name", "Password", "URL", "Notes" };
		rightPanel.setLayout(new BorderLayout(0, 0));

		becchuuTable.setModel(new DefaultTableModel(null, columnNames1));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(becchuuTable);

		rightPanel.add(scrollPane_1);

		JPanel rightButtonPanel = new JPanel();
		rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);
		rightButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));
		
		JButton btnDeleteBecchuu = new JButton("");
		btnDeleteBecchuu.setIcon(new ImageIcon(MainForm.class.getResource("/resources/icon/icon_trash.png")));
		rightButtonPanel.add(btnDeleteBecchuu);

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(null);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblWelcome = new JLabel("Welcome");
		statusPanel.add(lblWelcome);

	}

	private void initializeData() {
		bukkenRepository = new BukkenRepository();
	}

	private void setupTable() {
	
		BukkenTableModel bukkenTableModel = new BukkenTableModel(this.bukkenRepository);
		//TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(bukkenTableModel);
		//bukkenTable.setRowSorter(sorter);
		//bukkenTable.setAutoCreateRowSorter(true);
		//sorter.setRowFilter(RowFilter.regexFilter("1"));
		bukkenTable.setModel(bukkenTableModel);
		rowSorter = new TableRowSorter<TableModel>(bukkenTable.getModel());
		bukkenTable.setRowSorter(rowSorter);
		bukkenTable.setRowHeight(25);
	
		becchuuTable.setRowHeight(25);	
	}

	private void loadShouhinTypeCombobox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("全て");
		model.addElement("XEVO");
		model.addElement("Σ");
		cbType.setModel(model);
		cbType.setSelectedIndex(0);
	}
	
	// handle events--------------------------------------------------------------------------------------
	private void btnAddBukkenActionPerformed(ActionEvent event) {
		BukkenDetailsForm form = new BukkenDetailsForm(null);
		form.delegate = this;
		form.setLocationRelativeTo(this);
		form.setVisible(true);
	}
	
	// begin: search on bukkenTable
	RowFilter typeFilter;
	RowFilter textFilter;	
	LinkedList<RowFilter<TableModel, Object>> rowFilters = new LinkedList<>();
	
	private void filterBukkenTable() {
		rowFilters.clear();
		if (textFilter != null) {
			rowFilters.add(textFilter);
		}
		if (typeFilter != null) {
			rowFilters.add(typeFilter);
		}
		rowSorter.setRowFilter(RowFilter.andFilter(rowFilters));
	}
	
	private void txtBukkenSearchKeyReleased(KeyEvent event) {		 
		if (txtBukkenSearch.getText().trim().length() == 0) {
			textFilter = null;
		} else {
			textFilter = RowFilter.regexFilter("(?i)" + txtBukkenSearch.getText());
		}
		filterBukkenTable();		
	}
	
	private void cbTypeitemStateChanged(ItemEvent event) {
		if (cbType.getSelectedIndex() == 0) {
			// rowSorter.setRowFilter(RowFilter.regexFilter(""));
			typeFilter = null;
		} else {
			// rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + cbType.getSelectedItem().toString()));
			typeFilter = RowFilter.regexFilter("(?i)" + cbType.getSelectedItem().toString());
		}
		filterBukkenTable();
	}
	// end: search on bukkenTable
	
	// end handle events------------------------------------------------------------------------------------

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
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
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);

		initializeData();
		createAndSetupGUI();
		loadShouhinTypeCombobox();
		setupTable();
		
		
		pack();
	}
	
	// BEGIN implements BukkenDetailFormsDelegate--------------------------------------------
	@Override
	public void submitData(Bukken bukken, boolean insert) {
		BukkenTableModel bukkenTableModel = (BukkenTableModel)bukkenTable.getModel();
		if (insert) {
			bukkenTableModel.insertBukken(bukken);			
		} else {
			
		}
		
	}
	// END implements BukkenDetailFormsDelegate--------------------------------------------
}
