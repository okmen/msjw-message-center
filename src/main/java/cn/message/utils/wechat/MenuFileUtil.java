package cn.message.utils.wechat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
public class MenuFileUtil {
	/**
	 * 读取menu.json文件 用来创建菜单
	 * @param fileName
	 * @return
	 */
	public static String readFileByChars(String fileName) {
		FileInputStream file = null;
		BufferedReader reader = null;
		InputStreamReader inputFileReader = null;
		String content = "";
		String tempString = null;
		try {
			file = new FileInputStream(fileName);
			inputFileReader = new InputStreamReader(file, "utf-8");
			reader = new BufferedReader(inputFileReader);
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				content += tempString;
			}
			reader.close();
		} catch (IOException e) {
			return "";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}
	
	/**
	 * 读取jar包中的资源文件
	 * @param fileName
	 * @return
	 */
	public static String readJarResourceFile(String fileName){
		InputStream in= MenuFileUtil.class.getClass().getResourceAsStream("/"+fileName);  
        Reader f = new InputStreamReader(in);         
        BufferedReader fb = new BufferedReader(f);  
        StringBuffer sb = new StringBuffer("");  
        String s = "";  
        try {
			while((s = fb.readLine()) != null) {  
			    sb = sb.append(s);  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return sb.toString();  
	}
	
	public static void main(String[] args) {
		System.out.println(readFileByChars("C:/worktools/apache-tomcat-8.5.12/webapps/web/WEB-INF/classes/menu.json"));
	}
}
