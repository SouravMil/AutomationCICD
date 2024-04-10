package ParentCompany.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import souravmil.pageobjects.LandingPage;

public class StandAloneTest {
	//new comments added
	public static void main(String[] args) throws InterruptedException {
		String productName = "ADIDAS ORIGINAL";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client/");
		LandingPage landingpage = new LandingPage(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// ***********NEW REGISTRATION*******************
		/*
		 * driver.findElement(By.linkText("Register")).click();
		 * driver.findElement(By.id("firstName")).sendKeys("Jack");
		 * driver.findElement(By.id("lastName")).sendKeys("Reacher");
		 * driver.findElement(By.id("userEmail")).sendKeys("jack@reacher.com");
		 * driver.findElement(By.id("userMobile")).sendKeys("9009009001");
		 */

		// Fault exists from here
		/*
		 * WebElement staticdropdown =
		 * driver.findElement(By.xpath("//select[contains(@class,'custom-select']"));
		 * Select dropdown = new Select(staticdropdown); dropdown.selectByIndex(2);
		 */
		// Till here

		/*
		 * driver.findElement(By.xpath("//input[@value='Male']")).click();
		 * driver.findElement(By.id("userPassword")).sendKeys("Jack@123");
		 * driver.findElement(By.id("confirmPassword")).sendKeys("Jack@123");
		 * driver.findElement(By.xpath("//input[@type='checkbox']")).click();
		 * driver.findElement(By.id("login")).click();
		 * driver.findElement(By.cssSelector("p[class*='login-wrapper']")).click();
		 */
		// **********************************************

		// Logging in using credentials
		driver.findElement(By.id("userEmail")).sendKeys("jack@reacher.com");
		driver.findElement(By.id("userPassword")).sendKeys("Jack@123");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		// Choose and add right product to cart

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// "ng-animating" is the class name of toaster message appear and disappear
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equals(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		/*
		 * driver.findElement(By.xpath("//*[@placeholder='Select Country']")).sendKeys(
		 * "in"); List<WebElement> results =
		 * driver.findElements(By.cssSelector(".ta-results")); Optional<WebElement>
		 * finalOptions = results.stream().filter(result ->
		 * result.findElement(By.cssSelector(".ta-item span")).getText().
		 * equalsIgnoreCase("India")).findFirst();
		 */
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.xpath("//*[@placeholder='Select Country']")), "India").build().perform();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		//wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
		driver.findElement(By.cssSelector(".action__submit")).click(); //Not clickable. May need JSExecutor to scroll
		
		String confirmMessage =driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANK YOU FOR YOUR ORDER"));
		driver.close();
		
	}

}
