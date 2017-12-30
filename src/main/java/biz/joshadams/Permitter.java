package biz.joshadams;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Permitter {
    private static String username;
    private static String password;

    public static void main(String[] args) {
        loadCredentials();
        getPermit();
    }

    private static void loadCredentials() {
        try {
            String credentials = new String(Files.readAllBytes(Paths.get("credentials")), "UTF-8");
            String[] tokens = credentials.split(",");
            if (tokens.length != 2) {
                System.out.println("File credentials does not have format \"username,password\".");
                System.exit(-1);
            }
            username = tokens[0];
            password = tokens[1];
        }
        catch (IOException e) {
            System.out.println("File credentials does not have format \"username,password\".");
            System.exit(-1);
        }
    }

    private static void getPermit() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.select-a-spot.com/bart/");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.linkText("Select")).click();
        driver.findElement(By.id("type_id_34")).click(); // 34: RockRidge  37: Orinda
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        driver.findElement(By.xpath("//a[2]/span")).click();
        driver.findElement(By.xpath("//a[2]/span")).click();
        driver.findElement(By.xpath("//a[text() = '4']")).click(); // Day: 4
        driver.findElement(By.xpath("//tr[2]/td/div/div/div/a[2]/span")).click();
        driver.findElement(By.xpath("//tr[2]/td/div/div/div/a[2]/span")).click();
        driver.findElement(By.xpath("(//a[text() = '4'])[2]")).click(); // Day: 4
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        driver.findElement(By.xpath("(//input[@value='Next'])[2]")).click();
        driver.findElement(By.id("conditions")).click();
        driver.findElement(By.id("complete_order")).click();
    }

    private static void sleep() {
        Long sleepTime = 2000L;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("Thread.sleep() was interrupted.");
            System.exit(-1);
        }
    }
}