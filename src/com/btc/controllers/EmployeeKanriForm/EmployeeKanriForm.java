package com.btc.controllers.EmployeeKanriForm;

import com.btc.model.Employee;
import com.btc.repositoty.EmployeeRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Created by BTC on 11/22/15.
 */
public class EmployeeKanriForm  extends JDialog implements ActionListener {

   EmployeeRepository repository;
   Employee selectedEmployee;

   public EmployeeKanriForm() {
      setContentPane(contentPane);
      initializeData();
      setupTable();
      setupEvents();

      pack();
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


   void initializeData() {
      repository = EmployeeRepository.Instance();
   }

   void setupTable() {
      EmployeeKanriTableModel model = new EmployeeKanriTableModel(this.repository);
      employeeTable.setModel(model);
      employeeTable.setRowHeight(25);
      employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               tableSelectionChange(e);
            }
         }
      });
   }

   void populateField(Employee employee) {
      if (employee == null) {
         shainBangouTextField.setText(null);
         nameTextField.setText(null);
         shainBangouTextField.setEditable(true);
         return;
      }
      shainBangouTextField.setEditable(false);
      shainBangouTextField.setText(employee.getId());
      nameTextField.setText(employee.getName());
   }

   void setupEvents() {
      addNewButton.addActionListener(this);
      cancelButton.addActionListener(this);
      saveButton.addActionListener(this);
      searchTextField.addKeyListener(new TextFieldKeyAdapter());
   }

   Employee getEmployeeFromField(Employee employee) {
      employee.setId(shainBangouTextField.getText().trim());
      employee.setName(nameTextField.getText().trim());
      return employee;
   }
   // Events
   void tableSelectionChange(ListSelectionEvent e) {
      int row = employeeTable.getSelectedRow();
      if (row < 0) return;
      String id = employeeTable.getValueAt(row, 0).toString();
      selectedEmployee = repository.getById(id);
      populateField(selectedEmployee);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object source = e.getSource();
      // 追加
      if (source == addNewButton) {
         selectedEmployee = null;
         populateField(selectedEmployee);
      }
      // キャンセル
      else if (source == cancelButton) {
         tableSelectionChange(null);
      }
      // 保存
      else if (source == saveButton) {
         boolean isInsert = false;
         if (selectedEmployee == null) {
            isInsert = true;
            selectedEmployee = new Employee();
         }
         selectedEmployee = getEmployeeFromField(selectedEmployee);

         EmployeeKanriTableModel model = (EmployeeKanriTableModel)employeeTable.getModel();
         if (isInsert)
            model.insert(selectedEmployee);
         else {
            model.update(selectedEmployee, employeeTable.getSelectedRow());
         }
      }
   }

   // End Events

   private class TextFieldKeyAdapter implements KeyListener {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {

      }

      @Override
      public void keyReleased(KeyEvent e) {
         if (!(e.getSource() instanceof JTextField)) return;
         JTextField source = (JTextField) e.getSource();
         RowFilter textFilter = null;
         if (source.getText().trim().length() == 0) {
            textFilter = null;
         } else {
            String regex = Helpers.convertGlobToRegExCaseInsensitive(source.getText());
            textFilter = RowFilter.regexFilter(regex);
         }
         employeeTable.setRowFilter(textFilter);
      }
   }

   public static void main(String[] args) {
      Config.setLookAndField();
      EmployeeKanriForm employeeKanriForm = new EmployeeKanriForm();
      employeeKanriForm.pack();
      employeeKanriForm.setVisible(true);
   }

   private JPanel contentPane;
   private JPanel headerPanel;
   private JPanel mainContentPanel;
   private JTextField searchTextField;
   private JPanel leftPane;
   private JPanel rightPane;
   private JPanel detailsPanel;
   private JTextField shainBangouTextField;
   private JTextField nameTextField;
   private JButton saveButton;
   private JButton cancelButton;
   private JButton addNewButton;
   private JXTable employeeTable;
}
