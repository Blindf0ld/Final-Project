package final_project.steps;

import final_project.steps.serenity.EndUserSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Created by pc on 11.07.2016.
 */
public class SearchWeatherSteps {

    @Steps
    EndUserSteps endUserSteps;

    @Given("the user is on the Weather Forecast home page")
    public void openWeatherSitePage(){
        endUserSteps.open_weather_page();
    }

    @When("user enters <capital>")
    public void enterCapital(@Named("capital") String city){
        endUserSteps.enter_city(city);
    }

    @Then("dropdown should be shown along with corresponding <capital> and <country>")
    public void enteredCapitalCheckPresence(@Named("capital") String city, @Named("country") String country){
        endUserSteps.check_dropdown_list(city, country);
    }
}
