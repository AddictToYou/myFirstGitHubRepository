package downloadfile.utils;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

public class NetProxy {
	private String userName;
	private String Password;
	
	public NetProxy(String userName, String Password) {
		this.userName = userName;
		this.Password = Password;
	}
	public Proxy getProxy(){	
		InetSocketAddress addr = new InetSocketAddress("proxy.neusoft.com", 8080);
	    Authenticator.setDefault(new MyAuthenticator(userName, Password));
	    Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
		return proxy;
	}
 static class MyAuthenticator extends Authenticator {
     private String user = "";
     private String password = "";
     public MyAuthenticator(String user, String password) {
         this.user = user;
         this.password = password;
     }
     protected PasswordAuthentication getPasswordAuthentication() {
         return new PasswordAuthentication(user, password.toCharArray());
     }
 }
}
