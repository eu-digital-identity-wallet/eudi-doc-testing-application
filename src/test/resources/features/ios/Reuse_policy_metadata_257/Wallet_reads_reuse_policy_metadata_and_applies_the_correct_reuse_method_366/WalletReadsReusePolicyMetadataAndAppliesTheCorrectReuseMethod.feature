@IOS @US_WRRPMAATCRM @Q2_2026
Feature: Wallet Interprets Credential Reuse Policy Metadata
  As a Wallet User, I want the wallet to correctly interpret the issuer’s credential_reuse_policy metadata so that the wallet applies the correct reuse method.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/366

  @US_WRRPMAATCRM_TC_01
  Scenario: User sees a normal credential-issuance flow
    Given the issuer metadata includes a credential reuse policy with at least one supported option
    When I proceed through credential issuance
    Then I see a normal issuance flow with no additional steps related to reuse behavior

  @US_WRRPMAATCRM_TC_02
  Scenario: User is not asked to choose a reuse behavior
    Given the issuer metadata includes a credential reuse policy with multiple supported options
    When I proceed through credential issuance
    Then I am not prompted to select a reuse method

  @US_WRRPMAATCRM_TC_03
  Scenario: Wallet automatically applies the issuer's preferred behavior
    Given the issuer metadata includes a credential reuse policy with a single option that the wallet supports
    When the wallet processes the issuer metadata
    Then the wallet silently applies that reuse method to the issued credential

  @US_WRRPMAATCRM_TC_04
  Scenario: Wallet silently selects the first fully supported policy when multiple options exist
    Given the issuer metadata includes a credential reuse policy listing multiple options in order of preference
    When the wallet processes the issuer metadata
    Then the wallet silently selects and applies the first option it fully supports

  @US_WRRPMAATCRM_TC_05
  Scenario: Issuance is blocked when the metadata lacks a mandatory baseline policy
    Given the issuer metadata's credential reuse policy does not include once only or limited time
    When the wallet processes the issuer metadata
    Then I see an error and the issuance flow is blocked

  @US_WRRPMAATCRM_TC_06
  Scenario: Issuance is blocked when no reuse rules are provided
    Given the issuer metadata does not include a credential reuse policy
    When the wallet processes the issuer metadata
    Then I see an error and the issuance flow is blocked

  @US_WRRPMAATCRM_TC_07
  Scenario: Issuance proceeds when only the mandatory baseline policy is present
    Given the issuer metadata's credential reuse policy includes only once only
    When the wallet processes the issuer metadata
    Then the wallet silently applies the once only reuse method and issuance proceeds normally

  @US_WRRPMAATCRM_TC_08
  Scenario: Issuance proceeds when a non-mandatory policy is combined with a mandatory one
    Given the issuer metadata's credential reuse policy includes rotating batch and limited time
    When the wallet processes the issuer metadata
    Then the wallet silently selects the first fully supported option and issuance proceeds normally