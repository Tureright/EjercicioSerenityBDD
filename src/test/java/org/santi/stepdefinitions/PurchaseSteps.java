package org.santi.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.santi.abilities.NavegarLaWeb;
import org.santi.models.PurchaseData;
import org.santi.questions.ConfirmationMessage;
import org.santi.tasks.AddProductToCart;
import org.santi.tasks.FillPurchaseForm;
import org.santi.tasks.GoToCart;
import org.santi.tasks.OpenDemoblaze;
import org.santi.tasks.PlaceOrder;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static org.hamcrest.Matchers.containsString;

public class PurchaseSteps {

    private Actor customer;

    @Before
    public void setUpStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("Customer opens the Demoblaze store")
    public void customerOpensTheDemoblazeStore() {
        customer = Actor.named("Customer");
        customer.whoCan(NavegarLaWeb.conChrome());
        givenThat(customer).attemptsTo(OpenDemoblaze.inTheBrowser());
    }

    @When("Customer adds the product {string} to the cart")
    public void customerAddsTheProductToTheCart(String productName) {
        when(customer).attemptsTo(AddProductToCart.named(productName));
    }

    @And("Customer goes to the cart")
    public void customerGoesToTheCart() {
        when(customer).attemptsTo(GoToCart.page());
    }

    @And("Customer completes the order form with the following details:")
    public void customerCompletesTheOrderFormWithTheFollowingDetails(DataTable table) {
        Map<String, String> row = table.asMaps().get(0); // Aqui hay un error

        PurchaseData data = new PurchaseData(
                row.get("name"),
                row.get("country"),
                row.get("city"),
                row.get("card"),
                row.get("month"),
                row.get("year")
        );

        when(customer).attemptsTo(PlaceOrder.fromTheCart());
        when(customer).attemptsTo(FillPurchaseForm.with(data));
    }

    @Then("Customer should see the message {string}")
    public void customerShouldSeeTheMessage(String expectedMessage) {
        then(customer).should(
                seeThat(
                        ConfirmationMessage.onScreen(),
                        containsString(expectedMessage)
                )
        );
    }
}
