package com.btc.controllers.BukkenDetailsForm;

import com.btc.model.Bukken;

/**
 * Created by BTC on 11/14/15.
 */
public interface BukkenDetailsFormDelegate {
   public void submitData(Bukken bukken, boolean insert);
}
