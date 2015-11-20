/*
 * Created by JFormDesigner on Sat Nov 14 13:07:23 ICT 2015
 */

package com.btc.controllers.MainForm;

import com.btc.controllers.BecchuuIraiForm.BecchuuIraiForm;
import com.btc.controllers.BecchuuKanriForm.BecchuuKanriForm;
import com.btc.controllers.BukkenKanriForm.BukkenKanriForm;
import com.btc.controllers.ChangePasswordForm.ChangePasswordForm;
import com.btc.controllers.EmployeeManagementForm.EmployeeViewForm;
import com.btc.supports.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.*;
import java.awt.event.ActionListener;

/**
 * @author Thanh Chung
 */
public class MainForm extends JFrame {

   private JPanel[] tabs;

   int maxWinDowWidth;
   int maxWindowHeight;
   JPanel createPanel(JFrame frame) {
     // frame.pack();
      JPanel p = (JPanel)frame.getContentPane();
      int w = frame.getPreferredSize().width;
      int h = frame.getPreferredSize().height;

      p.setPreferredSize(new Dimension(w, h));
      maxWinDowWidth = Math.max(w, maxWinDowWidth);
      maxWindowHeight = Math.max(h, maxWindowHeight);
      return p;
   }
   void initTabs() {
      tabs = new JPanel[3];
      tabs[1] = createPanel(new BecchuuKanriForm());
      tabs[0] = createPanel(new BecchuuIraiForm());
      tabs[2] = createPanel(new BukkenKanriForm());

      mainTabPane.addTab("別注管理", tabs[0]);
      mainTabPane.addTab("別注依頼", tabs[1]);
      mainTabPane.addTab("物件管理", tabs[2]);

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
      mainTabPane.setSelectedIndex(1);
//      ActionEvent event = new ActionEvent(mnBecchuuKanriForm, 1, "");
//      fileMenuActionPerformed(event);
   }

   private void openNewWinDow(JPanel jPanel) {
      getContentPane().removeAll();
      getContentPane().add(jPanel);
      pack();
   }

   private void fileMenuActionPerformed(ActionEvent e) {
//      Object source = e.getSource();
//
//      if (source == mnBecchuuKanriForm) {
//         becchuuKanriForm = (JPanel)(new BecchuuKanriForm().getContentPane());
//         openNewWinDow(becchuuKanriForm);
//
//      } else if (source == mnBecchuuIrai) {
//         becchuuIraiForm = (JPanel)new BecchuuIraiForm().getContentPane();
//         openNewWinDow(becchuuIraiForm);
//      } else if (source == mnBukkenKanri) {
//         bukkenKanriForm = (JPanel)new BukkenKanriForm().getContentPane();
//         openNewWinDow(bukkenKanriForm);
//      }
   }

   private void initComponents() {
      // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
      // Generated using JFormDesigner Evaluation license - Thanh Chung
      menuBar1 = new JMenuBar();
      menu1 = new JMenu();
      mnBecchuuKanriForm = new JMenuItem();
      mnBukkenKanri = new JMenuItem();
      mnBecchuuIrai = new JMenuItem();
      menuItem4 = new JMenuItem();
      menuItem1 = new JMenuItem();
      menu2 = new JMenu();
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

            //---- mnBecchuuKanriForm ----
            mnBecchuuKanriForm.setText("\u5225\u6ce8\u7ba1\u7406");
            mnBecchuuKanriForm.addActionListener(e -> fileMenuActionPerformed(e));
            menu1.add(mnBecchuuKanriForm);

            //---- mnBukkenKanri ----
            mnBukkenKanri.setText("\u7269\u4ef6\u7ba1\u7406");
            mnBukkenKanri.addActionListener(e -> fileMenuActionPerformed(e));
            menu1.add(mnBukkenKanri);

            //---- mnBecchuuIrai ----
            mnBecchuuIrai.setText("\u5225\u6ce8\u4f9d\u983c");
            mnBecchuuIrai.addActionListener(e -> fileMenuActionPerformed(e));
            menu1.add(mnBecchuuIrai);

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
            menu2.setText("\u30d8\u30eb\u30d7");
         }
         menuBar1.add(menu2);
      }
      setJMenuBar(menuBar1);

      //======== contentPane ========
      {

         // JFormDesigner evaluation mark
         contentPane.setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
               "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
               javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
               java.awt.Color.red), contentPane.getBorder())); contentPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

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
   // Generated using JFormDesigner Evaluation license - Thanh Chung
   private JMenuBar menuBar1;
   private JMenu menu1;
   private JMenuItem mnBecchuuKanriForm;
   private JMenuItem mnBukkenKanri;
   private JMenuItem mnBecchuuIrai;
   private JMenuItem menuItem4;
   private JMenuItem menuItem1;
   private JMenu menu2;
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
