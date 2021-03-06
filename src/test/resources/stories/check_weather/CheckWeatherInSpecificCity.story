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
And table with forecast for nearest three days included today for our <capital> present
And today temperature for <capital> corresponding to request
And user click on selected country and new page with <capital> opens


Examples:
capital|country|zone
Washington D.C.|United States|U.S.A.
Riga|Latvia|Europe
Paris|France|Europe
Budapest|Hungary|Europe
Amsterdam|Netherlands|Europe
Sidney|Australia|Australia
Rome|Italy|Europe
Warsaw|Poland|Europe
London|United Kingdom|Europe
Copenhagen|Denmark|Europe


