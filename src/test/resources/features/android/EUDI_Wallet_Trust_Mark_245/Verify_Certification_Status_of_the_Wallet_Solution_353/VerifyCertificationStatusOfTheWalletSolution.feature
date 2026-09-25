@ANDROID @US_VCSOTWS @Q3_2026
Feature: Access Wallet Solution certification status information
  As a EUDI Wallet User
  I want to access the certification status information of the Wallet Solution,
  so that I can verify that the wallet I am using is officially certified and trusted within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/353

  @US_VCSOTWS_TC_01
  Scenario: Access Trust Mark during first-time login
    Given the user is logging in to the Wallet for the first time
    When the first-time login experience is displayed
    Then the Wallet displays the Trust Mark view

  @US_VCSOTWS_TC_02
  Scenario: Access Trust Mark from the Wallet menu
    Given the authenticated user is on the Wallet home screen
    When the user selects the burger menu and chooses About EUDI Wallet
    Then the Wallet displays the Trust Mark view

  @US_VCSOTWS_TC_03
  Scenario: Display certification information link
    Given the user is viewing the Trust Mark screen
    When the Trust Mark content is displayed
    Then the Wallet displays a link to the Wallet Solution certification information page

  @US_VCSOTWS_TC_04
  Scenario: Open Wallet certification information
    Given the certification information link is displayed in the Trust Mark view
    When the user selects the certification information link
    Then the Wallet opens the European Commission-hosted certification information page in an external browser or supported web view
    And the page allows the user to verify the certification status of the Wallet Solution

  @US_VCSOTWS_TC_05
  Scenario: Handle certification information page opening failure
    Given the certification information page cannot be opened due to network or device limitations
    When the user selects the certification information link
    Then the Wallet displays an appropriate error message
    And the Wallet allows the user to retry the action

