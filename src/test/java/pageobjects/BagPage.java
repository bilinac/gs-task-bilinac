package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  private static final By REMOVE_MESSAGE = By.cssSelector("[data-locator-id^='snackbox-component']");

  private static By getRemoveFromBagButton(Long id) {
    return By.cssSelector(String.format("[data-locator-id='miniBag-remove-%s-remove']", id.toString()));
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
}
