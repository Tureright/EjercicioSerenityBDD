package org.santi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.santi.ui.DemoblazePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddProductToCart implements Task {

    private final String productName;

    public AddProductToCart(String productName) {
        this.productName = productName;
    }

    public static AddProductToCart named(String productName) {
        return Tasks.instrumented(AddProductToCart.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var productTarget = productName.equalsIgnoreCase("Samsung galaxy s6")
                ? DemoblazePage.FIRST_PRODUCT
                : DemoblazePage.SECOND_PRODUCT;

        actor.attemptsTo(
                Click.on(productTarget),

                WaitUntil.the(DemoblazePage.ADD_TO_CART_BUTTON, isVisible())
                        .forNoMoreThan(10).seconds(),

                Click.on(DemoblazePage.ADD_TO_CART_BUTTON)
        );

        tryAcceptAlert(BrowseTheWeb.as(actor).getDriver());

        actor.attemptsTo(
                Click.on(DemoblazePage.HOME_LINK),

                WaitUntil.the(DemoblazePage.FIRST_PRODUCT, isVisible())
                        .forNoMoreThan(10).seconds()
        );
    }

    private void tryAcceptAlert(WebDriver driver) {
        try {
            Thread.sleep(1500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {

        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}