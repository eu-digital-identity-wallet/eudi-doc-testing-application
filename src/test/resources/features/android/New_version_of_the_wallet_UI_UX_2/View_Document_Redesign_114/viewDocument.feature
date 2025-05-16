@ANDROID @manual @US_VD @Q4_2024
Feature: Viewing and managing attestations in the EUDI Wallet

  As a EUDI Wallet User
  I want to be able to view my attestations issued in the EUDI Wallet
  So that I can review the details of my issued attestation

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/114

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And attestations are issued in the EUDI Wallet

  @US_VD_TC_01 @manual:Passed
  Scenario: Navigating to the 'Documents' screen
    Given the user is on the Home screen of the EUDI Wallet
    When the user navigates to the Documents screen
    Then the Documents screen should be displayed showing a list of issued attestations

  @US_VD_TC_02 @manual:Passed
  Scenario: Viewing details of an attestation
    Given the user is on the Documents screen
    When the user selects an attestation from the list
    Then the details of the selected attestation should be displayed
    And the details should be blurred by default
    And the user should see the eye icon to view the details of the attestation

  @US_VD_TC_03 @manual:Passed
  Scenario: Viewing attestation details after selecting the 'eye' icon
    Given the user is viewing the details of an attestation
    When the user selects eye icon
    Then the attestation details should no longer be blurred
    And the user should be able to view the full details of the attestation

  @US_VD_TC_04 @manual:Passed
  Scenario: Bookmarking an attestation
    Given the user is viewing the details of an attestation
    And no more than three attestations are currently bookmarked
    When the user selects the bookmark icon
    Then the attestation should be marked as bookmarked
    And the bookmark icon should change to indicate that the attestation is bookmarked

  @US_VD_TC_05 @manual:Failed
  Scenario: Restricting the bookmarking of more than 3 attestations
    Given the user has three bookmarked attestations
    When the user tries to bookmark another attestation
    Then the bookmark icon should be disabled
    And the user should be informed that only up to three attestations can be bookmarked

  @US_VD_TC_06 @manual:Passed
  Scenario: Viewing details of the issuer of the attestation
    Given the user is viewing the details of an attestation
    When the user navigates to the issuer details
    Then the details of the issuer should be displayed

  @US_VD_TC_07 @manual:Failed
  Scenario: Viewing latest transactions involving the attestation
    Given the user is viewing the details of an attestation
    When the user selects the option to view the latest transactions
    Then the latest transactions involving the attestation should be displayed

  @US_VD_TC_08 @manual:Passed
  Scenario: Deleting an attestation
    Given the user is viewing the details of an attestation
    When the user selects the Delete button
    Then the attestation should be removed from the EUDI Wallet
    And the user should be navigated back to the Documents screen

  @US_VD_TC_09 @manual:Passed
  Scenario: Returning to 'Documents' screen from attestation details
    Given the user is viewing the details of an attestation
    When the user closes the attestation
    Then the user should be returned to the Documents screen
