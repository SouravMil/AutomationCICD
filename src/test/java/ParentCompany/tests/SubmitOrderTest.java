package ParentCompany.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ParentCompany.TestComponents.BaseTest;
import souravmil.pageobjects.CartPage;
import souravmil.pageobjects.CheckOutPage;
import souravmil.pageobjects.ConfirmationPage;
import souravmil.pageobjects.OrdersPage;
import souravmil.pageobjects.ProductCatalogue;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class SubmitOrderTest extends BaseTest {

	public String productName;

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		// Logging in using credentials
		// LandingPage landingPage = launchApplication();
		ProductCatalogue catalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		// Choose and add right product to cart
		catalogue.addProductToCart(input.get("product"));
		CartPage cartPage = catalogue.goToCartPage();
		// Verify cart products
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkout = cartPage.goToCheckOut();
		checkout.selectCountry("India");
		ConfirmationPage confirmorder = checkout.placeTheOrder();
		String confirmMessage = confirmorder.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	
	@Test(dependsOnMethods = { "submitOrder" }) // To verify 'productName' is visible on order page
	public void orderHistoryTest() {
		ProductCatalogue catalogue = landingPage.loginApplication("jack@reacher.com", "Jack@123");
		OrdersPage ordersPage = catalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	


	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				"./src/test/java/ParentCompany/data/PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };

		/*
		 * 2nd way of running multiple data sets (HashMaps) HashMap<String, String> map
		 * = new HashMap<String, String>(); map.put("email", "jack@reacher.com");
		 * map.put("password", "Jack@123"); map.put("product", "ADIDAS ORIGINAL");
		 * 
		 * HashMap<String, String> map1 = new HashMap<String, String>();
		 * map1.put("email", "japanidance@gmail.com"); map1.put("password",
		 * "Janedoe@1234"); map1.put("product", "ZARA COAT 3"); return new Object[][] {
		 * { map }, { map1 } };
		 */

		/*
		 * 1st way of running multiple data sets
		 * 
		 * @DataProvider public Object[][] getData() { return new Object[][] { {
		 * "jack@reacher.com", "Jack@123", "ADIDAS ORIGINAL" }, {
		 * "japanidance@gmail.com", "Janedoe@1234", "ZARA COAT 3" } };}
		 */
	}

}
