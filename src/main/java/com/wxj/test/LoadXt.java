package com.wxj.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * <p>@ClassName:   </p>
 * <p>@Description: 修复线体数据</p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/4/21</p>
 */
public class LoadXt {

	public static void main(String[] args) {
		/**
		 * 线体这个字段后加的，一开始会没有值，所以这里需要初始化一下
		 * 线体业务:
		 *
		 */
		String path = "C:\\Users\\issuser\\Desktop\\copy";
		File fileDir = new File(path);
		List<File> files = Arrays.asList(fileDir.listFiles());
		for (File file : files) {
			String name = file.getName();
			String content = readIO(path, name, "UTF-8");
			JSONArray jsonArray = JSONArray.parseArray(content);
			String sql = null;
			for (int i = 0; i < jsonArray.size(); i++) {

				JSONObject json = jsonArray.getJSONObject(i);
				String xiant = json.get("XIANT").toString().trim();
				String pslx = json.get("PEISLX").toString().substring(0, 3).trim();
				if (!pslx.equals(xiant)) {
					System.out.println("线体:" + xiant + "配送类型:" + pslx);
				}

				sql = "UPDATE mutual_synchronize_order_detail SET XT = '" + json.get("XIANT") + "' ";
				sql = sql + "WHERE VIN = '" + json.get("VIN").toString() + "' AND DDBH = '" + json.get("DDBH")
						.toString() + "' AND LJBM = '" + json.get("LINGJBH").toString() + "' AND FD = '" + json
						.get("FHD_JC").toString() + "' AND GYSBM = '" + json.get("GONGYSDM").toString() + "';";
				System.out.println(sql);
			}

		}
	}

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
}
