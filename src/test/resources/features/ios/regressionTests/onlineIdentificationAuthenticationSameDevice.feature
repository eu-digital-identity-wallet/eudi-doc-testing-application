@IOS @automated @US_OIASD
Feature: Online Identification & Authentication (same-device)

  @US_OIASD_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the home screen

  @US_OIASD_TC_02 @before_01
  Scenario: User selects data to share
    Given user opens Verifier Application
    When user selects specific data to share
    Then user selects to be identified using EUDI Wallet

  @US_OIASD_TC_03 @before_01
  Scenario: User views and unselects data
    Given user selects to be identified using the EUDI Wallet
    When user views the data and can unselect any of them
    Then user presses the share button on wallet

  @US_OIASD_TC_04 @before_01
  Scenario: User authorizes data disclosure
    Given user presses the share button
    When user authorizes the disclosure of the data
    And user is authenticated successfully
    And the user clicks done
    Then the user gets redirected to verifier and views the respond



