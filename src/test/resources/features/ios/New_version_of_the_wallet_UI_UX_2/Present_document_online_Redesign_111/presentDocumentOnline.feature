@IOS @manual @US_PDO
Feature: Present Attestations from EUDI Wallet
  As a EUDI Wallet User
  I want to be able to present attestations from my EUDI Wallet
  So that I can authenticate, authorise transactions and present personal information upon requests of a Relying Party in remote scenarios

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/111

  @US_PDO_TC_01 @manual:Passed
  Scenario: User selects the option ‘Online’ in the “Present document” section
    Given the user is on the Home page
    When the user selects the Online option in the Authenticate section
    Then the wallet opens the scanner

  @US_PDO_TC_02 @manual:Passed
  Scenario: Wallet opens the scanner to scan QR-code
    Given the user has selected the Online option in the Present document section
    When the user scans a QR code rendered by a Relying Party
    Then the wallet displays the presentation request

  @US_PDO_TC_03 @manual:Passed
  Scenario Outline: Wallet displays the presentation request
    Given the user has scanned a QR code rendered by a Relying Party
    When the wallet displays the presentation request
    Then the presentation request <includes>:
    Examples:
      | includes                                                 |
      | The name of the requesting Relying Party                 |
      | The attestations requested by the Relying Party          |
      | The option to expand on the details for each attestation |
      | The option to unselect specific data elements            |
      | A back button                                            |
      | A Share button                                           |

  @US_PDO_TC_04 @manual:Passed
  Scenario: User cancels the data sharing process
    Given the user is viewing the presentation request
    When the user selects the back button
    Then the user is redirected to the Home screen

  @US_PDO_TC_05 @manual:Passed
  Scenario: User proceeds with the data sharing flow
    Given the user is viewing the presentation request
    When the user selects the Share button
    Then the wallet requests the user to enter the PIN to proceed

  @US_PDO_TC_06 @manual:Passed
  Scenario: User enters the PIN to proceed with data sharing
    Given the user has selected the Share button
    When the wallet requests the user to enter the PIN
    And the user enters the correct PIN on screen
    Then the wallet displays a success screen

  @US_PDO_TC_07 @manual:Passed
  Scenario: Wallet displays a success screen after data sharing
    Given the user has entered the correct PIN
    When the data sharing process is successful
    Then the wallet displays a success screen
    And the success screen provides details about the data that were shared
    And the success screen includes a Close button

  @US_PDO_TC_08 @manual:Passed
  Scenario: User closes the success screen
    Given the user is viewing the success screen after data sharing
    When the user selects the Close button
    Then the user is redirected to the Home screen
