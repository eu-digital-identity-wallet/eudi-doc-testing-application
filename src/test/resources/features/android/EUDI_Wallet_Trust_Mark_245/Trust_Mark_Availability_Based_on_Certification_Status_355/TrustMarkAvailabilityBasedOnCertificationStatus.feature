@ANDROID @US_TMABOCS @Q3_2026
Feature: Display Trust Mark based on Wallet Solution certification status
  As a EUDI Wallet User
  I want the Trust Mark displayed in the Wallet to reflect the certification status of the Wallet Solution,
  so that I can be confident that the wallet I am using is officially certified.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/355

  @US_TMABOCS_TC_01
  Scenario: Display Trust Mark for a valid certified Wallet Solution
    Given the Wallet Solution is certified and recognized by at least one Member State
    When the user accesses the Trust Mark functionality
    Then the Wallet displays the Trust Mark

  @US_TMABOCS_TC_02
  Scenario: Do not display Trust Mark for an uncertified Wallet Solution
    Given the Wallet Solution has not yet been certified
    When the user accesses the Wallet
    Then the Wallet does not display the Trust Mark

  @US_TMABOCS_TC_03
  Scenario: Do not display Trust Mark when certification is no longer valid
    Given the Wallet Solution certification is no longer valid or has been revoked
    When the user accesses the Wallet
    Then the Wallet does not display the Trust Mark

  @US_TMABOCS_TC_04
  Scenario: Do not display Trust Mark when the Wallet Solution is no longer recognized
    Given the Wallet Solution is no longer recognized by any Member State
    When the user accesses the Wallet
    Then the Wallet does not display the Trust Mark

  @US_TMABOCS_TC_05
  Scenario: Display certification information when Trust Mark is available
    Given the Trust Mark is displayed for the Wallet Solution
    When the user accesses the Trust Mark view
    Then the Wallet displays the associated certification information
    And the Wallet provides the certification-status links

  @US_TMABOCS_TC_06
  Scenario: Hide certification references when Trust Mark is unavailable
    Given the Trust Mark is not displayed for the Wallet Solution
    When the user accesses the Wallet
    Then the Wallet does not provide the associated certification links or references
