package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.btc.controllers.BecchuuKanriForm.BecchuuKanriForm;
import com.btc.supports.Config;
import com.btc.viewModel.BecchuuTableModel;
import com.btc.viewModel.BukkenTableModel;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JFrame currentFrame;
	private BecchuuKanriForm becchuuKanriForm;
	private BecchuuIraiForm becchuuIraiForm;
	
	private void openNewFrame(JFrame jFrame) {
		if (currentFrame != null) currentFrame.dispose();
		currentFrame = jFrame;
		this.setContentPane(jFrame.getContentPane());
		this.setSize(jFrame.getSize());
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Config.setLookAndField();
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
		setBounds(100, 100, 450, 300);
		createAndSetUpGUI();
		
	}
	
	private void createAndSetUpGUI() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("ファイル");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("更新");
		
		JMenuItem mnBecchuuKanri = new JMenuItem("別注管理");
		mnBecchuuKanri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (becchuuKanriForm == null) becchuuKanriForm = new BecchuuKanriForm();
				openNewFrame(becchuuKanriForm);
			}
		});
		mnFile.add(mnBecchuuKanri);
		
		
		JMenuItem mnBecchuuTools = new JMenuItem("別注依頼ツール");
		mnBecchuuTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (becchuuIraiForm == null) becchuuIraiForm = new BecchuuIraiForm();
				openNewFrame(becchuuIraiForm);
			}
		});
		
		mnFile.add(mnBecchuuTools);
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
	}

}
