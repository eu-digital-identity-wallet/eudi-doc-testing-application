@ANDROID @manual @US_ADBSQRC
Feature: Issuing and storing attestations in the EUDI Wallet via QR code

  As a EUDI Wallet User
  I want to issue and store attestations in my EUDI Wallet advertised by issuers
  So that I can prove to the Relying Parties that I own them when I am requested using my EUDI Wallet

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/113

  @US_ADBSQRC_TC_01 @manual:Passed
  Scenario: Successful navigation to the "Home" screen
    Given the user has opened the EUDI Wallet application
    When the user navigates to the Home screen
    Then Home screen should be displayed

  @US_ADBSQRC_TC_02 @manual:Passed
  Scenario: Successful navigation to the "Add Document" screen
    Given the user has clicked the option Documents
    When the user selects to add the document plus icon
    Then Add document screen should be displayed

  @US_ADBSQRC_TC_03 @manual:Passed
  Scenario: Selecting Scan a QR code option
    Given the user is on the Add document screen
    When the user selects the option Scan a QR code in the Add document screen
    Then the wallet should open the scanner allowing the user to scan the QR code rendered by the Issuer

  @US_ADBSQRC_TC_04 @manual:Passed
  Scenario Outline: Displaying credential offer after scanning QR code
    Given the user has scanned a valid QR code rendered by the Issuer
    Then the wallet should display the credential offer which <includes>:
    Examples:
      | includes                 |
      | Attestation to be issued |
      | Name of the issuer       |
      | Cancel button            |
      | Add document button      |
      |                          |

  @US_ADBSQRC_TC_05 @manual:Passed
  Scenario: Canceling the issuing process
    Given the user is viewing the credential offer
    When the user selects the Cancel button
    Then the issuing process should be canceled
    And the user should be returned to the Documents screen

  @US_ADBSQRC_TC_06 @manual:Passed
  Scenario: Proceeding with the attestation issuing flow
    Given the user is viewing the credential offer
    When the user selects the Add document button
    Then the user proceeds with the attestation (document) issuing flow

  @US_ADBSQRC_TC_07 @manual:Passed
  Scenario: Issuing a PID attestation successfully
    Given the user proceeds with the attestation (document) issuing flow
    When the user is viewing the success screen after issuing the PID attestation
    Then the wallet should display a success screen providing details about the attestation issued and added to the wallet
    And the screen should display the issuer who issued the attestation
    And the user should be able to expand the attestation to view the attributes of the attestation
    And the screen should display a Close button to return to Home screen
    And the attestation should be added to the wallet

  @US_ADBSQRC_TC_08 @manual:Passed
  Scenario: Error during attestation issuance
    Given the user has scanned a QR code
    When the issuance process encounters an error
    Then the wallet should display an error screen informing the user about the unsuccessful operation
