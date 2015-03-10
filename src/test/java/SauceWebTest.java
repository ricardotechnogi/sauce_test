import com.saucelabs.selenium.client.factory.SeleniumFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class SauceWebTest {

        private WebDriver driver;
        String baseUrl;
        /**
         * Create a WebDriver instance using Selenium Client Factory.  We don't have to refer to the environment
         * variables set by the CI plugin, as that's handled by the Selenium Client Factory logic.  We also don't have
         * to output the Sauce OnDemand Session id.
         *
         * @throws Exception
         */
        @Before
        public void setUp() throws Exception {
            baseUrl = "http://www.technogi.com.mx/";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String version = Utils.readPropertyOrEnv("SELENIUM_VERSION", "");
            if (!version.equals("")) {
                capabilities.setCapability("version", version);
            }
            capabilities.setCapability("platform", Utils.readPropertyOrEnv("SELENIUM_PLATFORM", "XP"));
            capabilities.setCapability("browserName", Utils.readPropertyOrEnv("SELENIUM_BROWSER", "firefox"));

            String username = Utils.readPropertyOrEnv("SAUCE_USER_NAME", "");
            String accessKey = Utils.readPropertyOrEnv("SAUCE_API_KEY", "");
            this.driver = new RemoteWebDriver(
                    new URL("http://" + username + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities);
        }

        @After
        public void tearDown() throws Exception {
            driver.quit();
        }

        /**
         *
         */
        @Test
        public void fullRun() throws Exception {
            driver.get(baseUrl + "/");
            driver.findElement(By.id("en_lang_btn")).click();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Modern IT Consulting Services"));
            driver.findElement(By.id("es_lang_btn")).click();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Servicios de Consultoría IT Modernos"));
            driver.findElement(By.id("en_lang_btn")).click();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(By.cssSelector("#menu_items > a.menu_item")).click();
            driver.findElement(By.xpath("(//a[contains(text(),'HOW WE DO IT')])[2]")).click();
            //driver.findElement(By.xpath("(//a[contains(text(),'CONTACT  US')])[2]")).click();
            driver.findElement(By.id("contact_form_name")).clear();
            driver.findElement(By.id("contact_form_name")).sendKeys("Ricardo Hernández");
            driver.findElement(By.id("contact_form_email")).clear();
            driver.findElement(By.id("contact_form_email")).sendKeys("mail incorrecto");
            driver.findElement(By.id("contact_form_msg")).clear();
            driver.findElement(By.id("contact_form_msg")).sendKeys("mensje");
            driver.findElement(By.id("send_btn")).click();
            Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Please type a valid email address"));
            driver.findElement(By.cssSelector("#menu_items > a.menu_item.menu_item_scrolled > i.fa.fa-plus")).click();
        }
    }

