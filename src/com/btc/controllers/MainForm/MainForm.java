/*
 * Created by JFormDesigner on Sat Nov 14 13:07:23 ICT 2015
 */

package com.btc.controllers.MainForm;

import com.btc.controllers.BecchuuIraiForm.BecchuuIraiForm;
import com.btc.controllers.BecchuuKanriForm.BecchuuKanriForm;
import com.btc.controllers.BukkenKanriForm.BukkenKanriForm;
import com.btc.controllers.DialogHelpers;
import com.btc.controllers.EmployeeKanriForm.EmployeeKanriForm;
import com.btc.controllers.KujouKanriForm.KujouKanriForm;
import com.btc.controllers.SeisekiHoukokuForm.SeisekiHoukokuForm;
import com.btc.controllers.ZangyouKanriForm.ZangyouTodoke;
import com.btc.supports.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Thanh Chung
 */
public class MainForm extends JFrame {

   private JPanel[] tabs;

   int maxWinDowWidth;
   int maxWindowHeight;

   JPanel createPanel(JFrame frame) {
      // frame.pack();
      JPanel p = (JPanel) frame.getContentPane();
      int w = frame.getPreferredSize().width;
      int h = frame.getPreferredSize().height;

      p.setPreferredSize(new Dimension(w, h));
      maxWinDowWidth = Math.max(w, maxWinDowWidth);
      maxWindowHeight = Math.max(h, maxWindowHeight);
      return p;
   }

   void initTabs() {
      tabs = new JPanel[2];
      //  tabs[0] = createPanel(new BecchuuIraiForm());
      tabs[0] = createPanel(new BecchuuKanriForm());
      tabs[1] = createPanel(new BukkenKanriForm());
//      mainTabPane.addTab("別注管理", tabs[0]);
      mainTabPane.addTab("別注依頼", tabs[0]);
      mainTabPane.addTab("物件管理", tabs[1]);
      Dimension tabDim = mainTabPane.getPreferredSize();
      mainTabPane.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent e) {
            Dimension panelDim = mainTabPane.getSelectedComponent().getPreferredSize();
            Dimension nd = new Dimension(
                tabDim.width - (maxWinDowWidth - panelDim.width),
                tabDim.height - (maxWindowHeight - panelDim.height));
            mainTabPane.setPreferredSize(nd);
            pack();
         }
      });
      setContentPane(mainTabPane);
   }

   public MainForm() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      initComponents();
      initTabs();
      mainTabPane.setSelectedIndex(0);
