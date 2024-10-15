@ANDROID @manual @US_VSHM
Feature: View Signatures History Menu
  As a user of EUDI Wallet,
  I want to view details of previously performed signatures with my EUDI Wallet,
  So that I can keep a record of signatures performed through the EUDI Wallet.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/91

  @US_VSHM_TC_01
  Scenario: Navigate to 'Signatures History' Menu
    Given the user is logged into the EUDI Wallet
    When the user clicks the Signatures History menu
    Then the user is navigated to the Signatures History screen
    And the page displays a list of previously executed signatures (if available)

  @US_VSHM_TC_02
  Scenario: Display List of Signatures on the Signatures History menu
    Given the user is on the Signatures History screen
    And the user has previously executed signatures in the EUDI Wallet
    When the user views the Signatures History screen
    Then the list of executed signatures is displayed with the following details:
      | Signature Date | Status (Success/Fail) | Remote Signing Service (QTSP) | Relying Party (if available) | Document Hash | Credential ID | Title of the signed document |

  @US_VSHM_TC_03
  Scenario: Empty List When No Signatures Exist
    Given the user is on the Signatures History screen
    And the user has not previously performed any signatures in the EUDI Wallet
    When the user views the Signatures History screen
    Then an empty list is displayed
    And an informative message appears: No signatures have been performed through the EUDI Wallet.

  @US_VSHM_TC_04
  Scenario: Sort Signatures by Date
    Given the user is on the Signatures History screen
    And there are multiple signature records available
    When the user sorts the list by date
    Then the signatures are reordered in ascending or descending order based on the selected date.

  @US_VSHM_TC_05
  Scenario: Filter Signatures by Date
    Given the user is on the Signatures History screen
    And there are multiple signature records available
    When the user filters the list by a specific date range
    Then only the signatures performed within the selected date range are displayed.