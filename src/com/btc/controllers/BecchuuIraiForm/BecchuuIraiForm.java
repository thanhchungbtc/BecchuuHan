package com.btc.controllers.BecchuuIraiForm;

import com.btc.DAL.ConnectionUtils;
import com.btc.controllers.BecchuuDetailsForm.BecchuuDetailsDelegate;
import com.btc.controllers.BukkenKanriForm.BukkenKanriForm;
import com.btc.controllers.ChangePasswordForm.ChangePasswordForm;
import com.btc.controllers.DialogHelpers;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class BecchuuIraiForm extends JFrame {

   private JPanel contentPane;
   private JTextField txtMotozuKigou;

   public BecchuuDetailsDelegate delegate;
   private JTextField txtBecchuuKigou;
   private JTextField txtIraiSha;

   private JTextArea txtBecchuuParameter;
   private JTextArea txtBecchuuNaiyou;
   private JTextArea txtMotozuParameter;
   private JTextField txtKoujibangou;
   private JLabel txtBukkenJouhou;

   private BukkenKanriForm bukkenKanriForm;
   private SwingWorker<Boolean, Void> swingWorker;
   // bukken must set before irai
   private Bukken bukken;
   private JLabel txtIraibi;
   private JLabel iconLabel;
   private JButton btnOK;
   private JButton btnCancel;
   private JLabel lblSendingMail;
   private JButton btnKanriGamen;

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

      becchuu.setIraiShaID(Config.UserID);
      becchuu.setIraibi(new Date());


      try {
         BecchuuRepository.Instance().insert(becchuu);
         lblSendingMail.setVisible(true);
         iconLabel.setVisible(true);
         getContentPane().setEnabled(false);
         swingWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
               sendMail();
               return true;
            }

            @Override
            protected void done() {
               getContentPane().setEnabled(true);
               iconLabel.setVisible(false);
               lblSendingMail.setVisible(false);
               DialogHelpers.showAlert("成功", "別注依頼を送信しました！");
               resetAllField();

            }
         };
         swingWorker.execute();
      } catch (SQLException e) {
         DialogHelpers.showError("エラー", "依頼失敗しました！");
         e.printStackTrace();
      }
   }


   // END handle Events

   private void sendMail() throws MessagingException {
      //      String toAddress = "kimhongngoc@daiwahouse.vn; phonguyetanh@daiwahouse.vn;buithanhchung@daiwahouse.vn";
      String toAddress = "thanhchungbtc@gmail.com";
      String subject = "別注依頼：" + txtKoujibangou.getText() + " - " + txtBukkenJouhou.getText();
      String message = "別注依頼を入力しました。" +
            "<br />別注記号：" + txtBecchuuKigou.getText() + "" +
            "<br />別注内容：" + txtBecchuuNaiyou.getText() + "" +
            "<br />依頼者：" + txtIraiSha.getText() + "" +
            "<br />依頼日： " + txtIraibi.getText();
      Properties defaultProps = new Properties();
      defaultProps.setProperty("mail.smtp.host", "smtp.gmail.com");
      defaultProps.setProperty("mail.smtp.port", "587");
      defaultProps.setProperty("mail.user", "becchuuhan@gmail.com");
      defaultProps.setProperty("mail.password", "daiwahouse");
      defaultProps.setProperty("mail.smtp.starttls.enable", "true");
      defaultProps.setProperty("mail.smtp.auth", "true");

      Properties configProperties = new Properties(defaultProps);

      final String userName = configProperties.getProperty("mail.user");
      final String password = configProperties.getProperty("mail.password");

      // creates a new session with an authenticator
      Authenticator auth = new Authenticator() {
         public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
         }
      };
      Session session = Session.getInstance(configProperties, auth);

      // creates a new e-mail message
      Message msg = new MimeMessage(session);

      msg.setFrom(new InternetAddress(userName));
      InternetAddress[] toAddresses = {
            new InternetAddress("buithanhchung@daiwahouse.vn"),
            new InternetAddress("phonguyetanh@daiwahouse.vn"),
            new InternetAddress("kimhongngoc@daiwahouse.vn")
      };
      msg.setRecipients(Message.RecipientType.TO, toAddresses);
      msg.setSubject(subject);
      msg.setSentDate(new Date());

      // creates message part
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(message, "text/html; charset=utf-8");

      // creates multi-part
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      // sets the multi-part as e-mail's content
      msg.setContent(multipart, "text/html; charset=utf-8");

      // sends the e-mail

      Transport.send(msg);

   }


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
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
      Border margin = BorderFactory.createEmptyBorder(5, 0, 5, 0);
      Border horiLine = new MatteBorder(0, 0, 1, 0, Color.GRAY);

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

            if (bukken != null) {
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
   }


   /**
    * Create the frame.
    */
   public BecchuuIraiForm() {
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setTitle("別注依頼");


      createAndSetupGUI();
      txtIraibi.setText(Helpers.stringFromDate(new Date()));
      txtIraiSha.setEditable(false);
      txtIraiSha.setText(Config.UserName);

      ImageIcon imageIcon = new ImageIcon(BecchuuIraiForm.class.getResource("/resources/icon/spinner.gif"));

      JSplitPane splitPane = new JSplitPane();
      splitPane.setDividerSize(0);
      contentPane.add(splitPane, BorderLayout.SOUTH);

      JPanel leftPanel = new JPanel();
      FlowLayout fl_leftPanel = (FlowLayout) leftPanel.getLayout();
      fl_leftPanel.setVgap(10);
      fl_leftPanel.setAlignment(FlowLayout.LEFT);
      splitPane.setLeftComponent(leftPanel);

      JLabel lblNewLabel_6 = new JLabel("Created by: Bui Thanh Chung");
      leftPanel.add(lblNewLabel_6);

      JPanel footerPanel = new JPanel();
      FlowLayout flowLayout = (FlowLayout) footerPanel.getLayout();
      flowLayout.setAlignment(FlowLayout.RIGHT);
      splitPane.setRightComponent(footerPanel);

      lblSendingMail = new JLabel("依頼中、この画面閉じないでください");
      lblSendingMail.setVisible(false);
      lblSendingMail.setForeground(Color.RED);
      footerPanel.add(lblSendingMail);

      iconLabel = new JLabel("");
      footerPanel.add(iconLabel);
      iconLabel.setIcon(imageIcon);
      iconLabel.setOpaque(false);

      imageIcon.setImageObserver(iconLabel);
      iconLabel.setVisible(false);

      btnKanriGamen = new JButton("管理画面");
      btnKanriGamen.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (bukkenKanriForm == null) {
               bukkenKanriForm = new BukkenKanriForm();
               bukkenKanriForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

            bukkenKanriForm.setVisible(true);
         }
      });
      footerPanel.add(btnKanriGamen);

      btnCancel = new JButton("キャンセル");
      footerPanel.add(btnCancel);

      btnOK = new JButton("依頼実行");
      footerPanel.add(btnOK);
      
      JSplitPane headerSplitPane = new JSplitPane();
      headerSplitPane.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
      headerSplitPane.setDividerSize(0);
      contentPane.add(headerSplitPane, BorderLayout.NORTH);
      
      JPanel panel = new JPanel();
      panel.setBorder(new EmptyBorder(5, 0, 0, 0));
      FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
      flowLayout_1.setVgap(0);
      flowLayout_1.setHgap(0);
      flowLayout_1.setAlignment(FlowLayout.LEFT);
      headerSplitPane.setLeftComponent(panel);
      
            JLabel lblNewLabel = new JLabel("別注依頼：");
            panel.add(lblNewLabel);
            lblNewLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
            lblNewLabel.setBackground(new Color(255, 255, 255));
            
            JPanel panel_1 = new JPanel();
            FlowLayout flowLayout_2 = (FlowLayout) panel_1.getLayout();
            flowLayout_2.setHgap(0);
            flowLayout_2.setVgap(0);
            flowLayout_2.setAlignment(FlowLayout.RIGHT);
            headerSplitPane.setRightComponent(panel_1);
            
            JButton btnChangePass = new JButton("<html><font color=\"#0000FF\">パスワード変更</html>");
            btnChangePass.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		ChangePasswordForm changePasswordForm = new ChangePasswordForm();
            		changePasswordForm.setVisible(true);
            	}
            });
            btnChangePass.setFocusPainted(false);
            btnChangePass.setBorderPainted(false);
            btnChangePass.setForeground(Color.BLUE);
            panel_1.add(btnChangePass);
      btnOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            btnOKActionPerformed(e);
         }
      });
      btnCancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosed(WindowEvent e) {
            if (swingWorker != null) {
               swingWorker.cancel(true);
            }
            super.windowClosed(e);
         }
      });

      pack();
      setBounds(100, 100, 650, (int) this.getPreferredSize().getHeight());
   }

}
