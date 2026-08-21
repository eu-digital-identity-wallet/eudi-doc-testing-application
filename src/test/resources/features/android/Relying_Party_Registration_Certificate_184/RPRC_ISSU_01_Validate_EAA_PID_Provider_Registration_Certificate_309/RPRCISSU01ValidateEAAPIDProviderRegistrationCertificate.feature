@ANDROID @US_VEAAPIDPRC @Q3_2026
Feature: Automatic validation of Provider's registration certificate before issuance
  As a Wallet user,
  I want the wallet to automatically validate the provider's registration certificate before the issuance of any attestation or PID,
  so that I only receive documents from verified and trusted providers.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/309

  Background:
    Given the user is authenticated to a valid EUDI Wallet on their device
    And a valid PID or QEAA or PuB-EAA or EAA Provider's Access Certificate is available
    And the Wallet Unit has verified that the requested attestation type is registered by the relevant Registrar
    And the Wallet retrieves issuer metadata during an issuance flow
    And the Wallet automatically and silently validates the provider's registration certificate, without requiring the user to opt in or manually trigger it

  @US_VEAAPIDPRC_TC_01 @manual:Passed
  Scenario: Issuance proceeds to approval screen when the registration certificate is valid
    Given the issuer metadata contains a registration certificate that is valid
    When the Wallet performs the automatic validation
    Then the validation passes
    And the issuance approval screen is displayed
    And the screen shows the provider details and the attestation or PID to be issued

  @US_VEAAPIDPRC_TC_02 @manual:Passed
  Scenario: Validation fails when the registration certificate does not contain a registration certificate
    Given the issuer metadata contains a registration certificate does not contain a registration certificate
    When the Wallet performs the automatic validation check
    Then the Wallet treats this as a validation failure
