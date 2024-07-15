@IOS @manual @US_DAVA
Feature: Delete age verification attestation

  @US_DAVA_TC_01
  Scenario: User enters PIN and selects an age verification document
    Given the user has successfully entered the PIN manually
    When the user opens an age verification doc
    Then the user should see the document contents manually

  @US_DAVA_TC_02
  Scenario: User initiates document deletion
    Given the user has an age verification document open
    When the user clicks the delete button
    Then a confirmation modal should appear

  @US_DAVA_TC_03
  Scenario: User cancels the document deletion
    Given a confirmation modal is displayed
    When the user clicks the No button
    Then the modal should close
    And the user should still view the age verification document

  @US_DAVA_TC_04
  Scenario: User re-initiates document deletion
    Given the user has an age verification document open
    When the user clicks the delete button again
    Then a confirmation modal should appear

  @US_DAVA_TC_05
  Scenario: User confirms the document deletion
    Given a confirmation modal is displayed
    When the user clicks the Yes button
    Then the document should be deleted manually
    And the user should be redirected to the dashboard
