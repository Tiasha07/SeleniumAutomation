package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {
	
	WebDriver driver;
	
	@FindBy(xpath = "//span[@id='our_price_display']")
	private WebElement price_display;
	
	@FindBy(xpath = "//a[@title='White']")
	private WebElement product_color;
	
	@FindBy(xpath = "//a[@title='White']")
	private WebElement product_size;
	
	@FindBy(id = "group_1")
	private WebElement sizesDropdown;
	
	@FindBy(xpath = "//*[@id='add_to_cart']/button/span")
	private WebElement addToCartButton;
	
	@FindBy(xpath = "(//span[contains(text(),'Proceed to checkout')])[2]")
	private WebElement proceedToCheckoutButton;
	
	@FindBy(className = "cross")
	private WebElement closeCartFrame;
	
	public ProductPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getPriceDisplay()
	{
		String price = price_display.getText();
		return price;
	}
	
	public void setProductColor()
	{
		product_color.click();
	}
	
	public void setSize(String size) {
		Select selectList = new Select(sizesDropdown);
		selectList.selectByVisibleText(size);
	} 
	
	public void clickAddToCart() {
		addToCartButton.click();
	}
	
	public void closeCartFrame()
	{
		closeCartFrame.click();
	}
	
	public void clickOnProceedToCheckout()
	{
		proceedToCheckoutButton.click();
	}
}
