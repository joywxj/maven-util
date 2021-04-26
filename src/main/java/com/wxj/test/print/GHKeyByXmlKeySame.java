package com.wxj.test.print;

import com.wxj.test.IOUtils;

/**  
* <p>类名: GHKeyByXml  </p>
* <p>描述: TODO 高行打印根据xml文件获取key编号</p>
* <p>作者:吴兴军</p>
* <p>电话:18772118541</p>
* <p>邮箱:18772118541@163.com</p>
* <p>日期: 2019-06-06 14:24</p>
*/
public class GHKeyByXmlKeySame {
	public static void main(String[] args) {
		String str = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "SQL.TXT", "UTF-8");
		String[] split = str.split("\n");
		String key = "";
		String value = "";
		for (String xml : split) {
			if(xml.contains("<data parentid")) {
				int begin = xml.indexOf("key=\"")+"key=\"".length();
				int end = xml.indexOf("\"", begin);
				key = xml.substring(begin, end);
				begin = xml.indexOf("value=\"")+"value=\"".length();
				end = xml.indexOf("\"", begin);
				if("replace".equals(xml.substring(begin, end).replace("#", ""))) {
					System.out.println(key+":"+value);
				}
				value = xml.substring(begin, end).replace("#", "");
			}
		}
	}
}
