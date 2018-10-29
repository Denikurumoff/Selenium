import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SberbankZadanie {

    WebDriver driver;
    String baseUrl;


    @Before
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        baseUrl = "http://www.sberbank.ru/ru/person";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl); //   1. Перейти на страницу http://www.sberbank.ru/ru/person
    }

    @Test
    public void testBank() throws InterruptedException {
        Thread.sleep(1000);
        ((JavascriptExecutor)driver).executeScript("scroll(0,4400)");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div/div/div/div/div[1]/div/div[5]/div[3]/a")).click(); // 2. Нажать на – Страхование

        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 2000);



        WebElement title = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/table/tbody/tr/td/div/div/div/div/div/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/div/div/h3"));
        Assert.assertEquals("Страхование путешественников", title.getText()); //3.  Выбрать – Путешествие и покупки (более не актуален) 4.  Проверить наличие на странице заголовка – Страхование путешественников

        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/table/tbody/tr/td/div/div/div/div/div/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/div/div/div/p/a")).click(); // 5.  Нажать на – Оформить Онлайн
        Wait<WebDriver> wait2 = new WebDriverWait(driver, 5, 5000);
        ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tab.get(1)); //переходим на новую вкладку

        driver.findElement(By.xpath("//*[@id=\"views\"]/form/section/section/section[2]/div[1]/div[1]")).click(); // 6.  На вкладке – Выбор полиса  выбрать сумму страховой защиты – Минимальная.



        driver.findElement(By.xpath("//*[@id=\"views\"]/form/section/section/section[6]/span/span")).click(); // 7.  Нажать Оформить


// вводим данные застрахованного
        fillField(By.xpath("//*[@id=\"views\"]/section/form/section/section[1]/div/insured-input/div/fieldset[2]/div/input"), "KURUMOV");
        fillField(By.xpath("//*[@id=\"views\"]/section/form/section/section[1]/div/insured-input/div/fieldset[3]/div/input"), "DENI");
        fillField(By.name("insured0_birthDate"), "19.06.1992");

        // вводим данные страхователя
        fillField(By.name("surname"), "Страхователева");
        fillField(By.name("name"), "Страхиня");
        fillField(By.name("middlename"), "Страховна");
        fillField(By.name("birthDate"), "19.06.1965");
        driver.findElement(By.xpath("//*[@id=\"views\"]/section/form/section/section[2]/div/fieldset[8]/span[2]/input")).click();
        fillField(By.name("passport_series"), "1234");
        fillField(By.name("passport_number"), "123456");
        fillField(By.name("issueDate"), "19.06.2012");
        fillField(By.name("issuePlace"), "ЗАО БВАО АКАДЕСМИСКЧКИЙ ПО ГОР ЮЮЗА");


        //проверяем, что все поля были заполнены данными из теста
        Assert.assertEquals("KURUMOV",
                driver.findElement(By.xpath("//*[@id=\"views\"]/section/form/section/section[1]/div/insured-input/div/fieldset[2]/div/input")).getAttribute("value"));
        Assert.assertEquals("DENI",
                driver.findElement(By.xpath("//*[@id=\"views\"]/section/form/section/section[1]/div/insured-input/div/fieldset[3]/div/input")).getAttribute("value"));
        Assert.assertEquals("19.06.1992",
                driver.findElement(By.name("insured0_birthDate")).getAttribute("value"));
        Assert.assertEquals("Страхователева",
                driver.findElement(By.name("surname")).getAttribute("value"));
        Assert.assertEquals("Страхиня",
                driver.findElement(By.name("name")).getAttribute("value"));
        Assert.assertEquals("Страховна",
                driver.findElement(By.name("middlename")).getAttribute("value"));
        Assert.assertEquals("19.06.1965",
                driver.findElement(By.name("birthDate")).getAttribute("value"));
        Assert.assertEquals("1234",
                driver.findElement(By.name("passport_series")).getAttribute("value"));
        Assert.assertEquals("123456",
                driver.findElement(By.name("passport_number")).getAttribute("value"));
        Assert.assertEquals("19.06.2012",
                driver.findElement(By.name("issueDate")).getAttribute("value"));
        Assert.assertEquals("ЗАО БВАО АКАДЕСМИСКЧКИЙ ПО ГОР ЮЮЗА",
                driver.findElement(By.name("issuePlace")).getAttribute("value"));


        // Нажимаем на кнопку продолжить
        driver.findElement(By.xpath("//*[@id=\"views\"]/section/form/section/section[5]/div[1]/span[2]")).click();

        Assert.assertEquals("Заполнены не все обязательные поля",
                driver.findElement(By.xpath("//div[text()='Заполнены не все обязательные поля']")).getText());



    }
    public void fillField (By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
    @After
    public void afterTest () {
        driver.quit();
    }


}