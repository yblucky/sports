/*  1:   */ package ru.paradoxs.bitcoin.client.exceptions;
/*  2:   */ 
/*  3:   */ public class BitcoinClientException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public BitcoinClientException(String message)
/*  7:   */   {
/*  8:20 */     super(message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public BitcoinClientException(String message, Throwable ex)
/* 12:   */   {
/* 13:24 */     super(message, ex);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.client.exceptions.BitcoinClientException
 * JD-Core Version:    0.7.0.1
 */