package org.kbank.account.model.test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps {

    @Given("A happy client")
    public void a_happy_client() {
        System.out.println("Mr. dupont");
    }
    @When("say hello")
    public void say_hello() {
        System.out.println("Hi my bank");
    }
    @Then("the account must greet him back")
    public void the_account_must_greet_him_back() {
        System.out.println("Hi Mr. dupont");
    }

}
