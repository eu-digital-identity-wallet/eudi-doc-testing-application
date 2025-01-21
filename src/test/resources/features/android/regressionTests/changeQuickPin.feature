@ANDROID @automated @US_CQP
Feature: Change quick Pin

  @US_CQP_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_CQP_TC_02 @before_01
  Scenario: User open the three-dot menu
    Given the user is on the dashboard screen
    When the user clicks on the three-dot menu
    Then the menu should open
    And sees typo in retrieve logs

  @US_CQP_TC_03 @before_01
  Scenario: User clicks change quick PIN
    Given the menu is open
    When the user clicks the change quick PIN option
    Then the change quick PIN page should open



