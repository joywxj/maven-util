package com.wxj.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类名: JDBCUtil
 * </p>
 * <p>
 * 描述: jdbc工具类
 * </p>
 * <p>
 * 作者:吴兴军
 * </p>
 * <p>
 * 电话:18772118541
 * </p>
 * <p>
 * 邮箱:18772118541@163.com
 * </p>
 * <p>
 * 日期: 2019-06-04 09:29
 * </p>
 */
public class JDBCUtil {
	static {// 集中加载驱动，支持一下加载了驱动的数据库，这是这个工具类能支持所有数据库连接的核心前提
		try {
//			Class.forName("com.mysql.jdbc.Driver");// MySql数据库
			Class.forName("oracle.jdbc.driver.OracleDriver");// Oracle数据库
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1、获取连接 2、关闭连接 3、数据插入
	 */

	public static Connection getConnect(String dbType, String ip, String port, String dbName, String user,
			String password) {
		String url = getUrl(dbType, ip, port, dbName);
		System.out.println(url);
		Connection connect = null;
		try {
			connect = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
	/**  
	* <p>方法名 getTables </p>
	* <p>方法描述: TODO 获取表集合</p>
	* <p>日期:2019-06-13 10:40</p>
	*/
	public static List<String> getTables(Connection connect, String dbType, String dbName) {
		List<String> list = new ArrayList<String>();
		String sql = "";
		if (dbType.equals("Oracle")) {
			sql = "SELECT table_name FROM USER_TABLES";
		} else {
			sql = "select table_name from information_schema.TABLES where TABLE_SCHEMA='" + dbName + "'"
					+ " and table_type='base table'";
		}
		ResultSet set = executeQuery(sql, connect);
		try {
			while (set.next()) {
				list.add(set.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(set, null, null);
		}
		return list;
	}

	/**
	 * @Title: getUrl
	 * @Description: TODO 获取url
	 * @param dbType
	 *            数据库类型
	 * @param ip
	 *            ip
	 * @param port
	 *            商端口
	 * @param dbName
	 *            数据库名(oracle的connId)
	 * @return String
	 * @date:2018-10-10 16:22
	 */
	private static String getUrl(String dbType, String ip, String port, String dbName) {
		StringBuilder url = new StringBuilder();
		if ("Oracle".equals(dbType)) {
			url.append("jdbc:oracle:thin:@");
			url.append(ip);
			url.append(":");
			url.append(port);
			url.append(":");
			url.append(dbName);
		} else if ("MySql".equals(dbType)) {
			url.append("jdbc:mysql://");
			url.append(ip);
			url.append(":");
			url.append(port);
			url.append("/");
			url.append(dbName);
		}
		return url.toString();
	}

	/**
	 * @Title: executeQuery
	 * @Description: TODO 给一个sql和一个连接，执行查询的sql，所有的参数都需要写在里面
	 * @param sql
	 * @param connect
	 * @return
	 * @date:2018-10-30 14:06
	 */
	public static ResultSet executeQuery(String sql, Connection connect) {
		ResultSet query = null;
		Statement st = null;
		try {
			st = connect.createStatement();
			query = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query;
	}

	public static int getCount(String sql, Connection connect) {
		ResultSet query = null;
		Statement st = null;
		int result = 0;
		try {
			st = connect.createStatement();
			query = st.executeQuery(sql);
			while (query.next()) {
				result = query.getInt("cc");
//				System.out.println(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void closeResult(ResultSet query) {
		if (query != null) {
			try {
				query.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet query, Statement st, Connection conn) {

		try {
			if (query != null) {
				query.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Title: executeUpdate
	 * @Description: TODO 给一个sql和一个连接，执行增删改的sql，所有的参数都需要写在里面
	 * @param sql
	 * @param connect
	 * @return
	 * @date:2018-10-30 14:05
	 */
	public static boolean executeUpdate(String sql, Connection connect) {
		boolean execute = false;
		Statement st = null;
		try {
			st = connect.createStatement();
			execute = st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(null, st, null);
		return execute;
	}

	public static int executeUpdateInt(String sql, Connection connect) {
		int execute = 0;
		Statement st = null;
		try {
			st = connect.createStatement();
			execute = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(null, st, null);
		return execute;
	}
}
