@ANDROID @US_DLRIRB @Q4_2026
Feature: Disable log retrieval in release builds
  As a EUDI Wallet User
  I want release versions of the wallet to avoid creating or exposing retrievable log files
  So that sensitive information cannot be retained or accessed from the production application

  #

@US_DLRIRB_TC_01
Scenario: Hide Retrieve logs in release builds
Given the user has installed a release build of the wallet
And the Wallet is unlocked
When the user navigates to Settings
Then the Retrieve logs option is not displayed
And no log retrieval action is available in Settings