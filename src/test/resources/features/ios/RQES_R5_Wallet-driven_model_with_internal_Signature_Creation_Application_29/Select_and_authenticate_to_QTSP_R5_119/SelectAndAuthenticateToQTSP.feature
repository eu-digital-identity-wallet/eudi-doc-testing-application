@IOS @US_SAATQTSP @Q3_2025
Feature: Authenticate and authorize EUDI Wallet User with QTSP for signing service
  As a EUDI Wallet User
  I want to use my EUDI Wallet to authenticate to QTSPs
  So that I can be securely authenticated and authorized by the QTSP which provides the signing service

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/119

  @US_SAATQTSP_TC_01 @manual:Passed
  Scenario: User initiates signing process
    Given the user has logged in to the EUDI Wallet application
    When the user selects the Sign Document option
    Then the EUDI Wallet prompts the user to select a QTSP

  @US_SAATQTSP_TC_02 @manual:Passed
  Scenario: User selects QTSP for signing
    Given the EUDI Wallet displays a list of QTSPs to the user
    When the user selects a preferred QTSP
    Then the EUDI Wallet navigates the user to the selected QTSP login page in the mobile browser

  @US_SAATQTSP_TC_03 @manual:Passed
  Scenario: User receives attestation presentation request via deep link
    Given the QTSP login page is displayed to the user
    When the QTSP provides a deep link for attestation presentation of the PID
    Then the deep link redirects the user to the EUDI Wallet

  @US_SAATQTSP_TC_04 @manual:Passed
  Scenario: EUDI Wallet prompts for attestation release
    Given the user is redirected to the EUDI Wallet from the QTSP deep link
    When the EUDI Wallet receives a PID attestation request from the QTSP
    Then the EUDI Wallet informs the user about the request and asks for consent

  @US_SAATQTSP_TC_05 @manual:Passed
  Scenario: User provides consent and authenticates
    Given the EUDI Wallet requests the user to authenticate for attestation release
    When the user enters the correct six-digit PIN
    Then the EUDI Wallet presents the PID attestation to the QTSP

  @US_SAATQTSP_TC_06 @manual:Passed
  Scenario: No matching attestation available
    Given the EUDI Wallet receives a PID attestation presentation request from the QTSP
    When the EUDI Wallet checks for available attestations
    Then the EUDI Wallet informs the user that no matching attestation is available

  @US_SAATQTSP_TC_07 @manual:Passed
  Scenario: QTSP verifies attestation successfully
    Given the QTSP receives the PID attestation from the EUDI Wallet
    When the QTSP verifies the attestation successfully
    Then the QTSP informs the user of successful verification

  @US_SAATQTSP_TC_08 @manual:Passed
  Scenario: User cancels the process
    Given the EUDI Wallet prompts the user for attestation release and authentication
    When the user chooses to cancel or return to the main page
    Then the EUDI Wallet returns the user to the main page

  @US_SAATQTSP_TC_09 @manual:Passed
  Scenario: User fails authentication and retries
    Given the EUDI Wallet prompts the user for PIN authentication
    When the user enters an incorrect PIN
    Then the EUDI Wallet prompts the user to retry authentication
