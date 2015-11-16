/*
 * Created by JFormDesigner on Sun Nov 15 23:32:42 ICT 2015
 */

package com.btc.controllers.LoginForm;

import com.btc.controllers.BecchuuIraiForm.BecchuuIraiForm;
import com.btc.controllers.DialogHelpers;
import com.btc.controllers.MainForm.MainForm;
import com.btc.model.Employee;
import com.btc.repositoty.EmployeeRepository;
import com.btc.supports.Config;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Thanh Chung
 */
public class LoginForm extends JFrame {
   public LoginForm() {
      initComponents();
      JRootPane rootPane = SwingUtilities.getRootPane(btnLogin);
      rootPane.setDefaultButton(btnLogin);
   }

   private void loginButtonActionPerformed(ActionEvent e) {
      if (txtUserID.getText().trim().equals("") || txtPassword.getText().trim().equals("")) {
         DialogHelpers.showError("エラー", "必要データ入力してください。");
         return;
      }
      Employee employee = EmployeeRepository.Instance().login(txtUserID.getText(), txtPassword.getText());
      if (employee == null) {
         DialogHelpers.showError("エラー", "パスワードが間違いました。");
         return;
      }
      if (employee.getHonshaBangou() != null && !employee.getHonshaBangou().trim().equals(""))
         Config.HonShaBangou = employee.getHonshaBangou();
      Config.UserID = employee.getId();
      Config.UserName = employee.getName();
      Config.RoleID = employee.getRoleID();
      Config.Password = employee.getPassword();
      switch (Config.RoleID) {
         case "AD":
         case "BC":
            MainForm mainForm = new MainForm();
            mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainForm.setSize(new Dimension(1100, 700));
            mainForm.setLocationRelativeTo(getOwner());
            mainForm.setVisible(true);
            break;
         default:
            BecchuuIraiForm becchuuIraiForm = new BecchuuIraiForm();
            becchuuIraiForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            becchuuIraiForm.setVisible(true);
            break;
      }
      this.dispose();
   }

   private void initComponents() {
      // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
      // Generated using JFormDesigner Evaluation license - Thanh Chung
      panel1 = new JPanel();
      label1 = new JLabel();
      panel2 = new JPanel();
      label2 = new JLabel();
      txtUserID = new JTextField();
      label3 = new JLabel();
      txtPassword = new JPasswordField();
      panel3 = new JPanel();
      btnLogin = new JButton();

      //======== this ========
      setResizable(false);
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      //======== panel1 ========
      {
         panel1.setBorder(new CompoundBorder(
            new EmptyBorder(5, 5, 5, 5),
            new MatteBorder(0, 0, 1, 0, Color.black)));

         panel1.setLayout(new BorderLayout());

         //---- label1 ----
         label1.setText("\u30ed\u30b0\u30a4\u30f3");
         label1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
         panel1.add(label1, BorderLayout.WEST);
      }
      contentPane.add(panel1, BorderLayout.NORTH);

      //======== panel2 ========
      {
         panel2.setBorder(new EmptyBorder(5, 5, 5, 5));
         panel2.setLayout(new GridBagLayout());
         ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
         ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
         ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
         ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

         //---- label2 ----
         label2.setText("\u793e\u54e1\u756a\u53f7\uff1a");
         panel2.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
         panel2.add(txtUserID, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

         //---- label3 ----
         label3.setText("\u30d1\u30b9\u30ef\u30fc\u30c9\uff1a");
         panel2.add(label3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
         panel2.add(txtPassword, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
      }
      contentPane.add(panel2, BorderLayout.CENTER);

      //======== panel3 ========
      {
         panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

         //---- btnLogin ----
         btnLogin.setText("\u30ed\u30b0\u30a4\u30f3");
         btnLogin.addActionListener(e -> loginButtonActionPerformed(e));
         panel3.add(btnLogin);
      }
      contentPane.add(panel3, BorderLayout.SOUTH);
      pack();
      setLocationRelativeTo(getOwner());
      // JFormDesigner - End of component initialization  //GEN-END:initComponents
   }

   // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
   // Generated using JFormDesigner Evaluation license - Thanh Chung
   private JPanel panel1;
   private JLabel label1;
   private JPanel panel2;
   private JLabel label2;
   private JTextField txtUserID;
   private JLabel label3;
   private JPasswordField txtPassword;
   private JPanel panel3;
   private JButton btnLogin;
   // JFormDesigner - End of variables declaration  //GEN-END:variables

   public static void main(String[] args) {
      Config.setLookAndField();
      LoginForm loginForm = new LoginForm();
      loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      loginForm.setSize(new Dimension(300, loginForm.getPreferredSize().height));
      loginForm.setVisible(true);
   }
}
