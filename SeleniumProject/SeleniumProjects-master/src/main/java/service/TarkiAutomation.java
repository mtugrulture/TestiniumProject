package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class TarkiAutomation {

	public static void setChromeFunctinality()
	{
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\fatih\\Desktop\\SeleniumProject\\chromedriver_win32 (1)\\chromedriver.exe");
	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	chromePrefs.put("profile.default_content_settings.popups", 0);
	//chromePrefs.put("download.default_directory", downloadFilepath);
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities cap = DesiredCapabilities.chrome();
	cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	cap.setCapability(ChromeOptions.CAPABILITY, options);
	WebDriver driver = new ChromeDriver(cap);
	processingForTestCAsesOngittigidiyor(driver);
	}

	private static void processingForTestCAsesOngittigidiyor(WebDriver driver) {
		try
		{
			boolean isTwoItemAdded = true;
			String id1 = null;
			driver.get("https://www.gittigidiyor.com/");
			driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
			driver.get("https://www.gittigidiyor.com/uye-girisi");
			driver.findElement(By.xpath("//*[@id=\"L-UserNameField\"]")).sendKeys("komttureko@gmail.com");

			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id=\"L-PasswordField\"]")).sendKeys("123asd123");

			driver.findElement(By.xpath("//*[@id=\"gg-login-enter\"]")).click();
			Thread.sleep(1000);

			driver.findElement(
					By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input"))
					.click();
			driver.findElement(
					By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input"))
					.sendKeys("bilgisayar");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button"))
					.click();

			Thread.sleep(3000);
			ArrayList<String> ar = new ArrayList<String>();
			String pageSource = driver.getPageSource();

			Pattern p = Pattern.compile("var TRACKING_SEARCHITEM_LIST = '(.*)';");
			Matcher m = p.matcher(pageSource);
			String item = "";
			if (m.find()) {
				item = m.group(1);
			}
			String arr[] = item.split(",");
			for (String id : arr) {

				try {

					driver.findElement(By.xpath("//*[@id=\"product_id_" + id + "\"]/a")).click();
					id1 = id;
					break;
				} catch (Exception e) {

				}
			}
			// *[@id="cart-item-464228340"]/div[2]/div[5]/div[1]/div[2]/strong
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('add-to-basket').click();");
			Thread.sleep(3000);
			driver.get("https://www.gittigidiyor.com/sepetim");
			String item2 = "";

			try {
				String pageSource2 = driver.getPageSource();
				Pattern p2 = Pattern.compile(
						"<div class=\"product-item-box product  last-item-box  selected\" id=\"cart-item-(.*?)\" data-product=\""
								+ id1 + "-0\">");
				Matcher m2 = p2.matcher(pageSource2);

				if (m2.find()) {
					item2 = m2.group(1);
				}

			} catch (Exception e) {
			}
			String amount1 = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p")).getText();
			Thread.sleep(1000);
			try {

				Select dropdown = new Select(driver
						.findElement(By.xpath("//*[@id=\"cart-item-" + item2 + "\"]/div[2]/div[4]/div/div[2]/select")));
				dropdown.selectByIndex(1);
			} catch (Exception e) {
				isTwoItemAdded = false;

			}
			if (isTwoItemAdded) {
				Thread.sleep(3000);
				String amount2 = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p")).getText();
			}
			Thread.sleep(4000);
			try {
				driver.findElement(By.xpath("//*[@id=\"cart-item-" + item2 + "\"]/div[2]/div[3]/div/div[2]/div/a[1]/i"))
						.click();
			} catch (Exception e) {
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}    
	}	
}


