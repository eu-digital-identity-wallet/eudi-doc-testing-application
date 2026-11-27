@IOS @US_PDRAAFRS @Q4_2026
Feature: Prevent duplicate responses after a failed remote share
  As a EUDI Wallet User
  I want closing a failed remote sharing request to end the request without sending an additional response
  So that the relying party receives only the original response and my transaction history remains accurate

  #

  @US_PDRAAFRS_TC_01
  Scenario: Verify available actions after a remote sharing response fails
    Given the wallet is unlocked
    And a valid remote sharing request has been received
    And the remote sharing request screen is displayed
    And the wallet contains documents eligible for the request
    And network connectivity is unavailable
    When the user selects Share
    And the remote sharing response fails to send
    Then the remote sharing error screen is displayed
    And the "Try again" action is available
    And the close action is available
    And both actions are enabled

  @US_PDRAAFRS_TC_02
  Scenario: Verify navigation after closing the remote sharing error screen
    Given the wallet is unlocked
    And a remote sharing response has failed to send
    And the remote sharing error screen is displayed
    When the user selects the close action
    Then the error screen is closed
    And the original remote sharing request screen is displayed
    And the remote sharing request remains available

  @US_PDRAAFRS_TC_03
  Scenario: Verify that Back closes a previously failed remote sharing request
    Given the wallet is unlocked
    And a remote sharing response has failed to send
    And the remote sharing error screen is displayed
    When the user selects the close action
    And the remote sharing request screen is displayed
    And the user selects Back
    Then the remote sharing request is closed
    And the remote sharing request screen is no longer displayed
    And the previous remote sharing request is no longer active

  @US_PDRAAFRS_TC_04
  Scenario: Verify that the transaction-history result remains unchanged after closing a failed remote sharing request
    Given the wallet is unlocked
    And a remote sharing response has failed to send
    And the existing transaction-history result for the request has been recorded for comparison
    And the remote sharing error screen is displayed
    When the user selects the close action
    And the remote sharing request screen is displayed
    And the user selects Back
    And the user navigates to the transaction history
    Then the existing transaction-history entry remains available
    And the transaction-history result matches the previously recorded result
    And the existing result is not overwritten as a result of selecting Back
    And the transaction is not incorrectly recorded as declined

  @US_PDRAAFRS_TC_05
  Scenario: Verify that Share remains available after a remote sharing failure
    Given the wallet is unlocked
    And a remote sharing response has failed to send
    And the remote sharing error screen is displayed
    When the user selects the close action
    Then the original remote sharing request screen is displayed
    And the Share action is available
    And the Share action is enabled
    And the user can attempt to share the requested documents again

  @US_PDRAAFRS_TC_06
  Scenario: Verify successful remote sharing using Share after a previous failure
    Given the wallet is unlocked
    And a valid remote sharing request has been received
    And the original remote sharing response has failed due to unavailable network connectivity
    And the original response has not reached the relying party
    And the remote sharing error screen is displayed
    When the user selects the close action
    And the original remote sharing request screen is displayed
    And network connectivity is restored
    And the user selects Share
    Then the wallet attempts to send the sharing response again
    And the response is successfully delivered to the relying party
    And the response corresponds to the original remote sharing request
    And the remote sharing process completes successfully




