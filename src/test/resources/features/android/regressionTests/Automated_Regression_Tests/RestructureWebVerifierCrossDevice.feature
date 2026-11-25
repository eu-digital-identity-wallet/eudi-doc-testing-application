@ANDROID @automated @US_RWVCD @Q4_2024
Feature: EUDIW Web Verifier
  As a User,
  I want the EUDIW web verifier to guide me through in the generation of presentation requests
  so that I can generate presentation requests as per my needs.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/206

  @US_RWVCD_TC_01
  Scenario Outline: User generates presentation requests for single and multiple attestations
    Given the user issues a <credential> attestation using <issuer>
    And the user opens the EUDIW web verifier
    When the user opens the presentation request section
    Then the user sees the presentation request guide
    When the user selects a single attestation option
    Then the verifier permits the user to move to the next step
    When the user requests to choose specific attributes
    And the user chooses the format of the attestation
    Then the user selects the next button
    When the user selects the applicable data items for the request
    Then the user reaches the third step
    When the user selects the next button on second step
    Then the verifier produces a QR code
    And  the user scans the generated QR code
    Then the user's request to the verifier appears in the wallet
    When the user selects the multiple attestations option
    Then the verifier shows the available attestations
    When the user requests to choose specific attributes for the attestations they want
    And the user chooses the format of the attestations
    Then the verifier stores the selected options
    And the user selects the next button on multiple attestations
    When the user scans the generated QR code with the EUDI wallet app
    Then the user's request to the verifier appears in the wallet

    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Kotlin |
