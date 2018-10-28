
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

public class InsuranceTest {
    WebDriver driver;
    String baseUrl;




    @Before
    public void beforeTest(){
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        baseUrl = "https://www.rgs.ru/";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }
    @Test
    public void testInsurance(){
        driver.findElement(By.xpath("//*[@id=\"main-navbar-collapse\"]/ol[1]/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"rgs-main-menu-insurance-dropdown\"]/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a")).click();

        Wait<WebDriver> wait = new WebDriverWait(driver, 5,1000);
        WebElement sendBtn =driver.findElement(By.xpath("//*[@id=\"rgs-main-context-bar\"]/div/div/div/a[3]"));
        wait.until(ExpectedConditions.visibilityOf(sendBtn)).click();

        WebElement title =driver.findElement(By.xpath("/html/body/div[7]/div/div/div/div[1]/h4/b"));
        wait.until(ExpectedConditions.visibilityOf(title));
        Assert.assertEquals("Заявка на добровольное медицинское страхование", title.getText());

        fillField(By.name("LastName"),"Иванов");
        fillField(By.name("FirstName"),"Иван");
        fillField(By.name("MiddleName"),"Иванович");
        driver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[9]/label/input")).click();
        new Select(driver.findElement(By.name("Region"))).selectByVisibleText("Москва");
        fillField(By.name("Comment"), "Autotest");

        fillField(By.name("Email"), "123445");
        driver.findElement(By.xpath("//*[@id=\"button-m\"]")).click();
        WebElement error =driver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[6]/div/label/span"));
        Assert.assertEquals("Введите адрес электронной почты",
                driver.findElement(By.xpath("//*[text()='Эл. почта']/..//*[@class='validation-error']")).getText());

    }

    public void fillField(By locator, String value) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    @After
    public void afterTest(){
        driver.quit();

    }
}
