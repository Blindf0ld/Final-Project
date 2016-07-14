Narrative:
As a traveler
I want to check the weather
So that I can prepare myself for everything

Scenario: checking weather in capitals
Given the user is on the Weather Forecast home page
And user enters <capital>
And dropdown is shown along with corresponding <capital> and <country>
When user selects capital
Then corresponding page with <capital> and <country> opens

Examples:
capital|country
Washington D.C.|United States
