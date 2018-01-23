package com.pf.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 订单号生成器
 * 
 * @author zhuzh
 * @date 2016年12月7日
 */
public class OrderNoUtil {

	private static final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

	private static final CountDownLatch latch = new CountDownLatch(1);

	// 每毫秒生成订单号数量最大值，约定取整百，整千。
	private static final int MAX_PER_MSEC_SIZE = 1000;

	private static void init() {
		for (int i = 0; i < MAX_PER_MSEC_SIZE; i++) {
			queue.offer(i);
		}
		latch.countDown();
	}

	private static Integer poll() {
		try {
			if (latch.getCount() > 0) {
				init();
				latch.await(1, TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer i = queue.poll();
		queue.offer(i);
		return i;
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public synchronized static String get() {
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String number = MAX_PER_MSEC_SIZE + poll() + "";
		return dateStr + number.substring(1);
	}

}
