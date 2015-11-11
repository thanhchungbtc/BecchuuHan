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

public class BecchuuDetailsForm extends JDialog {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtName;
	private JComboBox cbType;	
	private JLabel lblNewLabel;
	private JTextField txtDEPSF;
	private JTextField txtShiten;
	private JDatePickerImpl dpkNouki;
	
	public BecchuuDetailsDelegate delegate;

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
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		contentPane.add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JLabel lblNewLabel_1 = new JLabel("商品タイプ：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		mainPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		cbType = new JComboBox();
		GridBagConstraints gbc_cbType = new GridBagConstraints();
		gbc_cbType.insets = new Insets(0, 0, 5, 0);
		gbc_cbType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbType.gridx = 1;
		gbc_cbType.gridy = 0;
		mainPanel.add(cbType, gbc_cbType);
		
		JLabel lblNewLabel_2 = new JLabel("\u5DE5\u4E8B\u756A\u53F7\uFF1A");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		mainPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		GridBagConstraints gbc_txtID = new GridBagConstraints();
		gbc_txtID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtID.insets = new Insets(0, 0, 5, 0);
		gbc_txtID.gridx = 1;
		gbc_txtID.gridy = 1;
		mainPanel.add(txtID, gbc_txtID);
		
		JLabel lblUserName = new JLabel("\u65BD\u4E3B\u540D\uFF1A");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 2;
		mainPanel.add(lblUserName, gbc_lblUserName);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 2;
		mainPanel.add(txtName, gbc_txtName);
		
		JLabel lblPassword = new JLabel("DEPSF\uFF1A");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		mainPanel.add(lblPassword, gbc_lblPassword);
		
		txtDEPSF = new JTextField();
		GridBagConstraints gbc_txtDEPSF = new GridBagConstraints();
		gbc_txtDEPSF.anchor = GridBagConstraints.NORTH;
		gbc_txtDEPSF.insets = new Insets(0, 0, 5, 0);
		gbc_txtDEPSF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDEPSF.gridx = 1;
		gbc_txtDEPSF.gridy = 3;
		mainPanel.add(txtDEPSF, gbc_txtDEPSF);
		txtDEPSF.setColumns(10);
		
		JLabel lblRepeat = new JLabel("\u652F\u5E97\uFF1A");
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.WEST;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 4;
		mainPanel.add(lblRepeat, gbc_lblRepeat);
		
		txtShiten = new JTextField();
		GridBagConstraints gbc_txtShiten = new GridBagConstraints();
		gbc_txtShiten.anchor = GridBagConstraints.NORTH;
		gbc_txtShiten.insets = new Insets(0, 0, 5, 0);
		gbc_txtShiten.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtShiten.gridx = 1;
		gbc_txtShiten.gridy = 4;
		mainPanel.add(txtShiten, gbc_txtShiten);
		txtShiten.setColumns(10);
		
		JLabel lblUrl = new JLabel("納期：");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.anchor = GridBagConstraints.WEST;
		gbc_lblUrl.insets = new Insets(0, 0, 0, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 5;
		mainPanel.add(lblUrl, gbc_lblUrl);
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		dpkNouki = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		 
		
		GridBagConstraints gbc_dpkNouki = new GridBagConstraints();
		gbc_dpkNouki.fill = GridBagConstraints.HORIZONTAL;
		gbc_dpkNouki.gridx = 1;
		gbc_dpkNouki.gridy = 5;
		mainPanel.add(dpkNouki, gbc_dpkNouki);
		
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
		setBounds(100, 100, 450, (int) this.getPreferredSize().getHeight());
	}

}
interface BecchuuDetailsDelegate {
	public void submitData(Becchuu becchuu, boolean insert);
}