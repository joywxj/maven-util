package com.wxj.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
/**  
* @ClassName: SendHttpPostUtil  
* @Description: TODO �ⲿ������Ҫ��body�Ľӿ�
* @author: wxj  
* @date: 2018-08-17 17:09
* @Tel:18772118541
* @email:18772118541@163.com
*/
public class SendHttpPostUtil {
	/**  
	* @Title: httpPostBody  
	* @Description: TODO ������Ҫ��body�������ⲿ�ӿ�
	* @param urlPath ����·��
	* @param body json��ʽ��body����
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
			// ����doOutput����Ϊtrue��ʾ��ʹ�ô�urlConnectionд������
			urlConnection.setDoOutput(true);
			// �����д�����ݵ��������ͣ���������Ϊapplication/json����
			urlConnection.setRequestProperty("content-type", "application/json");
			// �õ���������������
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			// ������д�������Body
			out.write(body);
			out.flush();
			out.close();
			if (urlConnection.getResponseCode() == 200) {
				// �ӷ�������ȡ��Ӧ
				InputStream inputStream = urlConnection.getInputStream();
				String encoding = urlConnection.getContentEncoding();
				result = IOUtils.toString(inputStream, encoding);
				return result;
			} else {
				return result +"�������:"+urlConnection.getResponseCode();
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
