@ANDROID @US_MBTRSLTA @Q2_2026
Feature: Reusable attestation with silent renewal under Method B (limited_time)
  As a Wallet User, I want the wallet to reuse the same technical attestation for all presentations while it remains valid, so that I can continue using the credential without interruption during its lifetime.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/369

  @US_MBTRSLTA_TC_01 @manual:Passed
  Scenario: User sees a normal credential after issuance
    Given the issuer has issued a credential under Method B
    When I open my wallet
    Then I see a single credential instance displayed like any other credential
    And the credential has a batch size of one

  @US_MBTRSLTA_TC_02 @manual:Passed
  Scenario: User can present the credential repeatedly without noticing time-based behavior
    Given I hold a valid credential issued under Method B
    When I present the credential to a verifier multiple times
    Then each presentation completes successfully without decreasing the counter of the instances

  @US_MBTRSLTA_TC_03 @manual:Passed
  Scenario: No timers or countdowns are shown to the user
    Given I hold a valid credential issued under Method B
    When I view the credential in my wallet
    Then I do not see any timer, countdown, or expiration warning

  @US_MBTRSLTA_TC_04 @manual:Passed
  Scenario: Wallet silently refreshes the credential when it nears expiry
    Given I hold a credential whose remaining lifetime has fallen below the reissue trigger lifetime left threshold
    When the wallet checks the credential's remaining lifetime
    Then the wallet silently reissues a fresh credential instance without notifying me
