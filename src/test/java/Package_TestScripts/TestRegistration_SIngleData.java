package Package_TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Package_DDFUtilities.ExcelUtilities;
import Package_DDFUtilities.Project_Constants;

public class TestRegistration_SIngleData 
{
	WebDriver driver;
	String Username;
	String Email;
	String FirstName;
	String LastName;
	String ProgLang;
	
	@BeforeMethod
	public void launchApp() throws Exception {
		System.setProperty("webdriver.chrome.driver", Project_Constants.currentDirectory+"\\src\\main\\java\\Package_DDF_Drivers\\chromedriver.exe");
		
		driver=new ChromeDriver();
		driver.navigate().to(Project_Constants.URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebElement SingUpButton = driver.findElement(By.xpath("//a[contains(text(),'Sign up')][1]"));
		
		Actions a = new Actions(driver);
		a.moveToElement(SingUpButton);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", SingUpButton);
		
		String dirPath = Project_Constants.Path_TestData+Project_Constants.File_TestData;
		ExcelUtilities.setExcelFile(dirPath, "RegistrationPage");
		
	}
	
	public void getDataFromExcelSheet() throws Exception {
		Username = ExcelUtilities.getCellData(1, 0);
		Email = ExcelUtilities.getCellData(1, 1);
		FirstName = ExcelUtilities.getCellData(1, 2);
		LastName = ExcelUtilities.getCellData(1, 3);
		ProgLang = ExcelUtilities.getCellData(1, 4);
	}
	
	@Test
	public void RegistrationDataCheck() throws Exception
	{
		getDataFromExcelSheet();
		driver.findElement(By.id("UserName")).sendKeys(Username);
		driver.findElement(By.id("UserEmail")).sendKeys(Email);
		driver.findElement(By.id("UserFirstName")).sendKeys(FirstName);
		driver.findElement(By.id("UserLastName")).sendKeys(LastName);
		driver.findElement(By.id("UserProgrammingSkill")).sendKeys(ProgLang);
		Thread.sleep(6000);
		
	}
	
	@AfterTest
	public void closeApp() {
		driver.quit();
	}
}
