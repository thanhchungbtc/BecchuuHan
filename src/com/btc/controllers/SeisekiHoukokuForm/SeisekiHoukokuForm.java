package com.btc.controllers.SeisekiHoukokuForm;

import com.btc.model.Kujou;
import com.btc.repositoty.EmployeeRepository;
import com.btc.repositoty.GenericRepository;
import com.btc.repositoty.KujouRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import com.btc.supports.RoleHelpers;
import com.btc.viewModel.BTCAbstractTableModel;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.LinkedList;

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
   private JTextField dateSearchTextField;

   private KujouRepository kujouRepository;
   private EmployeeRepository employeeRepository;

   public SeisekiHoukokuForm() {
      setTitle("成績報告");
      setContentPane(contentPane);
      setModal(true);
      getRootPane().setDefaultButton(buttonOK);
      pack();
      initializeData();
      setupCombobox();
      setupTable();
      populateData();
      setupEvents();

      populateDefaultData();
      dateSearchTextField.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            dateSearchTextFieldKeyReleased(e);
         }
      });
   }

   private void populateDefaultData() {
      employeeCombobox.getModel().setSelectedItem(RoleHelpers.getLoginEmployee());
      setupRole();
      comboboxActionListener(new ActionEvent(employeeCombobox, 0, null));
      Calendar calendar = Calendar.getInstance();
      String term = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1);
      dateSearchTextField.setText(term);
      dateSearchTextFieldKeyReleased(new KeyEvent(dateSearchTextField, 0, 0, 0, 0));
   }

   public void showDialog() {
      setModal(true);
      pack();
      setLocationRelativeTo(getOwner());
      setVisible(true);
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
   }

   private void setupCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(employeeRepository.getBecchuuHandEmployees()
          .toArray());
      employeeCombobox.setModel(model);
      employeeCombobox.addActionListener(e -> {
         comboboxActionListener(e);
      });
   }

   private void setupTable() {
      kujouTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      kujouTable.setRowHeight(25);
      kujouTable.setModel(new KujouTableModel(this.kujouRepository));
      hideColumn(kujouTable, 0); // hide id column
      hideColumn(kujouTable, 1); // hide kenshusha column
      hideColumn(kujouTable, 6); // hide hiku point column

   }

   private void hideColumn(JXTable table, int columnIndex) {
      table.getColumn(columnIndex).setWidth(0);
      table.getColumn(columnIndex).setMinWidth(0);
      table.getColumn(columnIndex).setMaxWidth(0);
   }

   private void setupRole() {
      // show only current employee if not admin
      if (RoleHelpers.getRole() != RoleHelpers.ADMIN) {
         employeeCombobox.setVisible(false);
         dateSearchTextField.setVisible(false);
      }
   }

   private void populateKujouSummary() {

      int point = 0;
      for (int i = 0; i < kujouTable.getRowCount(); i++) {
         int p = Integer.parseInt(kujouTable.getValueAt(i, 6).toString());
         point += p;
      }
      String text = "- 苦情件数：" + (kujouTable.getRowCount() == 0 ? "苦情無し" : kujouTable.getRowCount()) +
          "<br/> - ポイント引く：" + point;
      String jouhouDescription = String.format("<html><div WIDTH=%d>%s</div><html>", kujouSummaryLabel.getWidth() - 10,
          text);
      kujouSummaryLabel.setText(jouhouDescription);
      employeeNameLabel.setText("苦情台帳 - 社員：" + employeeCombobox.getSelectedItem().toString());
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

   RowFilter textFilter;
   RowFilter typeFilter;
   LinkedList<RowFilter<TableModel, Object>> rowFilters = new LinkedList<>();

   private void filterTable() {
      rowFilters.clear();
      if (textFilter != null) {
         rowFilters.add(textFilter);
      }
      if (typeFilter != null) {
         rowFilters.add(typeFilter);
      }
      kujouTable.setRowFilter(RowFilter.andFilter(rowFilters));

      populateKujouSummary();
   }

   private void comboboxActionListener(ActionEvent e) {
      Object source = e.getSource();
      RowFilter employeeFilter;
      if (source == employeeCombobox) {
         typeFilter = RowFilter.regexFilter(employeeCombobox.getModel().getSelectedItem().toString(), 1);
         filterTable();
      }
   }

   private void dateSearchTextFieldKeyReleased(KeyEvent e) {
      if (!(e.getSource() instanceof JTextField)) return;
      JTextField source = (JTextField) e.getSource();

      if (source.getText().trim().length() == 0) {
         textFilter = null;
      } else {
         String regex = Helpers.convertGlobToRegExCaseInsensitive(source.getText());
         textFilter = RowFilter.regexFilter(regex);
      }
      filterTable();
   }


   public static void main(String[] args) {
      Config.setLookAndField();
      SeisekiHoukokuForm dialog = new SeisekiHoukokuForm();
      dialog.setSize(new Dimension(500, 300));
      dialog.setVisible(true);
      System.exit(0);
   }

   private class KujouTableModel extends BTCAbstractTableModel<Kujou> {
      public KujouTableModel(GenericRepository repository) {
         super(repository);
      }

      @Override
      protected String[] initColumnNames() {
         return new String[]{
             "id", "担当者", "工事番号", "施主名", "苦情タイプ", "発生日", "引くポイント"
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
                  return kujou.getKenshuuSha().getName();
               case 2:
                  return kujou.getBecchuu().getKoujibangou();
               case 3:
                  return kujou.getBecchuu().getBukken().getName();
               case 4:

                  return kujou.getKujouType().getName();
               case 5:
                  return Helpers.stringFromDate(kujou.getReceiptDate());
               case 6:
                  return kujou.getKujouType().getPoint();
               default:
                  return "Error";
            }
         } catch (Exception e) {
            return "";
         }
      }
   }
}
