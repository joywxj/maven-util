package com.wxj.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.telnet.TelnetClient;

/**
 * Util calss for Ping
 * 
 * @author yuanzhengyi
 */
public class PingUtil {
	/**
	 * @param ipAddress
	 *            ip
	 * @param timeOut
	 *            ms
	 * @return true: connect ok,false:no ok
	 * @throws Exception
	 */
	public static boolean ping(String ipAddress, int timeOut) throws Exception {
		boolean status = InetAddress.getByName(ipAddress).isReachable(null,
				timeOut, timeOut);
		return status;
	}

	public static String ping02(String ipAddress) throws Exception {
		StringBuffer result = new StringBuffer("");
		try {
			String line = null;
			Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					pro.getInputStream(), Charset.forName("GBK")));
			while ((line = buf.readLine()) != null)
				result.append(line);
			// System.out.println(line);
		} catch (Exception ex) {
		}
		return result.toString();
	}

	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime();
		String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w "
				+ timeOut;
		try {
			Process p = r.exec(pingCommand);
			if (p == null) {
				return false;
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			int connectedCount = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				connectedCount += getCheckResult(line);
			}
			return connectedCount == pingTimes;
		} catch (Exception ex) {
			return false;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean ping(String ip) throws IOException {
		String osName = System.getProperty("os.name");// 获取操作系统类型
		String command = "";
		if (osName.contains("Linux")) {
			command = "ping -c 100 -i 0 " + ip;
		} else {
			command = "ping -n 5 -w 1000 " + ip;
		}
		InputStream is = Runtime.getRuntime().exec(command).getInputStream();
		InputStreamReader isr = new InputStreamReader(is,
				Charset.forName("GBK"));
		BufferedReader br = new BufferedReader(isr);
		StringBuffer result = new StringBuffer("");
		;
		String line = "";
		while ((line = br.readLine()) != null) {
			result.append(line.trim());
		}
		// 英语Linux OS
		if (result != null && result.indexOf("Unreachable") != -1) {
			return false;
		} else if (result != null && result.indexOf("min/avg/max/mdev") != -1) {
			return true;
		}
		// 中文windows os
		else if (result != null && result.indexOf("超时") != -1) {
			return false;
		} else if (result != null && result.indexOf("平均") != -1) {
			return true;
		}
		return false;
	}

	public static List<String> pingCommen(String ip) throws IOException {
		List<String> list = new ArrayList<String>();
		String osName = System.getProperty("os.name");// 获取操作系统类型
		String command = "";
		if (osName.contains("Linux")) {
			command = "ping -c 10 -i 0 " + ip;
		} else {
			command = "ping -n 5 -w 1000 " + ip;
		}
		InputStream is = Runtime.getRuntime().exec(command).getInputStream();
		InputStreamReader isr = new InputStreamReader(is,
				Charset.forName("GBK"));
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.length() > 0) {
				list.add(line.trim());
			}
		}
		return list;
	}

	private static int getCheckResult(String line) {
		Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			return 1;
		}
		return 0;
	}

	/**
	 * 判断设备的某端口是否能够连接上
	 * 
	 * @param ip
	 *            设备ip
	 * @param port
	 *            服务端口
	 * @return 连得上true，连不上false
	 */
	public static boolean telnet(String ip, int port) {
		TelnetClient telnet = new TelnetClient();
		try {
			telnet.connect(ip, port);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				telnet.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @方法说明：对ip进行校验
	 * @方法名称：validate
	 * @作者：wxj
	 * @param ip
	 * @return
	 * @创建时间：2017-12-19 上午11:21:32
	 */
	private static boolean validate(String ip) {
		if (ip == null || ip.length() == 0)
			return false;
		String[] array = ip.split("\\.");
		if (array.length != 4)
			return false;
		for (String str : array) {
			try {
				Integer.valueOf(str);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

}