package com.btc.controllers.BecchuuDetailsForm;

import com.btc.model.Becchuu;

public interface BecchuuDetailsDelegate {
   public void submitData(BecchuuDetailsForm becchuuDetailsForm, Becchuu becchuu);

   public void movePrevious(BecchuuDetailsForm becchuuDetailsForm);

   public void moveNext(BecchuuDetailsForm becchuuDetailsForm);
}
