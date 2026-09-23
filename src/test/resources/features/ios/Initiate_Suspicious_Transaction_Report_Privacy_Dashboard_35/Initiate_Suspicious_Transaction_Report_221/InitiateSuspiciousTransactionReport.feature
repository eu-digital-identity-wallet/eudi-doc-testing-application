@IOS @US_ISTR @Q3_2026
Feature: Initiate a report for an unlawful or suspicious data request
  As an EUDI Wallet User
  I want to initiate a report of an unlawful or suspicious data request made by a Relying Party
  So that I can protect my privacy rights and help authorities identify potential violations

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/221

  @US_ISTR_TC_01
  Scenario: View Presentation Information from History
    Given the authenticated user has presentation transactions in the Wallet History
    When the user selects a presentation transaction from the History screen
    Then the Wallet displays the Presentation Information for the selected transaction

  @US_ISTR_TC_02
  Scenario: Display Presentation Information details
    Given the user has selected a presentation transaction from the History screen
    When the Presentation Information screen is displayed
    Then the Wallet displays the intended use of the presentation request
    And the Wallet displays the applicable privacy policy
    And the Wallet displays the attributes shared during the presentation transaction

  @US_ISTR_TC_03
  Scenario: Display the "Initiate Transaction Report" button for a successful presentation transaction
    Given the user selects a presentation transaction
    When the user views the Presentation Information screen
    Then the Wallet displays the Initiate Transaction Report button

  @US_ISTR_TC_04
  Scenario: Open the transaction report information screen
    Given the user is viewing the Presentation Information for a presentation transaction
    When the user selects the Initiate Transaction Report button
    Then the Wallet displays the transaction report information screen

  @US_ISTR_TC_05
  Scenario: Display transaction report information
    Given the transaction report information screen has been opened
    When the screen content is displayed
    Then the Wallet identifies the responsible Data Protection Authority
    And the Wallet informs the user that the Wallet does not submit or track the report
    And the Wallet informs the user that the report must be completed directly with the identified Data Protection Authority

  @US_ISTR_TC_06
  Scenario: Display all available communication methods
    Given the responsible Data Protection Authority provides website, email, and phone contact information
    When the Wallet displays the communication methods
    Then the Wallet displays Website, Email, and Phone as available options

  @US_ISTR_TC_07
  Scenario: Open the DPA reporting website
    Given the Website communication method is available
    When the user selects the Website communication method
    Then the Wallet opens the DPA reporting website in the device's default browser

  @US_ISTR_TC_08
  Scenario: Open the default email application
    Given the Email communication method is available
    When the user selects the Email communication method
    Then the Wallet opens the device's default email application with a pre-filled email
    And the pre-filled email contains the Data Protection Authority email address
    And the pre-filled email subject indicates an allegedly unlawful or suspicious presentation request
    And the pre-filled email content identifies the relevant Relying Party

  @US_ISTR_TC_09
  Scenario: Open the phone application with the DPA telephone number
    Given the Phone communication method is available
    When the user selects the Phone communication method
    Then the Wallet opens the device's phone application using the DPA telephone number

  @US_ISTR_TC_10
  Scenario: Record the transaction report initiation after successful external application launch
    Given the user has selected an available communication method
    When the corresponding external application is successfully launched
    Then the Wallet records the transaction report initiation

  @US_ISTR_TC_11
  Scenario: Return to Presentation Information using Back navigation
    Given the user is viewing the transaction report information screen
    When the user selects the Back navigation option
    Then the Wallet returns to the Presentation Information screen

  @US_ISTR_TC_12
  Scenario: Return to Presentation Information using the Close button
    Given the user is viewing the transaction report information screen
    When the user selects the Close button
    Then the Wallet returns to the Presentation Information screen

  @US_ISTR_TC_13
  Scenario: Display an error when the selected external application cannot be launched
    Given the selected communication method cannot launch its corresponding external application
    When the user selects the communication method
    Then the Wallet displays an appropriate error message

  @US_ISTR_TC_14
  Scenario: Do not record a transaction report attempt when the external application cannot be launched
    Given the selected external application cannot be launched
    When the external application launch fails
    Then the Wallet does not record the transaction report attempt
