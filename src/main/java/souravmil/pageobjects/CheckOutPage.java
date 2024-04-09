package souravmil.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import souravmil.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {

	WebDriver driver;
	
	@FindBy(xpath="//*[@placeholder='Select Country']")
	WebElement country;
	
	/*
	 * @FindBy(css=".ta-results") WebElement autosuggesteddrop;
	 */
	
	@FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
	WebElement mycountry;
	
	@FindBy(css=".action__submit")
	WebElement myorder;
	
	By results = By.cssSelector(".ta-results");
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public void selectCountry(String countryName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scroll(0,330)");
		Thread.sleep(1000);
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(results);
		mycountry.click();
	}
	
	public ConfirmationPage placeTheOrder()
	{
		myorder.click();
		return new ConfirmationPage(driver);
	}

}
