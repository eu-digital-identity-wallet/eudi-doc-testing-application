@ANDROID @US_PRSASSD @Q4_2026
Feature: Preserve revocation status and show suspended documents
  As a EUDI Wallet User
  I want the wallet to preserve a revoked document's status and clearly show when a document is suspended
  So that I do not use a document that the issuer has withdrawn, suspended, or whose status cannot be safely interpreted

  #

  @US_PRSASSD_TC_01
  Scenario: Update document status to Revoked
    Given the wallet contains a valid document
    When an issuer status check identifies the document as revoked
    Then the document status is updated to revoked
    And the document cannot be used for verification

  @US_PRSASSD_TC_02
  Scenario: Update document status to Suspended
    Given the wallet contains a valid document
    When an issuer status check identifies the document as suspended
    Then the document status is updated to suspended
    And the document is identified as suspended in the wallet
    And the document cannot be used for verification

  @US_PRSASSD_TC_03
  Scenario: Reinstate a suspended document
    Given the wallet contains a suspended document
    When a subsequent issuer status check confirms that the document is valid
    Then the document status is updated to valid
    And the suspended status is no longer displayed
    And the suspension-related verification restriction is removed

  @US_PRSASSD_TC_04
  Scenario: Display status notification sheet for revoked
    Given the wallet is unlocked
    And the dashboard is displayed
    And the wallet contains a valid document
    When a background status check identifies the document as revoked
    Then a notification sheet is displayed on the dashboard
    And the affected document is listed in the sheet
    And the sheet title is 'Revoked or suspended documents'
    And the sheet displays a corresponding message

  @US_PRSASSD_TC_05
  Scenario: Display status notification sheet for suspended
    Given the wallet is unlocked
    And the dashboard is displayed
    And the wallet contains a valid document
    When a background status check identifies the document as suspended
    Then a notification sheet is displayed on the dashboard
    And the affected document is listed in the sheet
    And the sheet title is 'Revoked or suspended documents'
    And the sheet displays a corresponding message

  @US_PRSASSD_TC_06
  Scenario: Prevent verification with revoked documents
    Given the wallet contains a revoked document
    And the document would otherwise satisfy a presentation request
    When the user initiates a presentation
    And the relying party requests the revoked document
    Then the revoked document cannot be used to satisfy the request
    And no data from the revoked document is shared for verification

  @US_PRSASSD_TC_07
  Scenario: Prevent verification with suspended documents
    Given the wallet contains a suspended document
    And the document would otherwise satisfy a presentation request
    When the user initiates a presentation
    And the relying party requests the suspended document
    Then the suspended document cannot be used to satisfy the request
    And no data from the suspended document is shared for verification