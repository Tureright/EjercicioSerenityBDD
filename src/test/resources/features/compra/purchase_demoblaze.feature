Feature: Purchase products on Demoblaze

  Scenario: Add two products to the cart and complete the purchase
    Given Customer opens the Demoblaze store
    When Customer adds the product "Samsung galaxy s6" to the cart
    And Customer adds the product "Nokia lumia 1520" to the cart
    And Customer goes to the cart
    And Customer completes the order form with the following details:
      | name     | country | city  | card             | month | year |
      | Santi QA | Ecuador | Quito | 1234567890123456 | June  | 2026 |
    Then Customer should see the message "Thank you for your purchase!"