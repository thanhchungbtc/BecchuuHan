package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.supports.DateLabelFormatter;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class BecchuuDetailsForm extends JDialog {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtMotozuKigou;
	private JDatePickerImpl dpkNouki;
	
	public BecchuuDetailsDelegate delegate;
	private JTextField txtBecchuuKigou;
	private JTextField txtIraiSha;
	private JTextField txtIraibi;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextArea txtBecchuuParameter;
	private JTextArea txtBecchuuNaiyou;
	private JTextArea txtMotozuParameter;

	// BEGIN handle Events
		private void btnOKActionPerformed(ActionEvent event) {
			
			this.dispose();
		}
		
		// END handle Events
	
	private void createAndSetupGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		headerPanel.setBackground(new Color(30, 144, 255));
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
		becchuuPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		becchuuPanel.setBorder(BorderFactory.createTitledBorder("別注情報"));
		GridBagLayout gbl_becchuuPanel = new GridBagLayout();
		gbl_becchuuPanel.columnWidths = new int[]{0, 0, 0};
		gbl_becchuuPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_becchuuPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_becchuuPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		becchuuPanel.setLayout(gbl_becchuuPanel);
		
		JLabel lblNewLabel_1 = new JLabel("別注記号：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		becchuuPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtBecchuuKigou = new JTextField();
		GridBagConstraints gbc_txtBecchuuKigou = new GridBagConstraints();
		gbc_txtBecchuuKigou.insets = new Insets(0, 0, 5, 0);
		gbc_txtBecchuuKigou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBecchuuKigou.gridx = 1;
		gbc_txtBecchuuKigou.gridy = 0;
		becchuuPanel.add(txtBecchuuKigou, gbc_txtBecchuuKigou);
		txtBecchuuKigou.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("別注パラメータ：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		becchuuPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtBecchuuParameter = new JTextArea();
		txtBecchuuParameter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtBecchuuParameter.setLineWrap(true);
		txtBecchuuParameter.setRows(5);
		GridBagConstraints gbc_txtBecchuuParameter = new GridBagConstraints();
		gbc_txtBecchuuParameter.insets = new Insets(0, 0, 5, 0);
		gbc_txtBecchuuParameter.fill = GridBagConstraints.BOTH;
		gbc_txtBecchuuParameter.gridx = 1;
		gbc_txtBecchuuParameter.gridy = 1;
		becchuuPanel.add(txtBecchuuParameter, gbc_txtBecchuuParameter);
		
		JLabel lblUserName = new JLabel("別注内容：");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 2;
		becchuuPanel.add(lblUserName, gbc_lblUserName);
		
		txtBecchuuNaiyou = new JTextArea();
		txtBecchuuNaiyou.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtBecchuuNaiyou.setRows(5);
		GridBagConstraints gbc_txtBecchuuNaiyou = new GridBagConstraints();
		gbc_txtBecchuuNaiyou.insets = new Insets(0, 0, 5, 0);
		gbc_txtBecchuuNaiyou.fill = GridBagConstraints.BOTH;
		gbc_txtBecchuuNaiyou.gridx = 1;
		gbc_txtBecchuuNaiyou.gridy = 2;
		becchuuPanel.add(txtBecchuuNaiyou, gbc_txtBecchuuNaiyou);
		
		JLabel lblPassword = new JLabel("元図記号：");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		becchuuPanel.add(lblPassword, gbc_lblPassword);
		
		txtMotozuKigou = new JTextField();
		GridBagConstraints gbc_txtMotozuKigou = new GridBagConstraints();
		gbc_txtMotozuKigou.anchor = GridBagConstraints.NORTH;
		gbc_txtMotozuKigou.insets = new Insets(0, 0, 5, 0);
		gbc_txtMotozuKigou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMotozuKigou.gridx = 1;
		gbc_txtMotozuKigou.gridy = 3;
		becchuuPanel.add(txtMotozuKigou, gbc_txtMotozuKigou);
		txtMotozuKigou.setColumns(10);
		
		JLabel lblRepeat = new JLabel("元図パラメータ：");
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.WEST;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 4;
		becchuuPanel.add(lblRepeat, gbc_lblRepeat);
		
		txtMotozuParameter = new JTextArea();
		txtMotozuParameter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		txtMotozuParameter.setRows(5);
		GridBagConstraints gbc_txtMotozuParameter = new GridBagConstraints();
		gbc_txtMotozuParameter.insets = new Insets(0, 0, 5, 0);
		gbc_txtMotozuParameter.fill = GridBagConstraints.BOTH;
		gbc_txtMotozuParameter.gridx = 1;
		gbc_txtMotozuParameter.gridy = 4;
		becchuuPanel.add(txtMotozuParameter, gbc_txtMotozuParameter);
		
		JLabel lblNewLabel_4 = new JLabel("依頼者：");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 5;
		becchuuPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		txtIraiSha = new JTextField();
		GridBagConstraints gbc_txtIraiSha = new GridBagConstraints();
		gbc_txtIraiSha.insets = new Insets(0, 0, 5, 0);
		gbc_txtIraiSha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIraiSha.gridx = 1;
		gbc_txtIraiSha.gridy = 5;
		becchuuPanel.add(txtIraiSha, gbc_txtIraiSha);
		txtIraiSha.setColumns(10);
		
		JLabel label = new JLabel("依頼日：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 6;
		becchuuPanel.add(label, gbc_label);
		
		txtIraibi = new JTextField();
		txtIraibi.setEditable(false);
		GridBagConstraints gbc_txtIraibi = new GridBagConstraints();
		gbc_txtIraibi.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIraibi.gridx = 1;
		gbc_txtIraibi.gridy = 6;
		becchuuPanel.add(txtIraibi, gbc_txtIraibi);
		txtIraibi.setColumns(10);
		
		
		JPanel bukkenPanel = new JPanel();
		bukkenPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		bukkenPanel.setBorder(BorderFactory.createTitledBorder("管理情報"));
		maincontentPanel.add(bukkenPanel);
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
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		bukkenPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("工事番号：");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 1;
		bukkenPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		bukkenPanel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("施主名：");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 2;
		bukkenPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		bukkenPanel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("支店：");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 3;
		bukkenPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 3;
		bukkenPanel.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("商品タイプ：");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 4;
		bukkenPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 4;
		bukkenPanel.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblDepsf = new JLabel("DEPSF番号：");
		GridBagConstraints gbc_lblDepsf = new GridBagConstraints();
		gbc_lblDepsf.anchor = GridBagConstraints.WEST;
		gbc_lblDepsf.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepsf.gridx = 0;
		gbc_lblDepsf.gridy = 5;
		bukkenPanel.add(lblDepsf, gbc_lblDepsf);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 5;
		bukkenPanel.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("作成者：");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 6;
		bukkenPanel.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		textField_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 6;
		bukkenPanel.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("作成状況：");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 0;
		gbc_lblNewLabel_10.gridy = 7;
		bukkenPanel.add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 7;
		bukkenPanel.add(comboBox, gbc_comboBox);
		
		JLabel lblNewLabel_11 = new JLabel("作成日：");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_11.gridx = 0;
		gbc_lblNewLabel_11.gridy = 8;
		bukkenPanel.add(lblNewLabel_11, gbc_lblNewLabel_11);
	
		dpkNouki = new JDatePickerImpl(datePanel, new DateLabelFormatter());			
		GridBagConstraints gbc_dpkNouki = new GridBagConstraints();
		gbc_dpkNouki.insets = new Insets(0, 0, 5, 0);
		gbc_dpkNouki.fill = GridBagConstraints.HORIZONTAL;
		gbc_dpkNouki.gridx = 1;
		gbc_dpkNouki.gridy = 8;
		bukkenPanel.add(dpkNouki, gbc_dpkNouki);
		
		JLabel lblNewLabel_14 = new JLabel("別注枚数：");
		GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
		gbc_lblNewLabel_14.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_14.gridx = 0;
		gbc_lblNewLabel_14.gridy = 9;
		bukkenPanel.add(lblNewLabel_14, gbc_lblNewLabel_14);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 9;
		bukkenPanel.add(spinner, gbc_spinner);
		
		JLabel lblNewLabel_12 = new JLabel("研修者：");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12.gridx = 0;
		gbc_lblNewLabel_12.gridy = 10;
		bukkenPanel.add(lblNewLabel_12, gbc_lblNewLabel_12);
		
		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 10;
		bukkenPanel.add(comboBox_1, gbc_comboBox_1);
		
		JLabel lblNewLabel_13 = new JLabel("検収状況：");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 0;
		gbc_lblNewLabel_13.gridy = 11;
		bukkenPanel.add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		JComboBox comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 11;
		bukkenPanel.add(comboBox_2, gbc_comboBox_2);
		
		JLabel lblNewLabel_15 = new JLabel("ミス件数：");
		GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
		gbc_lblNewLabel_15.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_15.gridx = 0;
		gbc_lblNewLabel_15.gridy = 12;
		bukkenPanel.add(lblNewLabel_15, gbc_lblNewLabel_15);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner_1.getEditor().getComponent(0).setForeground(Color.RED);
		
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 12;
		bukkenPanel.add(spinner_1, gbc_spinner_1);
		
		JLabel lblNewLabel_16 = new JLabel("備考：");
		GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
		gbc_lblNewLabel_16.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_16.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_16.gridx = 0;
		gbc_lblNewLabel_16.gridy = 13;
		bukkenPanel.add(lblNewLabel_16, gbc_lblNewLabel_16);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		textArea_3.setRows(3);
		textArea_3.setLineWrap(true);
		GridBagConstraints gbc_textArea_3 = new GridBagConstraints();
		gbc_textArea_3.fill = GridBagConstraints.BOTH;
		gbc_textArea_3.gridx = 1;
		gbc_textArea_3.gridy = 13;
		bukkenPanel.add(textArea_3, gbc_textArea_3);
		
	}

	public void showDialog(BecchuuDetailsDelegate delegate) {
		this.delegate = delegate;		
		this.getRootPane().registerKeyboardAction(e -> {
		    this.dispose();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		this.setVisible(true);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BecchuuDetailsForm frame = new BecchuuDetailsForm();
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
	public BecchuuDetailsForm() {
		setTitle("別注修正");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		createAndSetupGUI();
		
		pack();
		setBounds(100, 100, 950, (int) this.getPreferredSize().getHeight());
	}
}
interface BecchuuDetailsDelegate {
	public void submitData(Becchuu becchuu, boolean insert);
}