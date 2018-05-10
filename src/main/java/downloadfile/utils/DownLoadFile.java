package downloadfile.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

import org.eclipse.jetty.util.StringUtil;

//import org.apache.commons.io.FileUtils;

public class DownLoadFile extends Thread{
	/** 
	 * 从网络Url中下载文件 
	 * @param urlStr 
	 * @param fileName 
	 * @param savePath 
	 * @throws IOException 
	 */  
//	public static void  downLoadFromUrl(List<String> urlList, String savePath) throws IOException{  
	public static void  downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException{       
	     
	    //InetSocketAddress addr = new InetSocketAddress("proxy.neusoft.com", 8080);
	    //Authenticator.setDefault(new MyAuthenticator("zhouminlin", "N@19940724"));
	    //Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
		Proxy proxy = new NetProxy("zhouminlin", "N@19940724").getProxy();
	    try {
//			for (String urlStr : urlList) {
	    	if(StringUtil.isBlank(fileName)||fileName == null){
	    		String[] doc =  urlStr.split("/");
				fileName = doc[doc.length-1];
	    	}
			//System.out.println(fileName);
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
			//设置超时间为3秒  
			conn.setConnectTimeout(3 * 1000);
			//防止屏蔽程序抓取而返回403错误  
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			//得到输入流  
			InputStream inputStream = conn.getInputStream();
			//获取自己数组  
			byte[] getData = readInputStream(inputStream);
			//文件保存位置  
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}
			File file = new File(saveDir + File.separator + fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			System.out.println("info:" + url + " download success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

	}  
//	static class MyAuthenticator extends Authenticator {
//	    private String user = "";
//	    private String password = "";
//	  
//	    public MyAuthenticator(String user, String password) {
//	      this.user = user;
//	      this.password = password;
//	    }
//	  
//	    protected PasswordAuthentication getPasswordAuthentication() {
//	      return new PasswordAuthentication(user, password.toCharArray());
//	    }
//	  }


	/** 
	 * 从输入流中获取字节数组 
	 * @param inputStream 
	 * @return 
	 * @throws IOException 
	 */  
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
	    byte[] buffer = new byte[1024];    
	    int len = 0;    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	    while((len = inputStream.read(buffer)) != -1) {    
	        bos.write(buffer, 0, len);    
	    }    
	    bos.close();    
	    return bos.toByteArray();    
	}    

	/*public static String downloadHttpUrl(String url, String dir, String fileName) {  
        //String fileName = "test.jpg";  
        try {  
            URL httpurl = new URL(url);  
            File f = new File(dir + fileName);   
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "";  
        }  
        return dir + fileName;  
    } */
	public static void main(String[] args) {
		
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://hrss.jl.gov.cn/zcfbjjd/zcfb/201710/P020171027576215321888.pdf");
		urlList.add("http://www.mohrss.gov.cn/SYrlzyhshbzb/shehuibaozhang/zcwj/gongshang/201507/W020150722572401611989.doc");
		urlList.add("http://hrss.jl.gov.cn/fwzc/bgxz/201705/P020170531379858189985.doc");
		urlList.add("http://hrss.jl.gov.cn/fwzc/bgxz/201705/P020170531379152819477.doc");
		urlList.add("http://hrss.jl.gov.cn/fwzc/bgxz/201702/P020170208476495655215.doc");
		urlList.add("http://hrss.jl.gov.cn/fwzc/bgxz/201608/P020170217311536538857.docx");
	    try{  
//	    	downLoadFromUrl(urlList, "d:/");    
	    }catch (Exception e) {  
	        // TODO: handle exception  
	    }  
		/*String file = downloadHttpUrl("http://www.mohrss.gov.cn/SYrlzyhshbzb/shehuibaozhang/zcwj/gongshang/201507/W020150722572401611989.doc",
				"d:/","2.doc");
		System.out.println(file);*/
		
	}  
}