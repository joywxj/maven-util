/**
 * 
 */
package com.wxj.test.print;

import com.wxj.test.IOUtils;
/**  
* <p>类名: GHPringInfo  </p>
* <p>描述: TODO 根据key生成打印信息</p>
* <p>作者:WXJ</p>
* <p>电话:18772118541</p>
* <p>邮箱:18772118541@163.com</p>
* <p>日期: 2019-06-13 09:11</p>
*/
public class GHPrintInfo {
	private static String start  ="[{ ";
	private static String end  ="}] ";
	private static StringBuffer body = new StringBuffer("");
	private static int areaid = 1;
	public static void main(String[] args) {
		String str = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "JSON.txt","UTF-8");
		body.append(start);
		String[] split = str.split("\n");
		for (String string : split) {
			string = string.replace("\n", "");
			int index = string.indexOf(":");
			String key = string.substring(0,index);
			int indexOf = key.indexOf("-");
			int indexArea = Integer.parseInt(key.substring(0,indexOf));
			String value = string.substring(index+1,string.length()).replaceAll("[\\t\\n\\r]", "");;
			if(indexArea != areaid) {
				areaid = indexArea;
				body.append("}] \n [{ ");
				body.append(" \""+key+"\"").append(": \"").append(value).append("\",");
			}else {
				body.append(" \""+key+"\"").append(": \"").append(value).append("\",");
			}
		}
		body.append(end);
		System.out.println(body.toString());
	}
}
