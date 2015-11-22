package com.btc.controllers.KujouKanriForm;

import com.btc.model.Becchuu;
import com.btc.repositoty.BecchuuRepository;
import com.btc.supports.Helpers;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.List;

public class BecchuuSelectForm extends JDialog {
   private JPanel contentPane;
   private JButton buttonOK;
   private JButton buttonCancel;
   private JTextField textField1;
   private JXTable becchuuTable;

   private BecchuuRepository repository;

   public BecchuuSelectDelegate delegate;

   public BecchuuSelectForm() {
      setContentPane(contentPane);
      setModal(true);
      getRootPane().setDefaultButton(buttonOK);
      initializeData();
      setupTable();
      setupEvent();

      textField1.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            searchTextKeyReleased(e);
         }
      });
   }

   private void searchTextKeyReleased(KeyEvent e) {
      if (!(e.getSource() instanceof JTextField)) return;
      JTextField source = (JTextField) e.getSource();
      RowFilter textFilter = null;
      if (source.getText().trim().length() == 0) {
         textFilter = null;
      } else {
         String regex = Helpers.convertGlobToRegExCaseInsensitive(source.getText());
         textFilter = RowFilter.regexFilter(regex);
      }
      becchuuTable.setRowFilter(textFilter);
   }

   private void onOK() {
      int row = becchuuTable.getSelectedRow();
      if (row >= 0) {
         if (delegate != null) {
            int id = Integer.parseInt(becchuuTable.getValueAt(row, 0).toString());
            Becchuu selectedBecchuu = repository.getBecchuuByID(id);
            delegate.submitSelectedBecchuu(this, selectedBecchuu);
         }
      }
      dispose();
   }

   private void onCancel() {
// add your code here if necessary
      dispose();
   }

   void initializeData() {
      this.repository = new BecchuuRepository();
   }

   void setupTable() {
      BecchuuSelectTableModel model = new BecchuuSelectTableModel(this.repository);
      becchuuTable.setModel(model);
      becchuuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }

   void setupEvent() {
      buttonOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            onOK();
         }
      });

      buttonCancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            onCancel();
         }
      });

// call onCancel() when cross is clicked
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            onCancel();
         }
      });


// call onCancel() on ESCAPE
      contentPane.registerKeyboardAction(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            onCancel();
         }
      }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
   }

   public static void main(String[] args) {
      BecchuuSelectForm dialog = new BecchuuSelectForm();
      dialog.pack();
      dialog.setVisible(true);
      System.exit(0);
   }

   private class BecchuuSelectTableModel extends AbstractTableModel {
      final String[] COLUMNNAMES = {"別注コー", "別注記号", "工事番号", "検収者", "作成日"};

      List<Becchuu> data;
      BecchuuRepository repository;

      public BecchuuSelectTableModel(BecchuuRepository repository) {
         this.repository = repository;
         this.data = repository.getListWithRefresh(false);
      }

      @Override
      public int getRowCount() {
         return data.size();
      }

      @Override
      public int getColumnCount() {
         return COLUMNNAMES.length;
      }

      @Override
      public String getColumnName(int column) {
         return COLUMNNAMES[column];
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
         Becchuu becchuu = this.data.get(rowIndex);
         try {
            switch (columnIndex) {
               case 0:
                  return becchuu.getId();
               case 1:
                  return becchuu.getBecchuuKigou();
               case 2:
                  return becchuu.getKoujibangou();
               case 3:
                  return becchuu.getKenshuuSha().getName();
               case 4:
                  return Helpers.stringFromDate(becchuu.getSakuseiBi());
               default:
                  return "Error";
            }
         } catch (NullPointerException e) {
            return "";
         }
      }
   }

   public static interface BecchuuSelectDelegate {
      void submitSelectedBecchuu(BecchuuSelectForm becchuuSelectForm, Becchuu selectedBecchuu);
   }
}
