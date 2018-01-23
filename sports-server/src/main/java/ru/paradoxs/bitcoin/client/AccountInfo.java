/*  1:   */ package ru.paradoxs.bitcoin.client;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ 
/*  5:   */ public class AccountInfo
/*  6:   */ {
/*  7:21 */   private String account = "";
/*  8:22 */   private BigDecimal amount = BigDecimal.ZERO;
/*  9:23 */   private long confirmations = 0L;
/* 10:   */   
/* 11:   */   public String getAccount()
/* 12:   */   {
/* 13:26 */     return this.account;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setAccount(String account)
/* 17:   */   {
/* 18:30 */     this.account = account;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public BigDecimal getAmount()
/* 22:   */   {
/* 23:34 */     return this.amount;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setAmount(BigDecimal amount)
/* 27:   */   {
/* 28:38 */     this.amount = amount;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public long getConfirmations()
/* 32:   */   {
/* 33:42 */     return this.confirmations;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setConfirmations(long confirmations)
/* 37:   */   {
/* 38:46 */     this.confirmations = confirmations;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:51 */     return "AccountInfo{account='" + this.account + '\'' + ", amount=" + this.amount + ", confirmations=" + this.confirmations + '}';
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.AccountInfo
 * JD-Core Version:    0.7.0.1
 */