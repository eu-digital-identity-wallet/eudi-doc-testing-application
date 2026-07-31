@IOS @US_MAOOTUB @Q2_2026
Feature: One-time-use attestation issuance under Method A (once_only)
  As a Wallet User, I want each technical attestation issued under Method A to be used only once so that my presentations remain unlinkable.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/367

  @US_MAOOTUB_TC_01
  Scenario: User sees a normal credential after issuance
    Given the issuer has issued a new batch of one-time-use credentials to my wallet
    When I open my wallet
    Then I see the credential displayed like any other credential

  @US_MAOOTUB_TC_02
  Scenario: User can present the credential without noticing special behavior
    Given I hold a one-time-use credential with 4 of 4 uses remaining
    When I present the credential to a verifier
    Then the presentation completes successfully with no unusual prompts or steps

  @US_MAOOTUB_TC_03
  Scenario: Counter is shown after issuance
    Given the issuer has issued a new batch of 4 one-time-use credential instances
    When I view the credential in my wallet
    Then I see a counter showing "4 of 4 remaining"

  @US_MAOOTUB_TC_04
  Scenario: Counter decreases after a successful presentation
    Given I hold a one-time-use credential with 4 of 4 uses remaining
    When I successfully present the credential to a verifier
    Then I see the counter update to "3 of 4 remaining"

  @US_MAOOTUB_TC_05
  Scenario: Counter continues to decrease with each subsequent presentation
    Given I hold a one-time-use credential with 2 of 4 uses remaining
    When I successfully present the credential to a verifier
    Then I see the counter update to "1 of 4 remaining"

  @US_MAOOTUB_TC_06
  Scenario: Warning indicator appears when only one use is left
    Given I hold a one-time-use credential with 1 of 4 uses remaining
    When I view the credential in my wallet
    Then I see a red warning indicator with an accompanying icon

  @US_MAOOTUB_TC_07
  Scenario: User is not prompted to act when the warning appears
    Given I hold a one-time-use credential with 1 of 4 uses remaining and the warning indicator is displayed
    When I view the credential in my wallet
    Then I am not asked to take any action

  @US_MAOOTUB_TC_08
  Scenario: Background re-issuance is triggered silently once the threshold is reached
    Given I hold a one-time-use credential with 1 of 4 uses remaining, matching the reissue_trigger_unused value
    When the wallet checks the remaining use count
    Then the wallet triggers background re-issuance without notifying me

  @US_MAOOTUB_TC_09
  Scenario: Counter resets to full after re-issuance completes
    Given the wallet has silently re-issued a fresh batch of credentials to replace the depleted batch
    When I next open my wallet
    Then I see the counter showing "4 of 4 remaining"