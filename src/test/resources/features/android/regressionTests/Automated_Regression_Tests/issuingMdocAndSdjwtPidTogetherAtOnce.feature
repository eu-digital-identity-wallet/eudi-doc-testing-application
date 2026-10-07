@ANDROID @manual @US_IMASPTAO @Q1_2026
Feature: PID-07 Issuing mdoc and sd-jwt PID together at once

  As a wallet user requesting a PID
  I want to receive my PID in both ISO mdoc and SD-JWT VC formats simultaneously
  So that I can use my digital identity across different systems and services

  https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/249

  @US_IMASPTAO_TC_01 @manual
  Scenario: Issuing and storing PID in both mdoc and SD-JWT VC formats
    Given a wallet user navigates to Documents section and selects to add from a list
    When the documents on list are displayed
    Then the user can see one PID option without format indication
    When the wallet user requests a PID issuance
    And the PID provider processes the request of issuance
    Then both ISO mdoc and SD-JWT VC formats are generated the same time
    And both formats are delivered to user's wallet in a secure transaction
    And both formats are stored in wallet
    And both formats are accessible in Documents section
    And they appear as PID mdoc and PID sd-jwt docs
    When service requests PID-based identity verification
    Then the wallet automatically selects appropriate format
    And the user does not need manually choose the format
    And if either the mdoc or sd-jwt format fails to generate, the entire PID issuance process fails
    And no partial PID is stored in wallet