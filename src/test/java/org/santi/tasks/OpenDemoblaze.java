package org.santi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.santi.ui.DemoblazePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class OpenDemoblaze implements Task {

    private static final String URL = "https://www.demoblaze.com/";

    public static OpenDemoblaze inTheBrowser() {
        return Tasks.instrumented(OpenDemoblaze.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(URL),
                WaitUntil.the(DemoblazePage.FIRST_PRODUCT, isVisible())
                        .forNoMoreThan(15).seconds()
        );
    }
}