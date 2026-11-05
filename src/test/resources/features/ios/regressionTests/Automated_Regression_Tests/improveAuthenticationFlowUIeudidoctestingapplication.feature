@IOS @automation @US_IAFUEDTA @Q2_2026 @GENERAL_TESTS
Feature: Improve Authentication Flow UI
  As a user
  I want clear guidance when authenticating my identity
  So that I understand what action is required and can complete the authentication process without confusion

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/320

  @US_IAFUEDTA_TC_01
  Scenario Outline: Verify Authentication introduction and guidance
    Given the user is on the Home page and then selects Authenticate with <credential> and <issuer>
    When the user chooses in person
    And the title Authenticate my identity is shown
    When the user chooses online
    Then the scan the QR code provided by the interacting party is displayed
    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Python |
