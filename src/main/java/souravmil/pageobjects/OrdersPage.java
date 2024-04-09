package souravmil.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import souravmil.AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent {
	WebDriver driver;

	@FindBy(css = "tr td:nth-child(3)")
	List<WebElement> productNames;

	@FindBy(css = ".totalRow button")
	WebElement checkOut;

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public Boolean VerifyOrderDisplay(String productName) {
		Boolean match = productNames.stream().anyMatch(cartProduct -> cartProduct.getText().equals(productName));
		return match;
	}

}
