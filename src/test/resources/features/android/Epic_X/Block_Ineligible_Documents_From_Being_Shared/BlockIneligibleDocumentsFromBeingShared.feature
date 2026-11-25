@ANDROID @US_BIDFBS @Q4_2026
Feature: Block ineligible documents from being shared
  As a EUDI Wallet User
  I want revoked, suspended, and unrecognised documents to be excluded from sharing and Digital Credentials API requests
  So that documents with an invalid or unknown status cannot be offered or disclosed

  #

  @US_BIDFBS_TC_01
  Scenario: DC API does not offer a revoked document
    Given a document with status Revoked exists in the wallet
    When a Digital Credentials API request is made to list offerable documents
    Then the revoked document is NOT included in the response
    And the response contains only valid/active documents

  @US_BIDFBS_TC_02
  Scenario: Wallet-initiated presentation does not show a revoked document
    Given a document with status Revoked exists in the wallet
    When the user initiates a presentation request from within the wallet
    Then the revoked document is NOT displayed in the document selection list

  @US_BIDFBS_TC_03
  Scenario: Document becomes revoked after status check and is no longer offered to a website
    Given a valid/active document exists in the wallet
    And the wallet performs a document-status check
    And the status check returns status Revoked for the document
    And a website has initiated a presentation request
    When the wallet responds to the website's presentation request
    Then the document is NOT offered to the website