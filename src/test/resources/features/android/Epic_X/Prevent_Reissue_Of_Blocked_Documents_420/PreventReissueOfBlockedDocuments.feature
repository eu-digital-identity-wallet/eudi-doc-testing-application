@ANDROID @US_PROBD @Q4_2026
Feature: Prevent reissue of blocked documents
  As a EUDI Wallet User
  I want revoked, suspended, and unrecognised documents to be unavailable for manual or background reissuance
  So that the wallet cannot obtain a new copy of a document that the issuer has blocked or whose status cannot be safely interpreted

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/420

  @US_PROBD_TC_01
  Scenario: Reissue attempt ends in error screen when document becomes revoked
    Given the user has opened the details of a document with status Active
    And a document-status check now identifies the document as Revoked
    When the user initiates the reissue action
    Then the reissue attempt is aborted
    And an error screen is displayed
    And the error screen displays the message: 'The issuer has revoked or suspended this document, so it cannot be reissued'
    And the error screen provides only a close action

  @US_PROBD_TC_02
  Scenario: Close action dismisses the error screen
    Given the error screen is displayed for a blocked document
    When the user taps the close action
    Then the error screen is dismissed
    And the user is returned to the previous screen
    And no reissue request has been sent

  @US_PROBD_TC_03
  Scenario: Reissue action is hidden on the issuer card for a revoked document
    Given a document with status Revoked exists in the wallet
    When the user views the issuer card for the revoked document
    Then the reissue action is hidden

  @US_PROBD_TC_04
  Scenario: Reissue action is disabled in the documents options menu for a revoked document
    Given a document with status Revoked exists in the wallet
    When the user opens the documents options menu for the revoked document
    Then the reissue option is disabled
    And the reissue option cannot be selected




