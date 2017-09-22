package cn.message.utils;

import java.net.InetAddress;

public class NetWorkIp {
	
	/**
	 * 获取局域网ip
	 * @return
	 */
	public static String getIp(){
		InetAddress addr = null;
		String ip = "";
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress().toString();// 获得本机IP　　
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}
}
