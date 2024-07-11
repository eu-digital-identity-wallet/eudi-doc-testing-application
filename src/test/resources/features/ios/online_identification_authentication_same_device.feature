@IOS @manual @US_OIASD
Feature: Online Identification & Authentication (same-device)

  @US_OIASD_TC_01 @without_data
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the add document page

  @US_OIASD_TC_02 @without_data
  Scenario: Dashboard page is displayed
    Given user clicks load sample data
    When the dashboard page is displayed on wallet
    Then user opens Verifier App

  @US_OIASD_TC_03  @without_data
  Scenario: User selects data to share
    Given user opens Verifier Application
    When user selects specific data to share
    Then user selects to be identified using EUDI Wallet

  @US_OIASD_TC_04
  Scenario: User views and unselects data
    Given user selects to be identified using EUDI Wallet
    When user views the data and can unselect any of them
    Then user presses the share button

  @US_OIASD_TC_05
  Scenario: User authorizes data disclosure
    Given user presses the share button
    When user authorizes the disclosure of the data
    Then user is authenticated successfully

