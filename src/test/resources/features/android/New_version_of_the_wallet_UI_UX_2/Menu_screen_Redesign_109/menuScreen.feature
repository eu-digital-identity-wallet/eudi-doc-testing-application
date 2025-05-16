@ANDROID @manual @US_MS @Q4_2024
Feature: EUDI Wallet Menu Screen
  As a EUDI Wallet User
  I want to access the additional secondary options of the EUDI Wallet
  So that I can be informed about EUDI Wallet aspects (help, privacy notice), change my access PIN, view my notifications or exit the application

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/109

  @US_MS_TC_01 @manual:Failed
  Scenario Outline: Access EUDI Wallet menu
    Given the user is on the EUDI Wallet Home screen
    When the user taps the menu button on the upper left corner of the screen
    Then the navigation drawer slides in from the right side of the screen
    And the navigation drawer includes an icon button to close the drawer, and the following <options>:
    Examples:
      | options        |
      | Notifications  |
      | Change PIN     |
      | Privacy Notice |
      | Help / FAQs    |
      | Exit           |

  @US_MS_TC_02 @manual:Passed
  Scenario: Close the navigation drawer using the 'Back' icon button
    Given the navigation drawer is open
    When the user taps the back icon button
    Then the navigation drawer slides away

  @US_MS_TC_03 @manual:Failed
  Scenario: Access Notifications from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Notifications option
    Then the Notifications screen is displayed

  @US_MS_TC_04 @manual:Passed
  Scenario: Access Change PIN from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Change PIN option
    Then the Change PIN screen is displayed

  @US_MS_TC_05 @manual:Failed
  Scenario: Access Privacy Notice from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Privacy Notice option
    Then the Privacy Notice screen is displayed

  @US_MS_TC_06 @manual:Failed
  Scenario: Access Help / FAQs from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Help_FAQs option
    Then the Help_FAQs screen is displayed

  @US_MS_TC_07 @manual:Failed
  Scenario: Exit the application from the navigation drawer
    Given the navigation drawer is open
    When the user taps the Exit option
    Then the application is closing


