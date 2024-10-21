@ANDROID @manual @US_RDTBS
Feature: Retrieve a document from the Relying Party to EUDI Wallet
  As a EUDI Wallet User,
  I want to retrieve a document from the Relying Party to my EUDI Wallet,
  So that I can view the document details prior to signing it.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/89

  @US_RDTBS_TC_01
  Scenario: Access Relying Party Page with Document Available
    Given the user is authenticated in the Relying Party service
    And the user is authenticated in the EUDI Wallet
    And the user is registered to a valid EUDI Wallet on their mobile device
    When the user navigates to the Relying Party page where the document to be signed is available
    Then page should display an option to retrieve the document into the EUDI Wallet

  @US_RDTBS_TC_02
  Scenario: Select Option to Retrieve Document to EUDI Wallet
    Given the user is on the Relying Party page where the document is available
    When the user selects the option to retrieve the document to the EUDI Wallet
    Then the Relying Party should render a QR-code on the screen requesting the user to scan it with their mobile device.

  @US_RDTBS_TC_03
  Scenario: Relying Party Renders QR-Code for Document Retrieval
    Given the user has selected the option to retrieve the document to the EUDI Wallet
    When the Relying Party renders the QR-code on the page
    Then the user should be able to see the QR-code clearly displayed on the Relying Party's device (e.g. desktop or other device).

  @US_RDTBS_TC_04
  Scenario: Scan QR-Code with EUDI Wallet Mobile Device
    Given the user has the QR-code displayed on the Relying Party's device
    And the user has the EUDI Wallet open on their mobile device
    When the user scans the QR-code using the mobile device
    Then EUDI Wallet should start the process of retrieving the document from the Relying Party service

  @US_RDTBS_TC_05
  Scenario: EUDI Wallet Fetches Document from Relying Party
    Given the user has scanned the QR-code from the Relying Party page
    When the EUDI Wallet successfully communicates with the Relying Party service
    Then the document should be fetched and transferred from the Relying Party to the EUDI Wallet
    And user should receive a confirmation that the document is being retrieved

  @US_RDTBS_TC_06
  Scenario: EUDI Wallet Presents the Document to the User
    Given the document has been successfully retrieved from the Relying Party
    When the document is available in the EUDI Wallet
    Then EUDI Wallet should present the document details to the user for review prior to signing

  @US_RDTBS_TC_07
  Scenario: Handle Internet Connectivity Issues
    Given the user attempts to scan the QR-code
    And there is no internet connection available
    When the EUDI Wallet tries to retrieve the document from the Relying Party
    Then user should receive an error message stating that an internet connection is required for document retrieval