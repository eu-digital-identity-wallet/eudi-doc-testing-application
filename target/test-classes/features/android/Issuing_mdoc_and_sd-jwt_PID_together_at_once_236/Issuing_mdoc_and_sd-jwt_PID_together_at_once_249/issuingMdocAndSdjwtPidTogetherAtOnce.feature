@ANDROID @manual @US_IMASPTAO @Q1_2026
Feature: PID-07 Issuing mdoc and sd-jwt PID together at once
  As a wallet user requesting a PID
  I want to receive my PID in both ISO mdoc and SD-JWT VC formats simultaneously
  So that I can use my digital identity across different systems and services

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/249

  @US_IMASPTAO_TC_01 @manual:Passed
  Scenario: PID Request in UI
    Given a wallet user navigates to the Documents section and selects to add from a list
    When the documents on the list are displayed
    Then the user can see one PID option without any format indication

  @US_IMASPTAO_TC_02 @manual:Passed
  Scenario: Successful Dual Format Generation
    Given a wallet user requests PID issuance
    When the PID provider processes the request
    Then both ISO mdoc and SD-JWT VC formats are generated simultaneously

  @US_IMASPTAO_TC_03 @manual:Passed
  Scenario: Secure Delivery of Both Formats
    Given both PID formats are generated successfully
    When the PID is delivered to the user's wallet
    Then both formats are received in a secure transaction

  @US_IMASPTAO_TC_04 @manual:Passed
  Scenario: Wallet Storage and Display
    Given both PID formats are delivered to the wallet
    When they are stored in the wallet
    Then both formats are accessible
    And they appear as PID mdoc and PID sd-jwt in the user interface's documents section

  @US_IMASPTAO_TC_05 @manual:Passed
  Scenario: Automatic Format Selection by Wallet
    Given the wallet has both PID formats stored
    When a service requests PID-based identity verification
    Then the wallet automatically selects the appropriate format
    And the user does not need to manually choose the format

  @US_IMASPTAO_TC_06 @manual:Passed
  Scenario: Error Handling for Generation Failure
    Given either the mdoc or sd-jwt format fails to generate
    When the issuance process encounters an error
    Then the entire PID issuance process fails
    And no partial PID is stored in the wallet