@IOS @US_WRRPMAATCRM @Q2_2026
Feature: Wallet Interprets Credential Reuse Policy Metadata
  As a Wallet User, I want the wallet to correctly interpret the issuer’s credential_reuse_policy metadata so that the wallet applies the correct reuse method.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/366

  @US_WRRPMAATCRM_TC_01 @manual:Passed
  Scenario: User sees a normal credential-issuance flow
    Given the issuer metadata includes a credential reuse policy with at least one supported option
    When I proceed through credential issuance
    Then I see a normal issuance flow with no additional steps related to reuse behavior

  @US_WRRPMAATCRM_TC_02 @manual:Passed
  Scenario: User is not asked to choose a reuse behavior
    Given the issuer metadata includes a credential reuse policy with multiple supported options
    When I proceed through credential issuance
    Then I am not prompted to select a reuse method

  @US_WRRPMAATCRM_TC_03 @manual:Passed
  Scenario: Wallet automatically applies the issuer's preferred behavior
    Given the issuer metadata includes a credential reuse policy with a single option that the wallet supports
    When the wallet processes the issuer metadata
    Then the wallet silently applies that reuse method to the issued credential

  @US_WRRPMAATCRM_TC_04 @manual:Failed
  Scenario: Wallet silently selects the first fully supported policy when multiple options exist
    Given the issuer metadata includes a credential reuse policy listing multiple options in order of preference
    When the wallet processes the issuer metadata
    Then the wallet silently selects and applies the first option it fully supports

  @US_WRRPMAATCRM_TC_05 @manual:Passed
  Scenario: Wallet falls back to its own default reuse policy in case of no rule
    Given the issuer metadata does not include a credential reuse policy
    When the wallet processes the issuer metadata
    Then the wallet does not show an error and it falls back to its own default reuse policy