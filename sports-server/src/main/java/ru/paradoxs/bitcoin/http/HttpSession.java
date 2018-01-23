/*  1:   */ package ru.paradoxs.bitcoin.http;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URI;
/*  5:   */ import net.sf.json.JSONException;
/*  6:   */ import net.sf.json.JSONObject;
/*  7:   */ import net.sf.json.util.JSONTokener;
/*  8:   */ import org.apache.commons.httpclient.Credentials;
/*  9:   */ import org.apache.commons.httpclient.HttpClient;
/* 10:   */ import org.apache.commons.httpclient.HttpException;
/* 11:   */ import org.apache.commons.httpclient.HttpState;
/* 12:   */ import org.apache.commons.httpclient.HttpStatus;
/* 13:   */ import org.apache.commons.httpclient.auth.AuthScope;
/* 14:   */ import org.apache.commons.httpclient.methods.PostMethod;
/* 15:   */ import org.apache.commons.httpclient.methods.RequestEntity;
/* 16:   */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/* 17:   */ import ru.paradoxs.bitcoin.http.exceptions.HttpSessionException;
/* 18:   */ 
/* 19:   */ public class HttpSession
/* 20:   */ {
/* 21:   */   private static final String JSON_CONTENT_TYPE = "application/json";
/* 22:   */   private static final String POST_CONTENT_TYPE = "text/plain";
/* 23:46 */   private HttpClient client = null;
/* 24:47 */   private URI uri = null;
/* 25:48 */   private Credentials credentials = null;
/* 26:   */   
/* 27:   */   public HttpSession(URI uri, Credentials credentials)
/* 28:   */   {
/* 29:51 */     this.uri = uri;
/* 30:52 */     this.credentials = credentials;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public JSONObject sendAndReceive(JSONObject message)
/* 34:   */   {
/* 35:56 */     PostMethod method = new PostMethod(this.uri.toString());
/* 36:   */     try
/* 37:   */     {
/* 38:59 */       method.setRequestHeader("Content-Type", "text/plain");
/* 39:   */       
/* 40:61 */       RequestEntity requestEntity = new StringRequestEntity(message.toString(), "application/json", null);
/* 41:62 */       method.setRequestEntity(requestEntity);
/* 42:   */       
/* 43:64 */       getHttpClient().executeMethod(method);
/* 44:65 */       int statusCode = method.getStatusCode();
/* 45:67 */       if (statusCode != 200) {
/* 46:68 */         throw new HttpSessionException("HTTP Status - " + HttpStatus.getStatusText(statusCode) + " (" + statusCode + ")");
/* 47:   */       }
/* 48:71 */       JSONTokener tokener = new JSONTokener(method.getResponseBodyAsString());
/* 49:72 */       Object rawResponseMessage = tokener.nextValue();
/* 50:73 */       JSONObject response = (JSONObject)rawResponseMessage;
/* 51:75 */       if (response == null) {
/* 52:76 */         throw new HttpSessionException("Invalid response type");
/* 53:   */       }
/* 54:79 */       return response;
/* 55:   */     }
/* 56:   */     catch (HttpException e)
/* 57:   */     {
/* 58:81 */       throw new HttpSessionException(e);
/* 59:   */     }
/* 60:   */     catch (IOException e)
/* 61:   */     {
/* 62:83 */       throw new HttpSessionException(e);
/* 63:   */     }
/* 64:   */     catch (JSONException e)
/* 65:   */     {
/* 66:85 */       throw new HttpSessionException(e);
/* 67:   */     }
/* 68:   */     finally
/* 69:   */     {
/* 70:87 */       method.releaseConnection();
/* 71:   */     }
/* 72:   */   }
/* 73:   */   
/* 74:   */   private HttpClient getHttpClient()
/* 75:   */   {
/* 76:92 */     if (this.client == null)
/* 77:   */     {
/* 78:93 */       this.client = new HttpClient();
/* 79:94 */       this.client.getState().setCredentials(AuthScope.ANY, this.credentials);
/* 80:   */     }
/* 81:97 */     return this.client;
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar
 * Qualified Name:     ru.paradoxs.bitcoin.http.HttpSession
 * JD-Core Version:    0.7.0.1
 */