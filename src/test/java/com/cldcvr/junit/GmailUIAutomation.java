package com.cldcvr.junit;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.junit.runners.SerenityRunner;

@RunWith(SerenityRunner.class)
public class GmailUIAutomation {
	
	{
		System.setProperty("webdriver.gecko.driver", 
		System.getProperty("user.dir")+"/src/test/resources/drivers/geckodriver");
	}
	
	@Test
	public void gmailTest() throws InterruptedException {
		
		WebDriver driver = new FirefoxDriver();
		getMailInfo(driver, 12, "coolchater", "net@social13");
	}
	
	
	
	public static void getMailInfo(WebDriver driver, int index, String userID, String password) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait=new WebDriverWait(driver, 20);
		
		// Navigate to gmail
		driver.get("https://gmail.com");
		
		// Enter user name and click next
		driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys(userID);
		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		Thread.sleep(3000);
		// Enter password and click next
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		Thread.sleep(3000);
		// Naviagate to social page
		driver.get("https://mail.google.com/mail/u/0/#category/social");
		
 		//	Added this, as I was getting stale element exception.
		driver.navigate().refresh();
		
		String str = driver.findElement(By.xpath("//a[@href='https://mail.google.com/mail/u/0/#category/social']")).getAttribute("aria-label");
		
		System.out.println("Social Email Count: "+str.split(" ")[1]);
		str = driver.findElement(By.xpath("(//table[@role='grid']//tr[@role='row'])["+index+"]//span[@class='bog']/span")).getText();
		System.out.println("Subject of the email: "+str);
		
		str = driver.findElement(By.xpath("(//table[@role='grid']//tr[@role='row'])["+index+"]//span[@class='bA4']/span")).getAttribute("name");
		System.out.println("Sender of the email: "+str);
		
		// Close the borwser
		driver.quit();	
	}

	
}
