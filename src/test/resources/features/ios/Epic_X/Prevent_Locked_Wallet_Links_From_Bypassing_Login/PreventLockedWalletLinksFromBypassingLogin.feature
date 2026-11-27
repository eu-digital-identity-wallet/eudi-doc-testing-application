@IOS @US_PLWLFBL @Q4_2026
Feature: Prevent locked wallet links from bypassing login
  As a EUDI Wallet User
  I want any wallet link received while my wallet is locked to wait until I authenticate
  So that wallet flows and internal screens cannot be opened without my login

  #

  @US_PLWLFBL_TC_01
  Scenario: Display login before request flow
    Given the wallet is locked
    When the wallet receives a request
    Then the login screen is displayed first
    And the request flow is not started
    And no request-specific information is displayed on the login screen

  @US_PLWLFBL_TC_02
  Scenario: Start request flow after login
    Given the wallet is locked
    And a valid request has been received
    And the login screen is displayed
    When the user successfully logs in
    Then the Home screen is opened
    And the corresponding request flow is started
    And the started flow corresponds to the original request

  @US_PLWLFBL_TC_03
  Scenario: Prevent request execution after failed login
    Given the wallet is locked
    And a valid request has been received
    And the login screen is displayed
    When the user attempts to log in unsuccessfully
    Then the wallet remains locked
    And the received request flow is not started
    And the Home screen is not accessible
    And no internal wallet screen is opened through the request

  @US_PLWLFBL_TC_04
  Scenario: Discard pending links after OS termination
    Given the wallet is locked
    And a valid request  has been received
    And the login screen is displayed
    And the user has not authenticated
    When the operating system terminates the wallet application process
    And the user launches the wallet without opening the request again
    And the user successfully logs in
    Then the Home screen is displayed
    And the previously received request flow is not started
    And the previously received request is not automatically restored






