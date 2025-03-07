@ANDROID @manual @US_ADBCFL @Q4_2024
Feature: Issuing and storing attestations in the EUDI Wallet
  As a EUDI Wallet User
  I want to issue and store attestations in my EUDI Wallet
  So that I can prove to the Relying Parties that I own them when I am requested using my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/112

  @US_ADBCFL_TC_01 @manual:Passed
  Scenario: Successful navigation to "Home" screen
    Given the user is on the Home screen
    When the user navigates to the Documents screen
    Then the Documents screen is displayed

  @US_ADBCFL_TC_02 @manual:Passed
  Scenario: Selecting attestation from the predefined list
    Given the user is on the Documents screen
    When the user selects to add a new document
    And the user selects to add a new document From list
    Then the wallet displays a predefined list of attestations that the user can issue and add to their EUDI Wallet

  @US_ADBCFL_TC_03 @manual:Passed
  Scenario: Issuing an attestation successfully
    Given the user is viewing the predefined list of attestations
    When the user selects one attestation to be issued
    And the attestation is successfully issued
    Then the wallet displays a success screen
    And the screen informs the user about the attestation issued and the issuer who issued it
    And the screen displays a Close button to return to the Home screen

  @US_ADBCFL_TC_04 @manual:Passed
  Scenario: Returning to "Home" screen after success
    Given the user is viewing the success screen after an attestation is issued
    When the user closes the success screen
    Then the user navigates back to the Home screen
