package com.wxj.test;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
/**  
* @ClassName: FTPUtil  
* @Description: TODO
* @author: wxj  
* @date: 2019年2月1日
* @Tel:18772118541
* @email:18772118541@163.com
*/
public class FTPUtil {
	public static void main(String[] args) {  
    	ftpUpload("192.168.0.162", 22, "root", "root", "d:\\scan_machine.sql","/var/","scan_machine.sql");  
    }  
  
    /**
    	 * @方法说明：ftp上传文件到别的计算机上面(所在的设备)
    	 * @方法名称：ftpUpload
    	 * @作....者：wxj
    	 * @参....数：@param host 主机ip
    	 * @参....数：@param port  端口
    	 * @参....数：@param username 用户名
    	 * @参....数：@param password密码
    	 * @参....数：@param src 源路径
    	 * @参....数：@param dst目的路径
    	 * @参....数：@return
    	 * @创建时间：2018年2月6日 下午3:49:21
    	 */
    @SuppressWarnings("resource")
	public static boolean ftpUpload(String host, int port, String username,String password, String src,String dstPath,String dstFileName) {  
        ChannelSftp sftp = null;  
        Channel channel = null;  
        Session session = null;  
        try {  
            JSch jsch = new JSch();  
            session = jsch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();  
            channel = session.openChannel("sftp");  
            channel.connect();  
            sftp = (ChannelSftp) channel;  
            OutputStream out = sftp.put(dstPath+dstFileName, ChannelSftp.OVERWRITE);
            byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
            int read;
            if (out != null) {
                InputStream is = new FileInputStream(src);
                do {
                    read = is.read(buff, 0, buff.length);
                    if (read > 0) {
                        out.write(buff, 0, read);
                    }
                    out.flush();
                } while (read >= 0);
            }
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        } finally {  
            closeChannel(sftp);  
            closeChannel(channel);  
            closeSession(session);  
        }  
    }  
  
    private static void closeChannel(Channel channel) {  
        if (channel != null) {  
            if (channel.isConnected()) {  
                channel.disconnect();  
            }  
        }  
    }  
  
    private static void closeSession(Session session) {  
        if (session != null) {  
            if (session.isConnected()) {  
                session.disconnect();  
            }  
        }  
    }  
}
