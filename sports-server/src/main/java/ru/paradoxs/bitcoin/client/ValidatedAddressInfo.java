/*  1:   */ package ru.paradoxs.bitcoin.client;
/*  2:   */ 
/*  3:   */ public class ValidatedAddressInfo
/*  4:   */ {
/*  5:   */   private boolean isValid;
/*  6:   */   private boolean isMine;
/*  7:   */   private String address;
/*  8:   */   
/*  9:   */   public boolean getIsValid()
/* 10:   */   {
/* 11:24 */     return this.isValid;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void setIsValid(boolean isValid)
/* 15:   */   {
/* 16:28 */     this.isValid = isValid;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean getIsMine()
/* 20:   */   {
/* 21:32 */     return this.isMine;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setIsMine(boolean isMine)
/* 25:   */   {
/* 26:36 */     this.isMine = isMine;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getAddress()
/* 30:   */   {
/* 31:40 */     return this.address;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setAddress(String address)
/* 35:   */   {
/* 36:44 */     this.address = address;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.ValidatedAddressInfo
 * JD-Core Version:    0.7.0.1
 */