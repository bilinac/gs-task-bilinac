package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

  private final WebDriver driver;
  private Long productId;

  public ProductStepDefs(){
    this.driver = Hooks.getDriver();
  }

  @Given("the user is on a product page")
  public void theUserIsOnAProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productId = 39654522814667L;
    new ProductDisplayPage();
  }

  @When("adding the product to the Bag")
  public void addingTheProductToTheBag() {
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @Then("the product should appear in the Bag")
  public void theProductShouldAppearInTheBag() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  @And("remove product from the bag")
  public void removeFromBag() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    bagPage.removeProduct(variantIds.getFirst());
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  @Then("the product is successfully removed from the Bag")
  public void verifyRemovedProduct(){
    BagPage bagPage = new BagPage();
    assertThat(bagPage.getRemoveProductMessage()).as("Expected product is not in Bag").contains("You removed an item from your bag.");
  }

  @And("adding the quantity {string}")
  public void addingQuantity(String desiredQuantity) throws InterruptedException {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    bagPage.updateProductQuantity(desiredQuantity);
    assertThat(variantIds).as("Quantity is increased").contains(productId);
  }

  @Then("the product quantity has been updated to desired quantity {string}")
  public void verifyProductQuantityIsUpdated(String desiredQuantity) throws InterruptedException {
    BagPage bagPage = new BagPage();
    assertThat(bagPage.getProductQuantity()).as("Expected product is not in Bag").contains((desiredQuantity));
  }

  @And("there are products in the bag")
  public void addingTheProductToTheBagQuantity() {
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @When("I remove quantity")
  public void removeQuantity() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    bagPage.removeProduct(variantIds.getFirst());
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  @Then("product quantity is removed from the bag")
  public void verifyRemovedQuantity(){
    BagPage bagPage = new BagPage();
    assertThat(bagPage.getRemoveProductMessage()).as("Expected product is not in Bag").contains("You removed an item from your bag.");
  }
}