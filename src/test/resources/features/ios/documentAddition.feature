@IOS @US_DAF @automated
Feature: Initial setup and document addition in wallet

  @US_DAF_TC_01
  Scenario: User launches the EUDI Wallet
    Given the user launches the EUDI Wallet for the first time
    When the user sets up their PIN
    Then the 'Add document' screen is appeared
