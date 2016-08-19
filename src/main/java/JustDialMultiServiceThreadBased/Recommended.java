package JustDialMultiServiceThreadBased;

import java.util.ArrayList;
import java.util.List;

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
			recommendedNames.add(originalText.substring(0, originalText.indexOf(" [+]")));
		}
		
		wd.close();
		
		return recommendedNames;
		
	}
	
	public void process(String search, String city) {

		String input = search+" ("+city+")";
		List<String> recommendedSearches = getRecommendedSearchesFor(input);
		
		for(String query : recommendedSearches) {
			Searches searches = new Searches(query);
			Thread thread = new Thread(searches, query);
			thread.start();
		}
		
	}
	
}
