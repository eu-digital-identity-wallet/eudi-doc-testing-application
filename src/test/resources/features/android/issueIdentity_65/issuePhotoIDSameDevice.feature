@ANDROID @manual @US_IPIDSD
Feature: Issue photo ID attestation (same device)
  As a user of the EUDI Wallet
  I want to request and store photo ID attestations in my EUDI Wallet
  So that I can identify myself using my EUDI Wallet in my travel user journey by presenting the requested attestations

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/65

  @US_IPIDSD_TC_01 @manual:Passed
  Scenario: User selects to issue a photo ID
    Given the user is on the issuer service
    When the user selects to issue a photo ID
    Then the user is redirected to the EUDI Wallet
    And the details of the credential to be issued are presented

  @US_IPIDSD_TC_02 @manual:Passed
  Scenario: User proceeds with the credential issuance
    Given the user is presented with the credential details on the EUDI Wallet
    When the user presses the ISSUE button
    Then the user is redirected back to the issuer service
    And the user is prompted to authenticate and consent to the issuance

  @US_IPIDSD_TC_03 @manual:Passed
  Scenario: User authenticates and consents to the issuance
    Given the user is asked to authenticate and consent on the issuer service
    When the user authenticates and consents to the issuance
    And inserts the required credential details
    Then the user is redirected to the EUDI Wallet app
    And a success message is displayed on the EUDI Wallet app

  @US_IPIDSD_TC_04 @manual:Passed
  Scenario: User views the issued identity in the EUDI Wallet
    Given the user sees a success message in the EUDI Wallet app
    When the user presses the CONTINUE button
    Then the photo ID is presented in the EUDI Wallet dashboard screen
