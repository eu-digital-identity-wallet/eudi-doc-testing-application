@IOS @US_VRPDDPR @Q3_2026
Feature: Display of RP's registered details on the Consent Screen
  As a Wallet user,
  I want to see the RP's registered details on the consent screen,
  So that I know who is requesting my data and for what purpose.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/307

  @US_VRPDDPR_TC_01
  Scenario: Successful display of RP details and completion of the presentation flow
    Given the user visits the RP service
    And the RP requests the presentation of certain attestations of attributes
    When the user selects to present attestations from their installed Wallet Unit by clicking the corresponding button
    Then the Wallet displays the Data Sharing Request User Approval screen
    And the screen shows the RP name
    And the screen shows the RP unique identifier
    And the screen shows a user friendly description of the RP's intended use of the data
    And the screen shows a link to the RP's privacy policy
    And the screen shows a validation status indicator confirming RP verified

  @US_VRPDDPR_TC_02
  Scenario: User consents and completes the presentation successfully
    Given the Data Sharing Request User Approval screen is displayed with the RP's details
    When the user reviews the information, consents to share the selected attributes
    And the user authenticates by entering the correct six digit PIN
    Then the Wallet displays a confirmation of successful presentation

  @US_VRPDDPR_TC_03
  Scenario: User-friendly description of the RP's intended use is displayed
    Given the user has requested to present attestations via the Wallet
    When the Data Sharing Request User Approval screen is displayed
    Then a user friendly description of the RP's intended use of the requested data is shown

  @US_VRPDDPR_TC_04
  Scenario: Warning indicator is retained when WRPRC validation fails and the user chooses to proceed
    Given the Wallet RP Access Certificate (WRPRC) validation has failed
    When the Data Sharing Request screen is displayed
    Then a warning indicator is shown stating RP could not be verified
    And the warning indicator remains visible throughout the consent screen

  @US_VRPDDPR_TC_05
  Scenario: User reviews information and consents to share selected attributes
    Given the Data Sharing Request screen is displayed with the the warning indicator
    When the user gives consent to proceed
    Then the Wallet proceeds to the authentication step

  @US_VRPDDPR_TC_06
  Scenario: User authenticates successfully with PIN after giving consent
    Given the user has consented to share the selected attributes
    When the user enters the correct six-digit PIN
    Then the Wallet displays a confirmation of successful presentation
