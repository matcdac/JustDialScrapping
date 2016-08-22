package JustDialMultiServiceThreadPoolBasedParallelStream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Recommended extends Base {
	
	private List<String> getRecommendedSearchesFor(String query) {
		
		init(query);
		
		WebElement autoFillUnOrderedList = wd.findElement(By.id("auto"));
		List<WebElement> listItems = autoFillUnOrderedList.findElements(By.tagName("li"));
		
		List<String> recommendedNames = new ArrayList<String>();
		
		for(WebElement item : listItems) {
			String originalText = item.getText();
			try {
				recommendedNames.add(originalText.substring(0, originalText.indexOf(" [+]")));
			} catch(Exception e) {
				recommendedNames.add(originalText);
			}
		}
		
		wd.close();
		
		return recommendedNames;
		
	}
	
	public void process(String search, String city, ThreadPoolExecutor pool) {
		
		String input = search+" ("+city+")";
		List<String> recommendedSearches = getRecommendedSearchesFor(input);
		
		for(String query : recommendedSearches) {
			Searches searches = new Searches(search, city, query);
			pool.execute(searches);
		}
		
	}
	
}
