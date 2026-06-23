@IOS @US_IAFUEDTA @Q2_2026
Feature: Improve Authentication Flow UI
  As a user
  I want clear guidance when authenticating my identity
  So that I understand what action is required and can complete the authentication process without confusion

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/320

  @US_IAFUEDTA_TC_01 @manual:
  Scenario: Verify navigation to Authentication Intro screen
    Given the user is on the Home page and selects Authenticate
    When the user selects either in person or online
    Then the title Authenticate my identity is displayed

  @US_IAFUEDTA_TC_02 @manual:
  Scenario: Verify informative text
    Given the user is on the Home page and selects Authenticate
    When the user selects either online
    Then the informative text Scan a QR code provided from an interacting party to authenticate your identity is displayed
