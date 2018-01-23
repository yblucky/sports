/*  1:   */ package ru.paradoxs.bitcoin.client;
/*  2:   */ 
/*  3:   */ public class WorkInfo
/*  4:   */ {
/*  5:   */   private String midstate;
/*  6:   */   private String data;
/*  7:   */   private String hash1;
/*  8:   */   private String target;
/*  9:   */   
/* 10:   */   public String getMidstate()
/* 11:   */   {
/* 12:31 */     return this.midstate;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void setMidstate(String midstate)
/* 16:   */   {
/* 17:35 */     this.midstate = midstate;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getData()
/* 21:   */   {
/* 22:39 */     return this.data;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setData(String data)
/* 26:   */   {
/* 27:43 */     this.data = data;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getHash1()
/* 31:   */   {
/* 32:47 */     return this.hash1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setHash1(String hash1)
/* 36:   */   {
/* 37:51 */     this.hash1 = hash1;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getTarget()
/* 41:   */   {
/* 42:55 */     return this.target;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setTarget(String target)
/* 46:   */   {
/* 47:59 */     this.target = target;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:64 */     return "WorkInfo{midstate='" + this.midstate + '\'' + ", data='" + this.data + '\'' + ", hash1='" + this.hash1 + '\'' + ", target='" + this.target + '\'' + '}';
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.WorkInfo
 * JD-Core Version:    0.7.0.1
 */