//      ActionEvent event = new ActionEvent(mnBecchuuKanriForm, 1, "");
//      fileMenuActionPerformed(event);
   }

   private void openNewWinDow(JPanel jPanel) {
      getContentPane().removeAll();
      getContentPane().add(jPanel);
      pack();
   }

   private void fileMenuActionPerformed(ActionEvent e) {
      Object source = e.getSource();

      // 社員管理
      if (source == mnEmployeeKanri) {
         EmployeeKanriForm employeeKanriForm = new EmployeeKanriForm();
         employeeKanriForm.showDialog();
      }
      // 苦情管理
      else if (source == mnKujouKanri) {
         KujouKanriForm kujouKanriForm = new KujouKanriForm();
         kujouKanriForm.showDialog();
      }
      // ヘルプ
      else if (source == mnHelpAbout) {
         DialogHelpers.showAlert("コピーライター", "このツールは別注班業務関係の仕事として使用されるツールである。" +
             "\nメイン機能：" +
             "\n- 別注依頼管理" +
             "\n- 別注検索システム" +
             "\n- 苦情管理" +
             "\n- 社員管理、報告" +
             "\n作成者：ブイタンチュン - ©2015" +
             "\nパワー：Java ver 1.8, GUI: Swing, データベース：SQLite" +
             "\nソフトウェアバーション：1.0");
      }
      // 成績報告
      else if (source == mnSeisekiHoukoku) {
         SeisekiHoukokuForm form = new SeisekiHoukokuForm();
         form.showDialog();
      }
      // 別注依頼
      else if (source == mnBecchuuIrai) {
         BecchuuIraiForm f = new BecchuuIraiForm();
         f.pack();
         f.setVisible(true);
      }
      // 残業登録
      else if (source == mnZangyouTouroku) {
         ZangyouTodoke form = new ZangyouTodoke();
//         form.pack();
         form.setLocationRelativeTo(form.getOwner());
         form.setVisible(true);
      }
   }

   private void initComponents() {
      // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
      // Generated using JFormDesigner Evaluation license - thanh chung
      menuBar1 = new JMenuBar();
      menu1 = new JMenu();
      mnEmployeeKanri = new JMenuItem();
      mnBecchuuIrai = new JMenuItem();
      mnKujouKanri = new JMenuItem();
      menuItem4 = new JMenuItem();
      menuItem1 = new JMenuItem();
      menu2 = new JMenu();
      mnZangyouTouroku = new JMenuItem();
      menu3 = new JMenu();
      mnSeisekiHoukoku = new JMenuItem();
      mnHelp = new JMenu();
      mnHelpAbout = new JMenuItem();
      contentPane = new JPanel();
      mainContent = new JPanel();
      mainTabPane = new JTabbedPane();

      //======== this ========
      setBackground(null);
      setTitle("\u5225\u6ce8\u7ba1\u7406\u30b7\u30b9\u30c6\u30e0");
      Container contentPane2 = getContentPane();
      contentPane2.setLayout(new BorderLayout());

      //======== menuBar1 ========
      {

         //======== menu1 ========
         {
            menu1.setText("\u30d5\u30a1\u30a4\u30eb");

            //---- mnEmployeeKanri ----
            mnEmployeeKanri.setText("\u793e\u54e1\u53f0\u5e33");
            mnEmployeeKanri.addActionListener(e -> fileMenuActionPerformed(e));
            menu1.add(mnEmployeeKanri);

            //---- mnBecchuuIrai ----
            mnBecchuuIrai.setText("\u5225\u6ce8\u4f9d\u983c");
            mnBecchuuIrai.addActionListener(e -> fileMenuActionPerformed(e));
            menu1.add(mnBecchuuIrai);

            //---- mnKujouKanri ----
            mnKujouKanri.setText("\u82e6\u60c5\u7ba1\u7406");
            mnKujouKanri.addActionListener(e -> fileMenuActionPerformed(e));
            menu1.add(mnKujouKanri);

            //---- menuItem4 ----
            menuItem4.setText("\u30a8\u30c3\u30af\u30b9\u30dd\u30fc\u30c8");
            menu1.add(menuItem4);
            menu1.addSeparator();

            //---- menuItem1 ----
            menuItem1.setText("\u7d42\u4e86");
            menu1.add(menuItem1);
         }
         menuBar1.add(menu1);

         //======== menu2 ========
         {
            menu2.setText("\u696d\u52d9");

            //---- mnZangyouTouroku ----
            mnZangyouTouroku.setText("\u6b8b\u696d\u767b\u9332");
            mnZangyouTouroku.addActionListener(e -> fileMenuActionPerformed(e));
            menu2.add(mnZangyouTouroku);
         }
         menuBar1.add(menu2);

         //======== menu3 ========
         {
            menu3.setText("\u5831\u544a");

            //---- mnSeisekiHoukoku ----
            mnSeisekiHoukoku.setText("\u6210\u7e3e\u5831\u544a");
            mnSeisekiHoukoku.addActionListener(e -> fileMenuActionPerformed(e));
            menu3.add(mnSeisekiHoukoku);
         }
         menuBar1.add(menu3);

         //======== mnHelp ========
         {
            mnHelp.setText("\u30d8\u30eb\u30d7");
            mnHelp.addActionListener(e -> {
               fileMenuActionPerformed(e);
               fileMenuActionPerformed(e);
            });

            //---- mnHelpAbout ----
            mnHelpAbout.setText("\u30bd\u30d5\u30c8\u306b\u3064\u3044\u3066");
            mnHelpAbout.addActionListener(e -> fileMenuActionPerformed(e));
            mnHelp.add(mnHelpAbout);
         }
         menuBar1.add(mnHelp);
      }
      setJMenuBar(menuBar1);

      //======== contentPane ========
      {

         // JFormDesigner evaluation mark
         contentPane.setBorder(new javax.swing.border.CompoundBorder(
             new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                 "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                 javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                 java.awt.Color.red), contentPane.getBorder()));
         contentPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
               if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
         });

         contentPane.setLayout(new BorderLayout());

         //======== mainContent ========
         {
            mainContent.setLayout(new GridLayout(1, 1));
            mainContent.add(mainTabPane);
         }
         contentPane.add(mainContent, BorderLayout.CENTER);
      }
      contentPane2.add(contentPane, BorderLayout.CENTER);
      pack();
      setLocationRelativeTo(getOwner());
      // JFormDesigner - End of component initialization  //GEN-END:initComponents
   }

   // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
   // Generated using JFormDesigner Evaluation license - thanh chung
   private JMenuBar menuBar1;
   private JMenu menu1;
   private JMenuItem mnEmployeeKanri;
   private JMenuItem mnBecchuuIrai;
   private JMenuItem mnKujouKanri;
   private JMenuItem menuItem4;
   private JMenuItem menuItem1;
   private JMenu menu2;
   private JMenuItem mnZangyouTouroku;
   private JMenu menu3;
   private JMenuItem mnSeisekiHoukoku;
   private JMenu mnHelp;
   private JMenuItem mnHelpAbout;
   private JPanel contentPane;
   private JPanel mainContent;
   private JTabbedPane mainTabPane;
   // JFormDesigner - End of variables declaration  //GEN-END:variables

   public static void main(String[] args) {
      Config.setLookAndField();
      MainForm frame = new MainForm();
      // frame.setSize(new Dimension(1000, 700));
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
}
