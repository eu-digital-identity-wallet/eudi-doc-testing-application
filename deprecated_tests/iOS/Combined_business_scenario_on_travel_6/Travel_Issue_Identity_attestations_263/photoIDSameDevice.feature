@IOS @manual @US_IPIDSD
Feature: Issue photo ID attestation (same device)
  As a user of the EUDI Wallet
  I want to request and store photo ID attestations in my EUDI Wallet
  So that I can identify myself using my EUDI Wallet in my travel user journey by presenting the requested attestations

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/263

  @US_IPIDSD_TC_01 @manual:Passed
  Scenario: User selects to issue a photo ID
    Given the user is on issuer service
    When the user selects to issue a photo ID
    Then the user is redirected to EUDI Wallet
    And the details of the credential to be issued are presented on the screen

  @US_IPIDSD_TC_02 @manual:Passed
  Scenario: User proceeds with the credential issuance
    Given the user is presented with the credential details on EUDI Wallet
    When the user presses on the ISSUE button
    Then the user is redirected back to issuer
    And the user is prompted to authenticate and consent to issuance

  @US_IPIDSD_TC_03 @manual:Passed
  Scenario: User authenticates and consents to the issuance
    Given the user is asked to authenticate and consent on issuer service
    When the user authenticates and consents to issuance
    And the usser inserts the required credential details
    Then the user is redirected to EUDI Wallet app
    And a success message is displayed on EUDI Wallet app

  @US_IPIDSD_TC_04 @manual:Passed
  Scenario: User views the issued identity in the EUDI Wallet
    Given the user sees a success message in EUDI Wallet app
    When the user presses on the CONTINUE button
    Then the photo ID is presented in the EUDI Wallet dashboard screen
