package org.santi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.santi.models.PurchaseData;
import org.santi.ui.DemoblazePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FillPurchaseForm implements Task {

    private final PurchaseData data;

    public FillPurchaseForm(PurchaseData data) {
        this.data = data;
    }

    public static FillPurchaseForm with(PurchaseData data) {
        return Tasks.instrumented(FillPurchaseForm.class, data);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(data.getName()).into(DemoblazePage.NAME_FIELD),
                Enter.theValue(data.getCountry()).into(DemoblazePage.COUNTRY_FIELD),
                Enter.theValue(data.getCity()).into(DemoblazePage.CITY_FIELD),
                Enter.theValue(data.getCard()).into(DemoblazePage.CARD_FIELD),
                Enter.theValue(data.getMonth()).into(DemoblazePage.MONTH_FIELD),
                Enter.theValue(data.getYear()).into(DemoblazePage.YEAR_FIELD),
                Click.on(DemoblazePage.PURCHASE_BUTTON),
                WaitUntil.the(DemoblazePage.CONFIRMATION_MESSAGE, isVisible())
                        .forNoMoreThan(10).seconds()
        );
    }
}