@ANDROID @manual @US_DIP
Feature: Dynamic issuance process
  As a user of the issuer service
  I want to be able to dynamically present attestations requested by issuers
  So that I can securely issue credentials from trusted issuers in my EUDI Wallet

  @US_DIP_TC_01 @manual:Passed
  Scenario: User Initiates Credential Issuance
    Given the user visits the Issuer service
    When the user selects to issue a credential
    Then the user is presented with a URL to initiate the EUDI Wallet on the same device

  @US_DIP_TC_02 @manual:Passed
  Scenario: Wallet Initiation and Credential Details Presentation
    Given the user is presented with a URL to initiate the EUDI Wallet
    When the user selects the URL
    Then the Wallet is initiated
    And the user is presented with details of the credentials to be issued (type of credential, issuer name, image)

  @US_DIP_TC_03 @manual:Passed
  Scenario: User Proceeds with Credential Issuance
    Given the user is presented with details of the credentials to be issued
    When the user selects to proceed with the issuance process
    Then the user is redirected to the Issuer service to present their PID

  @US_DIP_TC_04 @manual:Passed
  Scenario: Presentation Request for PID
    Given the user has been redirected to the Issuer service to present their PID
    When the EUDI Wallet displays the presentation request for PID
    Then the user is prompted to consent by selecting the Share button

  @US_DIP_TC_05 @manual:Passed
  Scenario: User Consents to Share PID
    Given the user has been prompted to consent by selecting the Share button
    When the user selects the Share button
    Then the user is prompted to enter their six-digit PIN

  @US_DIP_TC_06 @manual:Passed
  Scenario: Successful PID Presentation
    Given the user has been prompted to enter their six-digit PIN
    When the user enters their six-digit PIN correctly
    Then a success message is displayed for the successful presentation of the PID

  @US_DIP_TC_07 @manual:Passed
  Scenario: Successful Credential Issuance
    Given a success message is displayed for the successful presentation of the PID
    When the user clicks the Continue button
    Then the user views a success message for issuing the document
    And the user views the document on the dashboard which issued based on the PID

