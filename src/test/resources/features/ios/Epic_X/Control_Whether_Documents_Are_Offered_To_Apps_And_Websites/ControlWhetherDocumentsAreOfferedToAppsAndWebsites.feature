@IOS @US_CWDAOTAAW @Q4_2026
Feature: Control whether documents are offered to apps and websites
  As a EUDI Wallet User
  I want to control whether my wallet documents are offered to apps and websites through the Digital Credentials API
  So that I can prevent external services from discovering or requesting my documents when I do not want to use them

  #

  @US_CWDAOTAAW_TC_01
  Scenario: Verify that the DC API setting is displayed in Wallet Settings -------corresponding setting maybe?
    Given the user has opened the wallet
    And the wallet supports the DC API setting on the current platform
    When the user navigates to Settings
    Then the setting "Allow documents requests from apps and websites (DC API)" is displayed
    And the setting is enabled

  @US_CWDAOTAAW_TC_02
  Scenario: Verify that wallet documents are offered when the DC API setting is enabled
    Given the wallet contains a document eligible for the request
    And the setting "Allow documents requests from apps and websites (DC API)" is enabled
    When an external source requests the document through the Digital Credentials API
    Then the eligible wallet document is offered for the request

  @US_CWDAOTAAW_TC_03
  Scenario: Verify that wallet documents are not offered when the DC API setting is disabled
    Given the wallet contains a document eligible for the request
    And the setting "Allow documents requests from apps and websites (DC API)" is disabled
    When an external source requests the document through the Digital Credentials API
    Then no wallet documents are offered for the request
    And a message related to the disabled document requests setting is displayed -------- ????

  @US_CWDAOTAAW_TC_04
  Scenario: Verify that disabling the DC API setting takes effect without restarting the wallet
    Given the wallet contains a document eligible for the request
    And the setting "Allow documents requests from apps and websites (DC API)" is enabled
    And the user navigates to Settings
    When the user disables the setting
    And an external source requests the document through the Digital Credentials API without restarting the wallet
    Then no wallet documents are offered for the request
    And a message related to the disabled document requests setting is displayed ----------------- same