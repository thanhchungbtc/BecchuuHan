package com.btc.repositoty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
