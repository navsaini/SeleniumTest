import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriver driver;
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void loginToGmail(WebDriver driver, String username, String password) {
		driver.get("http://gmail.com");
		
		WebElement emailElement = driver.findElement(By.id("Email"));
		emailElement.sendKeys(username);
		emailElement.submit();
		
		WebElement passwordElement = driver.findElement(By.id("Passwd"));
		passwordElement.sendKeys(password);
		passwordElement.submit();
		
		WebElement firstEmail = driver.findElement(By.id(":37"));
		firstEmail.click();
	}
	
	public static void checkout(WebDriver driver) {
		Actions action = new Actions(driver);
		WebElement hover = driver.findElement(By.xpath("//a[@title='View my shopping cart']"));
		action.moveToElement(hover).build().perform();
		driver.findElement(By.xpath("//a[@title='Check out']")).click();

		List<WebElement> checkout = driver.findElements(By.xpath("//a[@title='Proceed to checkout']"));
		checkout.get(1).click();
		driver.findElement(By.name("processAddress")).click();
		
		WebElement clickbox = driver.findElement(By.id("cgv"));
		if (!clickbox.isSelected()) {
			clickbox.click();
		}
		
		driver.findElement(By.name("processCarrier")).click();
		driver.findElement(By.xpath("//a[@title='Pay by check.']")).click();
		
		driver.findElement(By.xpath("//button/span[. = 'I confirm my order']")).click();
		
	}
	
	public static void buildCart(WebDriver driver) {
		List<WebElement> addtocarts = driver.findElements(By.className("ajax_add_to_cart_button"));
		for (WebElement each: addtocarts) {
			WebElement element = each.findElement(By.xpath(".//../.."));

			// Look for non-empty elements
			if (!element.getText().equals("")) {
				System.out.println(element.getText());
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", each);
				driver.findElement(By.className("cross")).click();
			}
		}

		driver.findElement(By.className("cross")).click();
	}
	
	public static void signIn(WebDriver driver, String username, String password) {
		driver.get("http://automationpractice.com/index.php");
		WebElement signin = driver.findElement(By.className("login"));
		signin.click();
		
		WebElement login = driver.findElement(By.name("email"));
		WebElement passwd = driver.findElement(By.name("passwd"));
		
		login.sendKeys(username);
		passwd.sendKeys(password);
		
		driver.findElement(By.id("SubmitLogin")).click();
		driver.findElement(By.id("header_logo")).findElement(By.xpath(".//img")).click();
	}
	
	
	public static void createAccount(WebDriver driver) {
		driver.get("http://automationpractice.com/index.php");
		WebElement signin = driver.findElement(By.className("login"));
		signin.click();
		
		// Generate random email and put it into email spot
		String email = generateRandomString() + "@" + generateRandomString() + ".com";
		WebElement formElement = driver.findElement(By.id("email_create"));
		formElement.sendKeys(email);
		formElement.submit();
		
		// Find all the required elements of the form
		List<WebElement> inputs = driver.findElements(By.className("required"));
		
		for (WebElement input: inputs) {
			try {
				// Try to find text inputs in the HTML
				WebElement a = input.findElement(By.xpath(".//input"));
				
				// Special case to clear pre-filled fields
				if (input.getText().toLowerCase().contains("address"))
					a.clear();
				
				// Check for zip code
				if (input.getText().toLowerCase().contains("zip")) {
					a.sendKeys(generateRandomNumber(5));
				// Check for phone number
				} else if (input.getText().toLowerCase().contains("phone")) {
					a.sendKeys(generateRandomNumber(10));
				// Otherwise create a random string
				} else if (!input.getText().toLowerCase().contains("email")) {
					a.sendKeys(generateRandomString());
				}
			} catch (Exception e) {
				try {
					// Try and see if there's a dropdown menu
					WebElement b = input.findElement(By.xpath(".//select"));
					Select c = new Select(b);
					
					// Check for state
					if (input.getText().toLowerCase().contains("state"))
						c.selectByVisibleText("Texas");
				} catch (Exception err) {
					System.out.println("no input or select element found for " + input.getText());
				}
			}
		}
		
		// Click the register button
		driver.findElement(By.id("submitAccount")).click();	
		driver.quit();
	}
	
	public static String generateRandomNumber(int size) {
		String number = "";
		for (int k = 0; k < size; k++) {
			number += (int) (Math.random() * 9);
		}
		return number;
	}
	
	
	public static String generateRandomString() {
		int size = (int) (Math.random() * 10 + 5);
		String result = "";
		
		for (int k = 0; k < size; k++) {
			result += (char) (Math.random() * 26 + 'a');
		}
		
		return result;
	}
	
	
	public static void exploreCNN(WebDriver driver) {
		driver.get("http://cnn.com");
		List<WebElement> headlines = driver.findElements(By.tagName("h2"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		
		try {
			for (WebElement headline: headlines) {
				String url = headline.findElement(By.xpath("./..")).getAttribute("href");
				if (url != null) {
					System.out.println(url);
					executor.executeScript("window.open(arguments[0].parentNode.href)", headline);
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong");
		} finally {
			driver.quit();
		}
	}
	
	public static void loginToCanvas(WebDriver driver, String username, String password) {
		driver.get("https://utexas.instructure.com/");
		
		WebElement loginField = driver.findElement(By.id("IDToken1"));
		WebElement passwordField = driver.findElement(By.id("IDToken2"));
		
		loginField.sendKeys(username);
		passwordField.sendKeys(password);
		
		loginField.submit();
		driver.quit();
		
	}

}
