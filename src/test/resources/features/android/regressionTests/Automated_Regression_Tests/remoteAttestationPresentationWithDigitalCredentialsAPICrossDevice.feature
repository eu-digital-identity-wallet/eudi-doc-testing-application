@ANDROID @automated @US_RAPDCAPICD @GENERAL_TESTS
Feature: Present Digital Attestations via Browser using EUDI Wallet on a Different Device
  As a EUDI Wallet User
  I want to present digital attestations to a requestor Relying Party residing on a different device (e.g., Laptop/Desktop) than my EUDI Wallet, by utilising the browser's Digital Credentials API
  So that I can provide verified information through the browser seamlessly, without manual app switching, even when the Relying Party is on another device.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/305

  @US_RAPDCAPICD_TC_01
  Scenario Outline: User presents an attestation to a Relying Party on a different device through the Digital Credentials API selector
    Given the user issues a <credential> attestation using <issuer>
    And the user is on the Home screen of the Wallet
    And the user opens the Relying Party service page on a different device
    When the user starts the wallet verification flow from the Relying Party page on the different device
    Then the Relying Party raises a presentation request
    When the Desktop browser invokes the Digital Credentials API for the presentation request
    Then the browser displays a QR code for the user to scan with the mobile device
    When the mobile device camera scans the displayed QR code
    Then the device searches the installed Wallet Units for matching attestations
    And the process ends with a user notification when no attestations match the request
    When suitable attestations are located on the device
    Then the Mobile Operating System presents a selector listing the matching attestations
    And each listed attestation shows its credential type and the Wallet Unit that holds it
    And the selector marks which Wallet Unit each listed attestation belongs to
    When the user picks one of the listed attestations
    Then the matching Wallet Unit launches automatically
    And the presentation request screen displays the requested attestations and their attributes
    When the user consents to share the requested attributes
    Then the Wallet prompts the user for a six-digit PIN
    When the user enters their six-digit PIN correctly
    Then the attestation is shared successfully
    And the Relying Party confirms the verification was successful in the Desktop browser
    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Python |
      | PID (MSO Mdoc) | Kotlin |

