@IOS @automated @US_RP
Feature: Deleting a PID document

  @US_RP_TC_01 @before_03
  Scenario: User enters PIN and selects PID
    Given the user has successfully entered the PIN
    When the user opens a PID (not the first one issued)
    Then the user should see the pid document contents

  @US_RP_TC_02 @before_03
  Scenario: User deletes the document
    Given the user has opened the selected PID
    When the user presses the delete button
    Then the user should see the dashboard

  @US_RP_TC_03 @before_03
  Scenario: User deletes the document and application reboots
    Given the user has opened the first PID that was issued
    When the user presses the delete button
    Then the application should reboot

  @US_RP_TC_04 @before_03
  Scenario: User sees the login screen after reboot
    Given the application has rebooted
    When the login screen appears
    Then the user should enter the PIN

  @US_RP_TC_05 @before_03
  Scenario: User needs to enter a PID again
    Given the user has successfully entered the PIN after reboot
    When the user is prompted to enter a PID
    Then the user should be able to enter a PID again
