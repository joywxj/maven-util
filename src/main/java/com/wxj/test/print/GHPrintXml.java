package com.wxj.test.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wxj.test.IOUtils;

import java.util.Set;

/**  
* @ClassName: CreatePrintXml  
* @Description: TODO 生成高行打印的xml
* @author: wxj  
* @date: 2019年4月17日
* @Tel:18772118541
* @email:18772118541@163.com
* 说明:
* 	1、把打印的文本的文件头和文件尾给去掉
* 	2、把BARCODE的数值给删掉
* 	3、生成好的数据没有生成文件，只是有文件内容，需要粘贴出去
* 	4、生成的内容只有xml文件里面的内容，具体的一些area是没有，所以你需要把这些内容粘贴到文件的area标签里面去
* 	5、如果有两部分，area2的部分，需要重新编号
* 	6、最后的那部分内容是那些对应的含义，但是像那种前面没有说明的是生成不出来的，需要自己去找一下，比如条形码那那样的数据。
* 	7、生成的sr全部都是#sr，所以第一行，BARCODE,和一些有特殊位置的sr需要自己手动去处理一下。
*/
public class GHPrintXml {
	private static String BAR_FONT = "C128B;X1A;H7;DARK;";
	private static String TWO_FONT = "F16;P8;";
	private static String parentid = "1";
	static String row = "";
	private static String sc = "";
	private static String sr = "#sr";
	private static String value = "";
	private static String key = "";
	private static String dataType = "";
	private static int key_index = 1;
	private static int ve  = 0;
	private static int he = 0;
	private static int row_index = 0;
	private static String duiy = "";
	private static String JSON_START = "[{";
	private static String JSON_END = "}]";
	private static StringBuffer JSON_BODY = new StringBuffer("");
	private static Map<Integer,List<String>> pringMaP;
	private static StringBuffer XML_BODY = new StringBuffer("");
	/** 存放row的map */
	private static Map<Integer,Integer> rowMaP;
	public static void main(String[] args) {
		pringMaP = new HashMap<Integer, List<String>>();
		rowMaP = new HashMap<Integer,Integer>();
		String font = "";
		JSON_BODY.append(JSON_START);
		/**
		 * 1、取txt
		 * 2、判断类型：TWOBYTE，BARCODE
		 * 3、BARCODE它的front = C128B;X1A;H7; TWOBYTE的 front = F16;P8;
		 * <data parentid="1" row="1" key="1-1" dataType="TWOBYTE" font="F16;P8;"  sr="170" sc="204;" ve="0;" he="0;"  value="要货令号:"/>
		 */
		String str = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "print.txt","gbk");
		String[] split = str.split("\n");
		for (int i = 0; i < split.length; i++) {
			String strs = split[i].trim();
			if("TWOBYTE".equals(strs) || "BARCODE".equals(strs) || "ALPHA".equals(strs)) {
				font = getFont(strs);
				getDataType(strs);
				continue;
			}else if(strs.startsWith("\"") || "STOP".equals(strs)) {
				continue;
			}
			key = parentid+"-"+key_index;
			String[] split2 = strs.split(";");
			setSr(strs,split2);
			setSc(strs,split2);
			setValue(strs,split2);
			setVe(strs,split2);
			setHe(strs,split2);
			inVokeRow();
			String xmlValue = getXmlValue();
			if("\"#replace\"".equals(xmlValue)) {
				JSON_BODY.append(" \""+parentid+"-"+(key_index)+"\"").append(": ").append(value).append(",");
			}
			font = getFont(dataType);
			row = "<data parentid=\""+parentid+"\" row=\""+rowMaP.get(Integer.parseInt(sr))+"\" key=\""+key+"\" dataType=\""+dataType+"\" font=\""+font+"\"  sr=\"#sr\" sc=\""+sc+";\" ve=\""+ve+";\" he=\""+he+";\"  value="+xmlValue+"/>";
			String beiZhu = "";
			
			if(value.trim().endsWith(":\"")) {
				beiZhu = "<!-- "+value.substring(1, value.length()-2)+" -->";
				duiy = duiy+""+parentid+"-"+(key_index+1)+":"+value.substring(1, value.length()-2)+"\n";
			}
			setPrintMap(beiZhu);
			key_index++;
		};
		JSON_BODY.append(JSON_END);
		printXml();
		try {
			IOUtils.writeIO("C:\\Users\\issuser\\Desktop", "XML.xml", XML_BODY.toString());
			IOUtils.writeIO("C:\\Users\\issuser\\Desktop", "comment.txt", duiy.toString());
			IOUtils.writeIO("C:\\Users\\issuser\\Desktop", "json.txt", JSON_BODY.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**  
	* @Title: setPrintMap  
	* @Description: TODO 填充打印的Map
	* @date:2019-04-26 15:55
	*/
	private static void setPrintMap(String beiZhu) {
		if(pringMaP.containsKey(Integer.parseInt(sr))) {
			List<String> list = pringMaP.get(Integer.parseInt(sr));
			if(!"".equals(beiZhu)) {
				list.add(beiZhu);
			}
			list.add(row);
			pringMaP.put(Integer.parseInt(sr), list);
		}else {
			List<String> list = new ArrayList<String>();
			if(!"".equals(beiZhu)) {
				list.add(beiZhu);
			}
			list.add(row);
			pringMaP.put(Integer.parseInt(sr), list);
		}
	}
	/**  
	* @Title: printXml   打印xml的内衬
	* @Description: TODO   
	* @date:2019-04-26 15:54
	*/
	private static void printXml() {
		Set<Entry<Integer,List<String>>> entrySet = pringMaP.entrySet();
		Iterator<Entry<Integer, List<String>>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			Entry<Integer, List<String>> next = iterator.next();
			List<String> list = next.getValue();
			for (String string : list) {
				XML_BODY.append(string).append("\n");
			}
		}
	}
	/**  
	* @Title: getXmlValue  
	* @Description: TODO
	* @return   
	* @date:2019-04-18 11:17
	*/
	private static String getXmlValue() {
		String xmlValue  = "";
		if(!value.trim().endsWith(":\"")) {
			xmlValue = "\"#replace\"";
		}else {
			xmlValue = value;
		}
		return xmlValue;
	}
	/**  
	* @Title: inVokeRow  
	* @Description: TODO   
	* @date:2019-04-18 11:16
	*/
	private static void inVokeRow() {
		if(!rowMaP.containsKey(Integer.parseInt(sr))) {
			row_index ++;
			rowMaP.put(Integer.parseInt(sr), row_index);
		}
	}
	/**  
	* @Title: setValue  
	* @Description: TODO
	* @param strs
	* @param split2   
	* @date:2019-04-18 11:14
	*/
	private static void setValue(String strs, String[] split2) {
		if("TWOBYTE".equals(strs)) {
			value = split2[4];
		}else {
			value = split2[split2.length-1];
		}
	}
	/**  
	* @Title: setHe  
	* @Description: TODO
	* @param strs
	* @param split2   
	* @date:2019-04-18 11:09
	*/
	private static void setHe(String strs, String[] split2) {
		if(dataType.equals("ALPHA")) {
			if(value.startsWith("\"B")) {
				he = 1;
			}else {
				he = 2;
			}
		}else {
			he = 0;
		}
	}
	/**  
	* @Title: setVe  
	* @Description: TODO
	* @param strs
	* @param split2   
	* @date:2019-04-18 11:09
	*/
	private static void setVe(String strs, String[] split2) {
		if(dataType.equals("ALPHA")) {
			if(value.startsWith("\"B")) {
				ve = 2;
			}else {
				ve = 3;
			}
		}else {
			ve = 0;
		}
	}
	/**  
	* @Title: 取sr
	* @Description: TODO 取sr
	* @param strs
	* @param split2   
	* @date:2019-04-18 11:09
	*/
	private static void setSr(String strs, String[] split2) {
		if("ALPHA".equals(dataType)) {
			sr = split2[1];
		}else if("TWOBYTE".equals(dataType)) {
			sr = split2[0];
		}else if("BARCODE".equals(dataType)) {
			sr = split2[split2.length-2];
		}
	}
	/**  
	* @Title: getSc  
	* @Description: TODO
	* @return   
	* @date:2019-04-18 11:06
	*/
	private static void setSc(String strs,String[] split2) {
		if("ALPHA".equals(dataType)) {
			sc = split2[2];
		}else if("TWOBYTE".equals(dataType)) {
			sc = split2[1];
		}else if("BARCODE".equals(dataType)) {
			sc = split2[split2.length-1];
		}
	}
	/**  
	* @Title: getDataType  
	* @Description: TODO
	* @param string
	* @return   
	* @date:2019-04-17 16:10
	*/
	private static String getDataType(String str) {
		if("BARCODE".equals(str) || "TWOBYTE".equals(str) || "ALPHA".equals(str)) {
			dataType = str;
			return str;
		}
		return null;
	}
	/**  
	* @Title: getFont  
	* @Description: TODO
	* @return   
	* @date:2019-04-17 15:55
	*/
	private static String getFont(String str) {
		String font = "";
		if("BARCODE".equals(str)) {
			font = BAR_FONT;
		}else if("TWOBYTE".equals(str)) {
			font = TWO_FONT;
		}
		return font;
	}
}
