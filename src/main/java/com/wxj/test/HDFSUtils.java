package com.wxj.test;
import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**  
* @ClassName: HDFSUtils  
* @Description: TODO HDFS工具类
* @author: wxj  
* @date: 2019年2月1日
* @Tel:18772118541
* @email:18772118541@163.com
*/
public class HDFSUtils {
	/**  
	* @Title: appendHDFS  
	* @Description: TODO 把新的日志追加到HDFS系统上面去
	* @param hdfs HDFS文件系统路径
	* @param append 是否追加
	* @param data 要追加的数据
	* @return void
	* @date:2018-08-09 15:37
	*/
	public static void appendHDFS(String hdfs, boolean append, String data) {
		Configuration conf = new Configuration();//配置类
		conf.setBoolean("dfs.support.append", append);// 添加了这个属性,就能再hdfs文件后面追加
		FileSystem fs;//文件系统
		try {
			System.out.println("hdfs文件系统路径:"+hdfs);
			fs = FileSystem.get(URI.create(hdfs), conf);
			Path path = new Path(hdfs);
			if (!fs.exists(path)) {// 判断文件是否存在
				FSDataOutputStream out = fs.create(path);//创建输出流
				out.write(data.getBytes("UTF-8"));//输出流输出
				out.close();
			} else {
				FSDataInputStream in = fs.open(path);//创建写入流
				byte[] ioBuffer = new byte[10240];
				int readLen = in.read(ioBuffer);
				//把原来的文件里面的东西全部拿出来
				while (readLen != -1) {
					readLen = in.read(ioBuffer);
				}
				in.close();
				//把原来的东西拼接到一直,
				//说明:这是因为在生成data的时候里面写了/n这里就不再加/n了，不然后面每一次的日志和上一次的日志都会有一个空行
				String str = new String(ioBuffer) + data;
				FSDataOutputStream out = fs.create(path);
				out.write(str.getBytes("UTF-8"));//数据写入到HDFS文件里面去
				out.close();
			}
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 我要从文件系统里面读文件内容
	 * @param hdfs hdfs文件路径
	 * @return
	 */
	public static String getFileContext(String hdfs) {
		String result = "";
		Configuration conf = new Configuration();
		FileSystem fs;//文件系统
		try {
			fs = FileSystem.get(URI.create(hdfs), conf);
			Path path = new Path(hdfs);//文件系统路径
			FSDataInputStream in = fs.open(path);//输出流
			byte[] ioBuffer = new byte[10240];
			int readLen = in.read(ioBuffer);
			while (readLen != -1) {
				readLen = in.read(ioBuffer);
			}
			in.close();//关闭流
			result = new String(ioBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String fileContext = HDFSUtils.getFileContext("hdfs://192.168.0.162:9000/log/1500/2018/08/car_06.log");
		System.err.println(fileContext);
	}
}
