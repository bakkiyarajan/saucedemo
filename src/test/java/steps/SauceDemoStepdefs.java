package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.CommonPage;

import java.util.List;

public class SauceDemoStepdefs extends CommonPage {
    private String highestPricedProductName = null;

    @Given("I go to url {string}")
    public static void iGoToUrl(String url) {
        getUrl(url);
    }

    @When("I enter a valid username {string}")
    public void i_enter_a_valid_username(String userName) {
        driver.findElement(By.id("user-name")).sendKeys(userName);
    }

    @When("I enter a valid password {string}")
    public void i_enter_a_valid_password(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @When("I clicks the login button")
    public void i_clicks_the_login_button() {
        driver.findElement(By.id("login-button")).click();

    }

    @Then("I should be redirected to the {string} page")
    public void i_should_be_redirected_to_the_page(String actualTitle) {
        String expectedTitle = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Then("I add the highest price item to the cart")
    public void i_add_the_highest_price_item_to_the_cart()  {
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        double highestPrice = 0;
        WebElement highestPricedProduct = null;
        for (WebElement product : products) {
            String priceText = product.findElement(By.className("inventory_item_price")).getText();
            double price = Double.parseDouble(priceText.replace("$", "").trim());

            if (price > highestPrice) {
                highestPrice = price;
                highestPricedProduct = product;
            }
        }

        if (highestPricedProduct != null) {
            highestPricedProductName = highestPricedProduct.findElement(By.className("inventory_item_name")).getText().trim();
            WebElement addButton = highestPricedProduct.findElement(By.tagName("button"));
            addButton.click();
        } else {
            throw new RuntimeException("No products found on the inventory page.");
        }
    }

    @Then("I click on the add cart link on the page")
    public void i_click_on_the_add_cart_link_on_the_page() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @Then("I should see the highest price item added to the cart")
    public void i_should_see_the_highest_price_item_added_to_the_cart() {
        String cartItemName = driver.findElement(By.className("inventory_item_name")).getText().trim();
        Assert.assertEquals(highestPricedProductName, cartItemName);

    }
}
