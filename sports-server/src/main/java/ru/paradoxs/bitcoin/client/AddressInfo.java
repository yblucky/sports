/*  1:   */ package ru.paradoxs.bitcoin.client;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ 
/*  5:   */ public class AddressInfo
/*  6:   */ {
/*  7:21 */   private String address = "";
/*  8:22 */   private String account = "";
/*  9:23 */   private BigDecimal amount = BigDecimal.ZERO;
/* 10:24 */   private long confirmations = 0L;
/* 11:   */   
/* 12:   */   public String getAddress()
/* 13:   */   {
/* 14:27 */     return this.address;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setAddress(String address)
/* 18:   */   {
/* 19:31 */     this.address = address;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getAccount()
/* 23:   */   {
/* 24:35 */     return this.account;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setAccount(String account)
/* 28:   */   {
/* 29:39 */     this.account = account;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public BigDecimal getAmount()
/* 33:   */   {
/* 34:43 */     return this.amount;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setAmount(BigDecimal amount)
/* 38:   */   {
/* 39:47 */     this.amount = amount;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public long getConfirmations()
/* 43:   */   {
/* 44:51 */     return this.confirmations;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setConfirmations(long confirmations)
/* 48:   */   {
/* 49:55 */     this.confirmations = confirmations;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:60 */     return "AddressInfo{address='" + this.address + '\'' + ", account='" + this.account + '\'' + ", amount=" + this.amount + ", confirmations=" + this.confirmations + '}';
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.AddressInfo
 * JD-Core Version:    0.7.0.1
 */