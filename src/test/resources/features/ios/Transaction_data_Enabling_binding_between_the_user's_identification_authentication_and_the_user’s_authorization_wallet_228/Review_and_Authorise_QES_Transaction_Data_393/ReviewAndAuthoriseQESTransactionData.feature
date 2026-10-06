@IOS @US_RAAQESTD @Q3_2026
Feature: Review and authorise QES transaction data
  As a EUDI Wallet User
  I want to review the transaction information related to the document I am about to sign and explicitly approve or reject the transaction
  So that I can understand and control what I am authorising before the document is signed

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/393

  @US_RAAQESTD_TC_01 @manual:Passed
  Scenario: Display QES transaction information before authorisation
    Given the user has initiated a QES signing operation
    When the Wallet presents the transaction information for review
    Then the Wallet displays the QES transaction type and applicable trust framework
    And the Wallet displays the document name or description

  @US_RAAQESTD_TC_02 @manual:Ignored
  Scenario: Provide access to the document to be signed
    Given the QES transaction contains a reference to the document to be signed where provided
    When the Wallet presents the transaction information
    Then the user can access the document associated with the signing transaction

  @US_RAAQESTD_TC_03 @manual:Passed
  Scenario: Display document and signing information when available
    Given the QES transaction contains additional document or signing information
    When the Wallet presents the transaction information for review
    Then the Wallet displays the applicable DTBSR information
    And the Wallet displays the signed attributes and requested credentials where applicable

  @US_RAAQESTD_TC_04 @manual:Ignored
  Scenario: Display additional QES transaction information when available
    Given optional QES transaction information is provided
    When the Wallet presents the transaction information for review
    Then the Wallet displays the OTP where provided
    And the Wallet displays the Response URI where applicable
    And the Wallet displays the document integrity status
    And the Wallet displays the signature format and conformance level where provided

  @US_RAAQESTD_TC_05 @manual:Passed
  Scenario: Approve the QES transaction
    Given the user has reviewed the transaction information and document
    When the user explicitly approves the QES transaction
    Then the Wallet continues the QES signing process

  @US_RAAQESTD_TC_06 @manual:Passed
  Scenario: Reject the QES transaction
    Given the user is reviewing the QES transaction
    When the user rejects or cancels the transaction
    Then the QES signing operation does not proceed

  @US_RAAQESTD_TC_07 @manual:Passed
  Scenario: Record the QES transaction in the Transaction Log
    Given the QES transaction has been completed
    When the user accesses the Transaction Log
    Then the QES transaction is available in the Transaction Log

  @US_RAAQESTD_TC_08 @manual:Ignored
  Scenario: Display recorded QES transaction information
    Given the QES transaction is available in the Transaction Log
    When the user opens the recorded QES transaction
    Then the Wallet displays the relevant Transaction Data associated with the transaction
    And the Wallet displays the user's recorded decision to approve or reject the transaction