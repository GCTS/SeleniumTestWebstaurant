package newproject;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    public static WebDriver driver = new ChromeDriver(); //Our browser object
	public static WebDriverWait wait = new WebDriverWait(driver, 30); //We're going to use 30 seconds max as a default timeout value

public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);

		//System.setProperty("webdriver.chrome.driver","C:\\Users\\Odidn\\Desktop\\chromedriver.exe");
		//WebDriverWait wait = new WebDriverWait(driver, 30); //We're going to use 30 seconds max as a default timeout value

		String homeUrl = "https://www.webstaurantstore.com"; //Base URL

        driver.get(homeUrl); //Navigate to the main page

		try{
			boolean linksContainTableText = testLinksForTableText();
			boolean lastItemFoundClicked = testLastAddToCartButton();
			boolean cartEmptied = testEmptyCartButton();

		if(linksContainTableText && lastItemFoundClicked && cartEmptied){
			System.out.println("All tests passed");
		}
		else{
			System.out.println("There were issues with one or more test(s)");
		}
			//close Chrome / clean up phase
			driver.close();
		}
		catch(ElementNotVisibleException e){
			System.out.println("There were issues with the driver in Main");
		}

    }

	private static boolean testLinksForTableText(){
		//Verify that all links have the word "Table" in their text
		try{
			WebElement searchBar = driver.findElement(By.name("searchval"));
			searchBar.sendKeys("stainless work table" + Keys.RETURN);
			wait.until(ExpectedConditions.elementToBeClickable(By.className("category_name")));
			List<WebElement> listOfElements = driver.findElements(By.className("category_name"));
			for (int i = 0; i < listOfElements.size(); i++){
				String linkText = listOfElements.get(i).getText();
				if(!linkText.contains("Table")){
					System.out.println("FAILED: Link did not contain text 'Table.' Test failed");
					return false;
				}
			}
		System.out.println("PASSED: All links contain the word 'Table' in their text");
		return true;
		}
		catch(ElementNotVisibleException e){
			handleError(e);
			return false;
		}
	}

	private static boolean testLastAddToCartButton(){
		//Verify that we can click the last item found in our search
		try{			wait.until(ExpectedConditions.elementToBeClickable(By.className("rc-pagination-item")));
			List<WebElement> pageButtons = driver.findElements(By.className("rc-pagination-item"));
			WebElement lastButton = pageButtons.get(pageButtons.size() - 1);

			JavascriptExecutor clickButtonScript = (JavascriptExecutor)driver;
			clickButtonScript.executeScript("arguments[0].click();", lastButton);

			wait.until(ExpectedConditions.elementToBeClickable(By.name("addToCartButton")));
			List<WebElement> cartButtons = driver.findElements(By.name("addToCartButton"));
			cartButtons.get(cartButtons.size() - 1).click();

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Cart")));
			driver.findElement(By.linkText("View Cart")).click();
			System.out.println("PASSED: Item added to cart and cart is viewable");
			return true;
		}
		catch(ElementNotVisibleException e){
			handleError(e);
			return false;
		}
	}

	private static boolean testEmptyCartButton(){
		//Verify that we can empty our cart
		try{
			wait.until(ExpectedConditions.elementToBeClickable(By.className("emptyCartButton")));
			driver.findElement(By.className("emptyCartButton")).click();		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Empty Cart')]")));
			driver.findElement(By.xpath("//button[contains(text(),'Empty Cart')]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Your cart is empty.']")));
			System.out.println("PASSED: Cart emptied successfully");
			return true;
		}
		catch(ElementNotVisibleException e){
			handleError(e);
			return false;
		}
	}

	private static void handleError(ElementNotVisibleException e){
		//Element can't be found, we're going to print out text for the test-runner
		System.out.println("FAILED: Element could not be found on page");
		e.printStackTrace();
		driver.close();
	}

}