@IOS @US_RPAIPE1D
  Feature: Present Employee ID credential to Employer device via proximity flow
    As a EUDI Wallet User
    I want to present the employee ID from my EUDI Wallet to the requesting Relying Party (i.e., Employer's reader) in a proximity flow
    So that I can prove my employment status during the onboarding process and get my physical company badge.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226

    Background:
      Given the user has a valid EUDI Wallet with an Employee ID credential issued

    @US_RPAIPE1D_TC_01
    Scenario: User authenticates successfully in the Wallet
    Given the user opens the EUDI Wallet
    When the user enters the correct six-digit PIN
    Then the Wallet unlocks successfully

    @US_RPAIPE1D_TC_02
    Scenario: User fails authentication
    Given the user opens the EUDI Wallet
    When the user enters an incorrect PIN
    Then the Wallet shows an error and asks the user to retry

    @US_RPAIPE1D_TC_03
    Scenario: User selects to show QR code for Employee ID
    Given the user is authenticated in the Wallet
    When the user selects Show QR Code
    Then the QR code is displayed on the Wallet screen

    @US_RPAIPE1D_TC_04
    Scenario: Employer device scans QR code
    Given the QR code is displayed on the Wallet
    When the Employer device scans the QR code
    Then a secure connection is established between the two devices

    @US_RPAIPE1D_TC_05
    Scenario: Wallet validates Employer Service request
    Given the Employer device requests Employee ID attestation
    When the Wallet validates the request
    Then the Wallet informs the user of the Employer Service request

    @US_RPAIPE1D_TC_06
    Scenario: Wallet cannot parse Employer Service request
    Given the Employer device sends an invalid request
    Then the Wallet shows an error and stops the process

    @US_RPAIPE1D_TC_07
    Scenario: User consents to release Employee ID
    Given the Wallet informs the user about the Employer request
    When the user agrees to share the Employee ID
    Then the Wallet asks the user to authenticate with PIN

    @US_RPAIPE1D_TC_08
    Scenario: User selects fields to disclose
    Given the Wallet displays Employee ID attestation fields
    When the user selects specific fields
    Then only the chosen fields are prepared for sharing

    @US_RPAIPE1D_TC_09
    Scenario: User authenticates to release the Employee ID
    Given the Wallet requests PIN for releasing Employee ID
    When the user enters the correct PIN
    Then the Employee ID attestation is shared with the Employer device

    @US_RPAIPE1D_TC_10
    Scenario: Employer device verifies Employee ID successfully
    Given the Employer device received the Employee ID
    When the attestation is verified
    Then the Wallet shows a success message to the user

    @US_RPAIPE1D_TC_11
    Scenario: Employer device fails to verify Employee ID
    Given the Employer device received the Employee ID
    When the verification fails
    Then the Wallet shows an error message to the user
