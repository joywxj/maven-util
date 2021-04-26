package com.wxj.test.print;

import com.wxj.test.IOUtils;

/**  
* <p>类名: GHPringTxt  </p>
* <p>描述: TODO lable高速行打印生成打印文本 </p>
* <p>作者:wxj</p>
* <p>电话:18772118541</p>
* <p>邮箱:18772118541@163.com</p>
* <p>日期: 2019-06-13 09:08</p>
* 使用说明:
* 1、需要将导出来的txt文件多余的字体设置给删除掉。
* 2、把数据类型全部删除掉，最后只留有坐标，大小，数值的一行一行的数据
*/
public class GHPringTxt {
	public static void main(String[] args) {
		int cha = 10;
		String str = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "transition.txt","UTF-8");
		String[] split = str.split("\n");
		StringBuffer sb  = new StringBuffer("");
		sb.append("~CREATE;NEWLABLE;269\n");
		sb.append("SCALE;DOT\n");
		String type  = "";
		for (int i = 0; i < split.length; i++) {
			int sc = 0;
			type = getType(split[i]);
			String font = getFontByType(type);
				sb.append(type).append("\n");
				String[] split2 = split[i].split(";");
				if("BARCODE".equals(type)) {
					sb.append(font+split2[3]+";"+split2[4]).append("\n");
				}else if("ALPHA".equals(type)) {
					sc = Integer.parseInt(split2[1]);
					if(!split[i].endsWith(":\"")) {
						sc = sc - cha;
					}
					if(split2[4].startsWith("\"B")) {
						String value = split2[4].substring(2, split2[4].length());
						sb.append(font+split2[0]+";"+sc+";1;1;\""+value).append("\n");
					}else {
						sb.append(font+split2[0]+";"+sc+";3;2;"+split2[4]).append("\n");					
					}
				}else {
					sc = Integer.parseInt(split2[1]);
					if(!split2[4].trim().endsWith(":\"")) {
						sc = sc - cha;
					}
					sb.append(font+split2[0]+";"+sc+";0;0;"+split2[4]).append("\n");
				}
				sb.append("STOP\n");
		}
		sb.append("END\n");
		sb.append("~LPI;6\n");
		sb.append("~EXECUTE;NEWLABLE\n\n");
		sb.append("~NORMAL\n");
		sb.append("~DELETE FORM;NEWLABLE\n");
		System.out.println(sb.toString());
	}

	/**  
	* @Title: getFontByType  
	* @Description: TODO
	* @param type
	* @return   
	* @date:2019-04-28 15:39
	*/
	private static String getFontByType(String type) {
		if("BARCODE".equals(type)){
			return "C128B;X1A;H7;DARK;";
		}	else if("ALPHA".equals(type)) {
			return "DARK;";
		}
		return "F16;P8;";
	}

	/**  
	* @Title: getType  
	* @Description: TODO
	* @param str
	* @return   
	* @date:2019-04-28 15:33
	*/
	private static String getType(String str) {
		if(str.contains("DARK")) {
			return "BARCODE";
		}else{
			String[] split = str.split(";");
			if("2".equals(split[3]) || split[4].startsWith("\"B")) {
				return "ALPHA";
			}
		}
		return "TWOBYTE";
	}
}
