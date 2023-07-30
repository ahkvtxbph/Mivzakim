package test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BaseTest extends Throwable {
    public static WebDriver driver;
    static chatTest chatTest;
    public BaseTest(WebDriver driver)
    {
        this.driver=driver;
    }

    @BeforeClass
    public static void testSetup()throws Exception {
        resetPage();
        resetBrowser("chrome");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public static void resetBrowser(String browseReset) {
        if (browseReset.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/data/chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("disable-popup-blocking");
            options.addArguments("--headless");
            options.addExtensions (new File("src/data/addon/extension_1_50_0_0.crx"));
            driver = new ChromeDriver(options);
          //  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } else {
            System.setProperty("webdriver.gecko.driver", "src/data/geckodriver.exe");
            driver = new FirefoxDriver();

        }
    }
    public static void resetPage() {
        chatTest = new chatTest();
    }
    @AfterClass
    public static void end()
    {

      driver.close();
      driver.quit();

    }
}
