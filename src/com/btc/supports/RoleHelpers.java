package com.btc.supports;

import com.btc.model.Employee;
import com.btc.repositoty.EmployeeRepository;

/**
 * Created by BTC on 11/22/15.
 */
public class RoleHelpers {

   public final static int ADMIN = 1;
   public final static int BECCHUU = 2;
   public final static int IPPAN = 3;
   public static int getRole() {
      switch (Config.RoleID) {
         case "AD": return RoleHelpers.ADMIN;
         case "BC": return RoleHelpers.BECCHUU;
         default: return RoleHelpers.IPPAN;
      }
   }

   public static Employee getLoginEmployee() {
      return EmployeeRepository.Instance().getById(Config.UserID);
   }
}
