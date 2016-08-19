package JustDialMultiServiceThreadPoolBased;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {
	
	private final static String baseUrl = "http://www.justdial.com/";
	protected final static String localPath = "C:\\Z-DataStore\\JustDial\\";
	protected WebDriver wd;
	private WebElement searchBox;
	
	protected void init(String query) {
		
		wd = new FirefoxDriver();
		wd.get(baseUrl);
		
		wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		searchBox = wd.findElement(By.id("srchbx"));
		searchBox.sendKeys(query);
		
	}
	
}
