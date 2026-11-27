@ANDROID @US_WUWARPCBV @Q4_2026
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
    And the following warning is displayed above the Cancel and Share buttons 'Warning: The identity of this relying party could not be verified. Review carefully before sharing your data.'
    And the 'I understand the risks and agree to share my data' switch is displayed
    And the risk acknowledgement switch is off by default
    And the Share button is disabled

  @US_WUWARPCBV_TC_02
  Scenario: Enable sharing after acknowledgement
    Given an unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    Then the Share button is enabled
    And the unverified requester warning remains displayed
    And no document data is automatically shared

  @US_WUWARPCBV_TC_03
  Scenario: Share data after acknowledgement
    Given a valid unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    And the user selects Share
    And the user successfully completes the required authorization
    Then the presentation completes successfully
    And only the approved document data is shared with the relying party

  @US_WUWARPCBV_TC_04
  Scenario: Prevent sharing without acknowledgement
    Given a valid unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    And the Share button is disabled
    When the user attempts to select Share
    Then the Share button remains disabled
    And the presentation does not proceed to sharing
    And no document data is shared with the relying party

  @US_WUWARPCBV_TC_05
  Scenario: Cancel without acknowledgement
    Given an unverified proximity presentation request is displayed
    And the risk acknowledgement switch is off
    And the Share button is disabled
    When the user selects Cancel
    Then the presentation request is cancelled
    And no risk acknowledgement is required
    And no document data is shared

  @US_WUWARPCBV_TC_06
  Scenario: Display unverified requester warning
    Given the Android wallet supports Digital Credentials API requests
    When the wallet receives a DC API request from an unverified relying party
    Then the request screen is displayed
    And the following warning is displayed above the Cancel and Share buttons 'Warning: The identity of this relying party could not be verified. Review carefully before sharing your data.'
    And the 'I understand the risks and agree to share my data' switch is displayed

  @US_WUWARPCBV_TC_07
  Scenario: Disable sharing by default
    Given the wallet supports Digital Credentials API requests
    When the wallet receives a valid DC API request from an unverified relying party
    Then the risk acknowledgement switch is off by default
    And the Share button is disabled

  @US_WUWARPCBV_TC_08
  Scenario: Enable sharing after acknowledgement
    Given an unverified DC API request is displayed
    And all other sharing prerequisites are satisfied
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    Then the Share button is enabled
    And the unverified requester warning remains displayed
    And no document data is automatically shared

  @US_WUWARPCBV_TC_09
  Scenario: Share data after acknowledgement
    Given a valid unverified DC API request is displayed
    And the risk acknowledgement switch is off
    When the user enables the risk acknowledgement switch
    And the user selects Share
    And the user successfully completes the required authorization
    Then the DC API presentation completes successfully
    And only the approved document data is shared with the relying party

  @US_WUWARPCBV_TC_10
  Scenario: Prevent sharing without acknowledgement
    Given a valid unverified DC API request is displayed
    And the risk acknowledgement switch is off
    And the Share button is disabled
    When the user attempts to select Share
    Then the Share button remains disabled
    And the presentation does not proceed to sharing
    And no credential-bearing DC API response is returned

  @US_WUWARPCBV_TC_11
  Scenario: Cancel without acknowledgement
    Given an unverified DC API request is displayed
    And the risk acknowledgement switch is off
    And the Share button is disabled
    When the user selects Cancel
    Then the DC API request is cancelled
    And no risk acknowledgement is required
    And no credential-bearing DC API response is returned

