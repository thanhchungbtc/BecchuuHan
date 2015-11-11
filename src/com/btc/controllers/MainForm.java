package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.btc.model.Becchuu;
import com.btc.model.BecchuuType;
import com.btc.model.Bukken;
import com.btc.model.Employee;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.supports.Config;
import com.btc.viewModel.BecchuuTableModel;
import com.btc.viewModel.BukkenTableModel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
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
import java.awt.event.MouseListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;

public class MainForm extends JFrame implements BukkenDetailsFormDelegate, BecchuuDetailsDelegate {

	private JPanel contentPane;
	private JTable bukkenTable;
	private JTable becchuuTable;
	private JLabel lblWelcome;

	private BukkenRepository bukkenRepository;
	private BecchuuRepository becchuuRepository;
	
	private JTextField txtBukkenSearch;
	TableRowSorter<TableModel> rowSorter;
	private JComboBox cbType;
	
	private void createAndSetupGUI(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("ファイル");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("更新");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((BecchuuTableModel)becchuuTable.getModel()).refresh();
				((BukkenTableModel)bukkenTable.getModel()).refresh();
			}
		});
		
		JMenuItem menuItem = new JMenuItem("別注依頼ツール");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BecchuuIraiForm becchuuIraiForm = new BecchuuIraiForm();
				becchuuIraiForm.setVisible(true);
			}
		});
		mnFile.add(menuItem);
		mnFile.add(mntmNewMenuItem);

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
		mainSplitPane.setDividerLocation(400);

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
		
		JPanel becchuuSearchPanel = new JPanel();
		rightPanel.add(becchuuSearchPanel, BorderLayout.NORTH);
		GridBagLayout gbl_becchuuSearchPanel = new GridBagLayout();
		gbl_becchuuSearchPanel.columnWidths = new int[]{103, 42, 28, 42, 28, 30, 28, 97, 73, 91, 0};
		gbl_becchuuSearchPanel.rowHeights = new int[]{21, 0};
		gbl_becchuuSearchPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_becchuuSearchPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		becchuuSearchPanel.setLayout(gbl_becchuuSearchPanel);
		
		txtBecchuuSearch = new JTextField();
		GridBagConstraints gbc_txtBecchuuSearch = new GridBagConstraints();
		gbc_txtBecchuuSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBecchuuSearch.weightx = 1.0;
		gbc_txtBecchuuSearch.insets = new Insets(0, 0, 0, 5);
		gbc_txtBecchuuSearch.gridx = 0;
		gbc_txtBecchuuSearch.gridy = 0;
		becchuuSearchPanel.add(txtBecchuuSearch, gbc_txtBecchuuSearch);
		txtBecchuuSearch.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("作成者：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		becchuuSearchPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		cbSakuseiSha = new JComboBox();
		GridBagConstraints gbc_cbSakuseiSha = new GridBagConstraints();
		gbc_cbSakuseiSha.anchor = GridBagConstraints.WEST;
		gbc_cbSakuseiSha.insets = new Insets(0, 0, 0, 5);
		gbc_cbSakuseiSha.gridx = 2;
		gbc_cbSakuseiSha.gridy = 0;
		becchuuSearchPanel.add(cbSakuseiSha, gbc_cbSakuseiSha);
		
		JLabel lblNewLabel_1 = new JLabel("研修者：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 0;
		becchuuSearchPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		cbKenshuusha = new JComboBox();
		GridBagConstraints gbc_cbKenshuusha = new GridBagConstraints();
		gbc_cbKenshuusha.anchor = GridBagConstraints.WEST;
		gbc_cbKenshuusha.insets = new Insets(0, 0, 0, 5);
		gbc_cbKenshuusha.gridx = 4;
		gbc_cbKenshuusha.gridy = 0;
		becchuuSearchPanel.add(cbKenshuusha, gbc_cbKenshuusha);
		
		JLabel lblNewLabel_2 = new JLabel("分類：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 5;
		gbc_lblNewLabel_2.gridy = 0;
		becchuuSearchPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		cbBecchuuType = new JComboBox();
		GridBagConstraints gbc_cbBecchuuType = new GridBagConstraints();
		gbc_cbBecchuuType.anchor = GridBagConstraints.WEST;
		gbc_cbBecchuuType.insets = new Insets(0, 0, 0, 5);
		gbc_cbBecchuuType.gridx = 6;
		gbc_cbBecchuuType.gridy = 0;
		becchuuSearchPanel.add(cbBecchuuType, gbc_cbBecchuuType);
		
		JCheckBox chkSakuseiZumi = new JCheckBox("作成済み表示");
		GridBagConstraints gbc_chkSakuseiZumi = new GridBagConstraints();
		gbc_chkSakuseiZumi.anchor = GridBagConstraints.WEST;
		gbc_chkSakuseiZumi.insets = new Insets(0, 0, 0, 5);
		gbc_chkSakuseiZumi.gridx = 7;
		gbc_chkSakuseiZumi.gridy = 0;
		becchuuSearchPanel.add(chkSakuseiZumi, gbc_chkSakuseiZumi);
		
		JCheckBox chkKenshuZumi = new JCheckBox("検収済み表示");
		GridBagConstraints gbc_chkKenshuZumi = new GridBagConstraints();
		gbc_chkKenshuZumi.anchor = GridBagConstraints.WEST;
		gbc_chkKenshuZumi.insets = new Insets(0, 0, 0, 5);
		gbc_chkKenshuZumi.gridx = 8;
		gbc_chkKenshuZumi.gridy = 0;
		becchuuSearchPanel.add(chkKenshuZumi, gbc_chkKenshuZumi);
		
		JCheckBox chkUploadZumi = new JCheckBox("ＤＢアップ済み表示");
		GridBagConstraints gbc_chkUploadZumi = new GridBagConstraints();
		gbc_chkUploadZumi.anchor = GridBagConstraints.WEST;
		gbc_chkUploadZumi.gridx = 9;
		gbc_chkUploadZumi.gridy = 0;
		becchuuSearchPanel.add(chkUploadZumi, gbc_chkUploadZumi);

		becchuuTable.setModel(new DefaultTableModel(null, columnNames1));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(becchuuTable);

		rightPanel.add(scrollPane_1);

		JPanel rightButtonPanel = new JPanel();
		rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);
		rightButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));
		
		JButton btnAddBecchuu = new JButton();
		btnAddBecchuu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAddBecchuuMouseClicked(e);
			}
		});
		btnAddBecchuu.setIcon(new ImageIcon(MainForm.class.getResource("/resources/icon/icon_plus.png")));
		rightButtonPanel.add(btnAddBecchuu);
		
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
		becchuuRepository = new BecchuuRepository();
	}

	private void setupTable() {
	
		BukkenTableModel bukkenTableModel = new BukkenTableModel(this.bukkenRepository);
		bukkenTable.setModel(bukkenTableModel);
		rowSorter = new TableRowSorter<TableModel>(bukkenTable.getModel());
		bukkenTable.setRowSorter(rowSorter);
		bukkenTable.setRowHeight(25);
		bukkenTable.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = e.getPoint();
				if (bukkenTable.columnAtPoint(point) == bukkenTable.getColumnCount() - 1) {
					bukkenTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					bukkenTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		bukkenTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					bukkenTableClicked(e);
				} else if (e.getClickCount() == 2) {
					bukkenTableDoubleClicked(e);
				}
			}
		});
		
		becchuuTable.setRowHeight(25);	
		BecchuuTableModel becchuuTableModel = new BecchuuTableModel(this.becchuuRepository);
		becchuuTable.setModel(becchuuTableModel);
	}

	private void loadShouhinTypeCombobox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("商品タイプ");
		model.addElement("XEVO");
		model.addElement("Σ");
		cbType.setModel(model);
		cbType.setSelectedIndex(0);
	}

	private void loadBecchuuTypeCombobox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		List<BecchuuType> becchuuTypes = CommonRepository.getBecchuuTypes();
		model.addElement("全て");
		for (BecchuuType becchuuType: becchuuTypes) {
			model.addElement(becchuuType.getName());
		}
		cbBecchuuType.setModel(model);
		cbBecchuuType.setSelectedIndex(0);
	}
	
	private void loadBecchuuEmployeesCombobox() {
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());		
		model.insertElementAt("全て", 0);
		cbSakuseiSha.setModel(model);
		
		cbSakuseiSha.setSelectedIndex(0);
		
		DefaultComboBoxModel<Object> model2 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());
		model2.insertElementAt("全て", 0);
		cbKenshuusha.setModel(model2);
		cbKenshuusha.setSelectedIndex(0);
		
	}
	
	// handle events--------------------------------------------------------------------------------------
	private void btnAddBukkenActionPerformed(ActionEvent event) {
		BukkenDetailsForm form = new BukkenDetailsForm(null);
		form.setLocationRelativeTo(this);
		form.showDialog(this);
	}
		
	private void btnAddBecchuuMouseClicked(MouseEvent event){
		BecchuuDetailsForm form = new BecchuuDetailsForm();
		form.setLocationRelativeTo(this);
		form.showDialog(this);
	}
	
	private void bukkenTableClicked(MouseEvent event)  {
		if (bukkenTable.getSelectedRow() == -1) return;
		int col = bukkenTable.getSelectedColumn();
		if (col != bukkenTable.getColumnCount() - 1) return;		
		
		int row = bukkenTable.getSelectedRow();
		String id = bukkenTable.getValueAt(row, 1).toString();
		Bukken bukken = bukkenRepository.getBukkenWithID(id);
		String depsf = bukken.getDepsf().replaceAll("-", "");
		String link = "http://sv04plemia.osaka.daiwahouse.co.jp/BzkWeb/search.aspx?zwid=" + depsf + "&user=" + Config.UserID;
		System.out.println(link);
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null) {
			try {
				desktop.browse(new URI(link));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void bukkenTableDoubleClicked(MouseEvent event) {		
		if (bukkenTable.getSelectedRow() == -1) return;
		int row = bukkenTable.getSelectedRow();
		String id = bukkenTable.getValueAt(row, 1).toString();
		Bukken bukkenToEdit = bukkenRepository.getBukkenWithID(id);
		// if found bukken at selectedRow
		if (bukkenToEdit != null) {		
			BukkenDetailsForm form = new BukkenDetailsForm(bukkenToEdit);
			form.setLocationRelativeTo(this);
			form.showDialog(this);
		}
	}
	// begin: search on bukkenTable
	RowFilter typeFilter;
	RowFilter textFilter;	
	LinkedList<RowFilter<TableModel, Object>> rowFilters = new LinkedList<>();
	private JComboBox cbBecchuuType;
	private JTextField txtBecchuuSearch;
	private JComboBox cbSakuseiSha;
	private JComboBox cbKenshuusha;
	
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
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	private void loadFromDataBase() {
		initializeData();
		setupTable();
	}
	/**
	 * Create the frame.
	 */
	public MainForm() {
		
		setTitle("別注管理  ― ハノイ支店");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(1000, 700));
		//setExtendedState(JFrame.MAXIMIZED_BOTH); ; 
		
		
		createAndSetupGUI();
		loadFromDataBase();
		loadShouhinTypeCombobox();
		loadBecchuuTypeCombobox();
		loadBecchuuEmployeesCombobox();
		
		
		
		//pack();
		//setBounds(100, 100, 1280, 800);
	}
	
	// BEGIN implements BukkenDetailFormsDelegate--------------------------------------------
	@Override
	public void submitData(Bukken bukken, boolean insert) {
		BukkenTableModel bukkenTableModel = (BukkenTableModel)bukkenTable.getModel();
		if (insert) {
			bukkenTableModel.insertBukken(bukken);			
		} else {
			bukkenTableModel.updateBukken(bukken, bukkenTable.getSelectedRow());
		}
		
	}
	// END implements BukkenDetailFormsDelegate--------------------------------------------

	@Override
	public void submitData(Becchuu becchuu, boolean insert) {
		// TODO Auto-generated method stub
		
	}
}
