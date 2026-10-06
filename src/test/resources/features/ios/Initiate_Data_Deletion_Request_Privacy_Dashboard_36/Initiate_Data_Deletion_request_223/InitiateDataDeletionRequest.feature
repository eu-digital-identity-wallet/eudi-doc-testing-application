@IOS @US_IDDR @Q3_2026
Feature: Initiate a data deletion request
  As a EUDI Wallet User
  I want to initiate a data deletion request to Relying Parties that have received my personal data,
  So that I can exercise my GDPR rights and maintain control over my personal data

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/223

  @US_IDDR_TC_01 @manual:Passed
  Scenario: Display Presentation Information
    Given the authenticated user has selected a presentation transaction from the Wallet History
    When the Presentation Information screen is displayed
    Then the Wallet displays the intended use of the presentation request
    And the Wallet displays the applicable privacy policy when available
    And the Wallet displays the attributes shared during the presentation transaction

  @US_IDDR_TC_02 @manual:Passed
  Scenario: Display Initiate Deletion Request button
    Given the authenticated user has selected a presentation transaction from the Wallet History
    When the user views the Presentation Information screen
    Then the Wallet displays the Initiate Deletion Request button

  @US_IDDR_TC_03 @manual:Passed
  Scenario: Display informational screen before leaving the Wallet
    Given user views the presentation information screen
    When the user selects the Initiate Deletion Request button
    Then the Wallet displays an informational screen explaining that the user is about to leave the Wallet
    And explains that the Wallet facilitates the initiation of the request by opening the appropriate communication channel
    And states that the Wallet does not submit, manage or track the request
    And states that the user is responsible for completing and submitting the request outside the Wallet
    And states that the Relying Party is responsible for evaluating and processing the request

  @US_IDDR_TC_04 @manual:Passed
  Scenario: Display single action button for Website communication method
    Given the WRPRC contains a website for the Relying Party
    When the Wallet displays the informational screen
    Then the Wallet displays a single action button Continue to [Relying Party]'s website

  @US_IDDR_TC_05 @manual:Passed
  Scenario: Use Website as the preferred communication method
    Given the WRPRC contains a website for the Relying Party
    When the user selects the Continue to [Relying Party]'s website button
    Then the Wallet opens the Relying Party website in the device's default browser

  @US_IDDR_TC_06 @manual:Passed
  Scenario: Return to Presentation Information using Back navigation
    Given the user is viewing the informational screen
    When the user selects the Back navigation button
    Then the Wallet returns to the Presentation Information screen

  @US_IDDR_TC_07 @manual:Passed
  Scenario: Record data deletion request initiation
    Given the user has initiated a data deletion request
    When the selected external application is successfully launched
    Then the Wallet records the data deletion request initiation

  @US_IDDR_TC_08 @manual:Passed
  Scenario: Disable deletion request when contact information cannot be retrieved
    Given contact information are not available in the WRPRC
    When contact information cannot be retrieved from the WRPRC
    Then the data deletion request cannot be initiated
    And the Initiate Deletion Request button in is disabled
