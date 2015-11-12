package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.btc.model.Becchuu;
import com.btc.model.BecchuuStatus;
import com.btc.model.Bukken;
import com.btc.model.Employee;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.supports.Config;
import com.btc.supports.DateLabelFormatter;
import com.btc.supports.Helpers;

import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;

public class BecchuuDetailsForm extends JDialog {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtMotozuKigou;
	private JDatePickerImpl dpkSakuseBi;

	public BecchuuDetailsDelegate delegate;
	private JTextField txtBecchuuKigou;
	private JTextArea txtBecchuuParameter;
	private JTextArea txtBecchuuNaiyou;
	private JTextArea txtMotozuParameter;


	private Becchuu becchuuToEdit;
	private JLabel txtIraisha;
	private JLabel ltxtIraiBi;
	private JLabel txtNouki;
	private JLabel txtKoujibangou;
	private JLabel txtBukkenMei;
	private JLabel txtShiten;
	private JLabel txtShouhinType;
	private JLabel txtDEPSF;
	private JComboBox cbSakuseiJoukyou;
	private JSpinner spMaisuu;
	private JComboBox cbKenshuuSha;
	private JComboBox cbKenshuuJoukyou;
	private JSpinner spMisu;
	private JTextArea txtBikou;
	private JComboBox cbsakuSeiSha;

	private void loadBecchuuEmployeesCombobox() {
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());		
		cbsakuSeiSha.setModel(model);		
		
		DefaultComboBoxModel<Object> model2 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());
		cbKenshuuSha.setModel(model2);		
		
	}
	
	private void loadStatusCombobox() {
		DefaultComboBoxModel<BecchuuStatus> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuStatus());	
		cbSakuseiJoukyou.setModel(model);	
		
		DefaultComboBoxModel<BecchuuStatus> model2 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuStatus());	
		cbKenshuuJoukyou.setModel(model2);
	}
	
	private void populateData() {
		txtBecchuuKigou.setText(becchuuToEdit.getBecchuuKigou());
		txtBecchuuParameter.setText(becchuuToEdit.getBecchuuParameter());
		txtBecchuuNaiyou.setText(becchuuToEdit.getBecchuuNaiyou());
		txtMotozuKigou.setText(becchuuToEdit.getMotozuKigou());
		txtMotozuParameter.setText(becchuuToEdit.getMotozuParameter());
		txtIraisha.setText(becchuuToEdit.getIraiShaID());
		ltxtIraiBi.setText(Helpers.stringFromDate(becchuuToEdit.getIraibi()));
		
		Bukken bukken = becchuuToEdit.getBukken();
		txtNouki.setText(Helpers.stringFromDate(bukken.getNouki()));
		txtKoujibangou.setText(bukken.getId());
		txtBukkenMei.setText(bukken.getName());
		txtShiten.setText(bukken.getShiten());
		txtShouhinType.setText(bukken.getBukkenType());
		txtDEPSF.setText(bukken.getDepsf());
		
		Calendar calendar = Calendar.getInstance();
		Date sakuseiBi = becchuuToEdit.getSakuseiBi();
		if (sakuseiBi == null) {
			sakuseiBi = new Date();
		}
		calendar.setTime(sakuseiBi);
		dpkSakuseBi.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DATE));
		dpkSakuseBi.getModel().setSelected(true);
		
		Employee sakuseiSha = becchuuToEdit.getSakuseiSha();
		cbsakuSeiSha.getModel().setSelectedItem(sakuseiSha);
		
		Employee kenshuuSha = becchuuToEdit.getKenshuuSha();
		cbKenshuuSha.getModel().setSelectedItem(kenshuuSha);
		
		BecchuuStatus sakuseiJoukyou = becchuuToEdit.getSakuseiStatus();
		cbSakuseiJoukyou.getModel().setSelectedItem(sakuseiJoukyou);
		
		BecchuuStatus kenshuuJoukyou = becchuuToEdit.getKenshuuStatus();
		cbKenshuuJoukyou.getModel().setSelectedItem(kenshuuJoukyou);
