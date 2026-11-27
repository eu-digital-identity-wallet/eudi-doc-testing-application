@IOS @US_SOWISOTCS @Q4_2026
Feature: Share only what is shown on the consent screen
  As a EUDI Wallet User
  I want the wallet to show all valid document options for a request and share only the documents and claims displayed on the consent screen
  So that I can make an informed decision about exactly what data I approve for sharing

  #

  Scenario: Display all valid document sets
    Given the wallet contains multiple document sets
    And more than one document set satisfies the proximity presentation request
    When the wallet receives the proximity presentation request
    Then all valid documents are displayed as selectable options
    And the user can select any document for sharing

  Scenario: The response matches exactly the document set displayed on the consent screen
    Given the user has selected a document set on the consent screen
    When the presentation response is sent
    Then the response contains exactly the documents and claims of the selected set
    And no other documents or claims are included

  Scenario: Share only displayed DC API claims
    Given the wallet contains a document with multiple claims
    And a valid Digital Credentials API request has been received
    And the iOS ID Provider extension is displayed
    And only a subset of the document claims is displayed for sharing
    When the user selects Accept
    And successfully completes any required authorization
    Then the Digital Credentials API presentation completes successfully
    And the response contains only the claims displayed for sharing in the ID Provider extension
    And the shared claim values match the approved claim values

  Scenario: Record rejected DC API requests as not completed
    Given a valid Digital Credentials API request has been received
    And the iOS ID Provider extension is displayed
    When the user selects Reject
    And navigates to the wallet transaction history
    Then a transaction-history entry exists for the rejected request
    And the transaction is recorded as not completed
    And the rejection reason is 'Cancelled by the user'










