@ANDROID @US_RIAWIOUR @Q3_2025
Feature: User Authentication and Re-Issuance of PIDs/Attestations
  As an EUDI User,
  I want to re-issue a PID/attestation from the original issuer by replacing an existing PID or attestation
  so that fresh attestations are available in my EUDI Wallet.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/181

  @US_RIAWIOUR_TC_01
  Scenario: Successful authentication
    Given the user opens the Wallet application
    When the user authenticates successfully using a six digit PIN or Biometrics
    Then the Wallet grants access to the user

  @US_RIAWIOUR_TC_02
  Scenario: Unsuccessful authentication
    Given the user opens the Wallet application
    When the user fails to authenticate using the six digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_RIAWIOUR_TC_03
  Scenario: User initiates re-issuance
    Given the user is authenticated in the Wallet
    When the user selects the re-issuance option from an existing PID or attestation
    Then the wallet checks for a valid refresh token

  @US_RIAWIOUR_TC_04
  Scenario: Wallet requests re-issuance with valid token
    Given the wallet has a valid refresh token
    When the wallet requests the issuer to issue the same PID or attestation
    Then the wallet receives the new PID or attestation

  @US_RIAWIOUR_TC_05
  Scenario: Notification on attribute differences
    Given the wallet received the new PID or attestation
    When the wallet compares the attribute values to the existing ones
    And there are differences
    Then the wallet notifies the user

  @US_RIAWIOUR_TC_06
  Scenario: Presentation of new PID or attestation
    Given the wallet received the new PID or attestation
    When the wallet confirms the issuance
    Then the wallet presents the PID or attestation to the user
    And informs the user it has been stored

  @US_RIAWIOUR_TC_07
  Scenario: User redirected for authentication
    Given the wallet does not have a valid refresh token
    When the user selects the re-issuance option
    Then the user is redirected to the issuer service for authentication

  @US_RIAWIOUR_TC_08
  Scenario: User consents to issuance
    Given the user is authenticated with the issuer service
    When the issuer displays the PID or attestation details
    Then the user clicks Agree and proceed to issuance

  @US_RIAWIOUR_TC_09
  Scenario: Issuer issues PID or attestation
    Given the user consented to the issuance
    When the issuer service issues the PID or attestation
    Then the user is redirected back to the Wallet

  @US_RIAWIOUR_TC_10
  Scenario: Successful storage of new PID or attestation
    Given the wallet received the new PID or attestation
    When the wallet confirms the issuance
    Then the wallet presents the PID or attestation to the user
    And informs the user it has been stored

  @US_RIAWIOUR_TC_11
  Scenario: Redirection issues to issuer service
    Given the wallet attempts to redirect the user to the issuer service
    When there is an issue in the redirection
    Then the user should be able to return back to the Wallet
    And still be able to make any other option