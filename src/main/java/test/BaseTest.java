package test;

import ch.qos.logback.core.joran.conditional.ThenAction;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BaseTest extends Throwable {
    public static WebDriver driver;;

    static chatTest chatTest;
    public static String driverpath ="C:\\webdriver\\chromedriver.exe";
    static String currHandle;


    public BaseTest(WebDriver driver)
    {
        BaseTest.driver =driver;
    }

    @BeforeClass
    public static void testSetup()throws Exception {
        System.setProperty("webdriver.chrome.driver", driverpath);
        System.out.println("driverpath "+driverpath);
       // driver=new ChromeDriver();
         resetBrowser("chrome");
        resetPage();
        System.out.println(driver+" Shilo 5");
       //  driver.manage().window().minimize();
    }

    public static void resetBrowser(String browseReset) throws InterruptedException {

        if (browseReset.equals("chrome")) {


        //   System.setProperty("webdriver.chrome.driver", driverpath);

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("disable-popup-blocking");
          //  options.addArguments("--headless");
           options.addExtensions (new File("src/data/addon/extension_1_50_0_0.crx"));



            System.out.println("Driver Mivzakim Base "+driver);
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            System.out.println("Driver Mivzakim Base2 "+driver);
            //System.setProperty("webdriver.chrome.driver", "C:/webdriver/chromedriver.exe");
         //   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
