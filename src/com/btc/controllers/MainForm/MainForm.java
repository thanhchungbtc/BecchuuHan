/*
 * Created by JFormDesigner on Sat Nov 14 13:07:23 ICT 2015
 */

package com.btc.controllers.MainForm;

import com.btc.controllers.BecchuuIraiForm.BecchuuIraiForm;
import com.btc.controllers.BecchuuKanriForm.BecchuuKanriForm;
import com.btc.controllers.BukkenKanriForm.BukkenKanriForm;
import com.btc.controllers.ChangePasswordForm.ChangePasswordForm;
import com.btc.supports.Config;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.LinkedList;
import java.awt.event.ActionListener;

/**
 * @author Thanh Chung
 */
public class MainForm extends JFrame {

   private JInternalFrame becchuuKanriForm;
   private JInternalFrame becchuuIraiForm;
   private JInternalFrame bukkenKanriForm;

   private java.util.List<JInternalFrame> internalFrames = new LinkedList<>();

   public MainForm() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      initComponents();
   }

   private JInternalFrame createJInternalFrameFromFrame(JFrame frame) {
      JInternalFrame jInternalFrame = new JInternalFrame(frame.getTitle(), true, true, true, true);

      jInternalFrame.setContentPane(frame.getContentPane());
      jInternalFrame.setSize(frame.getSize());
      jInternalFrame.setVisible(true);

      jInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {

         @Override
         public void internalFrameClosed(InternalFrameEvent e) {
            if (e.getSource() == becchuuIraiForm) {
               becchuuIraiForm = null;
            } else if (e.getSource() == becchuuKanriForm) {
               becchuuKanriForm = null;
            } else if (e.getSource() == bukkenKanriForm) {
               bukkenKanriForm = null;
            }
            super.internalFrameClosed(e);
         }
      });
      return jInternalFrame;
   }

   private void openNewWinDow(JInternalFrame jInternalFrame) {
      JInternalFrame[] allInternalFrames = this.desktopPane.getAllFrames();
      for (JInternalFrame jInternalFrame1 : allInternalFrames) {
         if (jInternalFrame1 == jInternalFrame) {

            continue;
         }
         jInternalFrame1.dispose();
      }

      if (jInternalFrame != becchuuIraiForm) {
         try {
            jInternalFrame.setMaximum(true);
         } catch (PropertyVetoException e) {
            e.printStackTrace();
         }
      }
   }

   private void fileMenuActionPerformed(ActionEvent e) {
      Object source = e.getSource();

      if (source == btnBecchuuKanri || source == mnBecchuuKanriForm) {
         if (becchuuKanriForm == null) {
            becchuuKanriForm = createJInternalFrameFromFrame(new BecchuuKanriForm());
            this.desktopPane.add(becchuuKanriForm);
         }
         openNewWinDow(becchuuKanriForm);

      } else if (source == btnBecchuuIrai || source == mnBecchuuIrai) {
         if (becchuuIraiForm == null) {
            becchuuIraiForm = createJInternalFrameFromFrame(new BecchuuIraiForm());
            this.desktopPane.add(becchuuIraiForm);
         }
         openNewWinDow(becchuuIraiForm);
      } else if (source == btnBukkenKanri || source == mnBukkenKanri) {
         if (bukkenKanriForm == null) {
            bukkenKanriForm = createJInternalFrameFromFrame(new BukkenKanriForm());
            this.desktopPane.add(bukkenKanriForm);
         }
         openNewWinDow(bukkenKanriForm);
      }
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
      toolBar1 = new JToolBar();
      btnBecchuuKanri = new JButton();
      btnBecchuuIrai = new JButton();
      btnBukkenKanri = new JButton();
      desktopPane = new JDesktopPane();

      //======== this ========
      setBackground(null);
      setTitle("\u5225\u6ce8\u7ba1\u7406\u30b7\u30b9\u30c6\u30e0");
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

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
            
            JMenuItem menuItem = new JMenuItem("パスワード変更変更");
            menuItem.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		ChangePasswordForm changePasswordForm = new ChangePasswordForm();
            		changePasswordForm.setVisible(true);
            	}
            });
            menu1.add(menuItem);

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

      //======== toolBar1 ========
      {
         toolBar1.setFloatable(false);
         toolBar1.setMaximumSize(new Dimension(68, 18));
         toolBar1.setMinimumSize(new Dimension(68, 18));

         //---- btnBecchuuKanri ----
         btnBecchuuKanri.setIcon(new ImageIcon(getClass().getResource("/resources/icon/toolbars/icon-toolbar-becchuukanri.png")));
         btnBecchuuKanri.setBorderPainted(false);
         btnBecchuuKanri.setFocusPainted(false);
         btnBecchuuKanri.addActionListener(e -> fileMenuActionPerformed(e));
         toolBar1.add(btnBecchuuKanri);

         //---- btnBecchuuIrai ----
         btnBecchuuIrai.setIcon(new ImageIcon(getClass().getResource("/resources/icon/toolbars/icon-toolbar-becchuukanri.png")));
         btnBecchuuIrai.setBorderPainted(false);
         btnBecchuuIrai.setFocusPainted(false);
         btnBecchuuIrai.addActionListener(e -> fileMenuActionPerformed(e));
         toolBar1.add(btnBecchuuIrai);

         //---- btnBukkenKanri ----
         btnBukkenKanri.setIcon(new ImageIcon(getClass().getResource("/resources/icon/toolbars/icon-toolbar-becchuukanri.png")));
         btnBukkenKanri.setBorderPainted(false);
         btnBukkenKanri.setFocusPainted(false);
         btnBukkenKanri.addActionListener(e -> {
            fileMenuActionPerformed(e);
            fileMenuActionPerformed(e);
         });
         toolBar1.add(btnBukkenKanri);
      }
      contentPane.add(toolBar1, BorderLayout.NORTH);

      //======== desktopPane ========
      {
         desktopPane.setDoubleBuffered(true);
      }
      contentPane.add(desktopPane, BorderLayout.CENTER);
      //pack();
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
   private JToolBar toolBar1;
   private JButton btnBecchuuKanri;
   private JButton btnBecchuuIrai;
   private JButton btnBukkenKanri;
   private JDesktopPane desktopPane;
   // JFormDesigner - End of variables declaration  //GEN-END:variables

   public static void main(String[] args) {
      Config.setLookAndField();
      MainForm frame = new MainForm();
      // frame.setSize(new Dimension(1000, 700));
      frame.setSize(new Dimension(1000, 700));
      frame.setExtendedState(MAXIMIZED_BOTH);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
}
