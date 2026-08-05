@IOS @US_MAOOTUB @Q2_2026
Feature: One-time-use attestation issuance under Method A (once_only)
  As a Wallet User, I want each technical attestation issued under Method A to be used only once so that my presentations remain unlinkable.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/367

  @US_MAOOTUB_TC_01 @manual:Passed
  Scenario: User sees a normal credential after issuance
    Given the issuer has issued a new batch of one time use credentials to my wallet
    When I open my wallet
    Then I see the credential displayed like any other credential

  @US_MAOOTUB_TC_02 @manual:Passed
  Scenario: User can present the credential without noticing special behavior
    Given I hold a one time use credential with four of four uses remaining
    When I present the credential to a verifier
    Then the presentation completes successfully with no unusual prompts or steps

  @US_MAOOTUB_TC_03 @manual:Passed
  Scenario: Counter is shown after issuance
    Given the issuer has issued a new batch of four one time use credential instances
    When I view the credential in my wallet
    Then I see a counter showing four of four remaining

  @US_MAOOTUB_TC_04 @manual:Passed
  Scenario: Counter decreases after a successful presentation
    Given I hold a one time use credential with four of four uses remaining
    When I successfully present the credential to a verifier
    Then I see the counter update to three of four remaining

  @US_MAOOTUB_TC_05 @manual:Passed
  Scenario: Warning indicator appears when only one use is left
    Given I hold a one time use credential with one of four uses remaining
    When I view the credential in my wallet
    Then I see an orange warning indicator with an accompanying icon

  @US_MAOOTUB_TC_06 @manual:Passed
  Scenario: User is not prompted to act when the warning appears
    Given I hold a one time use credential with one of four uses remaining and the warning indicator is displayed
    When I view the credential in my wallet
    Then I am not asked to take any action

  @US_MAOOTUB_TC_07 @manual:Passed
  Scenario: Counter resets to full after re-issuance completes
    Given the wallet has silently reissued a fresh batch of credentials to replace the depleted batch
    When I next open my wallet
    Then I see the counter showing four of four remaining