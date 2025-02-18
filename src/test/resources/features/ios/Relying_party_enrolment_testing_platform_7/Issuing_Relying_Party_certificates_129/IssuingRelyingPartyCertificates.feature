@IOS @manual @US_IRPC
Feature: Relying Party Trusted Certificate Creation
  As a Relying Party
  I want to be able to create a trusted certificate for my service
  So that I can use my Relying Party service in trusted interactions with an EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/129

  @US_IRPC_TC_01 @manual:Passed
  Scenario: User visits the Relying Party service and authenticates with PID
    Given the user is on the Relying Party Enrolment service page
    When the user presents their PID by scanning the QR code
    Then the user is authenticated successfully

  @US_IRPC_TC_02 @manual:Passed
  Scenario: User fills in the required information for certificate creation
    Given the user is authenticated
    When the user fills in the required information in the form provided
    Then the user previews the certificate information

  @US_IRPC_TC_03 @manual:Passed
  Scenario: User downloads the certificate
    Given the user has previewed the certificate information
    When the user clicks on the Download button
    Then the certificate is downloaded to the user's device

