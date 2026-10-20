@ANDROID @US_IDDR @Q3_2026
Feature: Initiate a data deletion request
  As a EUDI Wallet User
  I want to initiate a data deletion request to Relying Parties that have received my personal data,
  So that I can exercise my GDPR rights and maintain control over my personal data

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/223

  @US_IDDR_TC_01
  Scenario Outline: Complete data deletion request initiation flow
    Given the user issues the <credential> attestation using <issuer>
    Given the authenticated user has selected presentation transaction from the Wallet History
    When the Presentation Information screen is shown
    Then the intended use of the presentation request is visible
    And the applicable privacy policy is displayed when available
    And the attributes shared during the presentation transaction are listed
    And the Initiate Deletion Request button is presented
    When the user taps the Initiate Deletion Request button
    Then an informational screen appears explaining that the user is about to leave the Wallet
    And the screen clarifies that the Wallet facilitates the initiation of the request by opening the appropriate communication channel
    And the screen notes that the Wallet does not submit, manage or track the request
    And the screen informs that the user is responsible for completing and submitting the request outside the Wallet
    And the screen states that the Relying Party is responsible for evaluating and processing the request
    Given the WRPRC holds a website for the Relying Party
    Then a single action button labelled Continue to [Relying Party]'s website is shown
    When the user activates the Continue to [Relying Party]'s website button
    Then the Relying Party website opens in the device's default browser
    And the Wallet logs the data deletion request initiation
    When the user presses the Back navigation button
    Then the Wallet navigates back to the Presentation Information screen
    Given the WRPRC contains no contact information
    Then data deletion request cannot be initiated for <credential> using <issuer>
    And the Initiate Deletion Request button remains disabled

    Examples:
      | credential   | issuer |
      | PID (SD-JWT) | Kotlin |
      | PID (SD-JWT) | Python |

