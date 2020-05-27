package Elements_Repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelCourseDetailspage {
	
	String path="./Test_Data/TestData.xlsx";
	WebDriver driver;
	
	@FindBy(id="watchpromo")
	WebElement btn_watchpromo;
	
	@FindBy(xpath="//div[@data-handle='bigPlayButton']//button")
	WebElement btn_paly;
	
	@FindBy(xpath="//div[@class='modal-header']/button")
	WebElement btn_close;
	
	@FindBy(id="enroll-button-top")
	WebElement btn_enroll;
	
	public SelCourseDetailspage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void click_watchpromo()
	{
		btn_watchpromo.click();
	}
	
	public boolean click_btnpaly()
	{
		return btn_paly.isEnabled();
	}
	
	public void click_close()
	{
		btn_close.click();
	}
	
	public void click_enroll()
	{
		Actions action = new Actions(driver);
		action.moveToElement(btn_enroll).click().perform();
	}

}
