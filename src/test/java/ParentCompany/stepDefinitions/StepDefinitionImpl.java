package ParentCompany.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import ParentCompany.TestComponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import souravmil.pageobjects.CartPage;
import souravmil.pageobjects.CheckOutPage;
import souravmil.pageobjects.ConfirmationPage;
import souravmil.pageobjects.LandingPage;
import souravmil.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue catalogue;
	public ConfirmationPage confirmorder;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given ("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password)
	{
		 catalogue = landingPage.loginApplication(username, password);
	}
	
	@When ("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName)
	{
		catalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) throws InterruptedException
	{
		CartPage cartPage = catalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckOutPage checkout = cartPage.goToCheckOut();
		checkout.selectCountry("India");
		confirmorder = checkout.placeTheOrder();
	}
	
	//Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
	@Then("{String} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string)
	{
		String confirmMessage = confirmorder.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
	}
}
