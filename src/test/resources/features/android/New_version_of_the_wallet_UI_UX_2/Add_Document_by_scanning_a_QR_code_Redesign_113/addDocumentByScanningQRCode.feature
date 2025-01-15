@ANDROID @manual @US_ADBSQRC
Feature: Issuing and storing attestations in the EUDI Wallet via QR code

  As a EUDI Wallet User
  I want to issue and store attestations in my EUDI Wallet advertised by issuers
  So that I can prove to the Relying Parties that I own them when I am requested using my EUDI Wallet

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/113

  @US_ADBSQRC_TC_01 @manual:InProgress
  Scenario: Successful navigation to the "Home" screen
    Given the user has opened the EUDI Wallet application
    When the user navigates to the “Home” screen
    Then the Home screen should be displayed

  @US_ADBSQRC_TC_02 @manual:InProgress
  Scenario: Selecting "Scan QR to add" option
    Given the user is on the “Home” screen
    When the user selects the option Scan QR to add in the Add document section
    Then the wallet should open the scanner allowing the user to scan the QR code rendered by the Issuer

  @US_ADBSQRC_TC_03 @manual:InProgress
  Scenario: Displaying credential offer after scanning QR code
    Given the user has scanned a valid QR code rendered by the Issuer
    Then the wallet should display the credential offer which includes:
      | Field                    |
      | Attestation to be issued |
      | Name of the issuer       |
      | Cancel button            |
      | Add document button      |

  @US_ADBSQRC_TC_04 @manual:InProgress
  Scenario: Canceling the issuing process
    Given the user is viewing the credential offer
    When the user selects the Cancel button
    Then the issuing process should be canceled
    And the user should be returned to the “Home” screen

  @US_ADBSQRC_TC_05 @manual:InProgress
  Scenario: Proceeding with the attestation issuing flow
    Given the user is viewing the credential offer
    When the user selects the Add document button
    Then the wallet should display a success screen providing details about the attestation issued and added to the wallet
    And the screen should display the issuer who issued the attestation
    And the user should be able to expand the attestation to view the attributes of the attestation
    And the screen should display a Close button to return to the “Home” screen

  @US_ADBSQRC_TC_06 @manual:InProgress
  Scenario: Issuing a PID attestation successfully
    Given the user is viewing the success screen after issuing the PID attestation
    When the user is informed about the PID attestation issued
    Then the wallet should display an additional success screen
    And the screen should display a Go to my EUDI Wallet button to return to the “Home” screen

  @US_ADBSQRC_TC_07 @manual:InProgress
  Scenario: Error during attestation issuance
    Given the user has scanned a QR code
    When the issuance process encounters an error
    Then the wallet should display an error screen informing the user about the unsuccessful operation
