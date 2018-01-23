/*   1:    */ package ru.paradoxs.bitcoin.client;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ 
/*   5:    */ public class ServerInfo
/*   6:    */ {
/*   7: 25 */   private String version = "";
/*   8: 26 */   private BigDecimal balance = BigDecimal.ZERO;
/*   9: 27 */   private long blocks = 0L;
/*  10: 28 */   private int connections = 0;
/*  11: 29 */   private boolean isGenerateCoins = false;
/*  12: 30 */   private int usedCPUs = -1;
/*  13: 31 */   private BigDecimal difficulty = BigDecimal.ZERO;
/*  14: 32 */   private long HashesPerSecond = 0L;
/*  15:    */   
/*  16:    */   public String getVersion()
/*  17:    */   {
/*  18: 35 */     return this.version;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setVersion(String version)
/*  22:    */   {
/*  23: 39 */     this.version = version;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public BigDecimal getBalance()
/*  27:    */   {
/*  28: 43 */     return this.balance;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setBalance(BigDecimal balance)
/*  32:    */   {
/*  33: 47 */     this.balance = balance;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public long getBlocks()
/*  37:    */   {
/*  38: 51 */     return this.blocks;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setBlocks(long blocks)
/*  42:    */   {
/*  43: 55 */     this.blocks = blocks;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getConnections()
/*  47:    */   {
/*  48: 59 */     return this.connections;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setConnections(int connections)
/*  52:    */   {
/*  53: 63 */     this.connections = connections;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isIsGenerateCoins()
/*  57:    */   {
/*  58: 67 */     return this.isGenerateCoins;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIsGenerateCoins(boolean isGenerateCoins)
/*  62:    */   {
/*  63: 71 */     this.isGenerateCoins = isGenerateCoins;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getUsedCPUs()
/*  67:    */   {
/*  68: 75 */     return this.usedCPUs;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setUsedCPUs(int usedCPUs)
/*  72:    */   {
/*  73: 79 */     this.usedCPUs = usedCPUs;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public BigDecimal getDifficulty()
/*  77:    */   {
/*  78: 83 */     return this.difficulty;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setDifficulty(BigDecimal difficulty)
/*  82:    */   {
/*  83: 87 */     this.difficulty = difficulty;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public long getHashesPerSecond()
/*  87:    */   {
/*  88: 91 */     return this.HashesPerSecond;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setHashesPerSecond(long HashesPerSecond)
/*  92:    */   {
/*  93: 95 */     this.HashesPerSecond = HashesPerSecond;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String toString()
/*  97:    */   {
/*  98:100 */     return "ServerInfo{version='" + this.version + '\'' + ", balance=" + this.balance + ", blocks=" + this.blocks + ", connections=" + this.connections + ", isGenerateCoins=" + this.isGenerateCoins + ", usedCPUs=" + this.usedCPUs + ", difficulty=" + this.difficulty + ", HashesPerSecond=" + this.HashesPerSecond + '}';
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.ServerInfo
 * JD-Core Version:    0.7.0.1
 */