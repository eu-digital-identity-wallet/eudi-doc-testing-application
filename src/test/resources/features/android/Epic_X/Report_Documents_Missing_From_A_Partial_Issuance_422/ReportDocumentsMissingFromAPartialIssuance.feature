@ANDROID @US_RDMFAPI @Q4_2026
Feature: Report documents missing from a partial issuance
  As a EUDI Wallet User
  I want the wallet to tell me when an issuer has not issued all the documents I requested
  So that I understand which documents were added to my wallet and which were not

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/422

  @US_RDMFAPI_TC_01
  Scenario: Partial issuance identifies the requested document that was not issued
    Given the user requests two documents from an issuer: document A and document B
    And the issuer issues only document A
    When the issuance success screen is displayed
    Then the success screen identifies document B as requested but not issued

  @US_RDMFAPI_TC_02
  Scenario: A partial issuance is not presented as a complete success without identifying missing documents
    Given the user requests two documents from an issuer: document A and document B
    And the issuer issues only document A
    When the issuance success screen is displayed
    Then the success screen identifies that document B was not added
    And no message is shown that falsely implies all requested documents were issued

  @US_RDMFAPI_TC_03
  Scenario: A warning is displayed below the header when one or more documents were not issued
    Given the user requests two documents from an issuer: document A and document B
    And the issuer issues only document A
    When the issuance success screen is displayed
    Then a warning is displayed below the header of the success screen
    And the warning displays the text 'These documents were not added to your wallet: document B'

  @US_RDMFAPI_TC_04
  Scenario: Documents successfully issued remain available in the wallet after a partial issuance
    Given a partial issuance occurred and only document A was issued
    When the user closes the issuance success screen
    Then document A is present in the wallet documents list
    And document A is available for use

