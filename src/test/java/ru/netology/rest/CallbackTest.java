package ru.netology.rest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDawn() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendForm() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", resultElement.getText().trim());
    }

    @Test
    void shouldSendInvalidName() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan Petrov");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector("button")).click();
        WebElement resultInvalidName = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(resultInvalidName.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", resultInvalidName.getText().trim());
    }

    @Test
    void shouldSendEmptyName() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector("button")).click();
        WebElement resultInvalidName = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(resultInvalidName.isDisplayed());
        assertEquals("Поле обязательно для заполнения", resultInvalidName.getText().trim());
    }

    @Test
    void shouldSendInvalidPhoneNumber() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7901234567");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector("button")).click();
        WebElement resultInvalidName = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(resultInvalidName.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", resultInvalidName.getText().trim());
    }

    @Test
    void shouldSendEmptyPhoneNumber() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector("button")).click();
        WebElement resultInvalidName = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(resultInvalidName.isDisplayed());
        assertEquals("Поле обязательно для заполнения", resultInvalidName.getText().trim());
    }

    @Test
    void shouldSentWithoutCheckbox() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        form.findElement(By.cssSelector("button")).click();
        WebElement resultInvalidName = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        assertTrue(resultInvalidName.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", resultInvalidName.getText().trim());
    }


}