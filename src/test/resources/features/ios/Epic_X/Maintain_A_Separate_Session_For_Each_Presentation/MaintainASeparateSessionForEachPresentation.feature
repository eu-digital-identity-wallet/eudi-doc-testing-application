@IOS @US_MASSFEP @Q4_2026
Feature: Maintain a separate session for each presentation
  As a EUDI Wallet User
  I want every presentation request to use its own session
  So that one request cannot interfere with, replace, or mix data with another presentation

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/425

  @US_MASSFEP_TC_01
  Scenario: Verify session isolation between different presentation types
    Given the Wallet is unlocked
    And the wallet contains documents eligible for both presentation requests
    When the user initiates a presentation
    And the presentation is completed
    And the user initiates another presentation
    Then the second presentation uses a session different from the first presentation
    And the second session contains only data associated with the second presentation request
    And no request-specific data from the first presentation is carried over to the second session

  @US_MASSFEP_TC_02
  Scenario: Verify that overlapping presentation requests remain isolated
    Given the Wallet is unlocked
    And a presentation is active
    And the presentation request contains identifiable request-specific data
    When a second presentation request is received
    And the second request contains different request-specific data
    And data from the two presentation requests is not mixed
    And the second presentation request does not reuse the first presentation session

  @US_MASSFEP_TC_03
  Scenario: Verify that locking the wallet terminates an active presentation
    Given the Wallet is unlocked
    And a presentation is active
    When the wallet becomes locked
    Then the active presentation is terminated
    And the associated presentation session is no longer active
    And the interrupted session cannot continue the document presentation

  @US_MASSFEP_TC_04
  Scenario: Verify dashboard navigation after a remote presentation is interrupted by a wallet lock
    Given the Wallet is unlocked
    And a remote presentation is active
    When the wallet becomes locked
    And the user successfully logs in to the wallet again
    Then the wallet dashboard is displayed
    And the user is not redirected to the interrupted remote presentation

  @US_MASSFEP_TC_05
  Scenario: Verify silent presentation termination due to a wallet lock
    Given the Wallet is unlocked
    And a presentation is active
    When the wallet becomes locked
    And the user successfully logs in to the wallet again
    Then the previous presentation is no longer active
    And no message related to the presentation termination is displayed when the wallet locks
    And no message related to the presentation termination is displayed after login

