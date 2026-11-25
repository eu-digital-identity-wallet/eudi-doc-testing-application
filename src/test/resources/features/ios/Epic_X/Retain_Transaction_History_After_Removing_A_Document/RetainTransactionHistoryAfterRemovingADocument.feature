@IOS @US_RTHARAD @Q4_2026
Feature: Retain transaction history after removing a document
  As a EUDI Wallet User
  I want transaction history for a removed document to remain available
  So that the wallet preserves the legally required record of my past document-sharing activity

  #

  @US_RTHARAD_TC_01
  Scenario: Removing a document removes it from the wallet documents list
    Given a document exists in the wallet
    When the user removes the document
    Then the document no longer appears in the wallet documents list
    And the document is no longer offerable or reissuable

  @US_RTHARAD_TC_02
  Scenario: Removing a document does not affect other documents in the wallet
    Given the wallet contains two documents: document A and document B
    When the user removes document A
    Then document B remains in the wallet documents list
    And document B remains offerable and reissuable

  @US_RTHARAD_TC_03
  Scenario: Transaction-history entries remain after the document is removed
    Given a document with sharing transactions exists in the wallet
    When the user removes the document
    Then the document's transaction-history entries are NOT deleted
    And the transaction entries remain in the wallet transaction-history screen

  @US_RTHARAD_TC_04
  Scenario: Uninstalling the wallet clears the transaction history of a removed document
    Given a document with sharing transactions was removed from the wallet
    When the user uninstalls the wallet app
    And reinstalls it and completes onboarding
    Then the transaction-history entries of the removed document are NOT restored