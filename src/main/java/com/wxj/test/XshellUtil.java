package com.wxj.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：青云科技
 * @作者：wxj
 * @Email：18772118541@163.com
 * @TEL：18772118541
 * @创建时间：2017-12-22 下午5:25:12
 * @版本：V1.0
 */
public class XshellUtil {
	/**
	 * @方法说明：通过一个连接，用户名，密码，nikto的命令,获取这个命令在linuxdos窗口里面打印出来的东西
	 * 用一个String 类型的东西返回去
	 * @方法名称：getCommondMsg
	 * @作者：wxj
	 * @param conn
	 * @param username
	 * @param password
	 * @param commond
	 * @return
	 * @throws IOException
	 * @创建时间：2017-12-23 下午12:17:02
	 */
	public static String getCommondMsg(Connection conn,String commond,boolean isAuthenticated) throws IOException {
		StringBuffer sb = new StringBuffer("");

		if (isAuthenticated == false)
			throw new IOException("Authentication failed.");
		// 创建一个session
		Session sess = conn.openSession();
		// 执行linux命令
		sess.execCommand(commond);
		// 建立一个输入流
		InputStream stdout = new StreamGobbler(sess.getStdout());
		// 建立一个字节流
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			sb.append(line.trim() + "\n");
		}
		br.close();
		stdout.close();
		// 关闭session
		sess.close();
		return sb.toString();
	}
	
	/**
	 * @方法说明：创建一个linux的连接
	 * @方法名称：getLinuxConnect
	 * @作者：wxj
	 * @param hostname
	 * @return
	 * @创建时间：2017-12-23 下午2:46:26
	 */
	public static Connection getLinuxConnect(String hostname) {
		Connection conn = new Connection(hostname);
		return conn;
	}
}
