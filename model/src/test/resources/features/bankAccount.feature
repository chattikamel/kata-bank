Feature: Account
  As a client
  I want to say hello


  Scenario: Greeting
    Given A happy client
    When say hello
    Then the account must greet him back


