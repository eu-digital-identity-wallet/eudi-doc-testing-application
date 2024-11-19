@ANDROID @manual @US_RWVCD
Feature: EUDIW Web Verifier
  As a User,
  I want the EUDIW web verifier to guide me through in the generation of presentation requests
  so that I can generate presentation requests as per my needs.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/105

  @US_RWVCD_TC_01
  Scenario: User accesses the presentation request
    Given the user is on the EUDIW web verifier
    When the user navigates to the presentation request section
    Then the user views the presentation request guide

  @US_RWVCD_TC_02
  Scenario: User selects request related to one attestation
    Given the user is on the first step
    When the user selects one attestation option
    Then the verifier allows the user to proceed to the next step

  @US_RWVCD_TC_03
  Scenario: User selects applicable data items and the format of the attestation
    Given the user has selected an attestation option
    When the user indicates that they want to choose specific attributes
    And the user selects the format of the attestation
    Then the user clicks on the next button

  @US_RWVCD_TC_04
  Scenario: User selects data items
    Given the user is on the second step of the guide
    When the user selects the applicable data items to be included in the request
    And the user clicks the next button
    Then the user navigates to the third step

  @US_RWVCD_TC_05
  Scenario: Verifier generates a QR code
    Given the user is on the final step
    When the user clicks on the next button
    Then the verifier generates a QR code

  @US_RWVCD_TC_06
  Scenario: User scans QR code and request appears in the wallet
    Given the verifier displays the QR code
    When the user scans the QR code
    Then the user’s request made to the verifier appears in the wallet

  @US_RWVCD_TC_07
  Scenario: User selects request related to multiple attestations
    Given the user is on the verifier
    When the user selects multiple attestations option
    Then the verifier displays the available attestations

  @US_RWVCD_TC_08
  Scenario: User selects applicable data items and the format of the attestations
    Given the user has selected a multiple option
    When the user indicates that they want to choose specific attributes in the attestations they desire
    And the user selects the format of the attestations
    Then the verifier saves the selected options
    And the user clicks on the next button

  @US_RWVCD_TC_09
  Scenario: User scans QR code with EUDI wallet app
    Given the user follows the steps of the guide
    When the verifier displays the QR code
    Then the user scans the QR code with the EUDI wallet app
    Then the user’s request made to the verifier appears in the wallet
