@ANDROID @automated @US_IADFUEDTA
Feature: Improve Add Document Flow UI
  As a user
  I want a clearer and more consistent experience when adding documents to my wallet
  So that I can easily understand available actions and complete the process without confusion

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/317

  @US_IADFUEDTA_TC_01
  Scenario: Verify Add Document screen options and issuance display
    Given the user opens the Add Document screen
    When the user selects add document from the list
    Then the QR code option is displayed in the top right corner
    And when the user proceeds with attestation issuance, no instance count is shown



