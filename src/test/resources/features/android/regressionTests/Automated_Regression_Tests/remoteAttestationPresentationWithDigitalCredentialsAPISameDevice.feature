@ANDROID @automated @US_RAPDCAPISD @GENERAL_TESTS
Feature: Present Digital Attestations via Browser using EUDI Wallet on Same Device
  As a EUDI Wallet User
  I want to present digital attestations to a requestor Relying Party residing on the same device as my EUDI Wallet, by utilising browser capabilities
  So that I can provide verified information through the browser seamlessly, without manual app switching.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/235

  @US_RAPDCAPISD_TC_01
  Scenario Outline: User presents an attestation to a Relying Party through the Digital Credentials API selector on the same device
    Given the user issues a <credential> attestation using <issuer>
    And the user is on the Home screen of the Wallet
    And the user opens the Relying Party service page on the same device
    When the user starts the wallet verification flow from the Relying Party page
    Then the Relying Party raises a presentation request
    When Chrome invokes the Digital Credentials API for the presentation request
    Then the browser asks the user to grant permission to proceed
    When the user grants the requested permission
    Then the device searches the installed Wallet Units for matching attestations
    And the process ends with a user notification when no attestations match the request
    When suitable attestations are located on the device
    Then the browser presents a selector listing the matching attestations
    And each listed attestation shows its credential type and the Wallet Unit that holds it
    And the selector marks which Wallet Unit each listed attestation belongs to
    When the user picks one of the listed attestations
    Then the matching Wallet Unit launches automatically
    And the presentation request screen displays the requested attestations and their attributes
    When the user consents to share the requested attributes
    Then the Wallet prompts the user for a six-digit PIN
    When the user enters their six-digit PIN correctly
    Then the attestation is shared successfully
    And the Relying Party confirms the verification was successful in the browser
    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Python |
      | PID (MSO Mdoc) | Kotlin |
