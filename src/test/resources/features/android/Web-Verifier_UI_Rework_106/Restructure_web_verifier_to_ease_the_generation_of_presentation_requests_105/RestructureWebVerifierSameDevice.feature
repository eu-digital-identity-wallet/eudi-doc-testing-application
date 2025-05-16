@ANDROID @US_RWVSD @Q4_2024
Feature: EUDIW Web Verifier
  As a User,
  I want the EUDIW web verifier to guide me through in the generation of presentation requests
  so that I can generate presentation requests as per my needs.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/105

  @US_RWVSD_TC_01 @manual:Passed
  Scenario: User accesses the presentation request
    Given the user is on the EUDIW web verifier
    When the user navigates to the presentation request section
    Then the user views the presentation request guide

  @US_RWVSD_TC_02 @manual:Passed
  Scenario: User selects request related to one attestation
    Given the user is on the first step
    When the user selects one attestation option
    Then the verifier allows the user to proceed to the next step

  @US_RWVSD_TC_03 @manual:Passed
  Scenario: User selects applicable data items and the format of the attestation
    Given the user has selected an attestation option
    When the user indicates that they want to choose specific attributes
    And the user selects the format of the attestation
    Then the user clicks on the next button

  @US_RWVSD_TC_04 @manual:Passed
  Scenario: User selects data items
    Given the user is on the second step of the guide
    When the user selects the applicable data items to be included in the request
    And the user clicks the next button
    Then the user navigates to the third step

  @US_RWVSD_TC_05 @manual:Passed
  Scenario: Verifier generates a deep link
    Given the user is on the final step
    When the user clicks on the next button
    Then the verifier generates a deep link

  @US_RWVSD_TC_06 @manual:Passed
  Scenario: User clicks deep link and request appears in EUDI wallet app
    Given the verifier displays the deep link
    When the user clicks on the deep link
    And the user is redirected to the EUDI wallet app
    Then the user’s request made to the verifier appears in the wallet

  @US_RWVSD_TC_07 @manual:Passed
  Scenario: User selects request related to multiple attestations
    Given the user is on the verifier
    When the user selects multiple attestations option
    Then the verifier displays the available attestations

  @US_RWVSD_TC_08 @manual:Passed
  Scenario: User selects applicable data items and the format of the attestations
    Given the user has selected a multiple option
    When the user indicates that they want to choose specific attributes in the attestations they desire
    And the user selects the format of the attestations
    Then the verifier saves the selected options
    And the user clicks on the next button

  @US_RWVSD_TC_09 @manual:Passed
  Scenario: User clicks deep link to EUDI wallet app
    Given the user follows the steps of the guide
    When the verifier displays a deep link
    Then the user clicks on the deep link
    And the user is redirected to the EUDI wallet app
    Then the user’s request made to the verifier appears in the wallet