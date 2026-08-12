@IOS @US_WWWUWPRCVF @Q3_2026
Feature: Warning when the provider's registration certificate cannot be validated
  As a Wallet user,
  I want to be clearly warned if the provider's registration certificate could not be validated,
  So that I can make an explicit and informed decision about whether to proceed with receiving the attestation or PID.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/310

  Background:
    Given the user is registered to a valid EUDI Wallet on their device
    And a Provider's Access Certificate is available
    And the issuer metadata includes a WRPRC
    And the Wallet automatically validates the WRPRC before displaying any issuance approval screen

  @US_WWWUWPRCVF_TC_01 @manual:Passed
  Scenario: User requests to add a document and is redirected to the provider's service
    Given the user has opened the Wallet app and authenticated successfully using PIN or biometrics
    When the user selects Documents and then adds a document
    Then the Wallet redirects the user to the Provider's service, which sends the offer

  @US_WWWUWPRCVF_TC_02 @manual:Failed
  Scenario: Issuance approval screen is not displayed when WRPRC validation fails
    Given the provider has sent an offer containing issuer metadata with a WRPRC
    When the WRPRC validation fails
    Then the Wallet does not display the issuance approval screen
    And the Wallet displays a blocker stating that the provider could not be validated

  @US_WWWUWPRCVF_TC_03 @manual:Passed
  Scenario: User cancels from the blocker and returns to the Home screen
    Given the blocker stating that the provider could not be validated is displayed
    When the user selects Cancel
    Then the Wallet returns the user to the issuance initiation request page