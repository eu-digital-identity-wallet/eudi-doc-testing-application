@IOS @manual @US_SATQTSP
Feature: Authenticate to QTSPs using EUDI Wallet
  As a EUDI Wallet User,
  I want to use my EUDI Wallet to authenticate to QTSPs,
  So that I can be securely identified by the QTSP which provides the signing service.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/90

  @US_SATQTSP_TC_01
  Scenario: User Selects "Sign Document" in EUDI Wallet
    Given the user has a valid EUDI Wallet with the document to be signed
    When the user selects the Sign Document option in the EUDI Wallet
    Then EUDI Wallet should prompt the user to select a QTSP from a preloaded list of available QTSPs

  @US_SATQTSP_TC_02
  Scenario: User Selects Preferred QTSP
    Given the EUDI Wallet displays a list of QTSPs
    When the user selects a preferred QTSP from the list
    Then EUDI Wallet should navigate the user to the selected QTSP login page using a mobile web browser on the same device

  @US_SATQTSP_TC_03
  Scenario: QTSP Provides a Deep Link for Attestation Presentation
    Given the user is on the QTSP login page in the mobile web browser
    When the QTSP provides a deep link requesting the user to proceed to attestation presentation
    Then the user should be able to click the deep link
    And deep link should redirect the user back to the EUDI Wallet

  @US_SATQTSP_TC_04
  Scenario: EUDI Wallet Requests Attestation Presentation for QTSP
    Given the user is redirected to the EUDI Wallet via the QTSPs deep link
    When the EUDI Wallet presents a screen informing the user that the QTSP requests to release the matching attestation (PID)
    Then screen should display the name of the QTSP and request the user's consent to release the attestation

  @US_SATQTSP_TC_05
  Scenario: No Matching Attestations Available
    Given the user is prompted to release the attestation to the QTSP
    And the EUDI Wallet checks for available matching attestations
    When there are no available matching attestations
    Then the EUDI Wallet should inform the user
    And user should be able to select Abort operation to return to the main page of the EUDI Wallet

  @US_SATQTSP_TC_06
  Scenario: User Consents to Release Attestation
    Given the EUDI Wallet has found a matching attestation for the QTSPs request
    When the EUDI Wallet asks for the user's consent to release the attestation
    And the user authenticates by entering the 6-digit PIN
    Then  EUDI Wallet should release the attestation to the QTSP

  @US_SATQTSP_TC_07
  Scenario: Unsuccessful Authentication in EUDI Wallet
    Given the user is requested to authenticate for attestation release
    When the user enters an incorrect PIN
    Then the EUDI Wallet should display an error message
    And user should be given the option to retry the authentication

  @US_SATQTSP_TC_08
  Scenario: User Aborts the Attestation Release Operation
    Given the user is asked to authenticate and release the requested attestation
    When the user decides not to proceed
    Then the user should be able to select the Abort operation option
    And EUDI Wallet should return the user to the main page of the Wallet

  @US_SATQTSP_TC_09
  Scenario: EUDI Wallet Presents Attestation to QTSP
    Given the user has successfully authenticated and provided consent
    When the EUDI Wallet presents the requested attestation to the QTSP
    Then QTSP should receive and verify the attestation

  @US_SATQTSP_TC_10
  Scenario: QTSP Successfully Verifies Attestation
    Given the QTSP receives the attestation from the EUDI Wallet
    When the QTSP successfully verifies the attestation
    Then the QTSP should inform the user of the successful verification
    And EUDI Wallet should display a confirmation message indicating the presentation outcome

  @US_SATQTSP_TC_11
  Scenario: QTSP Fails to Verify Attestation
    Given the QTSP receives the attestation from the EUDI Wallet
    When the QTSP fails to verify the attestation
    Then the QTSP should display an error message informing the user of the verification failure
    And EUDI Wallet should display a message indicating the failed presentation


