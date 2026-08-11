@IOS @US_WDAWAOA @Q3_2026
Feature: Warning for presentation requests that exceed RP's registered scope
  As a Wallet user,
  I want to be warned if the presentation request does not comply with the registered information of the RP, including attributes scope and authorised intermediation,
  So that I can make an informed decision before sharing my data.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/308

  @US_WDAWAOA_TC_01 @manual:Passed
  Scenario: User initiates presentation of attestations from the RP service
    Given the user visits the RP service and is requested to present certain attestations
    When the user clicks the corresponding button to present attestations from their installed Wallet
    Then the Wallet processes the presentation request against the RP's WRPRC

  @US_WDAWAOA_TC_02 @manual:Passed
  Scenario: No warning is shown when all requested attributes are covered by the WRPRC
    Given all attributes requested by the RP are covered by its WRPRC
    When the Wallet evaluates the presentation request
    Then the consent screen proceeds normally without any warning

  @US_WDAWAOA_TC_03 @manual:Passed
  Scenario: Warning is displayed when requested attributes are not covered by the WRPRC
    Given one or more attributes requested by the RP are not covered by its WRPRC
    When the Wallet evaluates the presentation request
    Then a clear warning is displayed before any disclosure, listing the unregistered attributes

  @US_WDAWAOA_TC_04 @manual:Passed
  Scenario: Consent screen visually distinguishes registered from unregistered attributes
    Given the presentation request includes both registered and unregistered attributes
    When the consent screen is displayed
    Then registered attributes are shown with standard display and unregistered attributes are shown with a distinct warning indicator

  @US_WDAWAOA_TC_05 @manual:Passed
  Scenario: User cancels the presentation after seeing the warning
    Given the warning for unregistered attributes is displayed on the consent screen
    When the user chooses to cancel
    Then the Wallet returns the user to the Home screen

  @US_WDAWAOA_TC_06 @manual:Passed
  Scenario: Silence or pre-ticked boxes are not accepted as approval to proceed
    Given the warning for unregistered attributes is displayed on the consent screen
    When the user has not provided an explicit action to approve proceeding
    Then the Wallet does not treat this as approval and does not proceed with the presentation

  @US_WDAWAOA_TC_07 @manual:Passed
  Scenario: User reviews the Data Sharing/Approval screen and consents to share selected attributes
    Given the Data Sharing screen is displayed with registered and unregistered attributes distinguished
    When the user reviews the information and consents to share the selected attributes
    Then the Wallet proceeds to the authentication step

  @US_WDAWAOA_TC_08 @manual:Passed
  Scenario: User authenticates successfully with PIN/biometrics after giving consent
    Given the user has consented to share the selected attributes
    When the user authenticates using their six digit PIN or biometrics
    Then the Wallet displays a confirmation of successful presentation

  @US_WDAWAOA_TC_09 @manual:Passed
  Scenario: Confirmation of successful presentation is displayed
    Given the user has consented and successfully authenticated
    When the presentation is completed
    Then the Wallet displays a confirmation screen indicating successful presentation