@IOS @US_IMIOACWIRSD @Q2_2025
Feature: Batch Issuance of Attestations in EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/159

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the EUDI Wallet and the Issuer User Interface reside on the same device
    And the user has an active internet connection
    And the Issuer supports batch issuance with batch size greater than 1
    And the EUDI Wallet has an internal minimum number of attestations expected

  @US_IMIOACWIRSD_TC_01
  Scenario: Successful authentication in the Wallet
    When the user opens the Wallet
    And the user authenticates using a 6-digit PIN or Biometrics
    Then the authentication is successful

  @US_IMIOACWIRSD_TC_02
  Scenario: Unsuccessful authentication in the Wallet
    When the user opens the Wallet
    And the user fails to authenticate using a 6-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_IMIOACWIRSD_TC_03
  Scenario: Request issuance of an attestation type
    Given the user is successfully authenticated in the Wallet
    When the user requests the issuance of an attestation type by selecting 'Add document'
    Then the user is redirected to the issuer service

  @US_IMIOACWIRSD_TC_04
  Scenario: Redirection issue to the issuer service
    Given the user is successfully authenticated in the Wallet
    When there is an issue in redirecting to the issuer service
    Then the user should be able to return back to the Wallet
    And the user can select any other option in the Wallet

  @US_IMIOACWIRSD_TC_05
  Scenario: Successful authentication and consent in the Issuer service
    Given the user is redirected to the issuer service
    When the user authenticates as per the issuer's authentication means
    And the issuer service displays the attestation details to be issued
    And the user consents to the issuance by clicking "Agree and proceed to issuance"
    Then the issuer service issues multiple attestations of the same type
    And the user is redirected back to the Wallet

  @US_IMIOACWIRSD_TC_06
  Scenario: Issuance flow stops due to batch size limit
    Given the user consents to the attestation issuance
    When the maximum batch size advertised by the issuer is lower than the EUDI Wallet's internal minimum
    Then the issuance flow stops with an error
    And the Wallet does not store any attestations

  @US_IMIOACWIRSD_TC_07
  Scenario: Successful issuance and storage of attestations
    Given the user is redirected back to the Wallet after issuance
    When the issuer has issued the attestations successfully
    Then the Wallet presents the attestation to the user
    And informs the user that it has been successfully issued and stored
    And a counter is displayed showing the number of attestations issued

  @US_IMIOACWIRSD_TC_08
  Scenario: Error during attestation issuance
    Given the user consents to the attestation issuance
    When there is an error in the issuance process
    Then the Wallet informs the user about the error
    And the operation stops without storing any attestations in the Wallet