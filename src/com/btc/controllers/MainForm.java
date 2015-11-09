package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTable bukkenTable;
	private JTable becchuuTable;
	private JLabel lblWelcome;

	private BecchuuRepository becchuuRepository;
	private BukkenRepository bukkenRepository;

	private DefaultTableModel accountTableModel;

	
	private void createAndSetupGUI(){
		System.out.println("error");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("\u30D5\u30A1\u30A4\u30EB");
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
		mainSplitPane.setDividerLocation(250);

		contentPane.add(mainSplitPane, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(new Color(255, 255, 255));
		mainSplitPane.setLeftComponent(leftPanel);

		bukkenTable = new JTable();
		bukkenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		leftPanel.setLayout(new BorderLayout(0, 0));


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bukkenTable);
		leftPanel.add(scrollPane);	

		JPanel leftButtonPanel = new JPanel();
		leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);
		leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

		JButton btnAddGroup = new JButton();
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BukkenDetailsForm form = new BukkenDetailsForm();
				form.setVisible(true);
			}
		});
	
		btnAddGroup.setIcon(new ImageIcon(MainForm.class.getResource("/resources/icon/icon_plus.png")));
		leftButtonPanel.add(btnAddGroup);

		JButton btnDeleteGroup = new JButton("");
		btnDeleteGroup.setIcon(new ImageIcon(MainForm.class.getResource("/resources/icon/icon_trash.png")));
		
		leftButtonPanel.add(btnDeleteGroup);


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
		rightButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

		JButton btnAddAccount = new JButton("Add");
	
		rightButtonPanel.add(btnAddAccount);

		JButton btnDeleteAccount = new JButton("Delete");
		
		rightButtonPanel.add(btnDeleteAccount);

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(null);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblWelcome = new JLabel("Welcome");
		statusPanel.add(lblWelcome);

	}

	private void initializeData() {
		becchuuRepository = new BecchuuRepository(0);
		bukkenRepository = new BukkenRepository();
	}

	private void setupTable() {

		initializeData();
		bukkenTable.setModel(new BukkenTableModel(this.bukkenRepository));
		bukkenTable.setRowHeight(25);
	
		becchuuTable.setRowHeight(25);	


	}


	// handle events
	
	// end handle events

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
		setupTable();

		pack();
	}

}
