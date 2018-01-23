/*  1:   */ package ru.paradoxs.bitcoin.http.exceptions;
/*  2:   */ 
/*  3:   */ public class HttpSessionException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public HttpSessionException(String message)
/*  7:   */   {
/*  8:20 */     super(message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public HttpSessionException(Throwable ex)
/* 12:   */   {
/* 13:24 */     super(ex);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.http.exceptions.HttpSessionException
 * JD-Core Version:    0.7.0.1
 */