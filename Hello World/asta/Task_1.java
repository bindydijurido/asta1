package asta;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

public class Task_1 {

	private static WebDriver driver;
	private static String baseUrl;

	private String Price_1_Basket;
	private String Price_1_Product_String;

	double Price_Random_Product;
	static int random;
	static int min = 1;
	static int max = 10;
	int a;
	int sum;
	double Price_1_Product_Double;
	double Price_1_Double;

	static String random_string;

	JavascriptExecutor jse = (JavascriptExecutor) driver;

	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "C:/Users/devtest-pl/OneDrive - di-support/Eclipse/Projects/geckodriver/geckodriver.exe");

		driver = new FirefoxDriver();
		baseUrl = "https://testingcup.pgs-soft.com/task_1";

		random = ThreadLocalRandom.current().nextInt(min, max);
		random_string = String.valueOf(random);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void AddProductToBasket() throws Exception {
		
		System.out.println("I am starting the test: AddProductToBasket functionality");

		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[1]/div/div/div/input")));

		// insert random generated value into value forms

		driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[1]/div/div/div/input")).sendKeys(random_string);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[1]/div/div/div/span/button")));

		driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[1]/div/div/div/span/button")).click();

		// Assert result in the basket - product name, amount and price

		Price_1_Basket = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div/div[2]/div[2]/p[2]/span")).getText().toString().split(" ")[0];

		Price_1_Double = Double.parseDouble(Price_1_Basket);

		Price_1_Double = Math.round(Price_1_Double);

		System.out.println("Price before parsing: " + Price_1_Basket + " zl");

		Price_1_Product_String = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[1]/div/div/p[1]")).getText().toString()
				.split(" ")[1];

		Price_1_Product_Double = Double.parseDouble(Price_1_Product_String);

		Price_Random_Product = Math.round(Price_1_Product_Double * random);

		System.out.println("Product price is: " + Price_Random_Product + " zl");

		Assert.assertEquals(Price_1_Double, Price_Random_Product, random);
		
		System.out.println("Price assertion and data transformation succeeded");
	}

		
	@Test
	public void UpTo100() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		String static_one_value = "1";
		
		System.out.println("I am starting the test: UpTo100Products Alert Functionality");
		System.out.println("I am starting fulfilling the basket process with random data to cover all product types: ");

		do {

			for (a = 1; a < 5; a++) {

				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[" + a + "]/div/div/div/input")).sendKeys(static_one_value);
				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[1]/div[" + a + "]/div/div/div/span/button")).click();
			}

			for (a = 1; a < 5; a++) {

				jse.executeScript("scroll(0, 600);");

				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[2]/div[" + a + "]/div/div/div/input")).sendKeys(static_one_value);
				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[2]/div[" + a + "]/div/div/div/span/button")).click();
			}

			for (a = 1; a < 5; a++) {

				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[3]/div[" + a + "]/div/div/div/input")).sendKeys(static_one_value);
				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/form/div[3]/div[" + a + "]/div/div/div/span/button")).click();
			}

			String ba = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div/div[2]/div[2]/p[1]/span")).getText().toString();

			sum = Integer.parseInt(ba);
			
			System.out.print(sum + ", ");
			
		} while (sum <= 99);
		
				
		System.out.println(" - the maximum value has been reached");

		if (wait.until(ExpectedConditions.alertIsPresent()) == null)

			System.out.println("Alert was not present - test failed");

		else

			System.out.println("Alert was present - test passed");
	}

}