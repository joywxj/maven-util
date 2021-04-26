package com.wxj.test.print;
import java.util.HashMap;
import java.util.Map;
import com.wxj.test.IOUtils;
/**  
* <p>类名: GHPringTxt  </p>
* <p>描述: TODO 根据xml生成打印数据 </p>
* <p>作者:wxj</p>
* <p>电话:18772118541</p>
* <p>邮箱:18772118541@163.com</p>
* <p>日期: 2019-06-13 09:08</p>
*/
public class GHPringTxtByXml {
	private static String start  ="[{ ";
	private static String end  ="}] ";
	private static StringBuffer body = new StringBuffer("");
	private static Map<String,String> keyMap = new HashMap<String,String>();
	public static void main(String[] args) {
		String str = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "transition.txt","UTF-8");
		body.append(start);
		String[] split = str.split("\n");
		for (String string : split) {
			if(string.contains("#replace")) {
				int start = string.indexOf("key=\"")+"key=\"".length();
				int end = string.indexOf("\"", start);
				String key = string.substring(start, end);
				if(!keyMap.containsKey(key)) {
					keyMap.put(key, key);
					String sss = ""+key+":'wxj',";
					body.append(sss);
				}
			}
		}
		body.append(end);
		System.out.println(body.toString());
	}
}
