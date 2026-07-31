@IOS @US_MBTRSLTA @Q2_2026
Feature: Reusable attestation with silent renewal under Method B (limited_time)
  As a Wallet User, I want the wallet to reuse the same technical attestation for all presentations while it remains valid, so that I can continue using the credential without interruption during its lifetime.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/369

  @US_MBTRSLTA_TC_01
  Scenario: User sees a normal credential after issuance
    Given the issuer has issued a credential under Method B
    When I open my wallet
    Then I see a single credential instance displayed like any other credential
    And the credential has a batch size of 1

  @US_MBTRSLTA_TC_02
  Scenario: User can present the credential repeatedly without noticing time-based behavior
    Given I hold a valid credential issued under Method B
    When I present the credential to a verifier multiple times
    Then each presentation completes successfully without descreasing the counter of the instances

  @US_MBTRSLTA_TC_03
  Scenario: No timers or countdowns are shown to the user
    Given I hold a valid credential issued under Method B
    When I view the credential in my wallet
    Then I do not see any timer, countdown, or expiration warning

  @US_MBTRSLTA_TC_04
  Scenario: Credential remains valid and usable as its lifetime progresses
    Given I hold a credential whose remaining lifetime is above the reissue_trigger_lifetime_left threshold
    When I present the credential to a verifier
    Then the presentation completes successfully with no indication of impending expiry

  @US_MBTRSLTA_TC_05
  Scenario: Wallet silently refreshes the credential when it nears expiry
    Given I hold a credential whose remaining lifetime has fallen below the reissue_trigger_lifetime_left threshold
    When the wallet checks the credential's remaining lifetime
    Then the wallet silently re-issues a fresh credential instance without notifying me

  @US_MBTRSLTA_TC_06
  Scenario: Credential appears fully valid after silent renewal
    Given the wallet has silently re-issued a fresh credential instance to replace the near-expiry one
    When I next open my wallet
    Then I see the credential displayed as fully valid

  @US_MBTRSLTA_TC_07
  Scenario: User never sees an expired credential
    Given the wallet manages renewal of my Method B credential in the background
    When I attempt to present the credential at any point in time
    Then I am never shown an expired credential