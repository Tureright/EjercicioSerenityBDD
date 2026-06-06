package org.santi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.santi.ui.DemoblazePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PlaceOrder implements Task {

    public static PlaceOrder fromTheCart() {
        return Tasks.instrumented(PlaceOrder.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(DemoblazePage.PLACE_ORDER_BUTTON),
                WaitUntil.the(DemoblazePage.NAME_FIELD, isVisible())
                        .forNoMoreThan(10).seconds()
        );
    }
}