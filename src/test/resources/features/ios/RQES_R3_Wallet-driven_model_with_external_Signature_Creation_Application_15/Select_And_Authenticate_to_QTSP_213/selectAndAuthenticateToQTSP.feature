@IOS @manual @US_SATQTSP @Q4_2024
Feature: Authenticate to QTSPs using EUDI Wallet
  As a EUDI Wallet User,
  I want to use my EUDI Wallet to authenticate to QTSPs,
  So that I can be securely identified by the QTSP which provides the signing service.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/213

  @US_SATQTSP_TC_01 @manual:Passed
  Scenario: User Selects "Sign Document" in EUDI Wallet
    Given the user has a valid EUDI Wallet with the document to be signed
    When the user selects the Sign Document option in the EUDI Wallet
    Then EUDI Wallet should prompt the user to select a QTSP from a preloaded list of available QTSPs

  @US_SATQTSP_TC_02 @manual:Passed
  Scenario: User Selects Preferred QTSP
    Given the EUDI Wallet displays a list of QTSPs
    When the user selects a preferred QTSP from the list
    Then the EUDI Wallet presents a screen to inform the user that the QTSP requests to release the matching attestation

  @US_SATQTSP_TC_03 @manual:Passed
  Scenario: User consents to release attestation
    Given the user has selected a QTSP from the list
    When the EUDI Wallet requests the user to consent to the release of the requested attestation
    And the user authenticates successfully in the Wallet, e.x. Share and PIN
    Then the EUDI Wallet presents the requested attestation to the QTSP


