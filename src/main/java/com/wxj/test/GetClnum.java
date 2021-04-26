package com.wxj.test;

/**
 * <p>
 * 类名: GetClnum
 * </p>
 * <p>
 * 描述: TODO 从一个SQL里面提取字段
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
 * 日期: 2019-07-19 09:53
 * </p>
 */
public class GetClnum {

	public static void main(String[] args) {
		String sql = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "SQL.TXT", "UTF-8");
		String[] split = sql.split("\n");
		for (String string : split) {
			String[] split2 = string.split(" ");
			System.out.print("'" + split2[split2.length - 1].replaceAll("(\r\n|\r|\n|\n\r)", "") + "',");
		}
	}

}
