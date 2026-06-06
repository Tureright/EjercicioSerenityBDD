package org.santi.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class DemoblazePage {

    // ── Home page ─────────────────────────────────────────────────────

    public static final Target HOME_LINK =
            Target.the("Home navigation link")
                    .located(By.cssSelector("#navbarExample > ul > li.nav-item.active > a"));

    public static final Target FIRST_PRODUCT =
            Target.the("first product Samsung galaxy s6")
                    .located(By.cssSelector("#tbodyid > div:nth-child(1) > div > div > h4 > a"));

    public static final Target SECOND_PRODUCT =
            Target.the("second product Nokia lumia 1520")
                    .located(By.cssSelector("#tbodyid > div:nth-child(2) > div > div > h4 > a"));

    // ── Product detail page ───────────────────────────────────────────

    public static final Target ADD_TO_CART_BUTTON =
            Target.the("Add to cart button")
                    .located(By.cssSelector("#tbodyid > div.row > div > a"));

    // ── Navigation ────────────────────────────────────────────────────

    public static final Target CART_LINK =
            Target.the("Cart link")
                    .located(By.id("cartur"));

    // ── Cart page ─────────────────────────────────────────────────────

    public static final Target PLACE_ORDER_BUTTON =
            Target.the("Place Order button")
                    .located(By.cssSelector("#page-wrapper > div > div.col-lg-1 > button"));

    // ── Purchase form modal ───────────────────────────────────────────

    public static final Target NAME_FIELD =
            Target.the("name field").located(By.id("name"));

    public static final Target COUNTRY_FIELD =
            Target.the("country field").located(By.id("country"));

    public static final Target CITY_FIELD =
            Target.the("city field").located(By.id("city"));

    public static final Target CARD_FIELD =
            Target.the("credit card field").located(By.id("card"));

    public static final Target MONTH_FIELD =
            Target.the("month field").located(By.id("month"));

    public static final Target YEAR_FIELD =
            Target.the("year field").located(By.id("year"));

    public static final Target PURCHASE_BUTTON =
            Target.the("Purchase button")
                    .located(By.cssSelector(
                            "#orderModal > div > div > div.modal-footer > button.btn.btn-primary"));

    // ── Confirmation modal ────────────────────────────────────────────

    public static final Target CONFIRMATION_MESSAGE =
            Target.the("purchase confirmation message")
                    .located(By.cssSelector("div.sweet-alert.showSweetAlert.visible h2"));

    public static final Target CONFIRMATION_OK_BUTTON =
            Target.the("confirmation OK button")
                    .located(By.cssSelector("div.sweet-alert.showSweetAlert.visible button"));
}