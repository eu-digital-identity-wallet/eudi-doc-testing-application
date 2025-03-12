@ANDROID @manual @US_RDTBS @Q1_2025
Feature: Retrieve and View Document in EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/89

  Background:
    Given the user has been authenticated in the Relying Party service
    And the user has been authenticated in the EUDI Wallet
    And the user is registered to a valid EUDI Wallet on their mobile device
    And internet is available for connectivity and data transfer
    And the Wallet and the Relying Party User Interface reside on different devices

  @US_RDTBS_TC_01
  Scenario: Retrieve document from Relying Party
    Given the user accesses the Relying Party page where the document to be signed is available
    When the user selects the option to retrieve the document in their EUDI Wallet
    Then the Relying Party renders a QR-code requesting the user to scan the QR-code to retrieve the document

  @US_RDTBS_TC_02
  Scenario: View document to EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user selects the option to Sign a Document through Scan QR
    And the user scans the displayed QR-code with the EUDI Wallet
    Then the EUDI Wallet retrieves the document from the Relying Party service
    And the EUDI Wallet presents the document to the user
