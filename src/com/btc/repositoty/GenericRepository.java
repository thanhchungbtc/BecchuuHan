package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Kujou;
import com.btc.model.KujouType;
import com.btc.supports.Helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by BTC on 11/22/15.
 */
public abstract class GenericRepository<T> {
   protected List<T> data;
   protected Connection connection;

   protected abstract void initData();

   public List<T> getListWithRefresh(boolean refresh) {
      if (data != null) {
         if (refresh) data.clear();
         else return data;
      }
      initData();
      return data;
   }

   public abstract T getById(Object id);

   public abstract T insert(T t) throws SQLException;

   public abstract T update(T t) throws SQLException;

   public abstract boolean delete(T t) throws SQLException;
}
