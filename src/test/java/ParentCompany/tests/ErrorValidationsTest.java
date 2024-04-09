package ParentCompany.tests;

import java.io.IOException;
import java.time.Duration;

import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import ParentCompany.TestComponents.BaseTest;
import souravmil.pageobjects.CartPage;
import souravmil.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"},retryAnalyzer=ParentCompany.TestComponents.Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		//String productName = "ADIDAS ORIGINAL";
		// Logging in using credentials
		//ProductCatalogue catalogue = 
		landingPage.loginApplication("jack@reacher.com", "Jack123");
		Assert.assertEquals("Incorrect email ooor password", landingPage.getErrorMessage());
	}

	@Test
	public void ProductErrorValidation() {
		String productName = "ADIDAS ORIGINAL";
		// Logging in using credentials
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		ProductCatalogue catalogue = landingPage.loginApplication("jack@reacher.com", "Jack@123");
		// Choose and add right product to cart
		catalogue.addProductToCart(productName);
		CartPage cartPage = catalogue.goToCartPage();
		// Verify cart products
		Boolean match = cartPage.VerifyProductDisplay("ADIBAS oRIGIN");
		Assert.assertFalse(match);

	}

}
