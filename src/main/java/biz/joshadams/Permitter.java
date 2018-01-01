package biz.joshadams;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;

public class Permitter {
    private static String username;
    private static String password;
    private static int permitMonthsAhead;
    private static int permitDayOfMonth;
    private static WebDriver driver;

    public static void main(String[] args) {
        setupTime();
        loadCredentials();
        getPermit();
    }

    private static void setupTime() {
        final int daysAhead = 60;
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysAhead);
        DayOfWeek futureDayOfWeek = futureDate.getDayOfWeek();
        if (futureDayOfWeek == DayOfWeek.SATURDAY || futureDayOfWeek == DayOfWeek.SUNDAY) {
            System.out.println("Did not purchase permit because " + daysAhead + " from now is on a weekend.");
            System.exit(0);
        }
        // TODO: If futureDate is a holiday, exit without purchasing.
        permitDayOfMonth = futureDate.getDayOfMonth();
        int normalizedCurrentMonth = currentDate.getMonthValue();
        int normalizedFutureMonth = futureDate.getMonthValue();
        if (normalizedCurrentMonth > normalizedFutureMonth) {
            normalizedCurrentMonth -= 12;
        }
        else if (normalizedFutureMonth < normalizedCurrentMonth) {
            normalizedFutureMonth += 12;
        }
        permitMonthsAhead = normalizedFutureMonth - normalizedCurrentMonth;
    }

    private static void loadCredentials() {
        try {
            String credentials = new String(Files.readAllBytes(Paths.get("credentials")), "UTF-8");
            String[] tokens = credentials.split(",");
            if (tokens.length != 2) {
                System.out.println("Credentials file does not have format \"username,password\".");
                System.exit(-1);
            }
            username = tokens[0];
            password = tokens[1];
        }
        catch (IOException e) {
            System.out.println("Read of credentials file failed.");
            System.exit(-1);
        }
    }

    private static void getPermit() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.select-a-spot.com/bart/");
        verify("Single Day Reserved", "Initial-page load failed.");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).click();
        verify("Logged in as", "Login failed.");
        driver.findElement(By.linkText("Select")).click();
        verify("STEP ONE - Choose Your BART Station", "Station-selection-page load filed.");
        driver.findElement(By.id("type_id_37")).click(); // 34: RockRidge  37: Orinda
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        for (int i = 0; i < permitMonthsAhead; i++) {
            driver.findElement(By.xpath("//a[2]/span")).click();
        }
        driver.findElement(By.xpath("//a[text() = '" + permitDayOfMonth + "']")).click();
        for (int i = 0; i < permitMonthsAhead; i++) {
            driver.findElement(By.xpath("//tr[2]/td/div/div/div/a[2]/span")).click();
        }
        driver.findElement(By.xpath("(//a[text() = '" + permitDayOfMonth + "'])[2]")).click();
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        driver.findElement(By.xpath("(//input[@value='Next'])[2]")).click();
        driver.findElement(By.id("conditions")).click();
        driver.findElement(By.id("complete_order")).click();
        driver.close();
    }

    private static void verify(String pattern, String message) {
        if (!driver.getPageSource().contains(pattern)) {
            System.out.println(message + "\n" + "Page did not contain the text \"" + pattern + "\".");
            driver.close();
            System.exit(-1);
        }
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