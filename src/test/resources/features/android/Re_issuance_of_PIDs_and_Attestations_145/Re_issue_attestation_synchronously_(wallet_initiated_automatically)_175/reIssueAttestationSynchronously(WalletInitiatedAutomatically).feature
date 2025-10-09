@ANDROID @US_RIASWIA
Feature:Automatic PID/Attestation Re-issuance During Presentation
  As an EUDI Wallet,
  I want to synchronously and automatically re-issue a PID/attestation from the original issuer by replacing an existing PID/attestation that was revoked, expired or reached unused limit during a PID/attestation presentation,
  so that I can fulfill the presentation request.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/175

  Background:
    Given the EUDI Wallet is registered and connected to internet
    And a presentation request is received from a relying party

 @US_RIAWIA_TC_01
 Scenario: Re-issue expired batch PID_attestation
   Given an existing batch PID_attestation matches the request
   And the batch PID_attestation is expired but within administrative validity
   When the wallet checks the credential status
   Then the wallet re-issues the PID_attestation automatically
   And resumes the presentation operation

  @US_RIAWIA_TC_02
  Scenario: Re-issue when no unused batch instances available
    Given an existing batch PID_attestation matches the request
    And there are no unused instances in the batch
    When the wallet checks the credential status
    Then the wallet re-issues the PID_attestation automatically
    And resumes the presentation operation

  @US_RIAWIA_TC_03
  Scenario: Re-issue expired single PID_attestation
    Given an existing single PID_attestation matches the request
    And the PID_attestation is expired but within administrative validity
    When the wallet checks the credential status
    Then the wallet re-issues the PID_attestation automatically
    And resumes the presentation operation

  @US_RIAWIA_TC_04
  Scenario: No re-issuance for valid credentials
    Given an existing PID_attestation matches the request
    And the PID_attestation is valid with unused instances available
    When the wallet checks the credential status
    Then the wallet proceeds with normal presentation
    And no re-issuance is triggered

  @US_RIAWIA_TC_05
  Scenario: No re-issuance when no matching credential exists
    Given no existing PID_attestation matches the request
    When the wallet checks for matching credentials
    Then no re-issuance is triggered
    And the wallet handles the missing credential normally


