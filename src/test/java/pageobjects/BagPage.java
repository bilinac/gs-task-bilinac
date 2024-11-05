package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.awt.SystemColor.text;
import static stepdefs.hooks.Hooks.driver;
import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  private static final By REMOVE_MESSAGE = By.cssSelector("[data-locator-id^='snackbox-component']");
  private static final By PRODUCT_QUANTITY = By.cssSelector("div.product-card_quantity-container__oR3U4 span.qty-selector_text__4uAGo");

  private static By getRemoveFromBagButton(Long id) {
    return By.cssSelector(String.format("[data-locator-id='miniBag-remove-%s-remove']", id.toString()));
  }

  private static By quantityDropdown() {
    return By.cssSelector("select[data-locator-id*='miniBag-quantityDropdown-']");
  }

  public BagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
            .map(this::getVariantId)
            .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public void removeProduct(Long variantId) {
    getCommands().waitForAndClickOnElementLocated(getRemoveFromBagButton(variantId));
  }

  public String getRemoveProductMessage() {
    return getCommands().waitForAndGetVisibleElementLocated(REMOVE_MESSAGE).getText();
  }

  public void updateProductQuantity() {
    WebElement dropdownElement = driver.findElement(quantityDropdown()); // Locate the dropdown element
    Select quantityDrop = new Select(dropdownElement);                   // Initialize the Select with the element
    List<WebElement> options = quantityDrop.getOptions();                // Get all options in the dropdown

    // Loop through options to check if "3" is available
    boolean isQuantityThreeAvailable = options.stream()
            .anyMatch(option -> option.getText().equals("3"));

    if (isQuantityThreeAvailable) {
      quantityDrop.selectByVisibleText("3"); // Select "3" if it's an available option
      System.out.println("Selected specific quantity: 3");
    } else {
      // Fallback to random selection if "3" is not available
      Random rand = new Random();
      int randomIndex = rand.nextInt(options.size());
      quantityDrop.selectByIndex(randomIndex);
      WebElement selectedOption = options.get(randomIndex);
      System.out.println("Selected random quantity: " + selectedOption.getText());
    }
  }

  public String getProductQuantity() {
    String quantityText = getCommands().waitForAndGetVisibleElementLocated(PRODUCT_QUANTITY).getText();
    String[] parts = quantityText.split(": ");

    if (parts.length > 1) {
      return parts[1].trim();
    } else {
      throw new IllegalStateException("Unexpected format for quantity text: " + quantityText);
    }
  }
}
