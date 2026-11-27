@IOS @US_WUWARPCBV @Q4_2026
Feature: Warn users when a relying party cannot be verified
  As a EUDI Wallet User
  I want the wallet to clearly warn me when a relying party's identity cannot be verified and require my explicit acknowledgement before sharing data
  So that I can make an informed decision and do not accidentally disclose personal information to an untrusted requester

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/431

  @US_WUWARPCBV_TC_01
  Scenario: Display unverified requester warning
    Given the wallet is unlocked
    And the user has initiated a proximity presentation
    When an unverified relying party sends a presentation request
    Then the request screen is displayed
    And the following warning is displayed above the Share button 'Warning: The identity of this relying party could not be verified. Review carefully before sharing your data.'
    And the 'I understand the risks and agree to share my data' switch is displayed

  @US_WUWARPCBV_TC_02
  Scenario: Disable sharing by default
    Given the wallet is unlocked
    And the user has initiated a proximity presentation
    When an unverified relying party sends a valid presentation request
    Then the risk acknowledgement switch is off by default
    And the Share button is disabled

  @US_WUWARPCBV_TC_03
  Scenario: Enable sharing after acknowledgement
    Given an unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    Then the Share button is enabled
    And the unverified requester warning remains displayed
    And no document data is automatically shared

  @US_WUWARPCBV_TC_04
  Scenario: Disable sharing after withdrawing acknowledgement
    Given an unverified proximity presentation request is displayed
    And the risk acknowledgement switch is on
    And the Share button is enabled
    When the user disables the risk acknowledgement switch
    Then the Share button is disabled
    And the unverified requester warning remains displayed

  @US_WUWARPCBV_TC_05
  Scenario: Share data after acknowledgement
    Given a valid unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    And the user selects Share
    And the user successfully completes the required authorization
    Then the presentation completes successfully
    And only the approved document data is shared with the relying party

  @US_WUWARPCBV_TC_06
  Scenario: Prevent sharing without acknowledgement
    Given a valid unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    And the Share button is disabled
    When the user attempts to select Share
    Then the Share button remains disabled
    And the presentation does not proceed to sharing
    And no document data is shared with the relying party

  @US_WUWARPCBV_TC_07
  Scenario: Hide verified badge for unauthenticated readers
    Given the wallet is unlocked
    And the user has initiated a proximity presentation
    When a proximity reader sends a presentation request without authenticating
    Then the request screen is displayed
    And no verified badge is displayed for the reader
    And the unverified requester warning is displayed
    And risk acknowledgement is required before sharing

  @US_WUWARPCBV_TC_08
  Scenario:  Unauthenticated proximity reader is displayed as 'Verifier'
    Given the wallet is unlocked
    And the user has initiated a proximity presentation
    When a proximity reader sends a presentation request without authenticating
    Then the requester name is displayed as 'Verifier'
    And the name contained in the reader certificate is not used as the requester name

  @US_WUWARPCBV_TC_09
  Scenario: Display unverified requester warning
    Given a valid DC API request has been received
    When the ID Provider extension is displayed
    And the user successfully authenticates
    Then the Data sharing request screen is displayed
    And the following warning is displayed above the Accept and Reject buttons 'Warning: The identity of this relying party could not be verified. Review carefully before sharing your data.'
    And the 'I understand the risks and agree to share my data' switch is displayed

  @US_WUWARPCBV_TC_10
  Scenario: Accept button is disabled by default
    Given a valid DC API request has been received
    When the ID Provider extension is displayed
    And the user successfully authenticates
    Then the Data sharing request screen is displayed
    And the risk acknowledgement switch is off by default
    And the Accept button is disabled

  @US_WUWARPCBV_TC_11
  Scenario: Enable Accept after acknowledgement
    Given a DC API request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    Then the Accept button is enabled
    And the unverified requester warning remains displayed
    And no document data is automatically shared

  @US_WUWARPCBV_TC_12
  Scenario: Share data after acknowledgement
    Given a valid DC API request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    And the user selects Accept
    And the user successfully completes any required authorization
    Then the DC API presentation completes successfully
    And only the approved document data is returned to the relying party

  @US_WUWARPCBV_TC_13
  Scenario: Prevent sharing without acknowledgement
    Given a valid DC API request is displayed
    And the risk acknowledgement switch is off
    And the Accept button is disabled
    When the user attempts to select Accept
    Then the Accept button remains disabled
    And the presentation does not proceed to sharing

  @US_WUWARPCBV_TC_14
  Scenario: Display warning when website address is available
    Given a valid DC API request has been received
    When the ID Provider extension is displayed
    And the user successfully authenticates
    Then the Data sharing request screen is displayed
    And the unverified requester warning is displayed
    And the risk acknowledgement switch is off
    And the Accept button is disabled
    And no verified badge is displayed
