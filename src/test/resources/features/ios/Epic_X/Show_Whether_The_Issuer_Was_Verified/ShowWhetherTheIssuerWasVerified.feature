@IOS @US_SWTIWV @Q4_2026
Feature: Show whether the issuer was verified
  As a EUDI Wallet User
  I want to see whether the issuer of each document was verified
  So that I can understand the trust status of the issuer before relying on the document

   #

  @US_SWTIWV_TC_01
  Scenario: Issuance-success screen shows the verified badge for a single verified document
    Given a user completes an issuance flow for one document
    And the document's issuer is verified
    When the issuance-success screen is displayed
    Then the issuer header displays the verified badge

  @US_SWTIWV_TC_02
  Scenario: Issuance-success screen shows the verified badge when all documents are verified
    Given a user completes an issuance flow for multiple documents
    And every issued document has a verified issuer
    When the issuance-success screen is displayed
    Then the issuer header displays the verified badge

  @US_SWTIWV_TC_03
  Scenario: Issuance-success screen does NOT show the verified badge for a single unverified document
    Given a user completes an issuance flow for one document
    And the document's issuer is not verified
    When the issuance-success screen is displayed
    Then the issuer header does NOT display the verified badge
    And a corresponding message is displayed beneath the document

  @US_SWTIWV_TC_04
  Scenario: Issuance-success screen does NOT show the verified badge when one of several documents is unverified
    Given a user completes an issuance flow for multiple documents
    And at least one issued document has an unverified issuer
    When the issuance-success screen is displayed
    Then the issuer header does NOT display the verified badge
    And a corresponding message is displayed beneath the document

  @US_SWTIWV_TC_05
  Scenario: Issuance-success screen does NOT show the verified badge when all documents are unverified
    Given a user completes an issuance flow for multiple documents
    And every issued document has an unverified issuer
    When the issuance-success screen is displayed
    Then the issuer header does NOT display the verified badge
    And a corresponding message is displayed beneath the documents

  @US_SWTIWV_TC_06
  Scenario: Issuer card on document details shows "Issuer not verified" in a warning colour for an unverified issuer
    Given a document with an unverified issuer exists in the wallet
    When the user opens the document-details screen
    Then the issuer card displays the corresponding message
    And the message is displayed in the configured warning colour

  @US_SWTIWV_TC_07
  Scenario: Issuer card on document details shows "Issuer verified" for a verified issuer
    Given a document with a verified issuer exists in the wallet
    When the user opens the document-details screen
    Then the issuer card displays the corresponding message
    And the message is displayed in green

  @US_SWTIWV_TC_08
  Scenario: Pre-update document shows "Issuer not verified" on the issuance-success screen
    Given a document was issued before the issuer-verification update
    And the document has no historical issuer-verification result
    When the user views the document on the issuance-success screen
    Then the message "Issuer not verified" is displayed beneath the document

  @US_SWTIWV_TC_09
  Scenario: Single unverified document in an otherwise verified multi-document flow — badge absent
    Given a user completes an issuance flow for two documents
    And the first document's issuer is verified
    And the second document's issuer is not verified
    When the issuance-success screen is displayed
    Then the issuer header does NOT display the verified badge
