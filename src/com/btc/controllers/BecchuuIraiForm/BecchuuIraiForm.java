package com.btc.controllers.BecchuuIraiForm;

import com.btc.DAL.ConnectionUtils;
import com.btc.controllers.BecchuuDetailsForm.BecchuuDetailsDelegate;
import com.btc.controllers.DialogHelpers;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.Date;

public class BecchuuIraiForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtMotozuKigou;
	private JDatePickerImpl dpkNouki;

	public BecchuuDetailsDelegate delegate;
	private JTextField txtBecchuuKigou;
	private JTextField txtIraiSha;

	private JTextArea txtBecchuuParameter;
	private JTextArea txtBecchuuNaiyou;
	private JTextArea txtMotozuParameter;
	private JTextField txtKoujibangou;
	private JLabel txtBukkenJouhou;

	// bukken must set before irai
	private Bukken bukken; 
	private JLabel txtIraibi;
	private boolean validDataBeforeSubmit() {
		if (txtBecchuuKigou.getText().trim().equals("")) {
			DialogHelpers.showAlert("通知", "別注記号入力する必要があります！");
			txtBecchuuKigou.requestFocus();
			return false;
		}
		if (txtKoujibangou.getText().trim().equals("")) {
			DialogHelpers.showAlert("通知", "工事番号入力する必要があります！");
			txtKoujibangou.requestFocus();
			return false;
		}
		if (txtBecchuuNaiyou.getText().trim().equals("")) {
			DialogHelpers.showAlert("通知", "別注内容入力する必要があります！");
			txtBecchuuNaiyou.requestFocus();
			return false;
		}
		if (txtIraiSha.getText().trim().equals("")) {
			DialogHelpers.showAlert("通知", "依頼者入力する必要があります！");
			txtIraiSha.requestFocus();
			return false;
		}
		if (bukken == null) {
			DialogHelpers.showAlert("通知", "存在しない工事番号です！");
			txtKoujibangou.requestFocus();
			txtKoujibangou.setSelectionStart(0);
			txtKoujibangou.setSelectionEnd(txtKoujibangou.getText().length());
			return false;
		}
		return true;
	}

	// BEGIN handle Events
	private void btnOKActionPerformed(ActionEvent event) {
		if (!validDataBeforeSubmit()) return;

		Becchuu becchuu = new Becchuu();		
		becchuu.setBecchuuKigou(txtBecchuuKigou.getText().trim());
		becchuu.setBecchuuParameter(txtBecchuuParameter.getText().trim());
		becchuu.setBecchuuNaiyou(txtBecchuuNaiyou.getText().trim());
		becchuu.setMotozuKigou(txtMotozuKigou.getText().trim());
		becchuu.setMotozuParameter(txtMotozuParameter.getText().trim());
		becchuu.setKoujibangou(txtKoujibangou.getText().trim());
		becchuu.setIraiShaID(txtIraiSha.getText().trim());
		becchuu.setIraibi(new Date());		
		
		try {
			BecchuuRepository.Instance().insert(becchuu);
			DialogHelpers.showAlert("成功", "別注依頼を送信しました！");
			this.resetAllField();
		} catch (SQLException e) {
			DialogHelpers.showError("エラー", "依頼失敗しました！");
			e.printStackTrace();
		}
	}

	// END handle Events

	private void resetAllField() {
		txtBecchuuKigou.setText("");
		txtBecchuuParameter.setText("");
		txtBecchuuNaiyou.setText("");
		txtMotozuKigou.setText("");
		txtMotozuParameter.setText("");		
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Config.setLookAndField();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BecchuuIraiForm frame = new BecchuuIraiForm();
					frame.setVisible(true);
					ConnectionUtils.getConnection().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void createAndSetupGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel headerPanel = new JPanel();
		Border margin = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		Border horiLine = new MatteBorder(0, 0, 1, 0, Color.GRAY);
		headerPanel.setBorder(new CompoundBorder(margin, horiLine));
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		contentPane.add(headerPanel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("別注依頼：");
		lblNewLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		headerPanel.add(lblNewLabel);

		JPanel becchuuPanel = new JPanel();
		contentPane.add(becchuuPanel);
		becchuuPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		GridBagLayout gbl_becchuuPanel = new GridBagLayout();
		gbl_becchuuPanel.columnWidths = new int[]{0, 0, 0};
		gbl_becchuuPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_becchuuPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_becchuuPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		becchuuPanel.setLayout(gbl_becchuuPanel);

		JLabel lblNewLabel_1 = new JLabel("別注記号（＊）：");
		lblNewLabel_1.setForeground(Color.RED);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		becchuuPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtBecchuuKigou = new JTextField();
		GridBagConstraints gbc_txtBecchuuKigou = new GridBagConstraints();
		gbc_txtBecchuuKigou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBecchuuKigou.gridx = 1;
		gbc_txtBecchuuKigou.gridy = 0;
		becchuuPanel.add(txtBecchuuKigou, gbc_txtBecchuuKigou);
		txtBecchuuKigou.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("別注パラメータ：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		becchuuPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		becchuuPanel.add(scrollPane_1, gbc_scrollPane_1);

		
		txtBecchuuParameter = new JTextArea();
		scrollPane_1.setViewportView(txtBecchuuParameter);

      //txtBecchuuParameter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtBecchuuParameter.setLineWrap(true);
		txtBecchuuParameter.setRows(4);

		JLabel lblUserName = new JLabel("別注内容（＊）：");
		lblUserName.setForeground(Color.RED);
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(0, 0, 0, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 2;
		becchuuPanel.add(lblUserName, gbc_lblUserName);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		becchuuPanel.add(scrollPane, gbc_scrollPane);

		txtBecchuuNaiyou = new JTextArea();
		scrollPane.setViewportView(txtBecchuuNaiyou);
      // txtBecchuuNaiyou.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtBecchuuNaiyou.setRows(4);

		JLabel lblPassword = new JLabel("元図記号：");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		becchuuPanel.add(lblPassword, gbc_lblPassword);

		txtMotozuKigou = new JTextField();
		GridBagConstraints gbc_txtMotozuKigou = new GridBagConstraints();
		gbc_txtMotozuKigou.anchor = GridBagConstraints.NORTH;
		gbc_txtMotozuKigou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMotozuKigou.gridx = 1;
		gbc_txtMotozuKigou.gridy = 3;
		becchuuPanel.add(txtMotozuKigou, gbc_txtMotozuKigou);
		txtMotozuKigou.setColumns(10);

		JLabel lblRepeat = new JLabel("元図パラメータ：");
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.WEST;
		gbc_lblRepeat.insets = new Insets(0, 0, 0, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 4;
		becchuuPanel.add(lblRepeat, gbc_lblRepeat);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 4;
		becchuuPanel.add(scrollPane_2, gbc_scrollPane_2);

		txtMotozuParameter = new JTextArea();
		scrollPane_2.setViewportView(txtMotozuParameter);
      // txtMotozuParameter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtMotozuParameter.setRows(4);

		JLabel lblNewLabel_3 = new JLabel("工事番号（＊）：");
		lblNewLabel_3.setForeground(Color.RED);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 5;
		becchuuPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtKoujibangou = new JTextField();
		txtKoujibangou.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtKoujibangou.getText().trim().equals("")) { 
					bukken = null;
					txtBukkenJouhou.setText("");
					return;
				}
				bukken = BukkenRepository.Instance().contains(txtKoujibangou.getText().trim());
				
				if (bukken != null){					
					txtBukkenJouhou.setText(bukken.getName());
				} else {
					txtBukkenJouhou.setText("");
				}
			}
		});
		GridBagConstraints gbc_txtKoujibangou = new GridBagConstraints();
		gbc_txtKoujibangou.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKoujibangou.gridx = 1;
		gbc_txtKoujibangou.gridy = 5;
		becchuuPanel.add(txtKoujibangou, gbc_txtKoujibangou);
		txtKoujibangou.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("依頼者（＊）：");
		lblNewLabel_4.setForeground(Color.RED);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 6;
		becchuuPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		txtIraiSha = new JTextField();
		GridBagConstraints gbc_txtIraiSha = new GridBagConstraints();
		gbc_txtIraiSha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIraiSha.gridx = 1;
		gbc_txtIraiSha.gridy = 6;
		becchuuPanel.add(txtIraiSha, gbc_txtIraiSha);
		txtIraiSha.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("物件情報：");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(5, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 7;
		becchuuPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		txtBukkenJouhou = new JLabel();
		GridBagConstraints gbc_txtBukkenJouhou = new GridBagConstraints();
		gbc_txtBukkenJouhou.insets = new Insets(5, 0, 5, 0);
		gbc_txtBukkenJouhou.fill = GridBagConstraints.BOTH;
		gbc_txtBukkenJouhou.gridx = 1;
		gbc_txtBukkenJouhou.gridy = 7;
		becchuuPanel.add(txtBukkenJouhou, gbc_txtBukkenJouhou);

		JLabel label = new JLabel("依頼日：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(5, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 8;
		becchuuPanel.add(label, gbc_label);
		
		txtIraibi = new JLabel("");
		GridBagConstraints gbc_txtIraibi = new GridBagConstraints();
		gbc_txtIraibi.insets = new Insets(5, 0, 5, 0);
		gbc_txtIraibi.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIraibi.gridx = 1;
		gbc_txtIraibi.gridy = 8;
		becchuuPanel.add(txtIraibi, gbc_txtIraibi);

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

		JButton btnOK = new JButton("依頼実行");		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOKActionPerformed(e);
			}
		});
		footerPanel.add(btnOK);		
	}



	/**
	 * Create the frame.
	 */
	public BecchuuIraiForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("別注依頼");	

		createAndSetupGUI();
		txtIraibi.setText(Helpers.stringFromDate(new Date()));
		pack();
		setBounds(100, 100, 650, (int) this.getPreferredSize().getHeight());
	}

}
