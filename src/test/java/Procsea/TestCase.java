package Procsea;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestCase {

    Properties prop = new Properties();

    private String homeUrl;
    private int timeOutInSecond;

    public WebDriver driver;
    JavascriptExecutor jsExecutor;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");

        InputStream input = null;

        try {
            input = getClass().getClassLoader().getResourceAsStream("config.properties");

            // loading property file to our test
            prop.load(input);
            homeUrl = prop.getProperty("HOME_URL");
            timeOutInSecond = Integer.parseInt(prop.getProperty("timeOutInSecond"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //defining browser
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void ProcSea() throws InterruptedException {

        //Maximise window
        driver.get(homeUrl);
        driver.manage().window().fullscreen();

        //Selenium has no capability to change resolution. Bu we may see differences by changing dimension
        driver.manage().window().setSize(new Dimension(768, 1024));

        //Methods called which we created for test
        seeAllList();
        filterByUser();
        addNewExpense();
        totalExpense();
        addnewPayer();

    }

    //After test, exit browser and session
    @After
    public void close() {
        driver.quit();
    }


    public void seeAllList() throws InterruptedException {
        //Check and log in terminal if the List are seen properly
        WebElement element = driver.findElement(ObjectRepo.seeAllList);
        if(element.isEnabled()){
            System.out.println("Title, the name of the payer and the amount paid exist");
        } else {
            System.out.println("Title, the name of the payer and the amount paid do not exist");
        }
    }

    public void filterByUser() {
        Select filterByUser = new Select(driver.findElement(ObjectRepo.filterByUser));
        filterByUser.selectByVisibleText("Amine");
        //filterByUser.selectByIndex(2); --> It could be defined with element index of ComboBox
    }

    public void addNewExpense(){
        driver.findElement(ObjectRepo.addNewExpense).sendKeys("Lunch");

        Select filterByUser = new Select(driver.findElement(ObjectRepo.filterByUser));
        filterByUser.selectByVisibleText("Amine");
        //filterByUser.selectByIndex(0); --> It could be defined with element index of ComboBox

        driver.findElement(ObjectRepo.paidAmount).sendKeys("15");
        driver.findElement(ObjectRepo.buttonExpense).click();

    }

    //Just shows us the value. So no need to add ObjectRepo
    public void totalExpense(){
        String expensePrice=driver.findElement(By.cssSelector("div[class='total'] div ~ div")).getText();
        System.out.println("Total expense: "+expensePrice);
    }

    public void addnewPayer(){
        driver.findElement(By.cssSelector("input[placeholder='Add new user']")).sendKeys("Ozgur");
        WebElement element = driver.findElement(By.cssSelector("button[class='filter-button']"));
        element.click();
        System.out.println("User added");
    }


}


