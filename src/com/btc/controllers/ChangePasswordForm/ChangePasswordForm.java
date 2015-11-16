package com.btc.controllers.ChangePasswordForm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.border.MatteBorder;

import com.btc.controllers.DialogHelpers;
import com.btc.repositoty.EmployeeRepository;
import com.btc.supports.Config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePasswordForm extends JDialog {

	private JPanel contentPane;
	private JPasswordField txtOldPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtRepeatPassword;

	
	private void btnOKActionPerformed(ActionEvent event) {
		// validate before update
		if (txtOldPassword.getText().trim().equals("") ||
			txtNewPassword.getText().trim().equals("") ||
			txtRepeatPassword.getText().trim().equals("")) {
				DialogHelpers.showError("エラー", "入力してください。");
				return;
			}
		if (!txtOldPassword.getText().trim().equals(Config.Password)) {
			DialogHelpers.showError("エラー", "パスワード間違いました。");
			return;
		}
		if (!txtNewPassword.getText().trim().equals(txtNewPassword.getText().trim())) {
			DialogHelpers.showError("エラー", "パスワードが正しく入力されていません。");
			return;
		}

		EmployeeRepository.Instance().updatePassword(Config.UserID, txtNewPassword.getText());
		DialogHelpers.showAlert("成功", "パスワード変更しました。");
		this.dispose();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePasswordForm frame = new ChangePasswordForm();
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
	public ChangePasswordForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("パスワード変更");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 0, 0, 0));
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel label = new JLabel("現在パスワード：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);
		
		txtOldPassword = new JPasswordField();
		GridBagConstraints gbc_txtOldPassword = new GridBagConstraints();
		gbc_txtOldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtOldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOldPassword.gridx = 1;
		gbc_txtOldPassword.gridy = 0;
		panel_1.add(txtOldPassword, gbc_txtOldPassword);
		
		JLabel lblNewLabel_1 = new JLabel("新しいパスワード：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtNewPassword = new JPasswordField();
		GridBagConstraints gbc_txtNewPassword = new GridBagConstraints();
		gbc_txtNewPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtNewPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNewPassword.gridx = 1;
		gbc_txtNewPassword.gridy = 1;
		panel_1.add(txtNewPassword, gbc_txtNewPassword);
		
		JLabel label_1 = new JLabel("パスワード確認：");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		panel_1.add(label_1, gbc_label_1);
		
		txtRepeatPassword = new JPasswordField();
		GridBagConstraints gbc_txtRepeatPassword = new GridBagConstraints();
		gbc_txtRepeatPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepeatPassword.gridx = 1;
		gbc_txtRepeatPassword.gridy = 2;
		panel_1.add(txtRepeatPassword, gbc_txtRepeatPassword);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("更新");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOKActionPerformed(e);
			}
		});
		panel_2.add(btnNewButton);
		
		pack();
		setSize(new Dimension(300, getHeight()));
		setLocationRelativeTo(getOwner());
	}

}
