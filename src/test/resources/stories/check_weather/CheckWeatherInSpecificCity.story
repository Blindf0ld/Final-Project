Narrative:
As a traveler
I want to check the weather
So that I can prepare myself for everything

Scenario: checking weather in capitals
Given the user is on the Weather Forecast home page
When user enters <capital>
Then dropdown should be shown along with corresponding <capital> and <country>

Examples:
capital|country
Washington D.C.|United States
Kiev|Ukraine
Paris|France
Helsinki|Finland
Madrid|Spain
Sidney|Australia
Rome|Italy
Warsaw|Poland
Berlin|Germany
Copenhagen|Denmark
