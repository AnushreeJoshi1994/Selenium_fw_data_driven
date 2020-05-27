package Elements_Repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderSummarypage {

	String path="./Test_Data/TestData.xlsx";
	WebDriver driver;
	String url="https://www.paypal.com/";
	
	@FindBy(xpath="//button[@class='dropbtn minimal']")
	WebElement dd_payment;
	
	@FindBy(xpath="//div[@id='option-container']//a[@data-payment-method='paypal']")
	WebElement opt_pp;
	
	@FindBy(id="agreed_to_terms_checkbox")
	WebElement checkbox_agmnt;
	
	@FindBy(id="confirm-purchase")
	WebElement btn_enroll_final;
	
	
	public OrderSummarypage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void payment_click()
	{
		dd_payment.click();
	}
	
	public void pp_click()
	{
		opt_pp.click();
	}
	
	public void checkbox_click()
	{
		checkbox_agmnt.click();
	}
	
	public void enroll_click()
	{
		btn_enroll_final.click();
	}
	
	public boolean geturl()
	{
		String a_url=driver.getCurrentUrl();
		if(a_url.contains(url))
			return true;
		else
			return false;
	}
}
