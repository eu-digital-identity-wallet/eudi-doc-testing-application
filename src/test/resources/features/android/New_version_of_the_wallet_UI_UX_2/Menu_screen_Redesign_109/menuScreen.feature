@ANDROID @manual @US_MS
Feature: EUDI Wallet Menu Screen
  As a EUDI Wallet User
  I want to access the additional secondary options of the EUDI Wallet
  So that I can be informed about EUDI Wallet aspects (help, privacy notice), change my access PIN, view my notifications or exit the application

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/109

  @US_MS_TC_01
  Scenario: Access EUDI Wallet menu
    Given the user is on the EUDI Wallet Home screen
    When the user taps the menu button on the upper left corner of the screen
    Then the navigation drawer slides in from the left side of the screen
    And the navigation drawer includes an icon button to close the drawer, and the following options:
      | Notifications |
      | Change PIN    |
      | Privacy Notice|
      | Help / FAQs   |
      | Exit          |

  @US_MS_TC_02
  Scenario: Close the navigation drawer using the 'Go back' icon button
    Given the navigation drawer is open
    When the user taps the Go back icon button
    Then the navigation drawer slides away to the left side of the screen

  @US_MS_TC_03
  Scenario: Access Notifications from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Notifications option
    Then the Notifications screen is displayed

  @US_MS_TC_04
  Scenario: Access Change PIN from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Change PIN option
    Then the Change PIN screen is displayed

  @US_MS_TC_05
  Scenario: Access Privacy Notice from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Privacy Notice option
    Then the Privacy Notice screen is displayed

  @US_MS_TC_06
  Scenario: Access Help / FAQs from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Help_FAQs option
    Then the Help_FAQs screen is displayed

  @US_MS_TC_07
  Scenario: Exit the application from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Exit option
    Then the application is closing


