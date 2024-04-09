package ParentCompany.TestComponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import souravmil.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();

		/*
		 * (System.getProperty("user.dir")+
		 * "src\\main\\java\\souravmil\\resources\\GlobalData.properties");
		 */

		FileInputStream fis = new FileInputStream("./src/main/java/souravmil/resources/GlobalData.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");

		if (browserName.contains("chrome")) {					//browserName.equalsIgnoreCase("chrome")
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			//options.addArguments("--remote-allow-origins=*");
			if(browserName.contains("headless")) {
			options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));   //req mostly for headless mode. Helps run through full screen
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// FirFox invokes
			/*
			 * System.setProperty("webdriver.gecko.driver",
			 * "C:\\Users\\SOURAVMONIKA\\Desktop\\Eclipse\\workspace\\chromedriver-win64\\chromedriver.exe"
			 * );
			 */
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			System.setProperty("webdriver.edge.driver", "edge.exe__path");
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;

	}

	public String getScreenShot(String testCaseName,WebDriver driver) throws IOException // Taking a screenshot whenever there is a failure
	{
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, destFile);
		//return destFile;
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// String to HashMap

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
}
