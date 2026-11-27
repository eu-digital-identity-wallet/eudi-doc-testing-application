@IOS @US_SOWISOTCS @Q4_2026
Feature: Share only what is shown on the consent screen
  As a EUDI Wallet User
  I want the wallet to show all valid document options for a request and share only the documents and claims displayed on the consent screen
  So that I can make an informed decision about exactly what data I approve for sharing

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/429

  @US_SOWISOTCS_TC_01
  Scenario: Display all valid document sets
    Given the wallet contains multiple document sets
    And more than one document set satisfies the proximity presentation request
    When the wallet receives the proximity presentation request
    Then all valid documents are displayed as selectable options
    And the user can select any document for sharing

  @US_SOWISOTCS_TC_02
  Scenario: The response matches exactly the document set displayed on the consent screen
    Given the user has selected a document set on the consent screen
    When the presentation response is sent
    Then the response contains exactly the documents and claims of the selected set
    And no other documents or claims are included

  @US_SOWISOTCS_TC_03
  Scenario: Share only displayed DC API claims
    Given the wallet contains a document with multiple claims
    And a valid Digital Credentials API request has been received
    And the iOS ID Provider extension is displayed
    And all valid documents are displayed as selectable options
    When the user selects a document set on the consent screen
    And selects Accept and successfully completes any required authorization
    Then the Digital Credentials API presentation completes successfully
    And the response contains only the claims displayed in the ID Provider extension
    And the shared claim values match the approved claim values

  @US_SOWISOTCS_TC_04
  Scenario: Record rejected DC API requests as not completed
    Given a valid Digital Credentials API request has been received
    And the iOS ID Provider extension is displayed
    When the user selects Reject
    And navigates to the wallet transaction history
    Then a transaction-history entry exists for the rejected request
    And the transaction is recorded as not completed
    And the rejection reason is 'Cancelled by the user'










