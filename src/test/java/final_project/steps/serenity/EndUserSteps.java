package final_project.steps.serenity;

import final_project.pages.DictionaryPage;
import final_project.pages.SearchWeatherPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps extends ScenarioSteps {

    DictionaryPage dictionaryPage;
    SearchWeatherPage searchWeatherPage;

    @Step
    public void enters(String keyword) {
        dictionaryPage.enter_keywords(keyword);
    }

    @Step
    public void starts_search() {
        dictionaryPage.lookup_terms();
    }

    @Step
    public void should_see_definition(String definition) {
        assertThat(dictionaryPage.getDefinitions(), hasItem(containsString(definition)));
    }

    @Step
    public void is_the_home_page() {
        dictionaryPage.open();
    }

    @Step
    public void looks_for(String term) {
        enters(term);
        starts_search();
    }

    @Step
    public void enter_city(String city){
        searchWeatherPage.enter_word(city);
    }

    @Step
    public void search(){
        searchWeatherPage.search_perform();
    }

    @Step
    public void open_weather_page(){
        searchWeatherPage.open();
    }

    @Step
    public void check_dropdown_list(String city, String country){
        searchWeatherPage.check_presense_in_dropdown(city, country);
    }

    @Step
    public void search_capital(){
        searchWeatherPage.search_perform();
    }

    @Step
    public void check_page_open(String capital, String country){
        searchWeatherPage.check_needed_page_open(capital, country);
    }

    @Step
    public void forecats_for_three_days(String zone, String capital){
        searchWeatherPage.check_forecast_for_three_days(zone, capital);
    }

    @Step
    public void compare_temperature_with_request(String capital){
        searchWeatherPage.temperature_request_C(capital);
        searchWeatherPage.check_temperature_in_table_C();
        searchWeatherPage.temperature_request_F(capital);
        searchWeatherPage.check_temperature_in_table_F();
    }

    @Step
    public void capital_presented_on_opened_page(String capital){
        searchWeatherPage.capital_presented_in_city_list(capital);
    }

}