package org.etiya;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.awt.*;
import java.awt.event.KeyEvent;

import static java.util.concurrent.TimeUnit.SECONDS;

public class LoginTests {
    WebDriver driver;

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.navigate().to("http://localhost:4200/login");
    }
    @After
    public void end(){
        driver.quit();
    }
    public void sendID(String id){
        driver.findElement(By.id("username")).sendKeys(id);
    }
    public void sendPw(String pw){
        driver.findElement(By.id("password")).sendKeys(pw);
    }

    @Test
    public void validUPButton(){
        // Login button test Active
        //Username valid usName ve pw ile
        sendID("yagiz.delibas");
        sendPw("yagiz123");
        String loginButtonActive =driver.findElement(By.xpath("//button[text()='LOGIN']")).getAttribute("class");
        assert loginButtonActive.contains("active");
    }
    @Test
    public void invalidUPButton(){
        //Login button test Passive
        //Sadece usName girildiğinde inaktif
        sendID("yagiz.delibas");
        String loginButtonPassive =driver.findElement(By.xpath("//button[text()='LOGIN']")).getAttribute("class");
        assert(!loginButtonPassive.contains("active"));
    }
    public String eyeButtonCommon(){
        sendPw("yagiz123");
        driver.findElement(By.cssSelector("i[class='far fa-eye']")).click();
        return driver.findElement(By.id("password")).getAttribute("type");
    }
    @Test
    public void eyeButtonActive(){
        //Göz buton testi Aktiflik
        //Req1_Test_Case_6
        assert(eyeButtonCommon().contains("text"));
    }
    @Test
    public void eyeButtonPassive(){
        //Göz buton testi Inaktiflik
        //Req1_Test_Case_6
        String passwordAttribute = eyeButtonCommon();
        driver.findElement(By.cssSelector("i[class='far fa-eye-slash']")).click();
        passwordAttribute = driver.findElement(By.id("password")).getAttribute("type");
        assert(passwordAttribute.contains("password"));
    }
    @Test
    public void capsLockTest(){
        //Req1_Test_Case_7
        sendID("yagiz.delibas");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
        sendPw("yagiz123");
        //driver.findElement(By.id("password")).click();
        String isCapsActive = driver.findElement(By.xpath("//div[text()=' Capslock is on. ']")).getText();
        assert(isCapsActive.contains("Capslock"));

    }

}
