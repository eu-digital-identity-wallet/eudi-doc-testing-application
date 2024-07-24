@IOS @manual @US_PACCD
Feature: Pre-authorization code cross device

  @US_PACCD_TC_01 @manual:Passed
  Scenario: User visits the Issuer service
    Given the user visits the Issuer service
    When the user chooses to issue a doc with pre-authorization
    Then the Issuer service creates a QR code and a transaction code

  @US_PACCD_TC_02 @manual:Passed
  Scenario: User is presented with a QR for the issuance process
    Given the issuer has displayed a QR code
    When the user initiates the wallet app
    Then the dashboard screen is displayed

  @US_PACCD_TC_03 @manual:Passed
  Scenario: Wallet app is initiated
    Given the waller app has been initiated
    When the user clicks on the ADD DOC button on the wallet application
    Then the user views the Add document page screen

  @US_PACCD_TC_04 @manual:Passed
  Scenario: Initiating QR Scan from Add Document Screen
    Given the Add document screen is displayed
    When the user clicks on the SCAN QR button
    Then the phone camera opens

  @US_PACCD_TC_05 @manual:Passed
  Scenario: QR code verification
    Given the phone camera has opened
    When the user scans the QR code from the issuer
    Then the details of the request are displayed on the wallet app

  @US_PACCD_TC_06 @manual:Passed
  Scenario: Issuing the new document via QR code
    Given the user is presented with the request details to be issued
    When the user presses on the Issue button
    Then the Wallet app requests the transaction code
    And the user enters the transaction code provided by the Issuer

  @US_PACCD_TC_07 @manual:Passed
  Scenario: Document display
    Given the user enters the transaction code provided by the Issuer
    When the Wallet app displays a success message on screen
    Then the user clicks on the CONTINUE button
    And the doc is displayed in the dashboard screen






