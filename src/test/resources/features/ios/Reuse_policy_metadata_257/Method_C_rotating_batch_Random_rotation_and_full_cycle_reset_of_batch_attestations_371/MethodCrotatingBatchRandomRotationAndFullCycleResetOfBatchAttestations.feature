@IOS @US_MCBRAFCROBA @Q2_2026
Feature: Randomized balanced rotation with silent renewal under Method C (rotating-batch)
  As a Wallet User, I want the wallet to rotate through all technical attestations in a batch in a random and balanced way, and automatically reset the rotation cycle once every attestation has been used at least once, so that my presentations remain unlinkable and the batch continues functioning seamlessly until it expires.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/371

  @US_MCBRAFCROBA_TC_01 @manual:Passed
  Scenario: Credential always displays full availability
    Given the issuer has issued a batch of N credential instances under Method C
    When I view the credential in my wallet
    Then I see the counter showing N of N

  @US_MCBRAFCROBA_TC_02 @manual:Passed
  Scenario: Counter never shows deletion or decreasing values
    Given I hold a credential backed by a batch of N instances under Method C
    When I present the credential repeatedly
    Then the counter continues to display N of N and never decreases

  @US_MCBRAFCROBA_TC_03 @manual:Passed
  Scenario: Wallet silently refreshes the batch when its lifetime is close to ending
    Given the batch's remaining lifetime has fallen below the reissue trigger lifetime left threshold provided by the issuer
    When the wallet checks the batch's remaining lifetime
    Then the wallet silently reissues a fresh batch of N instances without notifying me

  @US_MCBRAFCROBA_TC_04 @manual:Passed
  Scenario: Credential shows full availability after silent renewal
    Given the wallet has silently reissued a fresh batch to replace the near expiry batch
    When I next open my wallet
    Then I see the counter showing N of N