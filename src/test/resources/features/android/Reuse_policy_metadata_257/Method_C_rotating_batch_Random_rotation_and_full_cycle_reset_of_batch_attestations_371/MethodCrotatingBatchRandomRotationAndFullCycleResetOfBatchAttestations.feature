@ANDROID @US_MCBRAFCROBA @Q2_2026
Feature: Randomized balanced rotation with silent renewal under Method C (rotating-batch)
  As a Wallet User, I want the wallet to rotate through all technical attestations in a batch in a random and balanced way, and automatically reset the rotation cycle once every attestation has been used at least once, so that my presentations remain unlinkable and the batch continues functioning seamlessly until it expires.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/371

  @US_MCBRAFCROBA_TC_01
  Scenario: User sees a single credential after issuance
    Given the issuer has issued a batch of N credential instances under Method C
    When I open my wallet
    Then I see a single credential displayed in the wallet UI

  @US_MCBRAFCROBA_TC_02
  Scenario: Credential always displays full availability
    Given the issuer has issued a batch of N credential instances under Method C
    When I view the credential in my wallet
    Then I see the counter showing N of N

  @US_MCBRAFCROBA_TC_03
  Scenario: User can present the credential repeatedly without noticing rotation
    Given I hold a credential backed by a batch of N instances under Method C
    When I present the credential to a verifier multiple times
    Then each presentation completes successfully with no indication that a different underlying instance was used

  @US_MCBRAFCROBA_TC_04
  Scenario: Wallet rotates through batch copies in a random and balanced way
    Given I hold a credential backed by a batch of N instances under Method C
    When I present the credential N times in succession
    Then each of the N instances is used at least once before any instance is reused

  @US_MCBRAFCROBA_TC_05
  Scenario: Rotation cycle resets automatically once every instance has been used
    Given every instance in the current batch has been used at least once during the rotation cycle
    When I present the credential again
    Then the wallet begins a new rotation cycle by drawing from the full set of instances again

  @US_MCBRAFCROBA_TC_06
  Scenario: Counter never shows depletion or decreasing values
    Given I hold a credential backed by a batch of N instances under Method C
    When I present the credential repeatedly
    Then the counter continues to display N of N and never decreases

  @US_MCBRAFCROBA_TC_07
  Scenario: No warnings are shown regardless of usage
    Given I hold a credential backed by a batch of N instances under Method C
    When I present the credential any number of times
    Then I see no warning indicators, low remaining alerts, or signs of depletion

  @US_MCBRAFCROBA_TC_08
  Scenario: Wallet silently refreshes the batch when its lifetime is close to ending
    Given the batch's remaining lifetime has fallen below the reissue trigger lifetime left threshold
    When the wallet checks the batch's remaining lifetime
    Then the wallet silently reissues a fresh batch of N instances without notifying me

  @US_MCBRAFCROBA_TC_09
  Scenario: Credential shows full availability after silent renewal
    Given the wallet has silently reissued a fresh batch to replace the near expiry batch
    When I next open my wallet
    Then I see the counter showing N of N