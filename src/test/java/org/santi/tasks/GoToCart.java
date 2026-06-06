package org.santi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.santi.ui.DemoblazePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class GoToCart implements Task {

    public static GoToCart page() {
        return Tasks.instrumented(GoToCart.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(DemoblazePage.CART_LINK),
                WaitUntil.the(DemoblazePage.PLACE_ORDER_BUTTON, isVisible())
                        .forNoMoreThan(10).seconds()
        );
    }
}