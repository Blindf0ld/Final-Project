package final_project.pages;

import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.objectMapper;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@DefaultUrl("http://www.weather-forecast.com/")
public class SearchWeatherPage extends PageObject {

    @FindBy(id = "location")
    private WebElementFacade searchForm;

    @FindBy(id = "searchbtn")
    private WebElementFacade searchButton;

    @FindBy(xpath = ".//img[@class='descr-topo-thumb']")
    private WebElementFacade mapIcon;

    @FindBy(xpath = "//*[@class='forecast-cont'][1]//td[@class='date-header day-end']/div[@class='dayname']")
    private List<WebElementFacade> forecastMapDayOfWeek;

    @FindBy(xpath = "//*[@class='forecast-cont'][1]//td[@class='date-header day-end']/div[@class='dom']")
    private List<WebElementFacade> forecastMapDate;

    @FindBy(xpath = ".//table/tbody/tr[9]/td[2]")
    private WebElementFacade tempMax;

    @FindBy(xpath = ".//table/tbody/tr[10]/td[3]")
    private WebElementFacade tempMin;

    @FindBy(xpath = "//*[@class='forecast-cont'][1]/div/a[contains(@class, 'units metric')]")
    private WebElementFacade tempC;

    @FindBy(xpath = "//*[@class='forecast-cont'][1]/div/a[contains(@class, 'units imperial')]")
    private WebElementFacade tempF;

    @FindBy(xpath = "//*[@class ='forecast-cont'][1]//tr[.//th[contains(.,'Max')]]//td[position()<4]")
    private List<WebElementFacade> getTempMax;

    @FindBy(xpath = "//*[@class ='forecast-cont'][1]//tr[.//th[contains(.,'Min')]]//td[position()<4]")
    private List<WebElementFacade> getTempMin;

    public void enter_word(String word) {
        searchForm.type(word);
    }

    public void search_perform() {
        searchButton.click();
    }

    public void check_presense_in_dropdown(String capital, String country) {
        Actions builder = new Actions(getDriver());
        builder.moveToElement(getDriver().findElement(By.id("location_autocomplete"))).perform();
        String presentCapital = getDriver().findElement(By.xpath(".//span[@class='autoCompleteHighlight']")).getText();
        assertEquals(presentCapital, capital);
        String presentCountry = getDriver().findElement(By.xpath(".//span[@class='cntry']")).getText();
        assertEquals(presentCountry, country);
        assertNotNull("flag present", getDriver().findElement(By.xpath(".//nobr/div[contains(@class, 'flag')]")));
    }

    public void check_needed_page_open(String capital, String country) {
        assertNotNull("New page with weather related to this city is opened", mapIcon.isDisplayed());
        String actual = getDriver().findElement(By.xpath("//ol/li/a[contains(@href, 'countries')]")).getText();
        assertEquals(actual, country);
        String actual1 = getDriver().findElement((By.xpath(".//li[@class='current']/a/span"))).getText();
        assertEquals(actual1, capital);
        getDriver().findElement(By.xpath("//ol/li/a[contains(@href, 'countries')]")).click();
        assertNotNull("Table with cities displayed", getDriver().findElement(By.xpath(".//div/table[@class='list_table country-loc-list']")).isDisplayed());
        getDriver().navigate().back();
    }

    public void check_forecast_for_three_days(String city) {
        LocalDateTime timeout = LocalDateTime.now(TimeZone.getTimeZone("Europe/ " + city).toZoneId());

        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(forecastMapDayOfWeek.get(i).getText().toUpperCase(), timeout.getDayOfWeek().toString());
            Assert.assertEquals(forecastMapDate.get(i).getText().toUpperCase(), String.valueOf(timeout.getDayOfMonth()));
            timeout = timeout.plusDays(1);
        }
    }


    public void rest_request_check_temp_C(String city) {
        Response response = given().param("units", "metric").given().
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("http://api.openweathermap.org/data/2.5/weather").
                then().assertThat().body("main.temp_min", allOf(greaterThan(0f)), lessThan(50f)).body("main.temp_max", allOf(greaterThan(0f)), lessThan(50f)).extract().response();
    }

    public void rest_request_check_temp_F(String city) {
        given().param("units", "imperial").given().
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("https://http://api.openweathermap.org/data/2.5/weather").
                then().assertThat().body("main.temp_min", allOf(greaterThan(0f)), lessThan(90f)).body("main.temp_max", allOf(greaterThan(0f)), lessThan(90f));
    }

    /*public void comparing_temp(){
        for (int i=0; i< 3; i++){
            Assert.assertThat(getTempMax.get(i).getText(), rest_request_check_temp_C(objectMapper ();).response;);
        }
    }*/

    public void temperature_checking_C(String city) {
         Response response = given().param("units", "metric").given().
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("http://api.openweathermap.org/data/2.5/weather").
                then().assertThat().body("main.temp_min", allOf(greaterThan(0f)), lessThan(50f)).extract().response();


        Response response1 = given().param("units", "metric").given().
                    param("q", city).
                    param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                    get("http://api.openweathermap.org/data/2.5/weather").
                    then().assertThat().body("main.temp_max", allOf(greaterThan(0f)), lessThan(50f)).extract().response();

        for (int i=0; i<3; i++){
//            Assert.assertThat(getTempMax.get(i).getText(), allOf(greaterThanOrEqualTo(response.toString())), lessThan(Float.parseFloat(response1.toString())));
        }
    }


    public void temperature_checking_F(String city){
        Response response = given().param("units", "imperial").given().
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("http://api.openweathermap.org/data/2.5/weather").
                then().assertThat().body("main.temp_min", allOf(greaterThan(0f)), lessThan(90f)).extract().response();
        Response response1 = given().param("units", "imperial").given().
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("http://api.openweathermap.org/data/2.5/weather").
                then().assertThat().body("main.temp_max", allOf(greaterThan(0f)), lessThan(90f)).extract().response();
    }


}
