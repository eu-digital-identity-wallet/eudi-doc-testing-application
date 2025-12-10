@IOS @manual @US_ATRP @Q4_2024
Feature: EUDI Wallet User Logs into a Relying Party
  As a EUDI Wallet User
  I want to use my EUDI Wallet to log in to a Relying Party
  so that I can access the services provided by the Relying Party.

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/210

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity


  @US_ATRP_TC_01 @manual:Passed
  Scenario: User logs in to the Relying Party using EUDI Wallet
    Given the user visits the Relying Party login page on a different device than EUDI Wallet
    When the user enters the username & password
    And the user clicks on the Login button
    Then the user should be successfully logged in to the Relying Party
    And the user can proceed with the signature process


