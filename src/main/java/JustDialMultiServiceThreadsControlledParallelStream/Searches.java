package main.java.JustDialMultiServiceThreadsControlledParallelStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Searches extends Base implements Runnable {
	
	private String search;
	private String city;
	private String query;
	
	private List<Data> data;
	
	public Searches(String search, String city, String query) {
		this.search = search+"\\";
		this.city = city+"\\";
		this.query = query;
	}
	
	private void scrollDownThePageForSometime() {
		
		System.out.println("Started fetching records for : "+query);
		JavascriptExecutor jse = (JavascriptExecutor)wd;
		
		WebElement paginationDiv = wd.findElement(By.id("srchpagination"));

        //This will scroll the page Horizontally till the element is found
        jse.executeScript("arguments[0].scrollIntoView();", paginationDiv);
		
		/*
		int i=0;
		while(i<4000) {
			jse.executeScript("window.scrollBy(0, (document.body.scrollHeight)-1000)");
			// jse.executeScript("window.scrollBy(0,100)", "");
			i++;
		}
		*/
		
		/*jse.executeScript("var bg = document.getElementById(\"content\")", "");
		jse.executeScript("var y = 100", "");
		jse.executeScript("var count = 0", "");
		jse.executeScript("var bgheight = bg.scrollHeight", "");
		
		jse.executeScript("while(bgheight>(y*count)) {", "");
		
			jse.executeScript("count++", "");
			jse.executeScript("window.scrollBy(0,y)", "");
			jse.executeScript("var bgheight = bg.scrollHeight", "");
			
		jse.executeScript("}", "");*/
		
	}
	
	private void extractDataAndInsertInList(WebElement item) {
		
		Actions action = new Actions(wd);
		
		WebElement one = item.findElement(By.tagName("section"))
			.findElement(By.className("colsp"))
			.findElement(By.tagName("section"))
			.findElement(By.className("store-details"));
		
		Data d = new Data();
		
		try {
			d.setName(one.findElement(By.tagName("h2")).findElement(By.tagName("span")).findElement(By.tagName("a")).findElement(By.tagName("span")).getText());
		} catch(Exception e) {
			d.setName("");
		}
		
		try {
			d.setRating(one.findElement(By.className("newrtings")).findElement(By.className("green-box")).getText());
		} catch(Exception e) {
			d.setRating("");
		}
		
		try {
			String voteStr = one.findElement(By.className("newrtings")).findElement(By.className("lng_vote")).getText();
			if (null != voteStr) {
				voteStr = voteStr.trim();
				voteStr = voteStr.split(" ")[0];
			}
			d.setVotes(voteStr);
		} catch(Exception e) {
			d.setVotes("");
		}
		
		try {
			List<WebElement> spanElements = one.findElements(By.className("mobilesv"));
			String phn = new String();
			for (WebElement spanElement : spanElements) {
				String classAttributeValue = spanElement.getAttribute("class");
				String digit = PhoneNumberDecoder.identify(classAttributeValue);
				phn = phn + digit;
			}
			d.setPhone(phn);
		} catch (Exception e) {
			e.printStackTrace();
			d.setPhone("");
		}
		
		try {
			WebElement addressElement = one.findElement(By.className("address-info")).findElement(By.tagName("span")).findElement(By.tagName("a"));
			action.moveToElement(addressElement).build().perform();
			d.setAddress(addressElement.findElement(By.className("mrehover")).findElement(By.className("cont_fl_addr")).getText());
		} catch(Exception e) {
			d.setAddress("");
		}
		
		data.add(d);
	
	}
	
	private List<Data> extractData() {
		
		System.out.println("Started extracting data from records for : "+query);
		
		data = new ArrayList<Data>();
		
		List<WebElement> listItems = wd.findElements(By.className("cntanr"));
		
		listItems.parallelStream()
				.forEach(item -> extractDataAndInsertInList(item));
		
		return data;
		
	}
	
	private void saveDataInExcel(List<Data> data) {
		
		System.out.println("Started saving data of records for : "+query);
		
		File dir = new File(localPath+city+search);
		File file = new File(localPath+city+search+query+".csv");
		
		try {
			dir.mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not Create File in Location : "+file.getAbsolutePath());
		}
		
		FileWriter outputFile = null;
		
		try {
			outputFile = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not Open File in Location : "+file.getAbsolutePath());
		}
		
		try {
			outputFile.write( Data.firstLineForExcel );
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not Write firstLine of Excel File in Location : "+file.getAbsolutePath());
		}
		
		for(Data entry : data) {
			try {
				outputFile.write( entry.toStringForExcel() );
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Could not Write File in Location : "+file.getAbsolutePath());
			}
		}
		
		try {
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not Close File in Location : "+file.getAbsolutePath());
		}
		
		System.out.println("Successfully saved to : "+file);
		
	}
	
	private void searchInJustDial() {
		
		init(query);
		
		WebElement searchButton = wd.findElement(By.className("search-button"));
		searchButton.click();
		
		//change the wait time, since no more loading is needed, for client to communicate with server
		//wd.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		
		//try to scroll the page till the end, to get most results out of it
		//scrollDownThePageForSometime();
		
		// TODO : add pagination
		// TODO : parse all pages
		
		//extract data
		List<Data> data = extractData();
		
		//save data in excel
		saveDataInExcel(data);
		
		//Close the browser, i.e. Web Driver
		wd.close();
		
	}
	
	public void run() {
		
		System.out.println("Thread Starts : "+query);
		searchInJustDial();
		System.out.println("Thread Ends : "+query);
		
	}

}
