package com.pf.common.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;

import java.io.File;
import java.io.IOException;

/**
 * SCP工具类
 * @author qsy
 * @version v1.0
 * @date 2016年12月28日
 */
public class ScpUtil {
	private String ip;
	private int port;
	private String userName;
	private String passWord;
	private String remoteRootDir;
	
	/**
	 * 建立连接
	 * @return 连接
	 * @throws Exception 异常对象
	 */
	private Connection connectServer() throws Exception{
		boolean isAuthed = false;
		Connection scpConnection = null;
		if(port==0){
			scpConnection = new Connection(ip);
		}else{
			scpConnection = new Connection(ip, port);
		}
		scpConnection.connect();
		isAuthed = scpConnection.authenticateWithPassword(userName, passWord);
		if(!isAuthed){
			throw new Exception("connect auth failed!");
		}
		return scpConnection;
	}
	
	/**
	 * 上传文件
	 * @param localFilePath 本地文件
	 * @param scpFileName 远程文件名
	 * @param scpDir 远程文件目录
	 * @throws Exception
	 */
	public void uploadFile(String localFilePath, String scpDir, String scpFileName) throws Exception{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		try {
			scpConnection = connectServer();
			scpClient = scpConnection.createSCPClient();
			createDir(scpDir,scpConnection);
			scpClient.put(localFilePath, scpFileName, scpDir, "0774");
		} catch (Exception ex) {
			LogUtils.error(ex.getMessage(), ex);
			throw ex;
		}finally{
			closeServer(scpConnection);
		}
	}
	
	/**
	 * 上传文件
	 * @param fileByte 二进制文件
	 * @param scpFileName 远程文件名
	 * @param scpDir 远程文件目录
	 * @throws Exception
	 */
	public void uploadFile(byte[] fileByte, String scpDir, String scpFileName) throws Exception{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		try {
			scpConnection = connectServer();
			scpClient = scpConnection.createSCPClient();
			createDir(scpDir,scpConnection);
			scpClient.put(fileByte, scpFileName, scpDir, "0774");
		} catch (Exception ex) {
			LogUtils.error(ex.getMessage(), ex);
			throw ex;
		}finally{
			closeServer(scpConnection);
		}
	}
	
	/**
	 * 创建目录
	 * @param dirName 目录名
	 * @param scpConnection 连接对象
	 * @throws IOException 异常对象
	 */
	private void createDir(String dirName,Connection scpConnection) throws IOException{
		SFTPv3Client sftpClient = null;
		try {
			sftpClient = new SFTPv3Client(scpConnection);
			sftpClient.mkdir(dirName, 0775);
		} catch (IOException e) {
			// 目录存在,不处理
		} finally {
			if (null != sftpClient){
				sftpClient.close();
			}
		}
	}
	
	/**
	 * 删除远程文件
	 * @param fileName 文件名
	 * @throws Exception 异常对象
	 */
	public void delFile(String fileName) throws Exception {
		Connection scpConnection = null;
		SFTPv3Client sftpClient = null;
		try {
			scpConnection = connectServer();
			sftpClient = new SFTPv3Client(scpConnection);
			sftpClient.rm(fileName);
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
			throw e;
		} finally {
			closeServer(scpConnection);
		}
	}
	
	/**
	 * 文件批量上传
	 * @param localFilePath 本地文件集合
	 * @param scpDir 远程目录
	 * @param scpFileName 远程文件集合
	 * @throws Exception
	 */
	public void uploadFile(String[] localFilePath,  String scpDir, String[] scpFileName) throws Exception{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		try {
			scpConnection = connectServer();
			scpClient = scpConnection.createSCPClient();
			//检查远程目录，不存在创建
			createDir(scpDir,scpConnection);
			//文件上传
			scpClient.put(localFilePath, scpFileName, scpDir, "0774");
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
			throw e;
		}finally{
			closeServer(scpConnection);
		}
	}
	
	/**
	 * 下载文件
	 * @param scpFileName
	 * @param localDir
	 * @throws Exception
	 */
	public void downloadFile(String scpFileName, String localDir) throws Exception {
		Connection scpConnection = null;
		SCPClient scpClient = null;
		try {
			scpConnection = connectServer();
			File localPath = new File(localDir);
			if (!localPath.exists() || !localPath.isDirectory()) {
				localPath.mkdirs();
			}
			scpClient = scpConnection.createSCPClient();
			scpClient.get(scpFileName, localDir);
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
			throw new RuntimeException("文件下载失败", e);
		} finally {
			if (scpConnection != null) {
				scpConnection.close();
			}
		}
	}
	
	/**
	 * 关闭连接
	 * @tilte closeServer
	 * @param scpConnection 连接对象
	 */
	private void closeServer(Connection scpConnection){
		try {
			if(scpConnection!=null)
				scpConnection.close();
		} catch (Exception e){
		}
	}
	
	public String getRemoteRootDir() {
		return remoteRootDir;
	}

	public void setRemoteRootDir(String remoteRootDir) {
		this.remoteRootDir = remoteRootDir;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	

}
