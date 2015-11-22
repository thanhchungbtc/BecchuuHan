package com.btc.controllers.SeisekiHoukokuForm;

import com.btc.controllers.KujouKanriForm.KujouKanriTableModel;
import com.btc.model.Kujou;
import com.btc.repositoty.EmployeeRepository;
import com.btc.repositoty.GenericRepository;
import com.btc.repositoty.KujouRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import com.btc.viewModel.BTCAbstractTableModel;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SeisekiHoukokuForm extends JDialog {
   private JPanel contentPane;
   private JButton buttonOK;
   private JButton buttonCancel;
   private JPanel mainContent;
   private JPanel headerPanel;
   private JPanel detailPanel;
   private JPanel kujouPanel;
   private JPanel headerKujouPanel;
   private JComboBox employeeCombobox;
   private JPanel footerKujouPanel;
   private JPanel contentKujouPanel;
   private JXTable kujouTable;
   private JLabel kujouSummaryLabel;
   private JLabel employeeNameLabel;

   private KujouRepository kujouRepository;
   private EmployeeRepository employeeRepository;

   public SeisekiHoukokuForm() {
      setContentPane(contentPane);
      setModal(true);
      getRootPane().setDefaultButton(buttonOK);
      initializeData();
      setupCombobox();
      setupTable();
      populateData();
      setupEvents();
   }

   private void initializeData() {
      kujouRepository = new KujouRepository();
      employeeRepository = EmployeeRepository.Instance();
   }

   private void setupEvents() {

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

      employeeCombobox.setSelectedIndex(1);
      employeeCombobox.setSelectedIndex(0);
   }

   private void setupCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(employeeRepository.getBecchuuHandEmployees()
            .toArray());
      employeeCombobox.setModel(model);
      employeeCombobox.addActionListener(new ComboboxActionListener());
   }

   private void setupTable() {
      kujouTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      kujouTable.setRowHeight(25);
      kujouTable.setModel(new KujouKanriTableModel(this.kujouRepository));
      hideColumn(kujouTable, 0); // hide id column
      hideColumn(kujouTable, 1);

   }

   private void hideColumn(JXTable table, int columnIndex) {
      table.getColumn(columnIndex).setWidth(0);
      table.getColumn(columnIndex).setMinWidth(0);
      table.getColumn(columnIndex).setMaxWidth(0);
   }

   private void populateKujouSummary() {
      String text = "合計：" + kujouTable.getRowCount();

      String jouhouDescription = String.format("<html><div WIDTH=%d>%s</div><html>", kujouSummaryLabel.getWidth() - 10,
            text);
      kujouSummaryLabel.setText(jouhouDescription);
      employeeNameLabel.setText("社員：" + employeeCombobox.getSelectedItem().toString());
   }

   private void populateData() {

   }

   private void onOK() {
// add your code here
      dispose();
   }

   private void onCancel() {
// add your code here if necessary
      dispose();
   }

   public static void main(String[] args) {
      Config.setLookAndField();
      SeisekiHoukokuForm dialog = new SeisekiHoukokuForm();
      dialog.pack();
      dialog.setSize(new Dimension(500, 300));
      dialog.setVisible(true);
      System.exit(0);
   }

   private class ComboboxActionListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         Object source = event.getSource();
         RowFilter employeeFilter;
         if (source == employeeCombobox) {
            employeeFilter = RowFilter.regexFilter(employeeCombobox.getModel().getSelectedItem().toString(), 1);
            kujouTable.setRowFilter(employeeFilter);

            populateKujouSummary();
         }
      }
   }

   private class KujouTableModel extends BTCAbstractTableModel<Kujou> {
      public KujouTableModel(GenericRepository repository) {
         super(repository);
      }

      @Override
      protected String[] initColumnNames() {
         return new String[]{
               "苦情コード", "苦情タイプ",
         };
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
         Kujou kujou = data.get(rowIndex);
         try {
            switch (columnIndex) {
               case 0:
                  return kujou.getId();
               case 1:
                  return kujou.getKujouType().getName();
               default:
                  return "Not support";
            }
         } catch (NullPointerException e) {
            return "";
         }
      }
   }
}
