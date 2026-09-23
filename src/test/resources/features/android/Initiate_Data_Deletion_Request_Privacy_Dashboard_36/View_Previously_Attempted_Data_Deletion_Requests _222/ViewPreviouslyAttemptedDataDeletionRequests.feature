@ANDROID @US_VPADDR @Q3_2026
Feature: View previously attempted data deletion requests
  As a EUDI Wallet User
  I want to view my previously attempted data deletion requests,
  So that I can review my previous data deletion request activity

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/222

  @US_VPADDR_TC_01
  Scenario: Display Presentation Information
    Given the authenticated user has presentation transactions in the Wallet History
    When the user selects a presentation transaction
    Then the Wallet displays the Presentation Information for the selected transaction

  @US_VPADDR_TC_02
  Scenario: Display previously attempted requests link
    Given the selected presentation transaction has one or more recorded data deletion request attempts
    When the user views the Presentation Information screen
    Then the Wallet displays the View previously attempted requests link

  @US_VPADDR_TC_03
  Scenario: Hide previously attempted requests link when no attempts exist
    Given the selected presentation transaction has no recorded data deletion request attempts
    When the user views the Presentation Information screen
    Then the Wallet does not display the View previously attempted requests link

  @US_VPADDR_TC_04
  Scenario: Display Data Deletion Request History information
    Given the selected presentation transaction has one or more recorded data deletion request attempts
    When the user selects the View previously attempted requests link
    Then the Wallet opens the Data Deletion Request History screen
    And the Wallet displays the corresponding title
    And the Wallet displays explanatory text above the list of previous attempts
    And the Wallet displays the previous attempts associated with the selected presentation transaction

  @US_VPADDR_TC_05
  Scenario: Display details for previous data deletion request attempts
    Given the Data Deletion Request History screen contains previous attempts
    When the user views the list of previous attempts
    Then each attempt displays the date of the attempt
    And each attempt displays the communication method used

  @US_VPADDR_TC_06
  Scenario: Return to Presentation Information
    Given the user is viewing the Data Deletion Request History screen
    When the user selects the Back navigation option
    Then the Wallet returns to the Presentation Information screen
