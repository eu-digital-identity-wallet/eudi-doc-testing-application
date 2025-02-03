@ANDROID @manual @US_DS
Feature: EUDI Wallet Documents Screen
  As a EUDI Wallet User
  I want to have a ‘Documents’ screen consolidating my issued attestations
  So that I can easily view and perform actions on the attestations

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/108

  @US_HS_TC_01 @manual:Passed
  Scenario: Bottom navigation bar includes 'Home', 'Documents', and 'Transactions' options
    Given the user is on the EUDI Wallet Documents screen
    And the bottom navigation bar includes the options Home, Documents, and Transactions
    When the user selects the Documents option from the bottom navigation bar
    Then the Documents button is highlighted

  @US_HS_TC_02 @manual:Passed
  Scenario: 'Documents' screen includes a 'Search' bar
    Given the user is on the Documents screen
    When the user views the screen
    Then the Documents screen includes a Search bar

  @US_HS_TC_03 @manual:Passed
  Scenario: 'Documents' screen includes a 'Filter/Sort' button
    Given the user is on the Documents screen
    When the user views the screen
    Then the Documents screen includes a Filter_Sort button

  @US_HS_TC_04 @manual:Failed
  Scenario: Documents screen includes a list of all issued attestations
    Given the user is on the EUDI Wallet Documents screen
    When the user clicks on the Document section
    Then the Documents screen includes a list of all attestations issued to the wallet
    And the attestations are grouped by category
    And for each attestation, a card is displayed including the attestation name, the issuer, and the validity end date

  @US_HS_TC_05 @manual:Passed
  Scenario: View details of an attestation
    Given the user is on the EUDI Wallet Documents screen
    And the list of attestations is visible
    When the user selects an attestation card
    Then the user should be able to view the details of the attestation
