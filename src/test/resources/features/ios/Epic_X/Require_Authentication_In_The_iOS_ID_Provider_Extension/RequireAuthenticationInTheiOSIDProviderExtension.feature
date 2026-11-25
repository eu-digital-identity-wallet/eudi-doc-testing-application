@IOS @US_RAITIIPE @Q4_2026
Feature: Require authentication in the iOS ID Provider extension
  As a EUDI Wallet User
  I want the iOS ID Provider extension to require authentication before displaying a document request and before sending a response
  So that my identity data is not exposed and no document is shared without my approval

  #

  @US_RAITIIPE_TC_01
  Scenario: The authentication screen is displayed before the request screen for a website request
    Given a website has requested a document
    When the ID Provider extension is invoked
    Then the PIN or biometric screen is displayed before the request screen
    And no user identity data is visible

  @US_RAITIIPE_TC_02
  Scenario: The login step succeeds with a PIN
    Given the login step is displayed
    When the user enters the correct PIN
    Then the initial authentication succeeds
    And the document request is displayed

  @US_RAITIIPE_TC_03
  Scenario: The login step succeeds with biometric authentication
    Given the login step is displayed
    When the user authenticates with Face ID
    Then the initial authentication succeeds
    And the document request is displayed

  @US_RAITIIPE_TC_04
  Scenario: The login step falls back to PIN when biometric authentication fails
    Given the login step is displayed
    And biometric authentication fails
    When the user enters the correct PIN
    Then the initial authentication succeeds

  @US_RAITIIPE_TC_05
  Scenario: The document request is not shown after a failed initial authentication
    Given the login step is displayed
    When the user enters an incorrect PIN
    Then the document request is NOT displayed
    And no user identity data is shown

  @US_RAITIIPE_TC_06
  Scenario: Selecting Accept triggers a second authentication step before the response is sent
    Given the document request is displayed
    When the user selects Accept
    Then the extension asks for PIN or biometric authentication again
    And the response is NOT sent before this second authentication succeeds

  @US_RAITIIPE_TC_07
  Scenario: The response is NOT sent when the second authentication fails
    Given the document request is displayed
    And the user has selected Accept
    When the second authentication fails
    Then the response is NOT sent



