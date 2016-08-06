package final_project.steps;

import final_project.steps.serenity.EndUserSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class SearchWeatherSteps {

    @Steps
    EndUserSteps endUserSteps;

    @Given("the user is on the Weather Forecast home page")
    public void openWeatherSitePage(){
        endUserSteps.open_weather_page();
    }

    @Given("user enters <capital>")
    public void enterCapital(@Named("capital") String city){
        endUserSteps.enter_city(city);
    }

    @Given("dropdown is shown along with corresponding <capital> and <country>")
    public void enteredCapitalCheckPresence(@Named("capital") String city, @Named("country") String country){
        endUserSteps.check_dropdown_list(city, country);
    }

    @When("user selects capital")
    public void clickOnEnteredCapital(){
        endUserSteps.search_capital();
    }

    @Then("corresponding page with <capital> and <country> opens")
    public void pageOpened(@Named("capital") String city, @Named("country") String country){
        endUserSteps.check_page_open(city, country);
    }

    @Then("table with forecast for nearest three days included today for our <capital> present")
    public void forecastPresent(@Named("zone") String zone, @Named("capital") String city){
        endUserSteps.forecats_for_three_days(zone, city);
    }

    @Then("today temperature for <capital> corresponding to request")
    public void forecastRequestChecking(@Named("capital") String city){
        endUserSteps.compare_temperature_with_request(city);
    }

    @Then("user click on selected country and new page with <capital> opens")
    public void openedPageHasNeededCapital(@Named("capital") String city){
        endUserSteps.capital_presented_on_opened_page(city);
    }
}
