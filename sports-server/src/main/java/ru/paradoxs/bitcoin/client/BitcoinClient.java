/*   1:    */ package ru.paradoxs.bitcoin.client;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.net.URISyntaxException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.UUID;
/*   9:    */ import net.sf.json.JSONArray;
/*  10:    */ import net.sf.json.JSONException;
/*  11:    */ import net.sf.json.JSONObject;
/*  12:    */ import org.apache.commons.httpclient.Credentials;
/*  13:    */ import org.apache.commons.httpclient.UsernamePasswordCredentials;
/*  14:    */ import ru.paradoxs.bitcoin.client.exceptions.BitcoinClientException;
/*  15:    */ import ru.paradoxs.bitcoin.http.HttpSession;
/*  16:    */ 
/*  17:    */ public class BitcoinClient
/*  18:    */ {
/*  19:    */   private static BigDecimal getBigDecimal(JSONObject jsonObject, String key)
/*  20:    */     throws JSONException
/*  21:    */   {
/*  22: 49 */     String string = jsonObject.getString(key);
/*  23: 50 */     BigDecimal bigDecimal = new BigDecimal(string);
/*  24: 51 */     return bigDecimal;
/*  25:    */   }
/*  26:    */   
/*  27: 54 */   private HttpSession session = null;
/*  28:    */   
/*  29:    */   public BitcoinClient(String host, String login, String password, int port)
/*  30:    */   {
/*  31:    */     try
/*  32:    */     {
/*  33: 66 */       Credentials credentials = new UsernamePasswordCredentials(login, password);
/*  34: 67 */       URI uri = new URI("http", null, host, port, null, null, null);
/*  35: 68 */       this.session = new HttpSession(uri, credentials);
/*  36:    */     }
/*  37:    */     catch (URISyntaxException e)
/*  38:    */     {
/*  39: 70 */       throw new BitcoinClientException("This host probably doesn't have correct syntax: " + host, e);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public BitcoinClient(String host, String login, String password)
/*  44:    */   {
/*  45: 82 */     this(host, login, password, 8332);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<String> getAddressesByAccount(String account)
/*  49:    */   {
/*  50: 93 */     if (account == null) {
/*  51: 94 */       account = "";
/*  52:    */     }
/*  53:    */     try
/*  54:    */     {
/*  55: 98 */       JSONArray parameters = new JSONArray().element(account);
/*  56: 99 */       JSONObject request = createRequest("getaddressesbyaccount", parameters);
/*  57:100 */       JSONObject response = this.session.sendAndReceive(request);
/*  58:101 */       JSONArray result = (JSONArray)response.get("result");
/*  59:102 */       int size = result.size();
/*  60:    */       
/*  61:104 */       List<String> list = new ArrayList();
/*  62:106 */       for (int i = 0; i < size; i++) {
/*  63:107 */         list.add(result.getString(i));
/*  64:    */       }
/*  65:110 */       return list;
/*  66:    */     }
/*  67:    */     catch (JSONException e)
/*  68:    */     {
/*  69:112 */       throw new BitcoinClientException("Got incorrect JSON for this account: " + account, e);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public BigDecimal getBalance()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:123 */       JSONObject request = createRequest("getbalance");
/*  78:124 */       JSONObject response = this.session.sendAndReceive(request);
/*  79:    */       
/*  80:126 */       return getBigDecimal(response, "result");
/*  81:    */     }
/*  82:    */     catch (JSONException e)
/*  83:    */     {
/*  84:128 */       throw new BitcoinClientException("Exception when getting balance", e);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public BigDecimal getBalance(String account)
/*  89:    */   {
/*  90:140 */     if (account == null) {
/*  91:141 */       account = "";
/*  92:    */     }
/*  93:    */     try
/*  94:    */     {
/*  95:145 */       JSONArray parameters = new JSONArray().element(account);
/*  96:146 */       JSONObject request = createRequest("getbalance", parameters);
/*  97:147 */       JSONObject response = this.session.sendAndReceive(request);
/*  98:    */       
/*  99:149 */       return getBigDecimal(response, "result");
/* 100:    */     }
/* 101:    */     catch (JSONException e)
/* 102:    */     {
/* 103:151 */       throw new BitcoinClientException("Exception when getting balance", e);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getBlockCount()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:163 */       JSONObject request = createRequest("getblockcount");
/* 112:164 */       JSONObject response = this.session.sendAndReceive(request);
/* 113:    */       
/* 114:166 */       return response.getInt("result");
/* 115:    */     }
/* 116:    */     catch (JSONException e)
/* 117:    */     {
/* 118:168 */       throw new BitcoinClientException("Exception when getting block count", e);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getBlockNumber()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:179 */       JSONObject request = createRequest("getblocknumber");
/* 127:180 */       JSONObject response = this.session.sendAndReceive(request);
/* 128:    */       
/* 129:182 */       return response.getInt("result");
/* 130:    */     }
/* 131:    */     catch (JSONException e)
/* 132:    */     {
/* 133:184 */       throw new BitcoinClientException("Exception when getting the block number", e);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getConnectionCount()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:195 */       JSONObject request = createRequest("getconnectioncount");
/* 142:196 */       JSONObject response = this.session.sendAndReceive(request);
/* 143:    */       
/* 144:198 */       return response.getInt("result");
/* 145:    */     }
/* 146:    */     catch (JSONException e)
/* 147:    */     {
/* 148:200 */       throw new BitcoinClientException("Exception when getting the number of connections", e);
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public long getHashesPerSecond()
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:212 */       JSONObject request = createRequest("gethashespersec");
/* 157:213 */       JSONObject response = this.session.sendAndReceive(request);
/* 158:    */       
/* 159:215 */       return response.getLong("result");
/* 160:    */     }
/* 161:    */     catch (JSONException e)
/* 162:    */     {
/* 163:217 */       throw new BitcoinClientException("Exception when getting the number of calculated hashes per second", e);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getDifficulty()
/* 168:    */   {
/* 169:    */     try
/* 170:    */     {
/* 171:228 */       JSONObject request = createRequest("getdifficulty");
/* 172:229 */       JSONObject response = this.session.sendAndReceive(request);
/* 173:    */       
/* 174:231 */       return getBigDecimal(response, "result");
/* 175:    */     }
/* 176:    */     catch (JSONException e)
/* 177:    */     {
/* 178:233 */       throw new BitcoinClientException("Exception when getting the difficulty", e);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean getGenerate()
/* 183:    */   {
/* 184:    */     try
/* 185:    */     {
/* 186:244 */       JSONObject request = createRequest("getgenerate");
/* 187:245 */       JSONObject response = this.session.sendAndReceive(request);
/* 188:    */       
/* 189:247 */       return response.getBoolean("result");
/* 190:    */     }
/* 191:    */     catch (JSONException e)
/* 192:    */     {
/* 193:249 */       throw new BitcoinClientException("Exception when getting whether the server is generating coins or not", e);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setGenerate(boolean isGenerate, int processorsCount)
/* 198:    */   {
/* 199:    */     try
/* 200:    */     {
/* 201:261 */       JSONArray parameters = new JSONArray().element(isGenerate).element(processorsCount);
/* 202:262 */       JSONObject request = createRequest("setgenerate", parameters);
/* 203:263 */       this.session.sendAndReceive(request);
/* 204:    */     }
/* 205:    */     catch (JSONException e)
/* 206:    */     {
/* 207:265 */       throw new BitcoinClientException("Exception when setting whether the server is generating coins or not", e);
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public ServerInfo getServerInfo()
/* 212:    */   {
/* 213:    */     try
/* 214:    */     {
/* 215:276 */       JSONObject request = createRequest("getinfo");
/* 216:277 */       JSONObject response = this.session.sendAndReceive(request);
/* 217:278 */       JSONObject result = (JSONObject)response.get("result");
/* 218:    */       
/* 219:280 */       ServerInfo info = new ServerInfo();
/* 220:281 */       info.setBalance(getBigDecimal(result, "balance"));
/* 221:282 */       info.setBlocks(result.getLong("blocks"));
/* 222:283 */       info.setConnections(result.getInt("connections"));
/* 223:284 */       info.setDifficulty(getBigDecimal(result, "difficulty"));
/* 224:285 */       info.setHashesPerSecond(result.getLong("hashespersec"));
/* 225:286 */       info.setIsGenerateCoins(result.getBoolean("generate"));
/* 226:287 */       info.setUsedCPUs(result.getInt("genproclimit"));
/* 227:288 */       info.setVersion(result.getString("version"));
/* 228:    */       
/* 229:290 */       return info;
/* 230:    */     }
/* 231:    */     catch (JSONException e)
/* 232:    */     {
/* 233:292 */       throw new BitcoinClientException("Exception when getting the server info", e);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getAccount(String address)
/* 238:    */   {
/* 239:    */     try
/* 240:    */     {
/* 241:305 */       JSONArray parameters = new JSONArray().element(address);
/* 242:306 */       JSONObject request = createRequest("getaccount", parameters);
/* 243:307 */       JSONObject response = this.session.sendAndReceive(request);
/* 244:    */       
/* 245:309 */       return response.getString("result");
/* 246:    */     }
/* 247:    */     catch (JSONException e)
/* 248:    */     {
/* 249:311 */       throw new BitcoinClientException("Exception when getting the account associated with this address: " + address, e);
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setAccountForAddress(String address, String account)
/* 254:    */   {
/* 255:    */     try
/* 256:    */     {
/* 257:324 */       JSONArray parameters = new JSONArray().element(address).element(account);
/* 258:325 */       JSONObject request = createRequest("setaccount", parameters);
/* 259:326 */       this.session.sendAndReceive(request);
/* 260:    */     }
/* 261:    */     catch (JSONException e)
/* 262:    */     {
/* 263:328 */       throw new BitcoinClientException("Exception when setting the account associated with a given address", e);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getAccountAddress(String account)
/* 268:    */   {
/* 269:342 */     if (account == null) {
/* 270:343 */       account = "";
/* 271:    */     }
/* 272:    */     try
/* 273:    */     {
/* 274:347 */       JSONArray parameters = new JSONArray().element(account);
/* 275:348 */       JSONObject request = createRequest("getaccountaddress", parameters);
/* 276:349 */       JSONObject response = this.session.sendAndReceive(request);
/* 277:    */       
/* 278:351 */       return response.getString("result");
/* 279:    */     }
/* 280:    */     catch (JSONException e)
/* 281:    */     {
/* 282:353 */       throw new BitcoinClientException("Exception when getting the new bitcoin address for receiving payments", e);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public BigDecimal getReceivedByAddress(String address, long minimumConfirmations)
/* 287:    */   {
/* 288:    */     try
/* 289:    */     {
/* 290:366 */       JSONArray parameters = new JSONArray().element(address).element(minimumConfirmations);
/* 291:367 */       JSONObject request = createRequest("getreceivedbyaddress", parameters);
/* 292:368 */       JSONObject response = this.session.sendAndReceive(request);
/* 293:    */       
/* 294:370 */       return getBigDecimal(response, "result");
/* 295:    */     }
/* 296:    */     catch (JSONException e)
/* 297:    */     {
/* 298:372 */       throw new BitcoinClientException("Exception when getting the total amount received by bitcoinaddress", e);
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public BigDecimal getReceivedByAccount(String account, long minimumConfirmations)
/* 303:    */   {
/* 304:    */     try
/* 305:    */     {
/* 306:386 */       JSONArray parameters = new JSONArray().element(account).element(minimumConfirmations);
/* 307:387 */       JSONObject request = createRequest("getreceivedbyaccount", parameters);
/* 308:388 */       JSONObject response = this.session.sendAndReceive(request);
/* 309:    */       
/* 310:390 */       return getBigDecimal(response, "result");
/* 311:    */     }
/* 312:    */     catch (JSONException e)
/* 313:    */     {
/* 314:392 */       throw new BitcoinClientException("Exception when getting the total amount received for account: " + account, e);
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public String help(String command)
/* 319:    */   {
/* 320:    */     try
/* 321:    */     {
/* 322:404 */       JSONArray parameters = new JSONArray().element(command);
/* 323:405 */       JSONObject request = createRequest("help", parameters);
/* 324:406 */       JSONObject response = this.session.sendAndReceive(request);
/* 325:    */       
/* 326:408 */       return response.getString("result");
/* 327:    */     }
/* 328:    */     catch (JSONException e)
/* 329:    */     {
/* 330:410 */       throw new BitcoinClientException("Exception when getting help for a command", e);
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public List<AddressInfo> listReceivedByAddress(long minimumConfirmations, boolean includeEmpty)
/* 335:    */   {
/* 336:    */     try
/* 337:    */     {
/* 338:423 */       JSONArray parameters = new JSONArray().element(minimumConfirmations).element(includeEmpty);
/* 339:424 */       JSONObject request = createRequest("listreceivedbyaddress", parameters);
/* 340:425 */       JSONObject response = this.session.sendAndReceive(request);
/* 341:426 */       JSONArray result = response.getJSONArray("result");
/* 342:427 */       int size = result.size();
/* 343:428 */       List<AddressInfo> list = new ArrayList();
/* 344:430 */       for (int i = 0; i < size; i++)
/* 345:    */       {
/* 346:431 */         AddressInfo info = new AddressInfo();
/* 347:432 */         JSONObject jObject = result.getJSONObject(i);
/* 348:433 */         info.setAddress(jObject.getString("address"));
/* 349:434 */         info.setAccount(jObject.getString("account"));
/* 350:435 */         info.setAmount(getBigDecimal(jObject, "amount"));
/* 351:436 */         info.setConfirmations(jObject.getLong("confirmations"));
/* 352:437 */         list.add(info);
/* 353:    */       }
/* 354:440 */       return list;
/* 355:    */     }
/* 356:    */     catch (JSONException e)
/* 357:    */     {
/* 358:442 */       throw new BitcoinClientException("Exception when getting info about all received transactions by address", e);
/* 359:    */     }
/* 360:    */   }
/* 361:    */   
/* 362:    */   public List<AccountInfo> listReceivedByAccount(long minimumConfirmations, boolean includeEmpty)
/* 363:    */   {
/* 364:    */     try
/* 365:    */     {
/* 366:456 */       JSONArray parameters = new JSONArray().element(minimumConfirmations).element(includeEmpty);
/* 367:457 */       JSONObject request = createRequest("listreceivedbyaccount", parameters);
/* 368:458 */       JSONObject response = this.session.sendAndReceive(request);
/* 369:459 */       JSONArray result = response.getJSONArray("result");
/* 370:460 */       int size = result.size();
/* 371:    */       
/* 372:462 */       List<AccountInfo> list = new ArrayList(size);
/* 373:464 */       for (int i = 0; i < size; i++)
/* 374:    */       {
/* 375:465 */         AccountInfo info = new AccountInfo();
/* 376:466 */         JSONObject jObject = result.getJSONObject(i);
/* 377:467 */         info.setAccount(jObject.getString("account"));
/* 378:468 */         info.setAmount(getBigDecimal(jObject, "amount"));
/* 379:469 */         info.setConfirmations(jObject.getLong("confirmations"));
/* 380:470 */         list.add(info);
/* 381:    */       }
/* 382:473 */       return list;
/* 383:    */     }
/* 384:    */     catch (JSONException e)
/* 385:    */     {
/* 386:475 */       throw new BitcoinClientException("Exception when getting the received amount by account", e);
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<TransactionInfo> listTransactions(String account, int count)
/* 391:    */   {
/* 392:488 */     if (account == null) {
/* 393:489 */       account = "";
/* 394:    */     }
/* 395:492 */     if (count <= 0) {
/* 396:493 */       throw new BitcoinClientException("count must be > 0");
/* 397:    */     }
/* 398:    */     try
/* 399:    */     {
/* 400:497 */       JSONArray parameters = new JSONArray().element(account).element(count);
/* 401:498 */       JSONObject request = createRequest("listtransactions", parameters);
/* 402:499 */       JSONObject response = this.session.sendAndReceive(request);
/* 403:500 */       JSONArray result = response.getJSONArray("result");
/* 404:501 */       int size = result.size();
/* 405:    */       
/* 406:503 */       List<TransactionInfo> list = new ArrayList(size);
/* 407:505 */       for (int i = 0; i < size; i++)
/* 408:    */       {
/* 409:506 */         JSONObject jObject = result.getJSONObject(i);
/* 410:507 */         TransactionInfo info = parseTransactionInfoFromJson(jObject);
/* 411:508 */         list.add(info);
/* 412:    */       }
/* 413:511 */       return list;
/* 414:    */     }
/* 415:    */     catch (JSONException e)
/* 416:    */     {
/* 417:513 */       throw new BitcoinClientException("Exception when getting transactions for account: " + account, e);
/* 418:    */     }
/* 419:    */   }
/* 420:    */   
/* 421:    */   public TransactionInfo getTransaction(String txId)
/* 422:    */   {
/* 423:    */     try
/* 424:    */     {
/* 425:526 */       JSONArray parameters = new JSONArray().element(txId);
/* 426:527 */       JSONObject request = createRequest("gettransaction", parameters);
/* 427:528 */       JSONObject response = this.session.sendAndReceive(request);
/* 428:529 */       JSONObject result = (JSONObject)response.get("result");
/* 429:    */       
/* 430:531 */       return parseTransactionInfoFromJson(result);
/* 431:    */     }
/* 432:    */     catch (JSONException e)
/* 433:    */     {
/* 434:533 */       throw new BitcoinClientException("Exception when getting transaction info for this id: " + txId, e);
/* 435:    */     }
/* 436:    */   }
/* 437:    */   
/* 438:    */   private TransactionInfo parseTransactionInfoFromJson(JSONObject jObject)
/* 439:    */     throws JSONException
/* 440:    */   {
/* 441:538 */     TransactionInfo info = new TransactionInfo();
/* 442:    */     
/* 443:540 */     info.setAmount(getBigDecimal(jObject, "amount"));
/* 444:542 */     if (jObject.has("category")) {
/* 445:543 */       info.setCategory(jObject.getString("category"));
/* 446:    */     }
/* 447:546 */     if (jObject.has("fee")) {
/* 448:547 */       info.setFee(getBigDecimal(jObject, "fee"));
/* 449:    */     }
/* 450:550 */     if (jObject.has("message")) {
/* 451:551 */       info.setMessage(jObject.getString("message"));
/* 452:    */     }
/* 453:554 */     if (jObject.has("to")) {
/* 454:555 */       info.setTo(jObject.getString("to"));
/* 455:    */     }
/* 456:558 */     if (jObject.has("confirmations")) {
/* 457:559 */       info.setConfirmations(jObject.getLong("confirmations"));
/* 458:    */     }
/* 459:562 */     if (jObject.has("txid")) {
/* 460:563 */       info.setTxId(jObject.getString("txid"));
/* 461:    */     }
/* 462:566 */     if (jObject.has("otheraccount")) {
/* 463:567 */       info.setOtherAccount(jObject.getString("otheraccount"));
/* 464:    */     }
/* 465:570 */     if (!jObject.has("time")) {
/* 466:571 */       info.setTime(jObject.getLong("time"));
/* 467:    */     }
/* 468:574 */     return info;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public List<TransactionInfo> listTransactions(String account)
/* 472:    */   {
/* 473:585 */     return listTransactions(account, 10);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public List<TransactionInfo> listTransactions()
/* 477:    */   {
/* 478:595 */     return listTransactions("", 10);
/* 479:    */   }
/* 480:    */   
/* 481:    */   public WorkInfo getWork()
/* 482:    */   {
/* 483:    */     try
/* 484:    */     {
/* 485:605 */       JSONObject request = createRequest("getwork");
/* 486:606 */       JSONObject response = this.session.sendAndReceive(request);
/* 487:607 */       JSONObject result = (JSONObject)response.get("result");
/* 488:    */       
/* 489:609 */       WorkInfo info = new WorkInfo();
/* 490:610 */       info.setMidstate(result.getString("midstate"));
/* 491:611 */       info.setData(result.getString("data"));
/* 492:612 */       info.setHash1(result.getString("hash1"));
/* 493:613 */       info.setTarget(result.getString("target"));
/* 494:    */       
/* 495:615 */       return info;
/* 496:    */     }
/* 497:    */     catch (JSONException e)
/* 498:    */     {
/* 499:617 */       throw new BitcoinClientException("Exception when getting work info", e);
/* 500:    */     }
/* 501:    */   }
/* 502:    */   
/* 503:    */   public boolean getWork(String block)
/* 504:    */   {
/* 505:    */     try
/* 506:    */     {
/* 507:628 */       JSONArray parameters = new JSONArray().element(block);
/* 508:629 */       JSONObject request = createRequest("getwork", parameters);
/* 509:630 */       JSONObject response = this.session.sendAndReceive(request);
/* 510:    */       
/* 511:632 */       return response.getBoolean("result");
/* 512:    */     }
/* 513:    */     catch (JSONException e)
/* 514:    */     {
/* 515:634 */       throw new BitcoinClientException("Exception when trying to solve a block with getwork", e);
/* 516:    */     }
/* 517:    */   }
/* 518:    */   
/* 519:    */   public String sendToAddress(String bitcoinAddress, BigDecimal amount, String comment, String commentTo)
/* 520:    */   {
/* 521:648 */     amount = checkAndRound(amount);
/* 522:    */     try
/* 523:    */     {
/* 524:651 */       JSONArray parameters = new JSONArray().element(bitcoinAddress).element(amount).element(comment).element(commentTo);
/* 525:    */       
/* 526:653 */       JSONObject request = createRequest("sendtoaddress", parameters);
/* 527:654 */       JSONObject response = this.session.sendAndReceive(request);
/* 528:    */       
/* 529:656 */       return response.getString("result");
/* 530:    */     }
/* 531:    */     catch (JSONException e)
/* 532:    */     {
/* 533:658 */       throw new BitcoinClientException("Exception when sending bitcoins", e);
/* 534:    */     }
/* 535:    */   }
/* 536:    */   
/* 537:    */   public String sendFrom(String account, String bitcoinAddress, BigDecimal amount, int minimumConfirmations, String comment, String commentTo)
/* 538:    */   {
/* 539:679 */     if (account == null) {
/* 540:680 */       account = "";
/* 541:    */     }
/* 542:683 */     if (minimumConfirmations <= 0) {
/* 543:684 */       throw new BitcoinClientException("minimumConfirmations must be > 0");
/* 544:    */     }
/* 545:687 */     amount = checkAndRound(amount);
/* 546:    */     try
/* 547:    */     {
/* 548:690 */       JSONArray parameters = new JSONArray().element(account).element(bitcoinAddress).element(amount).element(minimumConfirmations).element(comment).element(commentTo);
/* 549:    */       
/* 550:692 */       JSONObject request = createRequest("sendfrom", parameters);
/* 551:693 */       JSONObject response = this.session.sendAndReceive(request);
/* 552:    */       
/* 553:695 */       return response.getString("result");
/* 554:    */     }
/* 555:    */     catch (JSONException e)
/* 556:    */     {
/* 557:697 */       throw new BitcoinClientException("Exception when sending bitcoins with sendFrom()", e);
/* 558:    */     }
/* 559:    */   }
/* 560:    */   
/* 561:    */   public boolean move(String fromAccount, String toAccount, BigDecimal amount, int minimumConfirmations, String comment)
/* 562:    */   {
/* 563:715 */     if (fromAccount == null) {
/* 564:716 */       fromAccount = "";
/* 565:    */     }
/* 566:719 */     if (toAccount == null) {
/* 567:720 */       toAccount = "";
/* 568:    */     }
/* 569:723 */     if (minimumConfirmations <= 0) {
/* 570:724 */       throw new BitcoinClientException("minimumConfirmations must be > 0");
/* 571:    */     }
/* 572:727 */     amount = checkAndRound(amount);
/* 573:    */     try
/* 574:    */     {
/* 575:730 */       JSONArray parameters = new JSONArray().element(fromAccount).element(toAccount).element(amount).element(minimumConfirmations).element(comment);
/* 576:    */       
/* 577:732 */       JSONObject request = createRequest("move", parameters);
/* 578:733 */       JSONObject response = this.session.sendAndReceive(request);
/* 579:    */       
/* 580:735 */       return response.getBoolean("result");
/* 581:    */     }
/* 582:    */     catch (JSONException e)
/* 583:    */     {
/* 584:737 */       throw new BitcoinClientException("Exception when moving " + amount + " bitcoins from account: '" + fromAccount + "' to account: '" + toAccount + "'", e);
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   private BigDecimal checkAndRound(BigDecimal amount)
/* 589:    */   {
/* 590:743 */     if (amount.compareTo(new BigDecimal("0.01")) < 0) {
/* 591:744 */       throw new BitcoinClientException("The current machinery doesn't support transactions of less than 0.01 Bitcoins");
/* 592:    */     }
/* 593:747 */     if (amount.compareTo(new BigDecimal("21000000")) > 0) {
/* 594:748 */       throw new BitcoinClientException("Sorry dude, can't transfer that many Bitcoins");
/* 595:    */     }
///* 596:751 */     amount = roundToTwoDecimals(amount);
/* 597:752 */     return amount;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void stop()
/* 601:    */   {
/* 602:    */     try
/* 603:    */     {
/* 604:760 */       JSONObject request = createRequest("stop");
/* 605:761 */       this.session.sendAndReceive(request);
/* 606:    */     }
/* 607:    */     catch (JSONException e)
/* 608:    */     {
/* 609:763 */       throw new BitcoinClientException("Exception when stopping the bitcoin server", e);
/* 610:    */     }
/* 611:    */   }
/* 612:    */   
/* 613:    */   public ValidatedAddressInfo validateAddress(String address)
/* 614:    */   {
/* 615:    */     try
/* 616:    */     {
/* 617:774 */       JSONArray parameters = new JSONArray().element(address);
/* 618:775 */       JSONObject request = createRequest("validateaddress", parameters);
/* 619:776 */       JSONObject response = this.session.sendAndReceive(request);
/* 620:777 */       JSONObject result = (JSONObject)response.get("result");
/* 621:    */       
/* 622:779 */       ValidatedAddressInfo info = new ValidatedAddressInfo();
/* 623:780 */       info.setIsValid(result.getBoolean("isvalid"));
/* 624:782 */       if (info.getIsValid())
/* 625:    */       {
/* 626:784 */         info.setIsMine(result.getBoolean("ismine"));
/* 627:785 */         info.setAddress(result.getString("address"));
/* 628:    */       }
/* 629:788 */       return info;
/* 630:    */     }
/* 631:    */     catch (JSONException e)
/* 632:    */     {
/* 633:790 */       throw new BitcoinClientException("Exception when validating an address", e);
/* 634:    */     }
/* 635:    */   }
/* 636:    */   
/* 637:    */   public void backupWallet(String destination)
/* 638:    */   {
/* 639:    */     try
/* 640:    */     {
/* 641:801 */       JSONArray parameters = new JSONArray().element(destination);
/* 642:802 */       JSONObject request = createRequest("backupwallet", parameters);
/* 643:803 */       this.session.sendAndReceive(request);
/* 644:    */     }
/* 645:    */     catch (JSONException e)
/* 646:    */     {
/* 647:805 */       throw new BitcoinClientException("Exception when backing up the wallet", e);
/* 648:    */     }
/* 649:    */   }
/* 650:    */   
/* 651:    */   protected static BigDecimal roundToTwoDecimals(BigDecimal amount)
/* 652:    */   {
/* 653:814 */     BigDecimal amountTimes100 = amount.multiply(new BigDecimal(100)).add(new BigDecimal("0.5"));
/* 654:815 */     BigDecimal roundedAmountTimes100 = new BigDecimal(amountTimes100.intValue());
/* 655:816 */     BigDecimal roundedAmount = roundedAmountTimes100.divide(new BigDecimal(100.0D));
/* 656:    */     
/* 657:818 */     return roundedAmount;
/* 658:    */   }
/* 659:    */   
/* 660:    */   private JSONObject createRequest(String functionName, JSONArray parameters)
/* 661:    */     throws JSONException
/* 662:    */   {
/* 663:822 */     JSONObject request = new JSONObject();
/* 664:823 */     request.put("jsonrpc", "2.0");
/* 665:824 */     request.put("id", UUID.randomUUID().toString());
/* 666:825 */     request.put("method", functionName);
/* 667:826 */     request.put("params", parameters);
/* 668:    */     
/* 669:828 */     return request;
/* 670:    */   }
/* 671:    */   
/* 672:    */   private JSONObject createRequest(String functionName)
/* 673:    */     throws JSONException
/* 674:    */   {
/* 675:832 */     return createRequest(functionName, new JSONArray());
/* 676:    */   }
/* 677:    */ }



/* Location:           C:\Users\Administrator.SKY-20170517PBA\Desktop\bitcoin-client-0.4.0.jar

 * Qualified Name:     ru.paradoxs.bitcoin.client.BitcoinClient

 * JD-Core Version:    0.7.0.1

 */