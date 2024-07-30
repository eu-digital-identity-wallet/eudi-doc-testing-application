@IOS @manual @US_DAVA
Feature: Delete age verification attestation
  As a user of the EUDI Wallet
  I want to delete an Age Verification Attestation from my EUDI Wallet
  So that I can manage my attestations and remove those that are no longer needed

  @US_DAVA_TC_01 @manual:Passed
  Scenario: User enters PIN and selects an age verification document
    Given the user has successfully entered their PIN
    When the user opens an age verification doc
    Then the user see the document contents

  @US_DAVA_TC_02 @manual:Passed
  Scenario: User initiates document deletion
    Given the user has an age verification document open
    When the user clicks the delete button
    Then a confirmation modal should appear

  @US_DAVA_TC_03 @manual:Passed
  Scenario: User cancels the document deletion
    Given a confirmation modal is displayed
    When the user clicks the No button
    Then the modal should close
    And the user should still view the age verification document

  @US_DAVA_TC_04 @manual:Passed
  Scenario: User re-initiates document deletion
    Given the user has an age verification document open
    When the user clicks the delete button again
    Then a confirmation modal should appear

  @US_DAVA_TC_05 @manual:Passed
  Scenario: User confirms the document deletion
    Given a confirmation modal is displayed
    When the user clicks the Yes button
    Then their document should be deleted
    And the user should be redirected to the dashboard
