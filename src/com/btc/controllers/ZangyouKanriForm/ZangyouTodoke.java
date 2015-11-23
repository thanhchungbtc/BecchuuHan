package com.btc.controllers.ZangyouKanriForm;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Zangyou;
import com.btc.repositoty.EmployeeRepository;
import com.btc.repositoty.GenericRepository;
import com.btc.repositoty.ZangyouRepository;
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
import java.util.Date;
import java.util.LinkedList;

public class ZangyouTodoke extends JDialog {
   private JPanel contentPane;
   private JButton buttonCancel;
   private JPanel mainContent;
   private JSpinner startSpinner;
   private JSpinner endSpinner;
   private JButton tourokuButton;
   private JXTable zangyouTable;
   private JComboBox employeeCombobox;
   private JTextField dateSearchTextField;
   private JLabel summaryLabel;

   private ZangyouRepository zangyouRepository;

   public ZangyouTodoke() {

      setTitle("残業届け");
      setContentPane(contentPane);

      setModal(true);
      setSize(700, 500);
      initializeData();

      setupTable();
      setupCombobox();
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

      tourokuButton.addActionListener(e -> buttonActionPerform(e));

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

   private void setupCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(EmployeeRepository.Instance().getBecchuuHandEmployees()
          .toArray());
      employeeCombobox.setModel(model);
      employeeCombobox.addActionListener(e -> {
         comboboxActionListener(e);
      });
   }

   private void setupRole() {
      // show only current employee if not admin
      if (RoleHelpers.getRole() != RoleHelpers.ADMIN) {
         employeeCombobox.setVisible(false);
         dateSearchTextField.setVisible(false);
      }
   }

   private void onCancel() {
// add your code here if necessary
      dispose();
   }

   public static void main(String[] args) {
      Config.setLookAndField();

      ZangyouTodoke dialog = new ZangyouTodoke();
      dialog.pack();
      dialog.setVisible(true);
      System.exit(0);
   }

   private void createUIComponents() {
      Date date = new Date();
      date.setHours(17);
      date.setMinutes(00);
      SpinnerDateModel spinnerDateModel = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
      startSpinner = new JSpinner(spinnerDateModel);
      JSpinner.DateEditor editor = new JSpinner.DateEditor(startSpinner, "HH:mm");

      startSpinner.setEditor(editor);

      date = new Date();
      date.setHours(17);
      date.setMinutes(00);
      SpinnerDateModel spinnerDateModel2 = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
      endSpinner = new JSpinner(spinnerDateModel2);
      JSpinner.DateEditor editor2 = new JSpinner.DateEditor(endSpinner, "HH:mm");

      endSpinner.setEditor(editor2);
   }

   private float getZangyouDuration() {
      Date start = (Date) startSpinner.getModel().getValue();
      Date end = (Date) endSpinner.getModel().getValue();
      Calendar calendar1 = Calendar.getInstance();
      Calendar calendar2 = Calendar.getInstance();
      calendar1.setTime(start);
      calendar2.setTime(end);
      long diffHour = calendar2.get(Calendar.HOUR) - calendar1.get(Calendar.HOUR);
      long diffMin = (calendar2.get(Calendar.MINUTE) - calendar1.get(Calendar.MINUTE));
      long minute = 60 * diffHour + (diffMin / 30) * 30;
      float duration = (float) minute / 60;
      return duration;
   }

   private void initializeData() {
      this.zangyouRepository = new ZangyouRepository();
   }

   private void setupTable() {
      zangyouTable.setModel(new ZangyouTableModel(zangyouRepository));
      zangyouTable.setRowHeight(25);
      hideColumn(zangyouTable, 0);
   }

   private void hideColumn(JXTable table, int columnIndex) {
      table.getColumn(columnIndex).setWidth(0);
      table.getColumn(columnIndex).setMinWidth(0);
      table.getColumn(columnIndex).setMaxWidth(0);
   }


   private void buttonActionPerform(ActionEvent e) {
      if (e.getSource() == tourokuButton) {
         float duration = getZangyouDuration();
         if (duration <= 0) {
            DialogHelpers.showError("エラー", "正しいデータ入力してください。");
            return;
         }

         Calendar startDate = Calendar.getInstance();
         startDate.setTime((Date) startSpinner.getModel().getValue());
         Calendar startCal = Calendar.getInstance();
         startCal.set(Calendar.HOUR, startDate.get(Calendar.HOUR));
         startCal.set(Calendar.MINUTE, startDate.get(Calendar.MINUTE));

         Calendar endDate = Calendar.getInstance();
         endDate.setTime((Date) endSpinner.getModel().getValue());
         Calendar endCal = Calendar.getInstance();
         endCal.set(Calendar.HOUR, endDate.get(Calendar.HOUR));
         endCal.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE));

         Zangyou zangyou = new Zangyou();
         zangyou.setEmployeeID(Config.UserID);

         zangyou.setStartDate(startCal.getTime());
         zangyou.setToDate(endCal.getTime());
         ZangyouTableModel model = (ZangyouTableModel) zangyouTable.getModel();
         model.insert(zangyou);
         populateSummary();
      }
   }

   private void populateSummary() {
      float sum = 0;
      for (int i = 0; i < zangyouTable.getRowCount(); i++) {
         float h = Float.parseFloat(zangyouTable.getValueAt(i, 4).toString());
         sum += h;
      }
      summaryLabel.setText("合計：" + sum + "h");
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
      zangyouTable.setRowFilter(RowFilter.andFilter(rowFilters));

      populateSummary();
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

   private class ZangyouTableModel extends BTCAbstractTableModel<Zangyou> {
      public ZangyouTableModel(GenericRepository repository) {
         super(repository);
      }

      @Override
      protected String[] initColumnNames() {
         return new String[]{
             "id", "担当者", "○○から", "○○まで", "合計"
         };
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
         Zangyou zangyou = data.get(rowIndex);
         try {
            switch (columnIndex) {
               case 0:
                  return zangyou.getId();
               case 1:
                  return zangyou.getEmployee().getName();
               case 2:
                  return Helpers.stringFromFullDate(zangyou.getStartDate());
               case 3:
                  return Helpers.stringFromFullDate(zangyou.getToDate());
               case 4:
                  return zangyou.getDuration();
               default:
                  return "Error";
            }
         } catch (NullPointerException e) {
            return "";
         }
      }
   }

}
