@IOS @US_VPATR @Q3_2026
Feature: View previously attempted transaction reports
  As an EUDI Wallet User
  I want to view my previously attempted transaction reports
  So that I can review my previous transaction report activity

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/394

  @US_VPATR_TC_01
  Scenario: View Presentation Information from History
    Given the authenticated user has presentation transactions in the Wallet History
    When the user selects a presentation transaction from the History screen
    Then the Wallet displays the Presentation Information for the selected transaction

  @US_VPATR_TC_02
  Scenario: Display the "View previously attempted requests" link when previous attempts exist
    Given the selected presentation transaction has one or more recorded transaction report attempts
    When the user views the Presentation Information screen
    Then the Wallet displays the corresponding link

  @US_VPATR_TC_03
  Scenario: Do not display the "View previously attempted requests" link when no previous attempts exist
    Given the selected presentation transaction has no recorded transaction report attempts
    When the user views the Presentation Information screen
    Then the Wallet does not display the corresponding link

  @US_VPATR_TC_04
  Scenario: Open transaction report History
    Given the selected presentation transaction has one or more recorded transaction report attempts
    When the user selects the "View previously attempted requests" link
    Then the Wallet opens the transaction report History screen

  @US_VPATR_TC_05
  Scenario: Display the transaction report History title
    Given the user has opened the transaction report History for a selected presentation transaction
    When the transaction report History screen is displayed
    Then the title is displayed as Previous transaction report for [Relying Party Name]

  @US_VPATR_TC_06
  Scenario: Display explanatory text for previous transaction report attempts
    Given the user has opened the transaction report History for a selected presentation transaction
    When the transaction report History screen is displayed
    Then explanatory text is displayed above the list of previous attempts

  @US_VPATR_TC_07
  Scenario: Display previous transaction reports associated with the selected presentation transaction
    Given previous transaction report attempts are associated with the selected presentation transaction
    When the user opens the transaction report History screen
    Then the Wallet displays only the previous transaction report attempts associated with the selected presentation transaction
    And each transaction report attempt displays the date of the attempt

  @US_VPATR_TC_08
  Scenario: Display the communication method for each previous transaction report attempt
    Given the selected presentation transaction has previously attempted transaction reports
    When the transaction report History screen displays the previous attempts
    Then each transaction report attempt displays the communication method used
    And the Wallet does not display any indication of its completion, submission, or processing status

  @US_VPATR_TC_09
  Scenario: Return to Presentation Information using Back navigation
    Given the user is viewing the transaction report History screen
    When the user selects the Back navigation option
    Then the Wallet returns to the Presentation Information screen for the selected transaction
