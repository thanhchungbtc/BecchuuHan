package com.btc.controllers.KujouKanriForm;

import com.btc.model.Becchuu;
import com.btc.model.Kujou;
import com.btc.model.KujouType;
import com.btc.repositoty.KujouRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by BTC on 11/21/15.
 */
public class KujouKanriForm extends JDialog implements BecchuuSelectForm.BecchuuSelectDelegate {

   KujouRepository repository;
   Kujou selectedKujou;
   int selectedBecchuuId;

   void initializeData() {
      repository = new KujouRepository();
   }

   void setupTable() {

      KujouKanriTableModel kujouKanriTableModel = new KujouKanriTableModel(this.repository);
      kujouTable.setModel(kujouKanriTableModel);
      kujouTable.setRowHeight(25);

      kujouTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               tableSelectionChange(e);
            }
         }
      });

   }

   void setupCombobox() {
      DefaultComboBoxModel<Object> model2 = new DefaultComboBoxModel<Object>(repository.getListKujouType().toArray());
      kujouTypeCombobox.setModel(model2);
   }

   void populateField(Kujou kujou) {
      if (kujou == null) {
         receiptDatePicker.setDate(null);
         kujouTypeCombobox.getModel().setSelectedItem(null);
         populateBecchuuJouhou(null);
         descriptionTextArea.setText(null);
         bikouTextArea.setText(null);

         saveButton.setText("追加");
         addNewButton.setEnabled(false);
         return;
      }
      receiptDatePicker.setDate(kujou.getReceiptDate());
      kujouTypeCombobox.getModel().setSelectedItem(kujou.getKujouType());
      populateBecchuuJouhou(kujou.getBecchuu());
      descriptionTextArea.setText(kujou.getDescription());
      bikouTextArea.setText(kujou.getBikou());
      saveButton.setText("保存");
      addNewButton.setEnabled(true);
   }

   void populateBecchuuJouhou(Becchuu becchuu) {
      if (becchuu == null) {
         becchuuSelectButton.setText("別注選択");
         return;
      }
      String text = "担当者：" + becchuu.getKenshuuSha().getName()
          + "<br/>別注記号：" + becchuu.getBecchuuKigou()
          + "<br/>工事番号：" + becchuu.getKoujibangou()
          + "<br/>施主名：" + becchuu.getBukken().getName() + "<br/>作成日：" + Helpers.stringFromDate(becchuu
          .getSakuseiBi());

      String jouhouDescription = String.format("<html><div WIDTH=%d>%s</div><html>", becchuuSelectButton.getWidth() - 10,
          text);
      becchuuSelectButton.setText(jouhouDescription);
   }

   // Events

   void tableSelectionChange(ListSelectionEvent e) {
      int row = kujouTable.getSelectedRow();
      if (row < 0) return;

      int id = Integer.parseInt(kujouTable.getValueAt(row, 0).toString());
      selectedKujou = repository.getById(id);

      populateField(selectedKujou);
   }

   void buttonActionPerformed(ActionEvent e) {
      Object object = e.getSource();
      // キャンセル
      if (object == cancelButton) {
         tableSelectionChange(null);
      }
      // 保存
      else if (object == saveButton) {
         boolean isInsert = false;
         if (selectedKujou == null) {
            selectedKujou = new Kujou();
            isInsert = true;
         }
         selectedKujou.setReceiptDate(receiptDatePicker.getDate());
         selectedKujou.setDescription(descriptionTextArea.getText().trim());
         selectedKujou.setBikou(bikouTextArea.getText().trim());
         KujouType kujouType = (KujouType) kujouTypeCombobox.getModel().getSelectedItem();

         KujouKanriTableModel model = (KujouKanriTableModel) kujouTable.getModel();
         if (kujouType != null)
            selectedKujou.setKujouTypeId(kujouType.getId());
         selectedKujou.setBecchuuId(selectedBecchuuId);


         if (isInsert)
            model.insert(selectedKujou);
         else {
            model.update(selectedKujou, kujouTable.getSelectedRow());
         }
      }
      // 別注選択
      else if (object == becchuuSelectButton) {
         BecchuuSelectForm form = new BecchuuSelectForm();
         form.delegate = this;
         form.pack();
         form.setLocationRelativeTo(getOwner());
         form.setVisible(true);
      }
      // 追加
      else if (object == addNewButton) {
         selectedKujou = null;
         populateField(selectedKujou);
      }
      // 削除
      else if (object == deleteButton) {
         if (selectedKujou != null) {
            KujouKanriTableModel model = (KujouKanriTableModel) kujouTable.getModel();
            if (model.delete(selectedKujou)) {
               selectedKujou = null;
               populateField(selectedKujou);
            }
         }
      }
   }

   public void showDialog() {
      setModal(true);
      pack();
      setLocationRelativeTo(getOwner());
      this.getRootPane().registerKeyboardAction(e -> {
         this.dispose();
      }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
      setVisible(true);
   }

   public KujouKanriForm() {
      setTitle("苦情管理台帳");
      setContentPane(contentPane);
      initializeData();
      setupCombobox();
      setupTable();

      searchTextField.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            String regex = Helpers.convertGlobToRegExCaseInsensitive(searchTextField.getText());
            RowFilter filter = RowFilter.regexFilter(regex);
            kujouTable.setRowFilter(filter);
         }
      });

      setMinimumSize(new Dimension(900, 400));
      cancelButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
         }
      });

      becchuuSelectButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
         }
      });
      addNewButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
         }
      });
      saveButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
         }
      });
      deleteButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
         }
      });
   }

   @Override
   public void submitSelectedBecchuu(BecchuuSelectForm becchuuSelectForm, Becchuu selectedBecchuu) {
      populateBecchuuJouhou(selectedBecchuu);
      selectedBecchuuId = selectedBecchuu.getId();
   }

   public static void main(String[] args) {
      Config.setLookAndField();
      KujouKanriForm f = new KujouKanriForm();
      f.pack();
      f.setVisible(true);
   }

   // Variables
   private JPanel contentPane;
   private JPanel leftPane;
   private JXTable kujouTable;
   private JPanel rightPane;
   private JComboBox kujouTypeCombobox;
   private JTextArea descriptionTextArea;
   private JTextArea bikouTextArea;
   private JButton saveButton;
   private JButton cancelButton;
   private JXDatePicker receiptDatePicker;
   private JTextField searchTextField;
   private JButton becchuuSelectButton;
   private JButton addNewButton;
   private JButton deleteButton;

}
