package final_project.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

@DefaultUrl("http://www.weather-forecast.com/")
public class SearchWeatherPage extends PageObject {

    @FindBy(id="location")
    private WebElementFacade searchForm;

    @FindBy(id ="searchbtn")
    private WebElementFacade searchButton;

    @FindBy(xpath = ".//img[@class='descr-topo-thumb']")
    private WebElementFacade mapIcon;

    @FindBy(xpath = ".//td[@class='date-header day-end']")
    private WebElementFacade weatherMap;

    public void enter_word(String word){
        searchForm.type(word);
    }

    public void search_perform(){
        searchButton.click();
    }

    public void check_presense_in_dropdown(String capital, String country){
        Actions builder = new Actions(getDriver());
        builder.moveToElement(getDriver().findElement(By.id("location_autocomplete"))).perform();
        String s = getDriver().findElement(By.xpath(".//span[@class='autoCompleteHighlight']")).getText();
        Assert.assertEquals(s, capital);
        String s1 = getDriver().findElement(By.xpath(".//span[@class='cntry']")).getText();
        Assert.assertEquals(s1, country);
        Assert.assertNotNull("flag present", getDriver().findElement(By.xpath(".//nobr/div[contains(@class, 'flag')]")));
    }

    public void check_needed_page_open(String capital, String country){
        Assert.assertNotNull("New page with weather related to this city is opened", mapIcon.isDisplayed());
        String actual = getDriver().findElement(By.xpath("//ol/li/a[contains(@href, 'countries')]")).getText();
        Assert.assertEquals(actual, country);
        String actual1 = getDriver().findElement((By.xpath(".//li[@class='current']/a/span"))).getText();
        Assert.assertEquals(actual1, capital);
        getDriver().findElement(By.xpath("//ol/li/a[contains(@href, 'countries')]")).click();
        Assert.assertNotNull("Table with cities displayed", getDriver().findElement(By.xpath(".//div/table[@class='list_table country-loc-list']")).isDisplayed());
        getDriver().navigate().back();
    }




}
