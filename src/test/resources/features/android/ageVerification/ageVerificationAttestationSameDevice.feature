@ANDROID @manual @US_AVASD
Feature: Age verification attestation same device

  @US_AVASD_TC_01
  Scenario: Age Verification via EUDI Wallet
    Given the user visits the Relying Party service on their mobile device
    When the user selects to verify the age limit with the EUDI Wallet
    Then the Relying Party service redirects the user to the EUDI Wallet

  @US_AVASD_TC_02
  Scenario: Redirect to EUDI Wallet for successful authentication
    Given the Relying Party service redirects the user to the EUDI Wallet
    When the user authenticates successfully in the EUDI Wallet
    Then the EUDI Wallet presents a screen to inform the user about the Age Verification Attestation request

  @US_AVASD_TC_03
  Scenario: Inform user about Age Verification Attestation request
    Given the EUDI Wallet presents a screen to inform the user about the Age Verification Attestation request
    When the user views the Age Verification Attestation to be presented
    Then the user clicks on the 'Share' button

  @US_AVASD_TC_04
  Scenario: User authorization for attestation release
    Given the EUDI Wallet user clicks on the share button
    When a authentication page appears
    Then the wallet app prompts the user to enter the PIN

  @US_AVASD_TC_05
  Scenario: User enters incorrect PIN
    Given the wallet app prompts the user to enter the PIN
    When the user enters the incorrect PIN
    Then the wallet app displays a corresponding error message

  @US_AVASD_TC_06
  Scenario: User re-enters correct PIN
    Given the wallet app displays a corresponding error message
    When the user re-enters the correct PIN
    Then the wallet app displays a success message
    And the user clicks on the 'Continue' button

  @US_AVASD_TC_06
  Scenario: Relying Party receives age verification attestation
    Given the user has clicked on the 'Continue' button
    When the user is redirected to the relying party
    Then the Relying Party service presents the data from the EUDI wallet