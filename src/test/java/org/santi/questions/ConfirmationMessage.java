package org.santi.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.santi.ui.DemoblazePage;

public class ConfirmationMessage implements Question<String> {

    public static ConfirmationMessage onScreen() {
        return new ConfirmationMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(DemoblazePage.CONFIRMATION_MESSAGE)
                .answeredBy(actor);
    }
}