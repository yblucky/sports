/*   1:    */ package ru.paradoxs.bitcoin.client;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ 
/*   5:    */ public class TransactionInfo
/*   6:    */ {
/*   7:    */   private String category;
/*   8:    */   private BigDecimal amount;
/*   9:    */   private BigDecimal fee;
/*  10:    */   private long confirmations;
/*  11:    */   private String txId;
/*  12:    */   private String otherAccount;
/*  13:    */   private String message;
/*  14:    */   private String to;
/*  15:    */   private long time;
/*  16:    */   
/*  17:    */   public String getCategory()
/*  18:    */   {
/*  19: 40 */     return this.category;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setCategory(String category)
/*  23:    */   {
/*  24: 44 */     this.category = category;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public BigDecimal getAmount()
/*  28:    */   {
/*  29: 48 */     return this.amount;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setAmount(BigDecimal amount)
/*  33:    */   {
/*  34: 52 */     this.amount = amount;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public BigDecimal getFee()
/*  38:    */   {
/*  39: 56 */     return this.fee;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setFee(BigDecimal fee)
/*  43:    */   {
/*  44: 60 */     this.fee = fee;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public long getConfirmations()
/*  48:    */   {
/*  49: 64 */     return this.confirmations;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setConfirmations(long confirmations)
/*  53:    */   {
/*  54: 68 */     this.confirmations = confirmations;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getTxId()
/*  58:    */   {
/*  59: 72 */     return this.txId;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setTxId(String txId)
/*  63:    */   {
/*  64: 76 */     this.txId = txId;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getOtherAccount()
/*  68:    */   {
/*  69: 80 */     return this.otherAccount;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setOtherAccount(String otherAccount)
/*  73:    */   {
/*  74: 84 */     this.otherAccount = otherAccount;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getMessage()
/*  78:    */   {
/*  79: 88 */     return this.message;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setMessage(String message)
/*  83:    */   {
/*  84: 92 */     this.message = message;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getTo()
/*  88:    */   {
/*  89: 96 */     return this.to;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setTo(String to)
/*  93:    */   {
/*  94:100 */     this.to = to;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public long getTime()
/*  98:    */   {
/*  99:104 */     return this.time;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setTime(long time)
/* 103:    */   {
/* 104:108 */     this.time = time;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String toString()
/* 108:    */   {
/* 109:113 */     return "TransactionInfo{category='" + this.category + '\'' + ", amount=" + this.amount + ", time=" + this.time + ", fee=" + this.fee + ", confirmations=" + this.confirmations + ", txId='" + this.txId + '\'' + ", otherAccount='" + this.otherAccount + '\'' + ", message='" + this.message + '\'' + ", to='" + this.to + '\'' + '}';
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.TransactionInfo
 * JD-Core Version:    0.7.0.1
 */