@IOS @automated @US_RM
Feature: Deleting a mDL document

  @US_RM_TC_01 @before_03
  Scenario: User enters PIN and selects a mDL
    Given the user has successfully entered the PIN
    When the user opens a mDL
    Then the user should see the document contents

  @US_RM_TC_02 @before_03 @release
  Scenario: User deletes the document
    Given the user has opened the selected mDL
    When the user presses the delete button for mDL
    Then the user should see the Documents dashboard