//		
		int misu = becchuuToEdit.getMisu();
		spMisu.getModel().setValue(misu < 0 ? 0 : misu);
		int maisu = becchuuToEdit.getBecchuuMaisu();
		spMaisuu.getModel().setValue(maisu < 0 ? 0 : maisu);
		
		txtBikou.setText(becchuuToEdit.getBikou());
	}

	// BEGIN handle Events
	
	private void btnOKActionPerformed(ActionEvent event) {
		becchuuToEdit.setBecchuuKigou(txtBecchuuKigou.getText().trim());
		becchuuToEdit.setBecchuuParameter(txtBecchuuParameter.getText().trim());
		becchuuToEdit.setBecchuuNaiyou(txtBecchuuNaiyou.getText().trim());
		becchuuToEdit.setMotozuKigou(txtMotozuKigou.getText().trim());
		becchuuToEdit.setMotozuParameter(txtMotozuParameter.getText().trim());
		
		Employee sakuseiSha = (Employee)cbsakuSeiSha.getModel().getSelectedItem();
		if (sakuseiSha != null)
			becchuuToEdit.setSakuseiShaID(sakuseiSha.getId());

		Employee kenshuuSha = (Employee)cbKenshuuSha.getModel().getSelectedItem();
		if (kenshuuSha != null)
			becchuuToEdit.setKenshuShaID(kenshuuSha.getId());

		BecchuuStatus sakuseiStatus = (BecchuuStatus)cbSakuseiJoukyou.getModel().getSelectedItem();
		if (sakuseiStatus != null)
			becchuuToEdit.setSakuseiStatusID(sakuseiStatus.getId());
		
		BecchuuStatus kenshuuStatus = (BecchuuStatus)cbKenshuuJoukyou.getModel().getSelectedItem();
		if (kenshuuStatus != null)
			becchuuToEdit.setKenshuuStatusID(kenshuuStatus.getId());
		
		becchuuToEdit.setSakuseiBi(((Date)dpkSakuseBi.getModel().getValue()));
		becchuuToEdit.setBecchuuMaisu(Integer.parseInt(spMaisuu.getModel().getValue().toString()));
		becchuuToEdit.setMisu(Integer.parseInt(spMisu.getModel().getValue().toString()));
		becchuuToEdit.setBikou(txtBikou.getText().trim());
		
		try {
			BecchuuRepository.Instance().update(becchuuToEdit);
			DialogHelpers.showAlert("成功", "別注図更新しました！");
		} catch (SQLException e) {
			DialogHelpers.showError("エラー", "更新失敗しました！" + e.getMessage());
			e.printStackTrace();
		}
		 this.dispose();
	}

	// END handle Events

	private void createAndSetupGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Border margin = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);

		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		headerPanel.setBackground(Color.BLACK);
		contentPane.add(headerPanel, BorderLayout.NORTH);

		lblNewLabel = new JLabel("別注詳細");
		lblNewLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		headerPanel.add(lblNewLabel);

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		JPanel footerPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) footerPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		footerPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		footerPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
		contentPane.add(footerPanel, BorderLayout.SOUTH);

		JButton btnCancel = new JButton("キャンセル");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		footerPanel.add(btnCancel);

		JButton btnOK = new JButton("実行");		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOKActionPerformed(e);
			}
		});
		footerPanel.add(btnOK);		

		JPanel maincontentPanel = new JPanel();
		contentPane.add(maincontentPanel, BorderLayout.CENTER);
		maincontentPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel becchuuPanel = new JPanel();
		maincontentPanel.add(becchuuPanel);
		becchuuPanel.setLayout(new BorderLayout(0, 0));

		JPanel becchuuHeader = new JPanel();

		becchuuHeader.setBorder(new CompoundBorder(margin, bottomLine));
		FlowLayout fl_becchuuHeader = (FlowLayout) becchuuHeader.getLayout();
		fl_becchuuHeader.setAlignment(FlowLayout.LEFT);
		becchuuPanel.add(becchuuHeader, BorderLayout.NORTH);

		JLabel label_1 = new JLabel("別注情報");
		label_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		becchuuHeader.add(label_1);

		JPanel becchuuContentPanel = new JPanel();
		becchuuPanel.add(becchuuContentPanel);
		becchuuContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_becchuuContentPanel = new GridBagLayout();
		gbl_becchuuContentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_becchuuContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_becchuuContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_becchuuContentPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		becchuuContentPanel.setLayout(gbl_becchuuContentPanel);

		JLabel lblNewLabel_1 = new JLabel("別注記号：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		becchuuContentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtBecchuuKigou = new JTextField();
		GridBagConstraints gbc_txtBecchuuKigou = new GridBagConstraints();
		gbc_txtBecchuuKigou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBecchuuKigou.insets = new Insets(0, 0, 5, 0);
		gbc_txtBecchuuKigou.gridx = 1;
		gbc_txtBecchuuKigou.gridy = 0;
		becchuuContentPanel.add(txtBecchuuKigou, gbc_txtBecchuuKigou);
		txtBecchuuKigou.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("別注パラメータ：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		becchuuContentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		becchuuContentPanel.add(scrollPane, gbc_scrollPane);

		txtBecchuuParameter = new JTextArea();
		scrollPane.setViewportView(txtBecchuuParameter);
		scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtBecchuuParameter.setLineWrap(true);
		txtBecchuuParameter.setRows(5);

		JLabel lblUserName = new JLabel("別注内容：");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 2;
		becchuuContentPanel.add(lblUserName, gbc_lblUserName);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 2;
		becchuuContentPanel.add(scrollPane_1, gbc_scrollPane_1);

		txtBecchuuNaiyou = new JTextArea();
		scrollPane_1.setViewportView(txtBecchuuNaiyou);
		scrollPane_1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtBecchuuNaiyou.setRows(5);

		JLabel lblPassword = new JLabel("元図記号：");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		becchuuContentPanel.add(lblPassword, gbc_lblPassword);

		txtMotozuKigou = new JTextField();
		GridBagConstraints gbc_txtMotozuKigou = new GridBagConstraints();
		gbc_txtMotozuKigou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMotozuKigou.anchor = GridBagConstraints.NORTH;
		gbc_txtMotozuKigou.insets = new Insets(0, 0, 5, 0);
		gbc_txtMotozuKigou.gridx = 1;
		gbc_txtMotozuKigou.gridy = 3;
		becchuuContentPanel.add(txtMotozuKigou, gbc_txtMotozuKigou);
		txtMotozuKigou.setColumns(10);

		JLabel lblRepeat = new JLabel("元図パラメータ：");
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.WEST;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 4;
		becchuuContentPanel.add(lblRepeat, gbc_lblRepeat);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 4;
		becchuuContentPanel.add(scrollPane_2, gbc_scrollPane_2);

		txtMotozuParameter = new JTextArea();
		txtMotozuParameter.setLineWrap(true);
		scrollPane_2.setViewportView(txtMotozuParameter);
		scrollPane_2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtMotozuParameter.setRows(5);

		JLabel lblNewLabel_4 = new JLabel("依頼者：");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 5;
		becchuuContentPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		txtIraisha = new JLabel("New label");
		GridBagConstraints gbc_txtIraisha = new GridBagConstraints();
		gbc_txtIraisha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIraisha.insets = new Insets(0, 0, 5, 0);
		gbc_txtIraisha.gridx = 1;
		gbc_txtIraisha.gridy = 5;
		becchuuContentPanel.add(txtIraisha, gbc_txtIraisha);

		JLabel label = new JLabel("依頼日：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 6;
		becchuuContentPanel.add(label, gbc_label);
		
		ltxtIraiBi = new JLabel("New label");
		GridBagConstraints gbc_ltxtIraiBi = new GridBagConstraints();
		gbc_ltxtIraiBi.fill = GridBagConstraints.HORIZONTAL;
		gbc_ltxtIraiBi.gridx = 1;
		gbc_ltxtIraiBi.gridy = 6;
		becchuuContentPanel.add(ltxtIraiBi, gbc_ltxtIraiBi);

		JPanel panel = new JPanel();
		maincontentPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel bukkenHeader = new JPanel();
		bukkenHeader.setBorder(new CompoundBorder(margin, bottomLine));

		FlowLayout fl_bukkenHeader = (FlowLayout) bukkenHeader.getLayout();
		fl_bukkenHeader.setAlignment(FlowLayout.LEFT);
		panel.add(bukkenHeader, BorderLayout.NORTH);

		JLabel label_2 = new JLabel("管理情報");
		label_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		bukkenHeader.add(label_2);


		JPanel bukkenPanel = new JPanel();
		bukkenPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add(bukkenPanel);
		GridBagLayout gbl_bukkenPanel = new GridBagLayout();
		gbl_bukkenPanel.columnWidths = new int[]{0, 0, 0};
		gbl_bukkenPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_bukkenPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_bukkenPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		bukkenPanel.setLayout(gbl_bukkenPanel);

		JLabel lblNewLabel_3 = new JLabel("納期：");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		bukkenPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		txtNouki = new JLabel("New label");
		GridBagConstraints gbc_txtNouki = new GridBagConstraints();
		gbc_txtNouki.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNouki.anchor = GridBagConstraints.SOUTH;
		gbc_txtNouki.insets = new Insets(0, 0, 5, 0);
		gbc_txtNouki.gridx = 1;
		gbc_txtNouki.gridy = 0;
		bukkenPanel.add(txtNouki, gbc_txtNouki);

		JLabel lblNewLabel_5 = new JLabel("工事番号：");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 1;
		bukkenPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		txtKoujibangou = new JLabel("New label");
		GridBagConstraints gbc_txtKoujibangou = new GridBagConstraints();
		gbc_txtKoujibangou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKoujibangou.insets = new Insets(0, 0, 5, 0);
		gbc_txtKoujibangou.gridx = 1;
		gbc_txtKoujibangou.gridy = 1;
		bukkenPanel.add(txtKoujibangou, gbc_txtKoujibangou);

		JLabel lblNewLabel_6 = new JLabel("施主名：");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 2;
		bukkenPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		txtBukkenMei = new JLabel("New label");
		GridBagConstraints gbc_txtBukkenMei = new GridBagConstraints();
		gbc_txtBukkenMei.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBukkenMei.insets = new Insets(0, 0, 5, 0);
		gbc_txtBukkenMei.gridx = 1;
		gbc_txtBukkenMei.gridy = 2;
		bukkenPanel.add(txtBukkenMei, gbc_txtBukkenMei);

		JLabel lblNewLabel_7 = new JLabel("支店：");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 3;
		bukkenPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		txtShiten = new JLabel("New label");
		GridBagConstraints gbc_txtShiten = new GridBagConstraints();
		gbc_txtShiten.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtShiten.insets = new Insets(0, 0, 5, 0);
		gbc_txtShiten.gridx = 1;
		gbc_txtShiten.gridy = 3;
		bukkenPanel.add(txtShiten, gbc_txtShiten);

		JLabel lblNewLabel_8 = new JLabel("商品タイプ：");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 4;
		bukkenPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		txtShouhinType = new JLabel("New label");
		GridBagConstraints gbc_txtShouhinType = new GridBagConstraints();
		gbc_txtShouhinType.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtShouhinType.insets = new Insets(0, 0, 5, 0);
		gbc_txtShouhinType.gridx = 1;
		gbc_txtShouhinType.gridy = 4;
		bukkenPanel.add(txtShouhinType, gbc_txtShouhinType);

		JLabel lblDepsf = new JLabel("DEPSF番号：");
		GridBagConstraints gbc_lblDepsf = new GridBagConstraints();
		gbc_lblDepsf.anchor = GridBagConstraints.WEST;
		gbc_lblDepsf.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepsf.gridx = 0;
		gbc_lblDepsf.gridy = 5;
		bukkenPanel.add(lblDepsf, gbc_lblDepsf);
		
		txtDEPSF = new JLabel("New label");
		GridBagConstraints gbc_txtDEPSF = new GridBagConstraints();
		gbc_txtDEPSF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDEPSF.insets = new Insets(0, 0, 5, 0);
		gbc_txtDEPSF.gridx = 1;
		gbc_txtDEPSF.gridy = 5;
		bukkenPanel.add(txtDEPSF, gbc_txtDEPSF);

		JLabel lblNewLabel_9 = new JLabel("作成者：");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 6;
		bukkenPanel.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		cbsakuSeiSha = new JComboBox();
		GridBagConstraints gbc_cbsakuSeiSha = new GridBagConstraints();
		gbc_cbsakuSeiSha.insets = new Insets(0, 0, 5, 0);
		gbc_cbsakuSeiSha.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbsakuSeiSha.gridx = 1;
		gbc_cbsakuSeiSha.gridy = 6;
		bukkenPanel.add(cbsakuSeiSha, gbc_cbsakuSeiSha);

		JLabel lblNewLabel_10 = new JLabel("作成状況：");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 0;
		gbc_lblNewLabel_10.gridy = 7;
		bukkenPanel.add(lblNewLabel_10, gbc_lblNewLabel_10);

		cbSakuseiJoukyou = new JComboBox();
		GridBagConstraints gbc_cbSakuseiJoukyou = new GridBagConstraints();
		gbc_cbSakuseiJoukyou.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSakuseiJoukyou.insets = new Insets(0, 0, 5, 0);
		gbc_cbSakuseiJoukyou.gridx = 1;
		gbc_cbSakuseiJoukyou.gridy = 7;
		bukkenPanel.add(cbSakuseiJoukyou, gbc_cbSakuseiJoukyou);

		JLabel lblNewLabel_11 = new JLabel("作成日：");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_11.gridx = 0;
		gbc_lblNewLabel_11.gridy = 8;
		bukkenPanel.add(lblNewLabel_11, gbc_lblNewLabel_11);

		dpkSakuseBi = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		GridBagConstraints gbc_dpkSakuseBi = new GridBagConstraints();
		gbc_dpkSakuseBi.fill = GridBagConstraints.HORIZONTAL;
		gbc_dpkSakuseBi.insets = new Insets(0, 0, 5, 0);
		gbc_dpkSakuseBi.gridx = 1;
		gbc_dpkSakuseBi.gridy = 8;
		bukkenPanel.add(dpkSakuseBi, gbc_dpkSakuseBi);

		JLabel lblNewLabel_14 = new JLabel("別注枚数：");
		GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
		gbc_lblNewLabel_14.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_14.gridx = 0;
		gbc_lblNewLabel_14.gridy = 9;
		bukkenPanel.add(lblNewLabel_14, gbc_lblNewLabel_14);

		spMaisuu = new JSpinner();
		spMaisuu.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		GridBagConstraints gbc_spMaisuu = new GridBagConstraints();
		gbc_spMaisuu.fill = GridBagConstraints.HORIZONTAL;
		gbc_spMaisuu.insets = new Insets(0, 0, 5, 0);
		gbc_spMaisuu.gridx = 1;
		gbc_spMaisuu.gridy = 9;
		bukkenPanel.add(spMaisuu, gbc_spMaisuu);

		JLabel lblNewLabel_12 = new JLabel("研修者：");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12.gridx = 0;
		gbc_lblNewLabel_12.gridy = 10;
		bukkenPanel.add(lblNewLabel_12, gbc_lblNewLabel_12);

		cbKenshuuSha = new JComboBox();
		GridBagConstraints gbc_cbKenshuuSha = new GridBagConstraints();
		gbc_cbKenshuuSha.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbKenshuuSha.insets = new Insets(0, 0, 5, 0);
		gbc_cbKenshuuSha.gridx = 1;
		gbc_cbKenshuuSha.gridy = 10;
		bukkenPanel.add(cbKenshuuSha, gbc_cbKenshuuSha);

		JLabel lblNewLabel_13 = new JLabel("検収状況：");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 0;
		gbc_lblNewLabel_13.gridy = 11;
		bukkenPanel.add(lblNewLabel_13, gbc_lblNewLabel_13);

		cbKenshuuJoukyou = new JComboBox();
		GridBagConstraints gbc_cbKenshuuJoukyou = new GridBagConstraints();
		gbc_cbKenshuuJoukyou.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbKenshuuJoukyou.insets = new Insets(0, 0, 5, 0);
		gbc_cbKenshuuJoukyou.gridx = 1;
		gbc_cbKenshuuJoukyou.gridy = 11;
		bukkenPanel.add(cbKenshuuJoukyou, gbc_cbKenshuuJoukyou);

		JLabel lblNewLabel_15 = new JLabel("ミス件数：");
		GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
		gbc_lblNewLabel_15.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_15.gridx = 0;
		gbc_lblNewLabel_15.gridy = 12;
		bukkenPanel.add(lblNewLabel_15, gbc_lblNewLabel_15);

		spMisu = new JSpinner();
		spMisu.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spMisu.getEditor().getComponent(0).setForeground(Color.RED);

		GridBagConstraints gbc_spMisu = new GridBagConstraints();
		gbc_spMisu.fill = GridBagConstraints.HORIZONTAL;
		gbc_spMisu.insets = new Insets(0, 0, 5, 0);
		gbc_spMisu.gridx = 1;
		gbc_spMisu.gridy = 12;
		bukkenPanel.add(spMisu, gbc_spMisu);

		JLabel lblNewLabel_16 = new JLabel("備考：");
		GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
		gbc_lblNewLabel_16.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_16.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_16.gridx = 0;
		gbc_lblNewLabel_16.gridy = 13;
		bukkenPanel.add(lblNewLabel_16, gbc_lblNewLabel_16);

		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 1;
		gbc_scrollPane_3.gridy = 13;
		bukkenPanel.add(scrollPane_3, gbc_scrollPane_3);

		txtBikou = new JTextArea();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_3.setViewportView(txtBikou);
		scrollPane_3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtBikou.setRows(3);
		txtBikou.setLineWrap(true);

	}

	public void showDialog(BecchuuDetailsDelegate delegate) {
		this.delegate = delegate;		
		this.getRootPane().registerKeyboardAction(e -> {
			this.dispose();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		this.setVisible(true);
	}


	/**
	 * Create the frame.
	 */
	public BecchuuDetailsForm(Becchuu becchuu) {
		setTitle("別注修正");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.becchuuToEdit = becchuu;
		createAndSetupGUI();
		loadBecchuuEmployeesCombobox();
		loadStatusCombobox();
		
		populateData();
		
		pack();
		setBounds(100, 100, 950, (int) this.getPreferredSize().getHeight());
	}
}