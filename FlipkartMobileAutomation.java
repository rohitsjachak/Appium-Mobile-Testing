package com.appium.flipkart;


import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlipkartMobileAutomation {

	AndroidDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void setUp() {
		// Desired capabilities are used to set properties for web driver 
		// Using Below capabilities user able to launch device.
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// Default name should be Android
		capabilities.setCapability("BROWSER_NAME", "Android");
		
		capabilities.setCapability("VERSION", "5.0.1"); // Version of Android OS installed on device
		capabilities.setCapability("deviceName", "HTC One_M8"); // Name of device / Emulator
		capabilities.setCapability("platformName", "Android"); // Platform on which test cases will be executed
		capabilities.setCapability("appPackage", "com.flipkart.android"); // Name of package for application which user want to test 
		capabilities.setCapability("appActivity",
				"com.flipkart.android.SplashActivity"); // Name of activity 
		

		try {
			//the address is appium server address and port.
			//driver comm with appium sevrer.app server executes commands and sends back responds
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					capabilities) // Creating driver instance with above capabilities it will connect appium server address(127.0.0.1) and port(4723) 
			{
				//default methods in appium driver
				@Override
				public MobileElement scrollToExact(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public MobileElement scrollTo(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			
			wait=new WebDriverWait(driver,30); // creating wait instance it will wait for 30 seconds till element find
			 
			//
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(priority=1)
	public void menuOptionsCheck() {

	//waiting for the element untill it gets clicked.explic wait-if element is available it is clicked immediately and will not wait
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("More")))).click();
		
		//after clicking more the options 
		List<WebElement> menuOptions = driver.findElements(By
				.className("android.widget.TextView")); // Creating list all menuoptions 
		List<String> expectedMenuOptions = new ArrayList<String>();
		List<String> actualMenuOptions = new ArrayList<String>();
		//expected menu options
		expectedMenuOptions.add("Login");
		expectedMenuOptions.add("Wishlist");
		expectedMenuOptions.add("Track Order");
		expectedMenuOptions.add("Rate the app");
		expectedMenuOptions.add("Invite Friends");
		expectedMenuOptions.add("Contact Us");
		expectedMenuOptions.add("Policies");
		expectedMenuOptions.add("Flipkart First");

		for (WebElement webElement : menuOptions) {
			actualMenuOptions.add(webElement.getText());
		}
		
		Assert.assertEquals(actualMenuOptions, expectedMenuOptions);

	}

	@Test(priority=2)
	public void login() {
		wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By
						.name("Login")))).click();
//finding all the editable text boxes available on login page
		List<WebElement> editTextboxes = driver.findElements(By
				.className("android.widget.EditText"));
		//2 text boxes.  so 0 is email text box
		WebElement email = editTextboxes.get(0);
		
	//to verify the text contained in email box
		Assert.assertEquals(email.getText(), "Enter your email id");
		
		email.clear();
		email.sendKeys("jachakrohit07@gmail.com");
//so 1 is password text box
		WebElement password = editTextboxes.get(1);
		password.clear();
		password.sendKeys("Rohit007");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By
				.className("android.widget.Button")))).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
	}
	
	
	@Test(priority=4)
	public void addProductToWishlist()
	{
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		//finding element by id
		driver.findElement(By.id("android:id/up")).click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	//the elements till men are displayed 
		WebElement Men=driver.findElement(By.name("Men"));
		
		//to get the x,y co-ordinates of element Men which is visible currently
		//for scrolling list below Men
		driver.swipe(Men.getLocation().x,Men.getLocation().y,Men.getSize().height,Men.getSize().width,2000);
		
		
		
		//to get the x,y co-ordinates of Cameras element and scroll the list below that element
		WebElement Cameras=driver.findElement(By.name("Cameras"));
		driver.swipe(Cameras.getLocation().x,Cameras.getLocation().y,Cameras.getSize().height,Cameras.getSize().width,2000);
		
		
		//it will wait untill it clicks on the element Books
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("Books")))).click();	
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		//will wait untill it clicks on the Books category
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("Fiction & Non-Fiction")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("Literature & Fiction")))).click();
		
		   //will click on the specific book and add it to the wishlist
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("com.flipkart.android:id/product_list_product_item_wishlist_image")))).click();
  
		//will click on the More menu option
		 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("More")))).click();
		 //will click on the Wishlist option under More option
		 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("Wishlist")))).click();
		 
		 //to check whether product is added to the wishlist
		 Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("com.flipkart.android:id/product_list_product_item_image")))).isDisplayed());
		 
	}
	
	@Test(priority=5)
	public void deleteFromWishList()
	{
		 //will check whether the
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 //will click on the More menu option
		 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("More")))).click();
		 //will click on the Wishlist option under More option
		 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("Wishlist")))).click();
		 //will delete the item from the Wishlist
		 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("com.flipkart.android:id/product_list_product_item_wishlist_image")))).click();
		 // item is deleted from the wishlist or not
		 Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("com.flipkart.android:id/continue_shopping")))).isDisplayed());
   
	}
	
	@Test(priority=6)
	public void addProductToCart()
	{
		
	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	//will click on the search text box
	wait.until(ExpectedConditions.elementToBeClickable(By.id("com.flipkart.android:id/search_icon"))).click();;
	//will search for the product 
	WebElement searchTextBox= wait.until(ExpectedConditions.elementToBeClickable(By.name("Search for Products..")));
	searchTextBox.clear();
	//enter the search item in the search text box
	searchTextBox.sendKeys("moto e");
	//click on the specific item
	wait.until(ExpectedConditions.elementToBeClickable(By.className("android.widget.TextView"))).click();
	//will click on the image of the item
	wait.until(ExpectedConditions.elementToBeClickable(By.name("Prod Image"))).click();
	//will add the selected item to the cart
	wait.until(ExpectedConditions.elementToBeClickable(By.name("add_To_Cart"))).click();
	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	
	//clicks on Go to Cart
	wait.until(ExpectedConditions.elementToBeClickable(By.id("com.flipkart.android:id/product_page_bottom_bar_cart_button"))).click();
	
	//to check whether the product is added to the cart ot not
	 Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("android.webkit.WebView")))).isDisplayed());
	
	
	}
			

		@Test(priority=7)
	public void logout()
	{
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//will click on the More menu option
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("More")))).click();
		
		System.out.println("UserName"
				+ driver.findElement(By.className("android.widget.TextView"))
						.getText());
		//will click on the logout button
		driver.findElement(By.name("Logout")).click();
		
		//will click on the More menu option
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("More")))).click();
				
		
		//will check whether account has been logged out
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("android.widget.LinearLayout")))).isDisplayed());

		
	}
	
	

	  @AfterTest ()
	  public void tearDown() { driver.quit(); }
	 
	  }
