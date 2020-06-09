package ozon;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tests {

    // Авторизация
    @Test
    public void login() {
        // Драйвера
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver83.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // Открытие страницы
        driver.get("https://www.ozon.ru/");

        // Открытие окна авторизации
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("[data-widget=\"profileMenuAnonymous\"]")));
        driver.findElement(By.cssSelector("[data-widget=\"profileMenuAnonymous\"]")).click();

        // Ожидание подтверждения авторизации
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href=\"/my/main\"]")));

        // Закрытие страницы
        driver.quit();
    }

    // Смена города
    @Test
    public void changeCity() throws InterruptedException {
        // Драйвера
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver83.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // Открытие страницы
        driver.get("https://www.ozon.ru/");

        // Открытие формы смены города
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("div[role=\"navigation\"] button span")));
        driver.findElement(By.cssSelector("div[role=\"navigation\"] button span")).click();

        // Заполнение формы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"modal-container\"]")));
        driver.findElement(By.cssSelector("div[class=\"modal-container\"] input")).sendKeys("Вольск");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("div[class=\"modal-container\"] input")).sendKeys(Keys.ENTER);

        // Подтверждение смены города
        wait.until(ExpectedConditions
                .textToBePresentInElement(driver
                        .findElement(By.cssSelector("div[role=\"navigation\"] button span")), "Вольск"));

        // Открытие окна авторизации
        driver.findElement(By.cssSelector("[data-widget=\"profileMenuAnonymous\"]")).click();

        // Ожидание подтверждения авторизации
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href=\"/my/main\"]")));
        driver.findElement(By.cssSelector("[href=\"/my/main\"]")).click();

        // Подтверждение смены города в профиле
        wait.until(ExpectedConditions
                .textToBePresentInElement(driver
                        .findElement(By.cssSelector("div[role=\"navigation\"] button span")), "Вольск"));

        // Закрытие страницы
        driver.quit();
    }

    // Сортировка по цене
    @Test
    public void showByPrice() throws InterruptedException {
        // Драйвера
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver83.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // Открытие страницы
        driver.get("https://www.ozon.ru/");

        // Ввод поискового запроса
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder=\"Искать на Ozon\"]")));
        driver.findElement(By.cssSelector("[placeholder=\"Искать на Ozon\"]"))
                .sendKeys("Соковыжималка", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[qa-id=\"range-from\"]")));

        // Заполнение цены "От"
        WebElement showFrom = driver.findElements(By.cssSelector("[qa-id=\"range-from\"]")).get(0);
        for (int i = 0; i < 6; i++) {
            showFrom.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(500);
        }
        showFrom.sendKeys("3000");
        Thread.sleep(500);
        showFrom.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[style=\"border-radius: 16px;\"]")));


        // Заполнение цены "До"
        WebElement showTo = driver.findElements(By.cssSelector("[qa-id=\"range-to\"]")).get(0);
        for (int i = 0; i < 6; i++) {
            showTo.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(500);
        }
        showTo.sendKeys("4000", Keys.ENTER);
        wait.until(ExpectedConditions
                .textToBePresentInElement(driver
                        .findElement(By
                                .cssSelector("[style=\"border-radius: 16px;\"]")), "Цена: от 3 000 до 4 000"));

        // Изменение типа сортировки
        WebElement method = driver.findElement(By.cssSelector("[theme=\"select\"] input[role=\"combobox\"]"));
        method.click();
        Thread.sleep(1000);
        for (int i = 0; i < 2; i++) {
            method.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(500);
        }
        method.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        // Добавление товара в корзину
        WebElement addButton = driver.findElement(By.cssSelector("[data-widget=\"megaPaginator\"] > div"))
                .findElement(By.cssSelector("div[data-widget=\"searchResultsV2\"]"))
                .findElements(By.cssSelector("div > div[style=\"grid-column-start: span 12;\"]"))
                .get(1)
                .findElements(By.cssSelector("div > div > div[style=\"width: 25%; max-width: 25%; flex: 0 0 25%;\"]"))
                .get(1).findElements(By.cssSelector("div > button"))
                .get(1);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", addButton);
        wait.until(ExpectedConditions
                .textToBePresentInElement(driver.findElement(By.cssSelector("a[href=\"/cart\"] span")), "1"));

        // Проверка корзины
        driver.findElement(By.cssSelector("a[href=\"/cart\"] span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-widget=\"total\"]")));

        // Закрытие страницы
        driver.quit();
    }

    // Сортировка по мощности
    @Test
    public void showByPower() throws InterruptedException {
        // Драйвера
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver83.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // Открытие страницы
        driver.get("https://www.ozon.ru/");

        // Ввод поискового запроса
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder=\"Искать на Ozon\"]")));
        driver.findElement(By.cssSelector("[placeholder=\"Искать на Ozon\"]"))
                .sendKeys("Соковыжималка", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[qa-id=\"range-from\"]")));

        // Заполнение мощности "От"
        WebElement showFrom = driver.findElements(By.cssSelector("[qa-id=\"range-from\"]")).get(1);
        for (int i = 0; i < 6; i++) {
            showFrom.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(500);
        }
        showFrom.sendKeys("1100");
        Thread.sleep(500);
        showFrom.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[style=\"border-radius: 16px;\"]")));

        // Изменение типа сортировки
        WebElement method = driver.findElement(By.cssSelector("[theme=\"select\"] input[role=\"combobox\"]"));
        method.click();
        Thread.sleep(1000);
        for (int i = 0; i < 2; i++) {
            method.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(500);
        }
        method.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        // Добавление товара в корзину
        WebElement addButton = driver.findElement(By.cssSelector("[data-widget=\"megaPaginator\"] > div"))
                .findElement(By.cssSelector("div[data-widget=\"searchResultsV2\"]"))
                .findElements(By.cssSelector("div > div[style=\"grid-column-start: span 12;\"]"))
                .get(1)
                .findElements(By.cssSelector("div > div > div[style=\"width: 25%; max-width: 25%; flex: 0 0 25%;\"]"))
                .get(1).findElements(By.cssSelector("div > button"))
                .get(1);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", addButton);
        wait.until(ExpectedConditions
                .textToBePresentInElement(driver.findElement(By.cssSelector("a[href=\"/cart\"] span")), "1"));

        // Проверка корзины
        driver.findElement(By.cssSelector("a[href=\"/cart\"] span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-widget=\"total\"]")));

        // Закрытие страницы
        driver.quit();
    }

}
