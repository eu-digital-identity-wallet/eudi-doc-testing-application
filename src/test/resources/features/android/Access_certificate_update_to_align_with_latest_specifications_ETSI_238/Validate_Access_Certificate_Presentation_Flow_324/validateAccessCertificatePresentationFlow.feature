@ANDROID @manual @US_VACPF @Q2_2026
Feature: RP Access Certificate verification failure in presentation flow
  As an EUDI Wallet User,
  I want the Wallet to notify me whenever the processing of a Wallet Relying Party Access Certificate fails in the presentation flow,,
  so that I can avoid sharing my information with an untrusted party.

  @US_VACPF_TC_01
  Scenario: Block presentation when RP access certificate verification fails
    Given a presentation flow has been initiated by a Relying Party
    When the Wallet processes the RP Access Certificate
    And the certificate verification fails
    Then the Wallet terminates the presentation interaction
    And the Wallet displays a clear notification to the Wallet user that no information about the Wallet user has been shared