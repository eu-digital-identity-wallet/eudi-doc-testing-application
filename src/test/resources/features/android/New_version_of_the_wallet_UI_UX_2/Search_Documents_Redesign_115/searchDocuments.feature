@ANDROID @manual @US_SD
Feature: Searching for documents in the EUDI Wallet

  As a EUDI Wallet User
  I want to search for documents in the EUDI Wallet
  So that I can find the documents to match my search request

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/115

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is on the Documents screen

  @US_SD_TC_01 @manual:InProgress
  Scenario: Initial state of the "Documents" screen
    Given the user is viewing the Documents screen
    Then the screen should list all non-expired documents
    And the documents should be grouped by category
    And the search field should be empty and enabled
    And the search field should display a search icon on the left side
    And the search field should display the hinted search text Search Documents
    And categories with no documents should not be displayed

  @US_SD_TC_02 @manual:InProgress
  Scenario: Tapping on the search field for the first time
    Given the user is viewing the Documents screen
    When the user taps on the search field
    Then the hinted search text should be removed
    And the Clear trailing icon button (e.g., X) should be displayed inside the search field on the right side

  @US_SD_TC_03 @manual:InProgress
  Scenario: Tapping on the search field with previous search text
    Given the user has previously typed search text in the search field
    When the user taps on the search field
    Then the previously typed search text should be editable
    And the Clear trailing icon button (e.g., X) should be displayed inside the search field

  @US_SD_TC_04 @manual:InProgress
  Scenario: Typing in the search field
    Given the user is viewing the Documents screen
    When the user types text in the search field
    Then the search field should allow any text, including spaces
    And the user should be able to edit the search text

  @US_SD_TC_05 @manual:InProgress
  Scenario: Clearing the search field
    Given the user has entered text in the search field
    When the user taps on the Clear trailing icon button (e.g., X)
    Then the search field should be cleared of any text
    And the EUDI Wallet should reset any previously applied search
    And the screen should list all non-expired documents
    And the filtering and sorting should respect the current settings

  @US_SD_TC_06 @manual:InProgress
  Scenario: Performing a search with empty text
    Given the user has entered an empty search text
    When the user taps on the Search button on the device keyboard
    Then the EUDI Wallet should reset any previously applied search
    And the screen should list all non-expired documents
    And the filtering and sorting should respect the current settings

  @US_SD_TC_07 @manual:InProgress
  Scenario: Performing a search with valid search text
    Given the user has entered valid search text in the search field
    When the user taps on the Search button on the device keyboard
    Then the EUDI Wallet should apply the search text on the attributes of the issuer and attestation name
    And the screen should display matching documents grouped by category

  @US_SD_TC_08 @manual:InProgress
  Scenario: No matching documents found
    Given the user has entered search text in the search field
    When the search text does not match any documents
    Then the EUDI Wallet should display an informative message in the list section
    And categories with no matching documents should not be displayed
