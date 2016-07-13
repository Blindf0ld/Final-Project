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

    public void click_on_capital(){
        Actions builder = new Actions(getDriver());
        builder.moveToElement(getDriver().findElement(By.id("location_autocomplete"))).click();
    }



}
