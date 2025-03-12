@ANDROID @manual @US_RSDTRP @Q1_2025
Feature: Return Signed Document to Relying Party

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/164

  Background:
    Given the signing process has been completed as per the Rthree or Rfive flow
    And the Wallet and the Relying Party User Interface reside on different devices (e.g. mobile device and a desktop respectively)

  @US_RSDTRP_TC_01
  Scenario: Prompt user to return signed document to Relying Party
    Given the user views the signed document in the EUDI Wallet
    Then the user is prompted to return the signed document to the Relying Party

  @US_RSDTRP_TC_02
  Scenario: User confirms sharing of the signed document
    Given the user is prompted to return the signed document to the Relying Party
    When the user confirms the sharing of the document
    Then the EUDI Wallet shares the signed document with the Relying Party

  @US_RSDTRP_TC_03
  Scenario: View confirmation message after successful sharing
    Given the user confirms the sharing of the document
    When the EUDI Wallet shares the signed document with the Relying Party
    Then the user views a confirmation message that the signed document has been successfully shared
