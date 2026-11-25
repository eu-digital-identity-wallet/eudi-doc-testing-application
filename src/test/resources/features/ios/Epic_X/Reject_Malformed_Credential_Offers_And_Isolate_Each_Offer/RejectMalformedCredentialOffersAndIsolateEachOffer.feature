@IOS @US_RMCOAIEO @Q4_2026
Feature: Reject malformed credential offers and isolate each offer
  As a EUDI Wallet User
  I want the wallet to reject malformed credential offers and handle each offer on a separate approval screen
  So that I cannot approve an invalid offer or accidentally approve a different offer than the one I reviewed

  #

  @US_RMCOAIEO_TC_01
  Scenario: A credential offer with a transaction code missing its length is refused
    Given a credential offer is received
    And the offer contains a transaction code
    And the transaction code does not specify its length
    When the wallet processes the offer
    Then the offer is refused
    And the attestation is NOT added to the wallet

  @US_RMCOAIEO_TC_02
  Scenario: The wallet displays the existing transaction-code format error message for a refused offer
    Given a credential offer with a transaction code that does not specify its length is received
    When the wallet refuses the offer
    Then the wallet displays a corresponding error message

  @US_RMCOAIEO_TC_03
  Scenario: A second offer arriving while an offer screen is open closes the first screen and opens the new one
    Given an offer screen for offer A is open
    When a second credential offer B arrives
    Then the offer A screen is closed
    And the new offer screen for offer B is opened
    And only the offer B screen is displayed