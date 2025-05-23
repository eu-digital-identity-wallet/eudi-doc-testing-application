@ANDROID @US_IMIOACIIRCASD @Q2_2025
Feature: Batch Issuance of Attestations in EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/172

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the Issuer supports batch issuance with batch size greater than 1
    And the EUDI Wallet has an internal minimum number of attestations expected
    And there is an active internet connection

  @US_IMIOACIIRCASD_TC_01
  Scenario: Issuer service on a different device than the EUDI Wallet device
    Given the user visits the issuer service on a different device
    When the user requests the issuance of an attestation type
    Then the issuer service renders a QR code

    When the user opens the EUDI Wallet
    And the user authenticates using a 6-digit PIN or Biometrics
    Then the authentication is successful

    When the user scans the QR code with the Wallet device
    Then the EUDI Wallet presents the attestation offer
    And the Wallet requests the user to confirm to proceed

  @US_IMIOACIIRCASD_TC_02
  Scenario: Issuer service on the same device as the EUDI Wallet device
    Given the user visits the issuer service on the same device
    When the user requests the issuance of an attestation type
    Then the issuer service redirects the user to the Wallet

    When the user opens the EUDI Wallet
    And the user authenticates using a 6-digit PIN or Biometrics
    Then the authentication is successful

    Then the EUDI Wallet presents the attestation offer
    And the Wallet requests the user to confirm to proceed

  @US_IMIOACIIRCASD_TC_03
  Scenario: Unsuccessful authentication in the Wallet
    Given the user opens the EUDI Wallet
    When the user fails to authenticate using a 6-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_IMIOACIIRCASD_TC_04
  Scenario: Confirm and proceed with attestation issuance
    Given the EUDI Wallet presents the attestation offer
    When the user accepts to proceed
    Then the user is redirected to the issuer service in the mobile device browser

    When the user authenticates as per the issuer's authentication means
    Then the issuer service displays the attestation details to be issued

    When the user consents to the issuance by clicking "Agree and proceed to issuance"
    Then the issuer service issues multiple attestations
    And the user is redirected back to the Wallet

  @US_IMIOACIIRCASD_TC_05
  Scenario: Issuance flow stops due to batch size limit
    Given the user consents to the attestation issuance
    When the maximum batch size advertised by the issuer is lower than the EUDI Wallet's internal minimum
    Then the issuance flow stops with an error
    And the Wallet does not store any attestations

  @US_IMIOACIIRCASD_TC_06
  Scenario: Successful issuance and storage of attestations
    Given the user is redirected back to the Wallet after issuance
    When the issuer has issued the attestations successfully
    Then the Wallet presents the attestation to the user
    And informs the user that it has been successfully issued and stored
    And a counter is displayed showing the number of attestations issued

  @US_IMIOACIIRCASD_TC_07
  Scenario: Error during attestation issuance
    Given the user consents to the attestation issuance
    When there is an error in the issuance process
    Then the Wallet informs the user about the error
    And the operation stops without storing any attestations in the Wallet

  @US_IMIOACIIRCASD_TC_08
  Scenario: Redirection issue to the issuer service
    Given the user accepts to proceed with the attestation issuance
    When there is an issue in redirecting to the issuer service
    Then the user should be able to return to the EUDI Wallet
    And the user can select any other option in the Wallet
