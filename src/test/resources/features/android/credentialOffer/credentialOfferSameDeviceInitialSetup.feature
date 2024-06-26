@ANDROID @manual @US_COSDIS
Feature: Credential issuance on the same device through the issuer service (first-time setup)

  @US_COSDIS_TC_01 @manual:Passed
  Scenario: User initiates credential issuance from issuer service
    When the user opens the issuer service
    And selects to issue a credential
    Then the EUDI Wallet app opens

  @US_COSDIS_TC_02 @manual:Passed
  Scenario: User sets up a PIN in the EUDI Wallet app
    Given the EUDI Wallet app is open
    When the user sets up a PIN successfully
    Then the user is presented with details of the credential to be issued

  @US_COSDIS_TC_03 @manual:Passed
  Scenario: Credential details are displayed to the user
    Given the user has successfully set up a PIN
    When the wallet app presents the details of the credential
    Then the details include the type of credential and issuer name

  @US_COSDIS_TC_04 @manual:Passed
  Scenario: User proceeds with the credential issuance process
    Given the details of the credential are displayed
    When the user presses the ISSUE button
    Then the user is redirected to the issuer service for authentication and consent

  @US_COSDIS_TC_05 @manual:Passed
  Scenario: User authenticates and consents to the issuance at the issuer service
    Given the user is redirected to the issuer service
    When the user authenticates and consents to the issuance of the credential
    Then the credential is inserted into the user's EUDI Wallet
    And the user is redirected back to the EUDI Wallet app

  @US_COSDIS_TC_06 @manual:Passed
  Scenario: User views success message and accesses the new document
    Given the user is redirected back to the EUDI Wallet app after the credential issuance
    When a success message is displayed
    And the user presses the CONTINUE button
    Then the new document is displayed in the dashboard screen of the EUDI Wallet app
