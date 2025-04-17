@ANDROID @manual @US_RSDTRP @Q1_2025
Feature: Return Signed Document to Relying Party

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/164

  Background:
    Given the signing process has been completed as per the Rthree or Rfive flow
    And the Wallet and the Relying Party User Interface reside on different devices (e.g. mobile device and a desktop respectively)

  @US_RSDTRP_TC_01 @manual:Passed
  Scenario: User views shared data on success screen in EUDI Wallet
    Given the user has completed the signing process
    When a success screen is displayed
    Then the user sees the success screen and the data they shared

  @US_RSDTRP_TC_02 @manual:Passed
  Scenario: Confirm sharing and passing of signed document to Relying Party
    Given the user has completed the signing process
    When the user clicks on the Close button
    Then a success screen is displayed along with the signed document in the EUDI Wallet
    And the signed document is passed to the Relying Party



