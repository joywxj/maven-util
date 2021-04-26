package com.wxj.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
/**  
* @ClassName: SendHttpPostUtil  
* @Description: TODO 外部调用需要传body的接口
* @author: wxj  
* @date: 2018-08-17 17:09
* @Tel:18772118541
* @email:18772118541@163.com
*/
public class SendHttpPostUtil {
	/**  
	* @Title: httpPostBody  
	* @Description: TODO 请求需要传body参数的外部接口
	* @param urlPath 请求路径
	* @param body json格式的body参数
	* @return
	* @throws Exception   
	* @return String
	* @date:2018-08-18 18:03
	*/
	public static String httpPostBody(String urlPath, String body) throws Exception {
		String result = "";
		try {
			// Configure and open a connection to the site you will send the
			// request
			URL url = new URL(urlPath);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// 设置doOutput属性为true表示将使用此urlConnection写入数据
			urlConnection.setDoOutput(true);
			// 定义待写入数据的内容类型，我们设置为application/json类型
			urlConnection.setRequestProperty("content-type", "application/json");
			// 得到请求的输出流对象
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			// 把数据写入请求的Body
			out.write(body);
			out.flush();
			out.close();
			if (urlConnection.getResponseCode() == 200) {
				// 从服务器读取响应
				InputStream inputStream = urlConnection.getInputStream();
				String encoding = urlConnection.getContentEncoding();
				result = IOUtils.toString(inputStream, encoding);
				return result;
			} else {
				return result +"错误代码:"+urlConnection.getResponseCode();
			}
		} catch (IOException e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(11);
			String postBody = SendHttpPostUtil.httpPostBody("http://localhost:9993/test",
					"{\"sysTime\":\"2018-08-13\",\"ip\":\"2018-08-14\",\"port\":1,\"url\":1,\"sysTime\":10}");
			System.out.println(postBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
