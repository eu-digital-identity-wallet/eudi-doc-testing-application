@IOS @manual @US_COCD
Feature: Credential issuance process for cross device
  As a user of the issuer service
  I want to be able to issue a credential
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-reference-implementation-epics/issues/25

  @US_COCD_TC_01 @manual:Passed
  Scenario: Opening the EUDI Wallet and viewing the dashboard
    Given the user has the EUDI Wallet app installed
    When the user opens the EUDI Wallet app
    And the user enters their PIN correctly
    Then the dashboard page should be displayed

  @US_COCD_TC_02 @manual:Passed
  Scenario: Navigating to the 'Add document' page
    Given the user is on the dashboard page of the EUDI Wallet app
    When the user clicks on the Add doc button
    Then the Add document page should be displayed

  @US_COCD_TC_03 @manual:Passed
  Scenario: Initiating the QR code scanning for document addition
    Given the user is on the Add document page
    When the user clicks on the Scan QR option
    Then the QR code scanner should be activated

  @US_COCD_TC_04 @manual:Passed
  Scenario: Scanning the QR code and viewing credential details
    Given the QR code scanner is activated
    When the user scans a QR code from the issuer service
    Then the details of the credential to be issued should be displayed including the type of credential and the issuer name

  @US_COCD_TC_05 @manual:Passed
  Scenario: Issuing the new document via QR code
    Given the user is presented with the credential details to be issued
    When the user presses on the ISSUE button
    Then the user should be redirected to the issuer service for authentication and consent

  @US_COCD_TC_06 @manual:Passed
  Scenario: Completing the issuance process and viewing the success message
    Given the user is on the issuer's service page and has authenticated
    When the user consents to the issuance and inserts the credential
    And the credential issuance process completes
    Then the user should be redirected back to the EUDI Wallet app
    And a success message should appear

  @US_COCD_TC_07 @manual:Passed
  Scenario: Viewing the newly added document on the dashboard
    Given the user sees the success message in the EUDI Wallet app
    When the user presses on the CONTINUE button
    Then the new document should be presented in the dashboard screen


