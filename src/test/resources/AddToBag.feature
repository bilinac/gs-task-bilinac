Feature: Add To Bag

  #Add product ID as parameter in cucumber
  Scenario: Adding a product to the Bag
    Given the user is on a product page
    When adding the product to the Bag
    Then the product should appear in the Bag

  #AC1
  Scenario: Removing a product to the Bag
    Given the user is on a product page
    When adding the product to the Bag
    And remove product from the bag
    Then the product is successfully removed from the Bag

  #AC2
  Scenario: Product quantity is increased
    Given the user is on a product page
    When adding the product to the Bag
    And adding the quantity
    Then the product quantity has been updated to desired quantity 3

  #AC3
    #Given there are products in the bag
    #When I remove quantity
    #Then product quantity is removed from the bag