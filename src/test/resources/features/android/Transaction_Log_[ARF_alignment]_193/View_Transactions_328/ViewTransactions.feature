@ANDROID @US_VT @Q3_2026
Feature: View Transactions in History tab
  As a EUDI Wallet User
  I want to view any transaction in my EUDI Wallet
  So that I can review the details of my transaction

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/328

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_VT_TC_01 @manual:
  Scenario: User navigates to the History screen
    Given the user is on the Home screen
    When the user clicks the History button
    Then the user navigates to the Transactions screen
    And all transactions are displayed

  @US_VT_TC_02 @manual:
  Scenario Outline: User views credential presentation transaction details
    Given the user is on the History screen
    When the user selects a credential presentation transaction entry from the list
    Then the EUDI Wallet presents the following details by <Detail> and <Description>

    Examples:
      | Detail                                                                        | Description                                                                                                                  |
      | Relying Party name                                                            | The Interacting Party name with a verification badge next to it, for trusted Parties (if available)                          |
      | Relying Party information                                                     | URL, country and contact details (if available)                                                                              |
      | Intermediary RP information                                                   | Intermediary Relying Party name, contact details, country (if applicable)                                                    |
      | DateTime                                                                      | The presentation operation was performed in ISO8601 format                                                                   |
      | Status                                                                        | The presentation result (Completed or Not Completed), reason of non-completion shown where available                         |
      | Credential(s)                                                                 | Each credential details shared presented as an expandable/collapsible card; only attribute identifiers displayed (no values) |
      | Intended use                                                                  | The purpose and privacy policy link, where available                                                                         |
      | Transaction Data                                                              | Transactional data displayed only if explicitly required by the applicable technical specification                           |
      | "View previously attempted requests" and "Initiate Deletion Request" button   | The user can request deletion of personal data previously shared with the Relying Party (only for presentation transactions) |
      | "View previously attempted requests" and "Initiate Transaction report" button | The user can report any suspicious operations related to this presentation                                                   |

  @US_VT_TC_03 @manual:
  Scenario Outline: User views signing/sealing transaction details //Ignore
    Given the user is on the History screen
    When the user selects a signing sealing transaction entry from the list
    Then the EUDI Wallet presents the following details by <Detail> and <Description>

    Examples:
      | Detail          				| Description                                           |
      | DateTime        				| The datetime the operation was performed              |
      | Purpose         				| Purpose/type of the transaction, where applicable     |
      | Status          				| The transaction result (Completed or Not Completed)	|
      | Relying Party   				| The Interacting Party name                            |
      | signingTransactionIdentifier	| where applicable 										|
      | Document/data    				| The document or data signed or sealed, if available   |

  @US_VT_TC_04 @manual:
  Scenario Outline: User views credential (PID/attestation) issuance/re-issuance transaction details
    Given the user is on the History screen
    When the user selects a credential issuance or reissuance transaction entry from the list
    Then the EUDI Wallet presents the following details by <Detail> and <Description>

    Examples:
      | Detail                 | Description                                                                                                               |
      | DateTime               | The date and time of the transaction                                                                                      |
      | Status                 | The transaction result (Completed or Not Completed), reason of non-completion shown where available                       |
      | Interacting Party name | i.e. PID Provider or Attestation Provider name                                                                            |
      | Credential             | The credential requested and issued                                                                                       |
      | Re-issuance            | Whether User-triggered or automatically initiated by the Wallet Unit and Interacting Party contact details (if available) |

  @US_VT_TC_05 @manual:
  Scenario Outline: User views credential (PID/attestation) deletion transaction details
    Given the user is on the History screen
    When the user selects a credential deletion transaction entry from the list
    Then the EUDI Wallet presents the following details by <Detail> and <Description>

    Examples:
      | Detail          | Description                                            |
      | DateTime        | The date and time of the deletion                      |
      | Credential      | The credential deleted                                 |
      | Provider name   | The original PID Provider or Attestation Provider name |

  @US_VT_TC_06 @manual:
  Scenario: Details screen back button
    Given the user is viewing the details of a transaction entry
    When the user presses the back button on top of the screen
    Then the user is returned to the History page