@ANDROID @manual @US_ADTBS
Feature: EUDI Wallet User Views Document in Relying Party Service
  As a EUDI Wallet User,
  I want to access a document provided by the Relying Party
  so that that I can view the details of the document to be signed.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/101

  Background:
    Given the user has a valid EUDI Wallet
    And the user has authenticated in the Relying Party service

  @US_ADTBS_TC_01
  Scenario: User selects the option to view document to be signed
    Given the user is on the Relying Party service page
    When the user selects the option to view the document to be signed
    Then the Relying Party should present the document to the user for viewing

  @US_ADTBS_TC_02
  Scenario: Document presentation by the Relying Party
    Given the user has selected the option to view the document to be signed
    When the Relying Party presents the document
    Then the user should be able to view the details of the document in the Relying Party service
