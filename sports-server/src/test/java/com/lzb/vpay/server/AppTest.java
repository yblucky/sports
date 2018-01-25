package com.xlf.server;

import java.math.BigDecimal;

import org.junit.Test;

import ru.paradoxs.bitcoin.client.BitcoinClient;

public class AppTest{
	
	@Test
	public void test(){
		BitcoinClient client = new BitcoinClient("127.0.0.1","user","password", 20099);
//		System.out.println(client.move("10000", "10008", new BigDecimal(200), 1, ""));
		System.out.println(client.validateAddress("rswUdAxrm3mz4NjQzGTZJQ8kgVBZebFqTVd").getIsValid()+"=============");
		
		String tid = client.sendFrom("10027","ra5RdTpDYrJsnYtKzkmMneiFxihV3iqLx2", new BigDecimal(10), 3, "", "");
		System.out.println(tid+"====");
	}
}
