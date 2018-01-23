package com.xlf.common.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志核心工具类 关键业务操作日志记录
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月23日
 */
public class LogUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger("com.yanbao.common.util.LogUtils");
	static {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(LogUtils.class.getClassLoader().getResourceAsStream("logback.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		StatusPrinter.print(lc.getStatusManager());
	}

	public Logger getLogger() {
		return LOGGER;
	}

	// --------------------------------------------LogBack
	// Info----------------------------------

	// Log a message at the INFO level.
	public static void info(String msg) {
		LOGGER.info(msg);
	}

	// Log an exception (throwable) at the INFO level with an accompanying
	// message.
	public static void info(String msg, Throwable t) {
		LOGGER.info(msg, t);
	}

	// Log a message at the DEBUG level.
	public static void debug(String msg) {
		LOGGER.debug(msg);
	}

	// Log an exception (throwable) at the DEBUG level with an accompanying
	// message.
	public static void debug(String msg, Throwable t) {
		LOGGER.debug(msg, t);
	}

	// Log a message at the WARN level.
	public static void warn(String msg) {
		LOGGER.warn(msg);
	}

	// Log a message at the WARN level.
	public static void warn(String msg, Throwable t) {
		LOGGER.warn(msg, t);
	}

	// Log a message at the ERROR level.
	public static void error(String msg) {
		LOGGER.error(msg);
	}

	public static void error(String msg, Throwable t) {
		LOGGER.error(msg, t);
	}

	// -------------------------LogBack
	// Warn--------------------------------------------
	// Log a message at the TRACE level.
	public static void trace(String msg) {
		LOGGER.trace(msg);
	}

	// Log an exception (throwable) at the TRACE level with an accompanying
	// message.
	public static void trace(String msg, Throwable t) {
		LOGGER.trace(msg, t);

	}
}
