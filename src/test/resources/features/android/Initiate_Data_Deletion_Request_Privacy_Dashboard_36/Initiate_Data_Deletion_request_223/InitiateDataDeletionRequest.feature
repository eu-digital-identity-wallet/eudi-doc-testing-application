@ANDROID @US_IDDR @Q3_2026
Feature: Initiate a data deletion request
  As a EUDI Wallet User
  I want to initiate a data deletion request to Relying Parties that have received my personal data,
  So that I can exercise my GDPR rights and maintain control over my personal data

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/223

  @US_IDDR_TC_01
  Scenario: Display Presentation Information
    Given the authenticated user has selected a presentation transaction from the Wallet History
    When the Presentation Information screen is displayed
    Then the Wallet displays the intended use of the presentation request
    And the Wallet displays the applicable privacy policy when available
    And the Wallet displays the attributes shared during the presentation transaction

  @US_IDDR_TC_02
  Scenario: Display Initiate Deletion Request button
    Given the authenticated user has selected a presentation transaction from the Wallet History
    When the user views the Presentation Information screen
    Then the Wallet displays the Initiate Deletion Request button

  @US_IDDR_TC_03
  Scenario: Use Website as the preferred communication method
    Given the WRPRC contains a website and other contact information for the Relying Party
    When the user selects the Initiate Deletion Request button
    Then the Wallet opens the Relying Party website in the device's default browser

  @US_IDDR_TC_04
  Scenario: Use Email when Website is not available
    Given the WRPRC contains an email address but no website for the Relying Party
    When the user selects the Initiate Deletion Request button
    Then the Wallet opens the device's default email application with a pre-filled email

  @US_IDDR_TC_05
  Scenario: Use Phone when Website and Email are not available
    Given the WRPRC contains a telephone number but no website or email address for the Relying Party
    When the user selects the Initiate Deletion Request button
    Then the Wallet opens the device's phone application using the Relying Party telephone number

  @US_IDDR_TC_06
  Scenario: Display pre-filled deletion request email
    Given Email is the selected communication method for the data deletion request
    When the default email application is opened
    Then the email contains the Relying Party email address
    And the subject indicates a request for deletion of personal data
    And the email contains template content to assist the user in preparing the request

  @US_IDDR_TC_07
  Scenario: Record data deletion request initiation
    Given the user has initiated a data deletion request
    When the selected external application is successfully launched
    Then the Wallet records the data deletion request initiation

  @US_IDDR_TC_08
  Scenario: Disable deletion request when contact information cannot be retrieved
    Given contact information cannot be retrieved from the WRPRC or National Registry
    When the user views the Presentation Information screen
    Then the Initiate Deletion Request button is disabled

  @US_IDDR_TC_09
  Scenario: Handle external application launch failure
    Given the Wallet cannot launch the external application for the selected communication method
    When the user initiates the data deletion request
    Then the Wallet displays an appropriate error message
    And the Wallet does not record the data deletion request attempt
