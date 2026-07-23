@ANDROID @manual @US_VACIF @Q2_2026
Feature: RP Access Certificate verification failure in issuance flow
  As an EUDI Wallet User,
  I want the Wallet to notify me whenever the processing of a Wallet Relying Party Access Certificate fails in the issuance flow
  so that I can avoid sharing my information with an untrusted party.

 #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/326

  @US_VACIF_TC_01
  Scenario: Block issuance when RP access certificate verification fails
    Given an issuance flow has been initiated by a Relying Party
    When the Wallet processes the RP Access Certificate
    And the certificate verification fails
    Then the Wallet terminates the issuance interaction
    And the Wallet displays a notification informs the Wallet user that the issuance has been blocked

