package com.wxj.test;

import java.io.IOException;

/**
 * <p>@ClassName: LoadTag  </p>
 * <p>@Description: 标签</p>
 * <p>@author: joy  </p>
 * <p>@date: 2021/4/28</p>
 */
public class LoadTag {

	public static void main(String[] args) throws IOException {
		/**
		 * 业务说明:
		 * 业务需求:需要把标签里面的标题统统都缩小，且上移
		 * 文件特征:
		 * 		需求修改的标题的type=text包含default字眼
		 * 	特别说明:"请沿此虚线裁开"这一名话不可以处理
		 */
		String content = IOUtils.readIO("C:\\Users\\issuser\\Desktop", "Tag.TXT", "UTF-8");
		String[] split = content.split("\n");
		// 第， 页， 请沿此虚线裁开这三个字不改
		int start = 0;
		int end = 0;
		String key = "";
		StringBuffer sb = new StringBuffer("");
		Double cha = 0.5;
		for (String str : split) {
			if (str.contains("{ type: 'text',") && str.contains("default: '") && !str.contains("第") && !str
					.contains("页") && !str.contains("请沿此虚线裁开") && !str.contains("岚图汽车科技公司")) {
				key = "fontSize";
				start = str.indexOf(key);
				end = str.indexOf(",", start);
				String fontSize = str.substring(start + key.length() + 1, end);
				key = "x";
				start = str.indexOf(key, end);
				end = str.indexOf(",", start);
				String x = str.substring(start + key.length() + 1, end);

				key = "y";
				start = str.indexOf(key, end);
				end = str.indexOf(",", start);
				String y = str.substring(start + key.length() + 1, end);
				//
				if (str.contains("到货指示") || str.contains("工位") || str.contains("投料点") || str.contains("备注") || str
						.contains("是否软件类")) {
					cha = 0.7;
				} else if (str.contains("SNP") || str.contains("数量")) {
					cha = 0.50;
				} else if ((str.contains("上线") || str.contains("库") || str.contains("编")) && !str.contains("上线时间")) {
					cha = 0.30;
				} else {
					cha = 0.00;
				}
				Double dy = Double.parseDouble(y) - cha;
				key = "default";
				start = str.indexOf(key, end);
				end = str.indexOf("}", start);
				String defaul = str.substring(start + key.length() + 1, end);
				String text = "{ type: 'text', fontSize: " + 0.9 + ", x: " + x + ", y: " + dy + ", default: " + defaul
						+ " },";
				sb.append(text).append("\n");
			} else {
				sb.append(str).append("\n");
			}
		}

		IOUtils.writeIO("C:\\Users\\issuser\\Desktop", "xx.txt", sb.toString());
	}
}
