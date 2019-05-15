package biz.joshadams;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;

public class Permitter {
    private static WebDriver driver;
    private static String username;
    private static String password;
    private static int permitMonthsAhead;
    private static int permitDayOfMonth;
    private static List<LocalDate> holidays = null;
    private static List<Vacation> vacations = null;

    public static void main(String[] args) {
        setupTime();
        loadCredentials();
        getPermit();
    }

    private static void setupTime() {
        final int daysAhead = 60;
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysAhead);
        Logger.log(System.getProperty("line.separator") + "On " + currentDate + ", Permitter attempted to reserve a permit for " + futureDate + ".");
        DayOfWeek futureDayOfWeek = futureDate.getDayOfWeek();
        if (futureDayOfWeek == DayOfWeek.SATURDAY || futureDayOfWeek == DayOfWeek.SUNDAY) {
            Logger.log("Did not purchase permit because " + daysAhead + " from now is on a weekend.");
            System.exit(0);
        }
        loadExclusions();
        for (LocalDate holiday : holidays) {
            if (holiday.equals(futureDate)) {
                Logger.log("Did not purchase permit because " + holiday + " is a holiday.");
                System.exit(0);
            }
        }
        for (Vacation vacation : vacations) {
            LocalDate start = vacation.getStart();
            LocalDate end = vacation.getEnd();
            Boolean isDuring = start.equals(futureDate) || end.equals(futureDate) || (futureDate.isAfter(start) && futureDate.isBefore(end));
            if (isDuring) {
                Logger.log("Did not purchase permit because " + futureDate + " is during a vacation.");
                System.exit(0);
            }
        }
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

    private static void loadExclusions() {
        ExclusionHandler handler = new ExclusionHandler();
        handler.parse();
        vacations = handler.getVacations();
        holidays = handler.getHolidays();
    }

    private static void loadCredentials() {
        try {
            String credentials = new String(Files.readAllBytes(Paths.get("credentials")), "UTF-8");
            String[] tokens = credentials.split(",");
            if (tokens.length != 2) {
                Logger.log("Credentials file does not have format \"username,password\".");
                System.exit(-1);
            }
            username = tokens[0];
            password = tokens[1];
        }
        catch (IOException e) {
            Logger.log("Read of credentials file failed.");
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
        verify("STEP ONE - Choose Your BART Station", "Station-selection-page load failed.");
        driver.findElement(By.id("type_id_37")).click(); // 34: RockRidge 37: Orinda
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        verify("STEP TWO - Choose Your Parking Dates", "Date-selection-page load failed.");
        for (int i = 0; i < permitMonthsAhead; i++) {
            driver.findElement(By.xpath("//a[2]/span")).click();
        }
        driver.findElement(By.xpath("//a[text() = '" + permitDayOfMonth + "']")).click();
        for (int i = 0; i < permitMonthsAhead; i++) {
            driver.findElement(By.xpath("//tr[2]/td/div/div/div/a[2]/span")).click();
        }
        driver.findElement(By.xpath("(//a[text() = '" + permitDayOfMonth + "'])[2]")).click();
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        verify("STEP THREE - Checkout", "Checkout-page load failed because permit was not available.");
        driver.findElement(By.xpath("//input[@value='Next']")).click();
        verify("Payment Information", "Second part of checkout-page load failed.");
        driver.findElement(By.xpath("(//input[@value='Next'])[2]")).click();
        verify("Terms and Conditions", "Terms-and-conditions-page load failed.");
        driver.findElement(By.id("conditions")).click();
        driver.findElement(By.id("complete_order")).click();
        verify("You have successfully obtained a parking permit/pass", "Confirmation-page load failed.");
        driver.close();
        Logger.log("Reservation succeeded.");
    }

    private static void verify(String pattern, String message) {
        if (!driver.getPageSource().contains(pattern)) {
            Logger.log(message + System.getProperty("line.separator") + "Page did not contain the text \"" + pattern + "\".");
            driver.close();
            System.exit(-1);
        }
    }

    private static void sleep() {
        Long sleepTime = 10000L;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Logger.log("Thread.sleep() was interrupted.");
            System.exit(-1);
        }
    }
}