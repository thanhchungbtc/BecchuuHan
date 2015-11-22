package com.btc.controllers.EmployeeKanriForm;

import com.btc.model.Employee;
import com.btc.repositoty.GenericRepository;
import com.btc.viewModel.BTCAbstractTableModel;

/**
 * Created by BTC on 11/22/15.
 */
public class EmployeeKanriTableModel extends BTCAbstractTableModel<Employee> {

   @Override
   protected String[] initColumnNames() {
       return new String[]{
        "社員番号", "名前"
      };
   }

   public EmployeeKanriTableModel(GenericRepository<Employee> repository) {
      super(repository);
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      Employee employee = data.get(rowIndex);
      try {
         switch (columnIndex) {
            case 0:
               return employee.getId();
            case 1:
               return employee.getName();
            default:
               return "Error";
         }
      } catch (Exception e) {
         return "";
      }
   }
}
