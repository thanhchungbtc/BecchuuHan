/*
 * Created by JFormDesigner on Sat Nov 14 13:07:23 ICT 2015
 */

package com.btc.controllers.MainForm;

import com.btc.controllers.BecchuuIraiForm.BecchuuIraiForm;
import com.btc.controllers.BecchuuKanriForm.BecchuuKanriForm;
import com.btc.supports.Config;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Thanh Chung
 */
public class MainForm extends JFrame {

   private JInternalFrame becchuuKanriForm;
   private JInternalFrame becchuuIraiForm;


   public MainForm() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      initComponents();
   }

   private void openNewWindow(JFrame frame, JInternalFrame jInternalFrame) {
      JInternalFrame[] allInternalFrames = this.desktopPane.getAllFrames();
      for (JInternalFrame jInternalFrame1 : allInternalFrames) {
         jInternalFrame1.dispose();
      }
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
            }
            super.internalFrameClosed(e);
         }
      });
      this.desktopPane.add(jInternalFrame);

      System.out.println(desktopPane.countComponents());
   }

   private void fileMenuActionPerformed(ActionEvent e) {
      Object source = e.getSource();

      if (source == btnBecchuuKanri || source == mnBecchuuKanriForm) {
         if (becchuuKanriForm != null) return;
         BecchuuKanriForm kanriForm = new BecchuuKanriForm();
         becchuuKanriForm = new JInternalFrame(kanriForm.getTitle(), true, true, true, true);
         openNewWindow(kanriForm, becchuuKanriForm);
      } else if (source == btnBecchuuIrai || source == mnBecchuuIrai) {
         if (becchuuIraiForm != null) return;
         BecchuuIraiForm iraiForm = new BecchuuIraiForm();
         becchuuIraiForm = new JInternalFrame(iraiForm.getTitle(), true, true, true, true);
         openNewWindow(iraiForm, becchuuIraiForm);
      }
   }

   private void initComponents() {
      // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
      // Generated using JFormDesigner Evaluation license - Thanh Chung
      menuBar1 = new JMenuBar();
      menu1 = new JMenu();
      mnBecchuuKanriForm = new JMenuItem();
      mnBecchuuIrai = new JMenuItem();
      menuItem4 = new JMenuItem();
      menuItem1 = new JMenuItem();
      menu2 = new JMenu();
      toolBar1 = new JToolBar();
      btnBecchuuKanri = new JButton();
      btnBecchuuIrai = new JButton();
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
      }
      contentPane.add(toolBar1, BorderLayout.NORTH);

      //======== desktopPane ========
      {
         desktopPane.setDoubleBuffered(true);
      }
      contentPane.add(desktopPane, BorderLayout.CENTER);
      pack();
      setLocationRelativeTo(getOwner());
      // JFormDesigner - End of component initialization  //GEN-END:initComponents
   }

   // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
   // Generated using JFormDesigner Evaluation license - Thanh Chung
   private JMenuBar menuBar1;
   private JMenu menu1;
   private JMenuItem mnBecchuuKanriForm;
   private JMenuItem mnBecchuuIrai;
   private JMenuItem menuItem4;
   private JMenuItem menuItem1;
   private JMenu menu2;
   private JToolBar toolBar1;
   private JButton btnBecchuuKanri;
   private JButton btnBecchuuIrai;
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
