package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;

public class BukkenDetailsForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextField txtUserName;
	private JTextField txtURL;
	
	private String password;
	private boolean viewing = false;
	// must set
	public List<Bukken> groups;
	public BecchuuRepository repositoty;
	public Becchuu account;
	public JComboBox cbGroup;
	private JPasswordField txtPassword;
	private JPasswordField txtRepeatPassword;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BukkenDetailsForm frame = new BukkenDetailsForm();
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
	public BukkenDetailsForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		createAndSetupGUI();
		pack();
		setBounds(100, 100, 450, (int) this.getPreferredSize().getHeight());
	}

	

	private void createAndSetupGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		headerPanel.setBackground(new Color(72, 209, 204));
		contentPane.add(headerPanel, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("\u7269\u4EF6\u7DE8\u96C6");
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
		
		JLabel lblNewLabel_1 = new JLabel("\u5546\u54C1\u30BF\u30A4\u30D7");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		mainPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		cbGroup = new JComboBox();
		GridBagConstraints gbc_cbGroup = new GridBagConstraints();
		gbc_cbGroup.insets = new Insets(0, 0, 5, 0);
		gbc_cbGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGroup.gridx = 1;
		gbc_cbGroup.gridy = 0;
		mainPanel.add(cbGroup, gbc_cbGroup);
		
		JLabel lblNewLabel_2 = new JLabel("\u5DE5\u4E8B\u756A\u53F7\uFF1A");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		mainPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtTitle = new JTextField();
		txtTitle.setColumns(10);
		GridBagConstraints gbc_txtTitle = new GridBagConstraints();
		gbc_txtTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitle.insets = new Insets(0, 0, 5, 0);
		gbc_txtTitle.gridx = 1;
		gbc_txtTitle.gridy = 1;
		mainPanel.add(txtTitle, gbc_txtTitle);
		
		JLabel lblUserName = new JLabel("\u65BD\u4E3B\u540D\uFF1A");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 2;
		mainPanel.add(lblUserName, gbc_lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		GridBagConstraints gbc_txtUserName = new GridBagConstraints();
		gbc_txtUserName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUserName.insets = new Insets(0, 0, 5, 0);
		gbc_txtUserName.gridx = 1;
		gbc_txtUserName.gridy = 2;
		mainPanel.add(txtUserName, gbc_txtUserName);
		
		JLabel lblPassword = new JLabel("DEPSF\uFF1A");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		mainPanel.add(lblPassword, gbc_lblPassword);
		
		txtPassword = new JPasswordField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 3;
		mainPanel.add(txtPassword, gbc_txtPassword);
		
		JLabel lblRepeat = new JLabel("\u652F\u5E97\uFF1A");
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.WEST;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 4;
		mainPanel.add(lblRepeat, gbc_lblRepeat);
		
		
		txtRepeatPassword = new JPasswordField();
		GridBagConstraints gbc_txtRepeatPassword = new GridBagConstraints();
		gbc_txtRepeatPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtRepeatPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepeatPassword.gridx = 1;
		gbc_txtRepeatPassword.gridy = 4;
		mainPanel.add(txtRepeatPassword, gbc_txtRepeatPassword);
		
		JLabel lblUrl = new JLabel("\u7D0D\u671F");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.anchor = GridBagConstraints.WEST;
		gbc_lblUrl.insets = new Insets(0, 0, 0, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 5;
		mainPanel.add(lblUrl, gbc_lblUrl);
		
		txtURL = new JTextField();
		txtURL.setColumns(10);
		GridBagConstraints gbc_txtURL = new GridBagConstraints();
		gbc_txtURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtURL.gridx = 1;
		gbc_txtURL.gridy = 5;
		mainPanel.add(txtURL, gbc_txtURL);
		
		JPanel footerPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) footerPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		footerPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		footerPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
		contentPane.add(footerPanel, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		footerPanel.add(btnCancel);
		
		JButton btnOK = new JButton("OK");		
		footerPanel.add(btnOK);
		
		
	}

}
