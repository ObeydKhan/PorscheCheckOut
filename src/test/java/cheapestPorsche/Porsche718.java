package cheapestPorsche;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Porsche718 {
	
	public static void verifyTPrice(WebDriver driver, double basePrice) {
		  String EquipPrice=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		  double EPrice=price(EquipPrice);
		
		  String DelivPrice=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		  double dPrice=price(DelivPrice);
		  
		  String TotalPrice=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		  double tPrice= price(TotalPrice);
		  
		  if( tPrice==dPrice+basePrice+EPrice) {
				 System.out.println("PASS: Total Price==Items Price addition");
			 }else {
				 System.out.println("FAIL: Total Price!=Items Price addition");
			 }
	}
	
	public static List<Double> verifyEPrice(WebDriver driver, WebElement searchElement, List<Double> totalEquipPrice) {
		  String strPrice=searchElement.getText();
		  double Price=price(strPrice);
		totalEquipPrice.add(Price);
		
		String EquipPrice=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		  double EPrice=price(EquipPrice);
		
		double totalEPrice=0;
		  for (Double double1 : totalEquipPrice) {
			totalEPrice+=double1;
		}
		  
		  if( totalEPrice==EPrice) {
			 System.out.println("PASS: Total Equipment Price==Total Equipment Expenses");
		 }else {
			 System.out.println("FAIL: Total Equipment Price!=Total Equipment Expenses");
		 }
		 return totalEquipPrice; 
	}
	
	
	
	
	
	
	
	public static double price(String str) {   //abc213/cawd2  -> 2132.00
		
		String temp=new String();
		
		for(int i=0; i<str.length();i++) {
		
			
			if(Character.isDigit(str.charAt(i)) || str.charAt(i)=='.'){
				
				temp+=str.charAt(i)+"";
			}
		}
	
	return Double.parseDouble(temp);
	}
	
	
	
	public static void main(String[] args) {
		List<Double> totalEquipPrice=new ArrayList();
		
		WebDriverManager.chromedriver().setup(); //BoniGarcia
		
		WebDriver driver= new ChromeDriver(); //Opens Chrome Browser
		
		String url="https://www.porsche.com/usa/modelstart/";
		
		driver.get(url); //Goes to url
		
		driver.manage().window().maximize();  // Makes the browser window fullscreen
		driver.findElement(By.cssSelector("a[href='/usa/modelstart/all/?modelrange=718']")).click();;  //tagName[attrName='value']
		
		String strBasePrice=driver.findElement(By.cssSelector("div[class='m-14-model-price']")).getText(); // saves the price of the 718 Cayman
		
		double  basePrice = price(strBasePrice);
		
		driver.findElement(By.cssSelector("div[class='m-14-quick-link']")).click(); //clicks on Build & Price
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String parentWindow = driver.getWindowHandle();
		Set<String> handles =  driver.getWindowHandles();
		  for(String windowHandle  : handles)
		      {
		      if(!windowHandle.equals(parentWindow)){
		         driver.switchTo().window(windowHandle);
		         break;
		         }
		      }
		
		
		  
		  String strNBasePrice= driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		  double nBasePrice=price(strNBasePrice);
		  
		  if(basePrice==nBasePrice) {
			  System.out.println("PASS: Base Price Verified");
		  }else {
			  System.out.println("FAIL: Base Price is not verified");
		  }
		  
		  System.out.println("\n");
			
		  String EquipPrice=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		  double EPrice=price(EquipPrice);
		  
		  if( EPrice==0) {
			 System.out.println("PASS: Equipment Price: 0");
		 }else {
			 System.out.println("FAIL: Equipment not zero");
		 }
		  
		  System.out.println("\n");
		  
		  System.out.println("Verifying initial Total Prices:");
		verifyTPrice(driver,basePrice);
			System.out.println("\n");  
		  driver.findElement(By.cssSelector("span[style='background-color: rgb(0, 120, 138);']")).click();
		
		  WebElement ColorPElement=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]"));
		 
		  System.out.println("Verifying Equipment Price (Added Miami Blue)");
		  totalEquipPrice=verifyEPrice(driver,ColorPElement,totalEquipPrice);
		  System.out.println("\n");
		  
		  System.out.println("Verifying Total Prices (Added Miami Blue)");
		  verifyTPrice(driver,basePrice);
		  System.out.println("\n");
		  
		  driver.manage().window().fullscreen();
		  driver.findElement(By.xpath("//*[@id='s_exterieur_x_MXRD']/span/span")).click();
		 
		  WebElement RimPElement=driver.findElement(By.xpath("//*[@id='s_exterieur_x_IRA']/div[2]/div[1]/div/div[2]"));
		  System.out.println("Verifying Equipment Price (Added 20 Carrera Sport Wheels)");
		  totalEquipPrice=verifyEPrice(driver,RimPElement,totalEquipPrice);
		  System.out.println("\n");
		  
		  System.out.println("Verifying Total Prices (Added 20 Carrera Sport Wheels)");
		  verifyTPrice(driver,basePrice);
		  System.out.println("\n");


		  WebElement element= driver.findElement(By.id("s_interieur_x_PP06"));
		  JavascriptExecutor executor = (JavascriptExecutor) driver;
		  executor.executeScript("arguments[0].click();", element);
		  
		  WebElement PowerSeatElement=driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]/div[1]/div[3]/div"));
		  System.out.println("Verifying Equipment Price (Added Power Sport Seats (14-way) with Memory Package)");
		  totalEquipPrice=verifyEPrice(driver,PowerSeatElement,totalEquipPrice);
		  System.out.println("\n");
	
		  System.out.println("Verifying Total Prices (Added Power Sport Seats (14-way) with Memory Package)");
		  verifyTPrice(driver,basePrice);
		  System.out.println("\n");
		  
		  WebElement interiorCFiber=driver.findElement(By.id("IIC_subHdl"));
	      executor.executeScript("arguments[0].click();", interiorCFiber);
	      
	      WebElement iCFoption=driver.findElement(By.xpath("//*[@id=\'vs_table_IIC_x_PEKH_x_c01_PEKH\']"));
	      executor.executeScript("arguments[0].click();", iCFoption);
	      
	      WebElement iCFP=driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH\"]/div[1]/div[2]/div"));
	      
	      System.out.println("Verifying Equipment Price (Added Interior Trim in Carbon Fiber i.c.w. Standard Interior)");
		  totalEquipPrice=verifyEPrice(driver,iCFP,totalEquipPrice);
		  System.out.println("\n");
	
		  System.out.println("Verifying Total Prices (Added Interior Trim in Carbon Fiber i.c.w. Standard Interior)");
		  verifyTPrice(driver,basePrice);
		  System.out.println("\n");
		  
		  WebElement Performance=driver.findElement(By.id("IMG_subHdl"));
	      executor.executeScript("arguments[0].click();", Performance); 
	      
	      WebElement PDK=driver.findElement(By.id("vs_table_IMG_x_M250_x_c14_M250_x_shorttext"));
	      executor.executeScript("arguments[0].click();", PDK);  
	      
	      WebElement pdkP=driver.findElement(By.xpath("//*[@id=\'vs_table_IMG_x_M250\']/div[1]/div[2]/div"));
	      System.out.println("Verifying Equipment Price (Added Porsche Doppelkupplung (PDK))");
		  totalEquipPrice=verifyEPrice(driver,pdkP,totalEquipPrice);
		  System.out.println("\n");
	
		  System.out.println("Verifying Total Prices (Added Porsche Doppelkupplung (PDK))");
		  verifyTPrice(driver,basePrice);
		  System.out.println("\n");
	      
	      WebElement PCCB=driver.findElement(By.id("vs_table_IMG_x_M450_x_c94_M450_x_shorttext"));
	      executor.executeScript("arguments[0].click();", PCCB); 
	      
	      WebElement pccbP=driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M450\"]/div[1]/div[2]/div"));
	      System.out.println("Verifying Equipment Price (Added Porsche Ceramic Composite Brakes (PCCB))");
		  totalEquipPrice=verifyEPrice(driver,pccbP,totalEquipPrice);
		  System.out.println("\n");
	
		  System.out.println("Verifying Total Prices (Added Porsche Ceramic Composite Brakes (PCCB))");
		  verifyTPrice(driver,basePrice);
		  System.out.println("\n");
	      
		  driver.quit();
	}
}
