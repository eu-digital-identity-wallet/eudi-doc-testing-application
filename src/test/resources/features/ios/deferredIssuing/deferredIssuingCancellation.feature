@IOS @manual @US_DIC
Feature: Credential Issuance Cancellation

  @US_DIC_TC_01 @manual:Passed
  Scenario: User initiates credential issuance
    Given the user is on the issuer service page
    When the user chooses to issue a credential to the wallet app
    Then the user is redirected to the wallet app
    And the user views the details regarding the issuance

  @US_DIC_TC_02 @manual:Passed
  Scenario: User cancels the issuance process
    Given the user is on the wallet app with issuance details
    When the user clicks the cancel button
    Then a modal appears asking if they really want to cancel the issuance process

  @US_DIC_TC_03 @manual:Passed
  Scenario: User confirms cancellation
    Given the user views the cancellation confirmation modal
    When the user clicks the cancel button on the modal
    Then the issuance process is canceled
    And the user returns to the 'Add Document' screen

