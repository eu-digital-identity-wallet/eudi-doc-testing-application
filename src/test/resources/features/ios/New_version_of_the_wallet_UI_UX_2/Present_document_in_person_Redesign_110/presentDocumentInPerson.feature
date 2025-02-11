@IOS @manual @US_PDIP
Feature: Present Attestations from EUDI Wallet
  As a EUDI Wallet User
  I want to be able to present attestations from my EUDI Wallet
  So that I can authenticate, authorise transactions and present personal information upon requests of a Relying Party in proximity scenarios

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/110

  @US_PDIP_TC_01 @manual:Passed
  Scenario: Select 'In person' in "Present document" section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Authenticate section
    And the user clicks on the In person option
    Then the wallet displays a page which includes a QR code and the option to share via NFC

  @US_PDIP_TC_02 @manual:Passed
  Scenario Outline: Scan QR-code or tap for NFC with reader device and display presentation request
    Given the user is on the screen displaying a QR code and the option to share via NFC
    When the displayed QR code is scanned or the NFC option is tapped with a reader device
    And the reader device scans the QR code
    Then the wallet displays the presentation request which <includes>:
    Examples:
      | includes                                                                      |
      | The name of the requesting Relying Party                                      |
      | The attestations requested by the Relying Party                               |
      | The option to expand on the details for each requested attestation            |
      | The option to unselect specific data elements from each requested attestation |
      | A back button                                                                 |
      | A Share button                                                                |

  @US_PDIP_TC_03 @manual:Passed
  Scenario: Cancel data sharing process
    Given the wallet is displaying the presentation request
    When the user selects the back button
    Then the user is redirected to the Home screen

  @US_PDIP_TC_04 @manual:Passed
  Scenario: Proceed with data sharing flow
    Given the wallet is displaying the presentation request
    When the user selects the Share button
    Then the wallet requests the user to enter the PIN to proceed

  @US_PDIP_TC_05 @manual:Passed
  Scenario: Enter PIN to proceed with data sharing
    Given the user has selected the Share button in the presentation request screen
    When the wallet requests the user to enter the PIN
    And the user enters the correct PIN on screen
    Then the wallet displays a success screen which provides details about the data that were shared from the wallet
    And the success screen includes a Close button

  @US_PDIP_TC_06 @manual:Passed
  Scenario: Return to "Home" screen after successful data sharing
    Given the wallet displays the success screen with details about the data shared
    When the user selects the Close button
    Then the user is redirected to the Home screen

