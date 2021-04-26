package com.wxj.test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**  
* @ClassName: MysqlDemoGenerator  
* @Description: TODO
* @author: wxj  
* @date: 2019年2月1日
* @Tel:18772118541
* @email:18772118541@163.com
* 使用说明：
 * 1:要修改好数据库相关的东西：数据库名，用户名，密码
 * 2:输入表名
 * 3:输入实体类、Mapper和Mapper.xml的存放路径，这是绝对路径，一定要写准确
 * 4:输入实体类和Mapper接口的package路径,
*/
public class MysqlDemoGenerator {
	/**表名*/
	static String tableName = "salary";
	/**实体类的包路径*/
	static String beanPackages = "com.wxj.domain.entity.salary";
	/**mapper接口的包路径*/
	static String mapperPackages = "com.wxj.dao";
	/**mapper.xml文档的路径*/
	static String xmlPath = "C:\\Users\\issuser\\Desktop\\";
//	static String xmlPath = "D:\\WorkSpase_MyEclipse_new\\SafeView\\src\\com\\wangyun\\dao\\snmp\\";
	/**实体类文件路径*/
	static String beanPath = "C:\\Users\\issuser\\Desktop\\";
//	static String beanPath = "D:\\WorkSpase_MyEclipse_new\\SafeView\\src\\com\\wangyun\\vo\\snmp\\";
	/**mapper.java文件的路径*/
	static String mapperPath = "C:\\Users\\issuser\\Desktop\\";
//	static String mapperPath = "D:\\WorkSpase_MyEclipse_new\\SafeView\\src\\com\\wangyun\\dao\\snmp\\";
	static String user = "root";
	static String pwd = "root";
	static String databasesName ="leishi";
	static String ip = "localhost";
	/**是否生成实体类的开关*/
	static boolean beanKey = true;
	/**是否生成mapper接口的开关*/
	static boolean mapperKey = true;
	/**是否生成xml文件的开关*/
	static boolean xmlKey = true;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");// MySql数据库
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// 字段集合
		List<String> columnNameList = new ArrayList<String>();
		// 注释集合
		List<String> columnCommentList = new ArrayList<String>();
		// 获取到字段信息
		getcolumnInfo(columnNameList, columnCommentList);
		// 生成Java类
		if(beanKey){
			generateClass(columnNameList, columnCommentList);
		}
		// 生成Mapper接口
		if(mapperKey){
			generateMapperClass();
		}
		// 生成Mapper xml
		if(xmlKey){
			generateMapperXml(columnNameList);
		}
		
		
	}

	/**
	 * 获取字段名称集合、获取注释集合
	 * 
	 * @param columnNameList
	 * @param columnCommentList
	 * @throws SQLException
	 */
	public static void getcolumnInfo(List<String> columnNameList, List<String> columnCommentList) throws SQLException {
		String generatorSql = "select  column_name, column_comment  from Information_schema.columns  where table_schema='"+databasesName+"' and table_Name = '"
				+ tableName + "'";
		String url = "jdbc:mysql://"+ip+":3306/"+databasesName+"?useUnicode=true&characterEncoding=utf-8";
		Connection conn = null;
		conn = DriverManager.getConnection(url, user, pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(generatorSql);
		// 将字段名称赋值给字段名称集合columnNameList，将注释信息赋值给注释信息集合columnCommentList
		while (rs.next()) {
			columnNameList.add(rs.getString(1));
			columnCommentList.add(rs.getString(2));
		}
		rs.close();
		st.close();
		conn.close();
	}

	/**
	 * 生成Java Bean类
	 * 
	 * @param columnNameList
	 * @param columnCommentList
	 * @throws IOException
	 */
	public static void generateClass(List<String> columnNameList, List<String> columnCommentList) {
		// 获取类名
		String className = makeClassName(tableName);
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("请输入实体类存放路径(绝对路径,要准确):");
//		beanPath = scanner.nextLine()+"\\";
		File file = new File(beanPath + className + ".java");
//		System.out.println("请输入实体类包(package)路径:");
//		beanPackages = scanner.nextLine();
		String result = "package " + beanPackages+";" + "\r\n";
		result += "/**\r\n" + "* @ClassName:  " + className + "\r\n" + "* @Description:TODO\r\n"
				+ "* @Modified:第一版本\r\n" + "* @author: wxj\r\n" + "* @date:" + (new Date()).toLocaleString() + "\r\n"
				+ "* @email: 18772118541@163.com" + "\r\n" + "*/" + "\r\n";
		result += "public class " + className + "{\r\n";
		for (int i = 0; i < columnNameList.size(); i++) {
			result += "\t" + "/**" + columnCommentList.get(i) + "*/\r\n";
			result += "\t" + "private String " + makeFiledName(columnNameList.get(i)) + ";\r\n";
		}
		result += "\r\n";
		// 在此处增加getter、setter
		for (String columnName : columnNameList) {
			// 增加set方法
			result += "\t" + "public String " + getGetFunctionName(columnName) + "(){\r\n";
			result += "\t\t" + "return this." + makeFiledName(columnName) + ";\r\n";
			result += "\t}\r\n";
			// 增加get方法
			result += "\t" + "public void " + getSetFunctionName(columnName) + "(String " + makeFiledName(columnName)
					+ "){\r\n";
			result += "\t\t" + "this." + makeFiledName(columnName) + "=" + makeFiledName(columnName) + ";\r\n";
			result += "\t}\r\n";
		}
		/*
		 * public String getNoticeId() { return noticeId; } public void
		 * setNoticeId(String noticeId) { this.noticeId = noticeId; }
		 */
		result += "}";
		try {
			file.createNewFile();
			writeFlie(result, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFlie(String result, File file) throws IOException {
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(file));
		bw.write(result);
		bw.flush();
		bw.close();
	}

	/**
	 * 生成Mapper接口
	 * 
	 * @param columnNameList
	 * @param columnCommentList
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void generateMapperClass() throws IOException {
		// 获取类名
		String javaBeanClassName = makeClassName(tableName);
		String className = javaBeanClassName + "Mapper";
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("请输入Mapper接口类存放路径(绝对路径,要准确):");
//		mapperPath = scanner.nextLine()+"\\";
		File file = new File(mapperPath + className + ".java");
//		System.out.println("请输入Mapper包路径(package):");
//		mapperPackages = scanner.nextLine();
		String result = "package "+mapperPackages+";\r\n\r\n";
		result += "import java.util.List;\r\n";
		result += "import "+beanPackages+"."+javaBeanClassName+";\r\n";
		result += "/**\r\n" + "* @ClassName:  " + className + "\r\n" + "* @Description:TODO\r\n"
				+ "* @Modified:第一版本\r\n" + "* @author: wxj\r\n" + "* @date:" + (new Date()).toLocaleString() + "\r\n"
				+ "* @email: 18772118541@163.com" + "\r\n" + "*/" + "\r\n";
		result += "public interface " + className + "{\r\n";
		result += "\t/**\r\n\t *查询" + javaBeanClassName + "对象\r\n\t *@param " + getLowerStringName(javaBeanClassName)
				+ " 查询条件，通配\r\n\t *@return List<" + javaBeanClassName + ">\r\n\t */\r\n";
		result += "\tpublic List<" + javaBeanClassName + "> select" + javaBeanClassName + "(" + javaBeanClassName + " "
				+ getLowerStringName(javaBeanClassName) + ");\r\n";
		result += "\t/**\r\n\t *插入" + javaBeanClassName + "对象\r\n\t *@param " + getLowerStringName(javaBeanClassName)
				+ " 插入数据\r\n\t *@return\r\n\t */\r\n";
		result += "\tpublic int insert" + javaBeanClassName + "(" + javaBeanClassName + " "
				+ getLowerStringName(javaBeanClassName) + ");\r\n";
		result += "\t/**\r\n\t *删除" + javaBeanClassName + "对象\r\n\t *@param " + getLowerStringName(javaBeanClassName)
				+ " 删除条件，通配\r\n\t *@return\r\n\t */\r\n";
		result += "\tpublic int delete" + javaBeanClassName + "(" + javaBeanClassName + " "
				+ getLowerStringName(javaBeanClassName) + ");\r\n";
		result += "}";
		file.createNewFile();
		writeFlie(result, file);
	}

	/**
	 * 生成Mapper xml
	 * 
	 * @param columnNameList
	 * @param columnCommentList
	 * @throws IOException
	 */
	public static void generateMapperXml(List<String> columnNameList) throws IOException {
		// 获取类名
		String javaBeanClassName = makeClassName(tableName);
		String className = javaBeanClassName + "Mapper";
		File file = new File(xmlPath + className + ".xml");
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
		result += "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n";
		result += "<mapper namespace=\""+mapperPackages+"." + className + "\" >\r\n";
		result += "\t<resultMap id=\"BaseResultMap\" type=\""+beanPackages+"." + javaBeanClassName + "\">\r\n";
		for (String columnName : columnNameList) {
			result += "\t\t<result column=\"" + columnName + "\" property=\"" + makeFiledName(columnName)
					+ "\" jdbcType=\"VARCHAR\" />\r\n";
		}
		result += "\t</resultMap>\r\n";
		// sql mapper中的字段列表部分
		result += "\t<sql id=\"BaseColumnList\">\r\n";
		result += "\t\t" + columnNameList.get(0);
		for (int i = 1; i < columnNameList.size(); i++) {
			result += "," + columnNameList.get(i);
		}
		result += "\r\n\t</sql>\r\n";
		// select mapper中的查询部分
		result += "\t<select id=\"select" + javaBeanClassName
				+ "\" resultMap=\"BaseResultMap\"  parameterType=\""+beanPackages+"." + javaBeanClassName + "\" >\r\n";
		result += "\t\tselect <include refid=\"BaseColumnList\" /> from " + tableName + "\r\n";
		result += "\t\t<where>\r\n";
		for (String columnName : columnNameList) {
			result += "\t\t\t<if test=\"" + makeFiledName(columnName) + " != null and " + makeFiledName(columnName)
					+ " != ''\">\r\n";
			result += "\t\t\t\tand " + columnName + " = #{" + makeFiledName(columnName) + "}\r\n";
			result += "\t\t\t</if>\r\n";
		}
		result += "\t\t</where>\r\n";
		result += "\t</select>\r\n";

		// insert mapper中的查询部分
		result += "\t<insert id=\"insert" + javaBeanClassName + "\"  parameterType=\""+beanPackages+"."
				+ javaBeanClassName + "\" >\r\n";
		result += "\t\tinsert into " + tableName + "\r\n";
		result += "\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (String columnName : columnNameList) {
			result += "\t\t\t<if test=\"" + makeFiledName(columnName) + " != null and " + makeFiledName(columnName)
					+ " != ''\">\r\n";
			result += "\t\t\t\t" + columnName + ",\r\n";
			result += "\t\t\t</if>\r\n";
		}
		result += "\t\t</trim>\r\n";
		result += "\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (String columnName : columnNameList) {
			result += "\t\t\t<if test=\"" + makeFiledName(columnName) + " != null and " + makeFiledName(columnName)
					+ " != ''\">\r\n";
			result += "\t\t\t\t#{" + makeFiledName(columnName) + "},\r\n";
			result += "\t\t\t</if>\r\n";
		}
		result += "\t\t</trim>\r\n";
		result += "\t</insert>\r\n";
		// delete mapper中的查询部分
		result += "\t<delete id=\"delete" + javaBeanClassName + "\" parameterType=\""+beanPackages+"." + javaBeanClassName
				+ "\" >\r\n";
		result += "\t\tdelete from " + tableName + "\r\n";
		result += "\t\t<where>\r\n";
		for (String columnName : columnNameList) {
			result += "\t\t\t<if test=\"" + makeFiledName(columnName) + " != null and " + makeFiledName(columnName)
					+ " != ''\">\r\n";
			result += "\t\t\t\tand " + columnName + " = #{" + makeFiledName(columnName) + "}\r\n";
			result += "\t\t\t</if>\r\n";
		}
		result += "\t\t</where>\r\n";
		result += "\t</delete>\r\n";
		result += "</mapper>";
		file.createNewFile();
		writeFlie(result, file);
	}

	/**
	 * 将表名转换成类名
	 * 
	 * @param columnName
	 * @return
	 */
	private static String makeClassName(String columnName) {
		String[] temps = columnName.split("_");
		String result = "";
		for (String temp : temps) {
			result += temp.substring(0, 1).toUpperCase() + temp.substring(1);
		}
		return result;
	}

	/**
	 * 将字段名转换成属性名
	 * 
	 * @param columnName
	 * @return class name list classNameList
	 */
	private static String makeFiledName(String columnName) {
		String[] temps = columnName.split("_");
		String result = temps[0];
		for (int i = 1; i < temps.length; i++) {
			result += temps[i].substring(0, 1).toUpperCase() + temps[i].substring(1);
		}
		return result;
	}

	/**
	 * 根据属性名获取get方法名
	 * 
	 * @param columnName
	 * @return class name list classNameList
	 */
	private static String getGetFunctionName(String columnName) {
		String result = makeFiledName(columnName); // classNameList
													// ==>ClassNameList
		return "get" + result.substring(0, 1).toUpperCase() + result.substring(1);
	}

	/**
	 * 根据属性名获取set方法名
	 * 
	 * @param columnName
	 * @return class name list classNameList
	 */
	private static String getSetFunctionName(String columnName) {
		String result = makeFiledName(columnName); // classNameList
													// ==>ClassNameList
		return "set" + result.substring(0, 1).toUpperCase() + result.substring(1);
	}

	/**
	 * 返回字符串首字符小写
	 * 
	 * @param columnName
	 * @return class name list classNameList
	 */
	private static String getLowerStringName(String stringName) {
		return stringName.substring(0, 1).toUpperCase() + stringName.substring(1);
	}
}
