package com.wxj.test;

import java.io.*;

/**
 * @ClassName: IOUtils
 * @Description: TODO IO工具类
 * @author: wxj
 * @date: 2019年1月31日
 */
public class IOUtils {
	/**
	 * @方法说明：读文件
	 * @方法名称：readIO
	 * @作....者：wxj
	 * @参....数：@param path
	 * @参....数：@param textName
	 * @参....数：@return
	 * @业....务： @创建时间：2018年7月3日 下午8:25:46
	 */
	public static String readIO(String path, String textName, String charCode) {
		File file = new File(path + "/" + textName);// 指定要读取的文件
		BufferedReader reader;
		String newStr = "";
		try {

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charCode));
			char[] bb = new char[1024];// 用来保存每次读取到的字符
			String str = "";// 用来将每次读取到的字符拼接，当然使用StringBuffer类更好
			int n;// 每次读取到的字符长度
			while ((n = reader.read(bb)) != -1) {
				str += new String(bb, 0, n);
			}
			reader.close();// 关闭输入流，释放连接
			newStr = str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newStr;
	}

	/**
	 * @throws IOException
	 * @方法说明：写文件
	 * @方法名称：writeIO
	 * @作....者：wxj
	 * @参....数：@param path
	 * @参....数：@param textName
	 * @参....数：@return
	 * @业....务： @创建时间：2018年7月3日 下午8:26:03
	 */
	public static void writeIO(String path, String textName, String content) throws IOException {
		File file = new File(path + "/" + textName);// 即将写入文本的路径
		//		File dir = new File(path);
		//		boolean mkdirs = dir.mkdirs();
		if (!file.exists()) { // 判断是否存在
			file.createNewFile(); // 新建文本
		}
		FileWriter wr = new FileWriter(file);// 将File类型文本wr，封装成FileWriter类型
		try {
			wr.write(content); // 将j里的信息，写入wr文本中
			wr.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wr != null) {
			wr.close(); // 关闭流
		}
	}

	public static void main(String[] args) {
		String content =
				readIO("C:\\Users\\issuser\\Downloads", "b3b16c88-27ca-4b3a-a281-965336c44fdb20210521211854.txt",
						"utf-8");
		String[] split = content.split("\n");
		for (String str : split) {
			if (str.contains("BZBM") && str.length() > 25) {
				System.out.println(str);

			}
		}
		;
	}

}