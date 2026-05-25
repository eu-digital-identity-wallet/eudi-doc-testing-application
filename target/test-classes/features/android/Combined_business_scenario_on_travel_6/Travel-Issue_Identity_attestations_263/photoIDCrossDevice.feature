@ANDROID @manual @US_PIDCD
Feature: Issue photo ID attestation (cross device)
  As a user of the EUDI Wallet
  I want to request and store photo ID attestations in my EUDI Wallet
  So that I can identify myself using my EUDI Wallet in my travel user journey by presenting the requested attestations

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/263

  @US_PIDCD_TC_01 @manual:Passed
  Scenario: QR Code generation for Photo ID Attestation
    Given the user visits the issuer service on their desktop PC
    When the user requests to issue the photo ID Attestation
    Then the issuer service provides a QR code

  @US_PIDCD_TC_02 @manual:Passed
  Scenario: Opening the EUDI Wallet and viewing the dashboard
    Given the issuer service has provided the QR code
    When the user opens the EUDI Wallet app
    And the user enters their PIN correctly
    Then the dashboard page should be displayed

  @US_PIDCD_TC_03 @manual:Passed
  Scenario: Navigating to the 'Add document' page
    Given the user is on the dashboard page of the EUDI Wallet app
    When the user clicks on the Add doc button
    Then the Add document page should be displayed

  @US_PIDCD_TC_04 @manual:Passed
  Scenario: Initiating the QR code scanning for document addition
    Given the user is on the Add document page
    When the user clicks on the Scan QR option
    Then the QR code scanner should be activated

  @US_PIDCD_TC_05 @manual:Passed
  Scenario: Scanning the QR code and viewing credential details
    Given the QR code scanner is activated
    When the user scans a QR code from the issuer service
    Then the details of the credential to be issued should be displayed including the type of credential and the issuer name

  @US_PIDCD_TC_06 @manual:Passed
  Scenario: Issuing the new document via QR code
    Given the user is presented with the credential details to be issued
    When the user presses on the ISSUE button
    Then the user should be redirected to the issuer service for authentication and consent

  @US_PIDCD_TC_07 @manual:Passed
  Scenario: Completing the issuance process and viewing the success message
    Given the user is on the issuer's service page and has authenticated
    When the user consents to the issuance and provides their personal details into the form
    Then the user should be redirected back to the EUDI Wallet app
    And a success message should appear

  @US_PIDCD_TC_08 @manual:Passed
  Scenario: Viewing the newly added document on the dashboard
    Given the user sees the success message in the EUDI Wallet app
    When the user presses on the CONTINUE button
    Then the PhotoID should be presented in the dashboard screen
