package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

public class TestScenario{
	
	private WebDriver driver;
	HomePage hp;
	ProductPage pp;
	CartPage cp;
	String expectedPrice = "$27.00";
	String expected_title = "Order - My Store";
	String projectPath = System.getProperty("user.dir");
	String expectedOrderConfirmationText= "Your order on My Store is complete.";
	
	@BeforeClass
	public void setUp() 
	{
		System.out.println("Inside Step : Browser is open");
		System.out.println("Project path is " + projectPath);
		System.setProperty("webdriver.chrome.driver",projectPath+"/src/test/resources/Drivers/chromedriver.exe");
	    driver = new ChromeDriver();
        driver.manage().window().maximize();
	}
	
	@AfterClass
	public void teardown()
	{
		driver.quit();
	}

	@Test(priority=0)
	public void openWebSite()
	{
		driver.get("http://automationpractice.com/index.php");
		//WebDriverWait wait = new WebDriverWait(driver,20);
		hp = new HomePage(driver);
		hp.clickWomenCategory();
		String actual = hp.fetchWomenCategoryLandingPageText();
		String expected = "You will find here all woman fashion collections.";
		Assert.assertEquals(actual, expected,"Incorrect Landing Page Text");
	}
	
	@Test(priority=1)
	public void ShopWorkFlowSteps() throws InterruptedException, IOException
	{
		hp.addWomenFilters();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		hp.clickwomenBlouse();
		pp = new ProductPage(driver);
		String actualprice = pp.getPriceDisplay();
		Assert.assertEquals(actualprice, expectedPrice,"Incorrect Price Displayed");
		pp.setProductColor();
		pp.setSize("M");
		pp.clickAddToCart();
		Thread.sleep(5000);
		pp.closeCartFrame();
		cp = new CartPage(driver);
		cp.ClickonCart();
		String actual_title = driver.getTitle();
		Assert.assertEquals(actual_title, expected_title,"Incorrect Cart Page Title Displayed");
		pp.clickOnProceedToCheckout();
		FileInputStream fis = new FileInputStream(projectPath+"/src/test/resources/Properties/credential.properties");
        Properties property = new Properties();
        property.load(fis);
		cp.Login(property.getProperty("email"), property.getProperty("password"));
		cp.addAddress("Illinois");
		pp.clickOnProceedToCheckout();
		cp.clickonShippingAgreement();
		pp.clickOnProceedToCheckout();
		cp.clickonPaymentMode();
		cp.clickonConfirm();
		String actualOrderConfirmationText = cp.getConfirmOrderText();
		Assert.assertEquals(actualOrderConfirmationText, expectedOrderConfirmationText,"Incorrect Order Confirmation text Displayed");
		cp.validateErrorMessage();
	}
	
}
