@IOS " @manual @US_ADBCFL
Feature: Issuing and storing attestations in the EUDI Wallet
  As a EUDI Wallet User
  I want to issue and store attestations in my EUDI Wallet
  So that I can prove to the Relying Parties that I own them when I am requested using my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/112

  @US_ADBCFL_TC_01
  Scenario: Successful navigation to "Home" screen
    Given the user has opened the EUDI Wallet application
    When the user navigates to the “Home” screen
    Then the Home screen should be displayed

  @US_ADBCFL_TC_02
  Scenario: Selecting attestation from the predefined list
    Given the user is on the “Home” screen
    When the user selects the option Choose from list in the Add document section
    Then the wallet should display a predefined list of attestations that the user can issue and add to their EUDI Wallet

  @US_ADBCFL_TC_03
  Scenario: Issuing an attestation successfully
    Given the user is viewing the predefined list of attestations
    When the user selects one attestation to be issued
    And the attestation is successfully issued
    Then the wallet should display a success screen
    And the screen should inform the user about the attestation issued and the issuer who issued it
    And the screen should display a Close button to return to the “Home” screen

  @US_ADBCFL_TC_04
  Scenario: Issuing a PID attestation successfully
    Given the user is viewing the predefined list of attestations
    When the user selects the PID attestation to be issued
    And the PID attestation is successfully issued
    Then the wallet should display a success screen informing the user about the PID attestation issued and the issuer who issued it
    And the screen should display a Go to my EUDI Wallet button to return to the “Home” screen

  @US_ADBCFL_TC_05
  Scenario: Error during attestation issuance
    Given the user is viewing the predefined list of attestations
    When the user selects an attestation to be issued
    And an error occurs during the issuance process
    Then the wallet should display an error screen informing the user about the unsuccessful operation

  @US_ADBCFL_TC_06
  Scenario: Returning to "Home" screen after success
    Given the user is viewing the success screen after an attestation is issued
    When the user clicks on the Close button
    Then the user should be navigated back to the “Home” screen

  @US_ADBCFL_TC_07
  Scenario: Returning to "Home" screen after PID attestation issuance
    Given the user is viewing the additional success screen after the PID attestation is issued
    When the user clicks on the Go to my EUDI Wallet button
    Then the user should be navigated back to the “Home” screen
