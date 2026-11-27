@IOS @US_SPSWTULTW @Q4_2026
Feature: Stop proximity sharing when the user leaves the wallet
  As a EUDI Wallet User
  I want an active proximity-sharing session to stop when I leave the QR-code screen or move the wallet to the background
  So that an old QR code cannot be scanned or used after I am no longer actively presenting it

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/426

  @US_SPSWTULTW_TC_01
  Scenario: Verify that proximity sharing stops when the wallet moves to the background
    Given the Wallet is unlocked
    And the user has initiated a proximity presentation
    And the proximity QR code is displayed
    And no reader has taken over the proximity-sharing session
    When the user moves the wallet application to the background
    Then the proximity-sharing session is terminated
    And the associated QR code is no longer valid for document sharing

  @US_SPSWTULTW_TC_02
  Scenario: Verify that leaving the proximity QR-code screen terminates the session
    Given the Wallet is unlocked
    And the user has initiated a proximity presentation
    And the proximity QR code is displayed
    And no reader has taken over the proximity-sharing session
    When the user navigates away from the QR-code screen
    Then the proximity-sharing session is terminated
    And the QR code associated with the terminated session is no longer valid for document sharing
    And the Home screen is displayed
    And no message related to the proximity-sharing session termination is displayed

  @US_SPSWTULTW_TC_03
  Scenario: Verify that opening Control Center does not terminate proximity sharing
    Given the Wallet is unlocked
    And the user has initiated a proximity presentation
    And the proximity QR code is displayed
    When the user pulls down Control Center over the QR-code screen
    Then the proximity-sharing session remains active
    And the proximity-sharing session is not terminated due to opening Control Center
