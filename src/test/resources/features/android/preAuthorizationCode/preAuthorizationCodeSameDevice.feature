@ANDROID @manual @US_PACSD
Feature: Pre-authorization code same device

  @US_PACSD_TC_01
  Scenario: User selects to issue a credential
    Given the user is on the issuer service
    When the user chooses to issue a doc with pre-authorization
    Then the user registers personal data
    And a transaction code has been created

  @US_PACSD_TC_02
  Scenario: User proceeds with the doc issuance
    Given the transaction code has been created
    When the user selects to register with the EUDI wallet app
    Then the user is redirected to the EUDI wallet application
    And the user enters the PIN

  @US_PACSD_TC_03
  Scenario: Handling issuer request on wallet app
    Given the user has entered the PIN
    When the request from the issuer is displayed on the wallet app
    Then the user clicks on the 'ISSUE' button

  @US_PACSD_TC_04
  Scenario: Transaction code entry
    Given the user has clicked on the 'ISSUE' button
    When the Wallet app requests the transaction code
    Then the user enters the transaction code provided by the Issuer

  @US_PACSD_TC_05
  Scenario: Document display
    Given the user entered the transaction code provided by the Issuer
    When the Wallet application displays a success message
    Then the user clicks on the 'CONTINUE' button
    And the doc is displayed in the dashboard screen
