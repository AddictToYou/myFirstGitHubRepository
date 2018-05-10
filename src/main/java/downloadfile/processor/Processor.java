package downloadfile.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import downloadfile.utils.DownLoadFile;
import downloadfile.utils.RemoveDuplicateUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class Processor implements PageProcessor{
	
	private Site site = Site.me()
			.setRetryTimes(3)
			.setSleepTime(100)
			.setTimeOut(10000);
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	@Override
	public void process(Page page){//http://hrss.jl.gov.cn/fwzc/bgxz/index.html....http://hrss.jl.gov.cn/fwzc/bgxz/index_1.html
		// TODO Auto-generated method stub
		String url = page.getUrl().toString();
		System.out.println("開始爬取鏈接："+url);
		
		List<String> fileUrlList = new ArrayList<String>();
		
		if(page.getUrl().regex("(http://www\\.mohrss\\.gov\\.cn/gkml/81/83/list\\.html)").match()||page.getUrl().regex("(http://www\\.mohrss\\.gov\\.cn/gkml/81/83/list_\\d+\\.html)").match()){
			System.out.println("列表頁鏈接："+page.getUrl().toString());
			List<String> detailPages = page.getHtml().xpath("//div[@class='content']").links().all();
			
			for(String pageUrl : detailPages){
				if(pageUrl.endsWith("doc")||pageUrl.endsWith("pdf")){
					fileUrlList.add(pageUrl);
				}else{
					page.addTargetRequest(pageUrl);
				}
			}
		}else{
			System.out.println("詳情頁鏈接："+page.getUrl().toString());
			boolean status = false;
			int i = 0;
			List<String> fileWordName = new ArrayList<>();
			List<String> detailPages = RemoveDuplicateUtils.removeDuplicateByHashSet(page.getHtml().xpath("//div[@class='govInfoMainTabListTxtMain']").links().all());
			String[] xpath = {"//p[@class='MsoNormal']/a/span/allText()","//div/span/font/a/allText()","//p[@class='MsoNormal']/span/a/allText()","//p[@class='MsoNormal']/font/a/allTtext()"};
			for(String path : xpath){
				List<String> fileWord = page.getHtml().xpath(path).all();
//				List<WebElement> webElement = (List<WebElement>) ((WebDriver) page).findElement(By.id("DataList"));
				if(fileWord.isEmpty()){} //break;
				else{
					//System.out.println(fileWord);
					for(int j = 0;j < fileWord.size();j++){
						System.out.println(fileWord.get(j));
						if(fileWord.get(j).length() != 1 && !fileWord.get(j).equals("")){
							fileWordName.add(fileWord.get(j));
						}
						System.out.println(fileWordName);
					}
					break;
				}
			}
			for(String fileUrl : detailPages){
				String[] urlspe =  fileUrl.split("\\.");
				String fileType = urlspe[urlspe.length-1];
				switch (fileType) {
				case "doc": status = true;
					break;
				case "pdf": status = true;
					break;
				case "docx": status = true;
					break;	
				case "xlsx": status = true;
					break;
				case "xls": status = true;
				break;
				default:	status = false;
					break;
				}
				
				if(status){
					String fileName = "";
					
//					String[] xpath = {"//p[@class='MsoNormal']/span/a/text()","//div/span/font/a/text()"};
//					for(String path : xpath){
//						List<String> fileWord = page.getHtml().xpath(path).all();
//						List<WebElement> webElement = (List<WebElement>) ((WebDriver) page).findElement(By.id("DataList"));
//						if(fileWord.isEmpty()) break;
//						System.out.println(fileWord);
						fileName = fileWordName.get(i)+"."+fileType;
						i++;
//					}
					//String fileName = page.getHtml().xpath("//p[@class='MsoNormal']/span/a/text()").toString();
					try {
						DownLoadFile.downLoadFromUrl(fileUrl, fileName, "d:/Downloads");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
//		try{
//			
//			//DownLoadFile.downLoadFromUrl(detailPages, "d:/Downloads");    
//	    }catch (Exception e) {  
//	        // TODO: handle exception  
//	    } 
	}
	
	public static void main(String[] args) throws InterruptedException {
//		try {
//			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
//        WebDriver webDriver = new ChromeDriver();
//        webDriver.get("http://www.baidu.com");
//        webDriver.get("http://www.mohrss.gov.cn/gkml/xxgk/201707/t20170710_273761.html");
//        webDriver.get("http://huaban.com/");
//        webDriver.wait(300);
//        Thread.sleep(4000);
//        List<WebElement> webElement = webDriver.findElements(By.xpath("//p[@class='MsoNormal']/span/a"));
//        for(WebElement i : webElement){
//        	System.out.println(i.getText());
//        }
//        
//        webDriver.close();
        
//        System.out.println(webDriver.getTitle()); 
//        webDriver.close();
        
//        webDriver.quit();
//        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("D:\\chromedriver_win32\\chromedriver.exe");
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		httpClientDownloader.setProxyProvider(SimpleProxyProvider
				.from(new Proxy("proxy.neusoft.com", 8080, "zhouminlin",
						"ZML19940724+zzx")));
		Spider spider = new Spider(new Processor());
		spider.addUrl("http://search.mohrss.gov.cn/was5/web/search?page=1&channelid=226064&searchword=劳动用工备案&keyword=劳动用工备案&orderby=-DOCRELTIME&perpage=10&outlinepage=10&searchscope=&timescope=&timescopecolumn=&orderby=-DOCRELTIME&andsen=&total=&orsen=&exclude=")
			  .setDownloader(httpClientDownloader)
			  .thread(10);
//		for(int i = 1; i < 8; i++){
//			spider.addUrl("http://www.mohrss.gov.cn/gkml/81/83/list_"+ i +".html");
//		}
//		spider.setDownloader(seleniumDownloader);
		spider.run();
		//Spider.create(new Processor()).setDownloader(httpClientDownloader).addUrl("http://hrss.jl.gov.cn/fwzc/bgxz/index.html").thread(3).run();
	}
}

