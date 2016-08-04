package final_project.pages;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.yecht.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import static com.codeborne.selenide.Configuration.timeout;
import static io.restassured.RestAssured.given;
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

    @FindBy(xpath = "//*[@class ='forecast-cont'][1]//tr[.//th[contains(.,'Max')]]//td[position()<2]")
    private WebElementFacade getTempMax;

    @FindBy(xpath = "//*[@class ='forecast-cont'][1]//tr[.//th[contains(.,'Min')]]//td[position()<2]")
    private WebElementFacade getTempMin;

    @FindBy(xpath = ".//div/table[@class='list_table country-loc-list']")
    private WebElementFacade tableWithCities;

    @FindBy(xpath = "//ol/li/a[contains(@href, 'countries')]")
    private WebElementFacade countryLink;

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
        countryLink.click();
        assertNotNull("Table with cities displayed", tableWithCities.isDisplayed());
        getDriver().navigate().back();
    }

    public void capital_presented_in_city_list(String capital){
        countryLink.click();
        assertNotNull("Table with cities displayed", tableWithCities.isDisplayed());
        tableWithCities.containsText(capital);
    }

    public void check_forecast_for_three_days(String city) {
        LocalDateTime timeout = LocalDateTime.now(TimeZone.getTimeZone("Europe/ " + city).toZoneId());
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(forecastMapDayOfWeek.get(i).getText().toUpperCase(), timeout.getDayOfWeek().toString());
            Assert.assertEquals(forecastMapDate.get(i).getText().toUpperCase(), String.valueOf(timeout.getDayOfMonth()));
            timeout = timeout.plusDays(1);
        }
    }

    public void temperature_request_C(String city) {
        System.out.println(city);
        Response response = given().param("units", "metric").
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("http://api.openweathermap.org/data/2.5/weather");
        Float c_min = response.jsonPath().getFloat("main.temp_min");
        Float c_max = response.jsonPath().getFloat("main.temp_max");
        //System.out.println(c_max + " " + city);
        //System.out.println(c_min + " " + city);
        Serenity.setSessionVariable("c_min").to(c_min);
        Serenity.setSessionVariable("c_max").to(c_max);
    }

    public void temperature_request_F(String city) {
        Response response = given().param("units", "imperial").
                param("q", city).
                param("appid", "82c8a6d21da39718af1b0bd42d63827d").
                get("http://api.openweathermap.org/data/2.5/weather");
        Float f_min = response.jsonPath().getFloat("main.temp_min");
        Float f_max = response.jsonPath().getFloat("main.temp_max");

        Serenity.setSessionVariable("f_min").to(f_min);
        Serenity.setSessionVariable("f_max").to(f_max);
    }

    public void check_temperature_in_table_C() {
        String min_border = Serenity.sessionVariableCalled("c_min").toString();
        String max_border = Serenity.sessionVariableCalled("c_max").toString();
        for(int i=1; i<4; i++){
            String min = getDriver().findElement(By.xpath(".//table/tbody/tr[10]/td[" + i +"]")).getText();
            String max = getDriver().findElement(By.xpath(".//table/tbody/tr[9]/td[" + i +"]")).getText();
            Assert.assertThat(min, allOf(greaterThanOrEqualTo(min_border), lessThanOrEqualTo(max_border)));
            Assert.assertThat(max, allOf(greaterThanOrEqualTo(min_border), lessThanOrEqualTo(max_border)));
        }
    }

    public void check_temperature_in_table_F() {
        tempF.click();
        String min_border = Serenity.sessionVariableCalled("f_min").toString();
        String max_border = Serenity.sessionVariableCalled("f_max").toString();
        for(int i=1; i<4; i++){
            String min = getDriver().findElement(By.xpath(".//table/tbody/tr[10]/td[" + i +"]")).getText();
            String max = getDriver().findElement(By.xpath(".//table/tbody/tr[9]/td[" + i +"]")).getText();
            Assert.assertThat(min, allOf(greaterThanOrEqualTo(min_border), lessThanOrEqualTo(max_border)));
            Assert.assertThat(max, allOf(greaterThanOrEqualTo(min_border), lessThanOrEqualTo(max_border)));
        }
    }
}
