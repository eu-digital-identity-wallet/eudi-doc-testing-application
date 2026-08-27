@ANDROID @US_WDRPAPIFWRPACCS @Q3.1_2026
Feature: Retrieval and display of the interacting party's identity from the Access Certificate
  As a Wallet user,
  I want the wallet to retrieve and display the identity of the party I am interacting with from their access certificate,
  So that I know exactly who is requesting my data or issuing my attestation before I approve.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/362

  @US_WDRPAPIFWRPACCS_TC_01 @manual:Passed
  Scenario: Consent screen displays the RP's trade name and service trade name
    Given a presentation request is received from an RP presenting its registration certificate
    When the consent screen is displayed
    Then the screen shows the RP's user friendly trade name and the trade name of the specific service

  @US_WDRPAPIFWRPACCS_TC_02 @manual:Passed
  Scenario: Intermediary's trade name is not disclosed to the user during the transaction
    Given the RP uses an intermediary for the presentation request
    When the consent screen is displayed
    Then the Wallet does not inform the user of the intermediary's trade name or its service trade name

  @US_WDRPAPIFWRPACCS_TC_03 @manual:Passed
  Scenario: Intermediary's trade name is recorded in the transaction log
    Given the RP uses an intermediary for the presentation request
    When the transaction is completed
    Then the intermediary trade name is recorded in the Wallet transaction log

  @US_WDRPAPIFWRPACCS_TC_04 @manual:Passed
  Scenario: Issuance approval screen displays the identity of the Provider
    Given an issuance request is received from a PID Provider or Attestation Provider presenting its access certificate
    When the issuance approval screen is displayed
    Then the screen shows the provider's identity as included in the subject information of the access certificate