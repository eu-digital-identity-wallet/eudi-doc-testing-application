@IOS @manual @US_RM
Feature: Deleting a mDL document

  @US_RΜ_TC_01 @data
  Scenario: User enters PIN and selects a mDL
    Given the user has successfully entered the PIN
    When the user opens a mDL
    Then the user should see the document contents

  @US_RΜ_TC_02 @data
  Scenario: User deletes the document
    Given the user has opened the selected mDL
    When the user presses the delete button
    Then the user should see the dashboard


