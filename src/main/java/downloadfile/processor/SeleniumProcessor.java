package downloadfile.processor;

import java.util.ArrayList;
//import java.net.Proxy;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import downloadfile.utils.NetProxy;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SeleniumProcessor implements PageProcessor{

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(Page arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) throws Exception {
		try {
			System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\chromedriver_win32\\chromedriver.exe");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		WebDriver webDriver = new ChromeDriver();
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://www.chinajob.gov.cn/EmploymentServices/content/2017-04/03/content_1298120.htm");		
//		urlList.add("http://roll.news.qq.com/#");	
//		urlList.add("http://roll.news.qq.com/");		
//		urlList.add("http://roll.news.qq.com/");		
//		DesiredCapabilities caps = new DesiredCapabilities();
//		Proxy proxy = new NetProxy("zhouminlin", "N@19940724").getProxy();
//		Proxy proxy = new Proxy();
//		caps.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
//		caps.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
//		caps.setCapability(CapabilityType.PROXY, proxy);
//
//		webDriver = new ChromeDriver(caps);
//		webDriver.get("http://roll.news.qq.com/");
		for(String url : urlList){
			webDriver.get(url);
		}
		
		Thread.sleep(50000);
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
//		Page page = new Page();
//		page = webElement.getAttribute("outerHTML");
        System.out.println(webElement.getAttribute("outerHTML"));
//		for(WebElement i : webElement){
//        	System.out.println(i.getText());
//        }
		webDriver.close();
		webDriver.quit();
	}
	
}
