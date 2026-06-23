@ANDROID @US_IADFUEDTA @Q2_2026
Feature: Improve Add Document Flow UI
  As a user
  I want a clearer and more consistent experience when adding documents to my wallet
  So that I can easily understand available actions and complete the process without confusion

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/317

  @US_IADFUEDTA_TC_01 @manual:Passed
  Scenario: Instances count is not displayed during issuance
    Given the user is at the Add Document screen
    When user attempts to issue an attestation
    Then The instances count is not displayed during issuance

  @US_IADFUEDTA_TC_02 @manual:Passed
  Scenario: Verify QR button presence
    Given the user navigates to the Add Document screen
    When the user selects from List option
    Then a QR button should be visible in the upper right corner



