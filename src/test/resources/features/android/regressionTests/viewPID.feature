@ANDROID @automated @US_VP
Feature: View PID document

  @US_VP_TC_01 @before_01
  Scenario: Navigating to the 'Documents' screen
    Given the user is on the Home screen of the EUDI Wallet
    When the user navigates to the Documents screen
    Then the Documents screen should be displayed showing a list of issued attestations

  @US_VP_TC_02 @before_01
  Scenario: Viewing details of an attestation
    Given the user is on the Documents screen
    When the user selects an attestation from the list
    Then the details of the selected attestation should be displayed
    And the details should be blurred by default
    And the user should see the eye icon to view the details of the attestation

  @US_VP_TC_03 @before_01
  Scenario: Viewing attestation details after selecting the 'eye' icon
    Given the user is viewing the details of an attestation
    When the user selects eye icon
    Then the attestation details should no longer be blurred
    And the user should be able to view the full details of the attestation

