package com.btc.controllers.BecchuuKanriForm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.btc.controllers.BecchuuDetailsDelegate;
import com.btc.controllers.BecchuuDetailsForm;
import com.btc.controllers.BukkenDetailsForm;
import com.btc.controllers.BukkenKanri;
import com.btc.model.Becchuu;
import com.btc.model.BecchuuType;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import com.btc.viewModel.BecchuuTableModel;
import com.btc.viewModel.BukkenTableModel;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BecchuuKanriForm extends JFrame implements BecchuuDetailsDelegate {

	private BecchuuRepository becchuuRepository;

	TableRowSorter<TableModel> rowSorter;

	/**
	 * Create the frame.
	 */
	public BecchuuKanriForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		createAndSetUpGUI();
		initializeData();
		loadBecchuuEmployeesCombobox();
		loadBecchuuTypeCombobox();
		setupTable();
		// pack();
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

		DefaultComboBoxModel<Object> model2 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());
		model2.insertElementAt("全て", 0);
		cbKenshuusha.setModel(model2);

	}

	private void setupTable() {
		becchuuTable.setRowHeight(25);
		BecchuuKanriFormTableModel model = new BecchuuKanriFormTableModel(becchuuRepository);
		becchuuTable.setModel(model);
		becchuuTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					becchuuTableDoubleClicked(e);
				}
			}
		});

	}

	private void initializeData() {
		becchuuRepository = BecchuuRepository.Instance();
	}

	// BEGIN: handle Events;
	private void becchuuTableDoubleClicked(MouseEvent event) {
		if (becchuuTable.getSelectedRow() == -1) return;
		int row = becchuuTable.getSelectedRow();
		String koujibangou = becchuuTable.getValueAt(row, 2).toString();
		String becchuuKigou = becchuuTable.getValueAt(row, 0).toString();
		Becchuu becchuuToEdit = becchuuRepository.getBecchuuByID(koujibangou, becchuuKigou);

		if (becchuuToEdit != null) {		
			BecchuuDetailsForm form = new BecchuuDetailsForm(becchuuToEdit);
			form.setLocationRelativeTo(this);
			form.delegate = this;
			form.showDialog(this);
		}
	}

	// END: handle Events
	private void createAndSetUpGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		String[] columnNames1 = {"Title", "User Name", "Password", "URL", "Notes" };
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		headerPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		contentPane.add(headerPanel, BorderLayout.NORTH);

		JLabel lblNewLabel_3 = new JLabel("別注管理システム");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		headerPanel.add(lblNewLabel_3);

		JPanel maincontent = new JPanel();
		maincontent.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		contentPane.add(maincontent, BorderLayout.CENTER);
		maincontent.setLayout(new BorderLayout(0, 0));

		JPanel becchuuSearchPanel = new JPanel();
		maincontent.add(becchuuSearchPanel, BorderLayout.NORTH);
		GridBagLayout gbl_becchuuSearchPanel = new GridBagLayout();
		gbl_becchuuSearchPanel.columnWidths = new int[]{103, 42, 28, 42, 28, 30, 28, 97, 73, 91, 0};
		gbl_becchuuSearchPanel.rowHeights = new int[]{21, 0};
		gbl_becchuuSearchPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_becchuuSearchPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		becchuuSearchPanel.setLayout(gbl_becchuuSearchPanel);

		JTextField txtBecchuuSearch = new JTextField();
		GridBagConstraints gbc_txtBecchuuSearch = new GridBagConstraints();
		gbc_txtBecchuuSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBecchuuSearch.weightx = 1.0;
		gbc_txtBecchuuSearch.insets = new Insets(0, 0, 0, 5);
		gbc_txtBecchuuSearch.gridx = 0;
		gbc_txtBecchuuSearch.gridy = 0;
		becchuuSearchPanel.add(txtBecchuuSearch, gbc_txtBecchuuSearch);
		txtBecchuuSearch.setColumns(10);

		JLabel lblNewLabel = new JLabel("作成者");
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

		JLabel lblNewLabel_1 = new JLabel("研修者");
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

		JLabel lblNewLabel_2 = new JLabel("分類");
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

		chkSakuseiZumi = new JCheckBox("作成済み表示");
		GridBagConstraints gbc_chkSakuseiZumi = new GridBagConstraints();
		gbc_chkSakuseiZumi.anchor = GridBagConstraints.WEST;
		gbc_chkSakuseiZumi.insets = new Insets(0, 0, 0, 5);
		gbc_chkSakuseiZumi.gridx = 7;
		gbc_chkSakuseiZumi.gridy = 0;
		becchuuSearchPanel.add(chkSakuseiZumi, gbc_chkSakuseiZumi);

		chkKenshuZumi = new JCheckBox("検収済み表示");
		GridBagConstraints gbc_chkKenshuZumi = new GridBagConstraints();
		gbc_chkKenshuZumi.anchor = GridBagConstraints.WEST;
		gbc_chkKenshuZumi.insets = new Insets(0, 0, 0, 5);
		gbc_chkKenshuZumi.gridx = 8;
		gbc_chkKenshuZumi.gridy = 0;
		becchuuSearchPanel.add(chkKenshuZumi, gbc_chkKenshuZumi);

		chkUploadZumi = new JCheckBox("ＤＢアップ済み表示");
		GridBagConstraints gbc_chkUploadZumi = new GridBagConstraints();
		gbc_chkUploadZumi.anchor = GridBagConstraints.WEST;
		gbc_chkUploadZumi.gridx = 9;
		gbc_chkUploadZumi.gridy = 0;
		becchuuSearchPanel.add(chkUploadZumi, gbc_chkUploadZumi);

		becchuuTable = new JTable();
		becchuuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		becchuuTable.setModel(new DefaultTableModel(null, columnNames1));

		JScrollPane scrollPane_1 = new JScrollPane();
		maincontent.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setViewportView(becchuuTable);

		JPanel footerButtonPanel = new JPanel();
		maincontent.add(footerButtonPanel, BorderLayout.SOUTH);
		footerButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));

		JButton btnEdit = new JButton();
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnEdit.setIcon(new ImageIcon(BecchuuKanriForm.class.getResource("/resources/icon/icon-edit.png")));
		footerButtonPanel.add(btnEdit);

		JButton btnDeleteBecchuu = new JButton("");
		btnDeleteBecchuu.setIcon(new ImageIcon(BukkenKanri.class.getResource("/resources/icon/icon_trash.png")));
		footerButtonPanel.add(btnDeleteBecchuu);

		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((BecchuuKanriFormTableModel)becchuuTable.getModel()).refresh();
			}
		});
		btnRefresh.setIcon(new ImageIcon(BecchuuKanriForm.class.getResource("/resources/icon/icon-refresh.png")));
		footerButtonPanel.add(btnRefresh);

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout_1 = (FlowLayout) statusPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		JLabel lblNewLabel_4 = new JLabel("別注枚数：1000 - ミス");
		statusPanel.add(lblNewLabel_4);
	}

	private JPanel contentPane;
	private JTable becchuuTable;
	private JLabel lblWelcome;
	private JTextField txtBukkenSearch;	
	private JComboBox cbType;
	private JComboBox cbSakuseiSha;
	private JComboBox cbKenshuusha;
	private JComboBox cbBecchuuType;
	private JCheckBox chkSakuseiZumi;
	private JCheckBox chkKenshuZumi;
	private JCheckBox chkUploadZumi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Config.setLookAndField();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BecchuuKanriForm frame = new BecchuuKanriForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	@Override
	public void submitData(BecchuuDetailsForm becchuuDetailsForm, Becchuu becchuu) {
		try{
			BecchuuKanriFormTableModel becchuuKanriFormTableModel = (BecchuuKanriFormTableModel)becchuuTable.getModel();		
			if (becchuuKanriFormTableModel.updateBecchuu(becchuu, becchuuTable.getSelectedRow()) != null) {
				becchuuDetailsForm.dispose();
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
