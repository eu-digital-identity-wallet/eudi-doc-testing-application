@IOS @US_RIEIDCIIRCD
  Feature: Issuance of Employee ID Credential in EUDI Wallet
    As a EUDI Wallet User
    I want the employer to issue an employee ID credential and store it in my EUDI Wallet
    So that I can have a verifiable record of my employment status

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/179

    Background:
      Given the user has a valid EUDI Wallet on a mobile device
      And internet connectivity is available

    @US_RIEIDCIIRCD_TC_01
    Scenario: User logs into Employer Service
      Given the user opens the Employer Service
      When the user selects Login with PID
      Then the user is logged in successfully

    @US_RIEIDCIIRCD_TC_02
    Scenario: User requests Employee ID
      Given the user is logged in to the Employer Service
      When the user selects Issue Employee ID
      Then the Employer Service notifies that PDA1 and Tax residency attestations are required

    @US_RIEIDCIIRCD_TC_03
    Scenario: User scans QR code to provide attestations
      Given the Employer Service displays a QR code
      When the user scans the QR code with the Wallet
      Then the Wallet opens and prompts for authentication

    @US_RIEIDCIIRCD_TC_04
    Scenario: User authenticates successfully in the Wallet
      Given the Wallet prompts for a six-digit PIN
      When the user enters the correct PIN
      Then the Wallet is unlocked

    @US_RIEIDCIIRCD_TC_05
    Scenario: User fails authentication
      Given the Wallet prompts for a six-digit PIN
      When the user enters an incorrect PIN
      Then the Wallet displays an error and asks to retry

    @US_RIEIDCIIRCD_TC_06
    Scenario: User consents to share attestations
      Given the Wallet informs about Employer Service request for PDA1 and Tax residency
      When the user agrees to share the requested attestations
      Then the Wallet presents the attestations to the Employer Service

    @US_RIEIDCIIRCD_TC_07
    Scenario: Employer Service verifies attestations successfully
      Given the Employer Service has received the attestations
      Then the Employer Service confirms successful verification

    @US_RIEIDCIIRCD_TC_08
    Scenario: Employer Service cannot verify attestations
      Given the Employer Service has received the attestations
      Then the Employer Service displays an error message to the user

    @US_RIEIDCIIRCD_TC_09
    Scenario: User requests issuance of Employee ID
      Given attestations are verified successfully
      When the user selects Issue Employee ID
      Then the Employer Service displays a QR code to proceed

    @US_RIEIDCIIRCD_TC_10
    Scenario: User confirms issuance of Employee ID
      Given the Wallet scans the issuance QR code
      And the user authenticates successfully
      When the Employer Service offers to issue the Employee ID
      Then the user confirms the issuance

    @US_RIEIDCIIRCD_TC_11
    Scenario: Employee ID credential is stored in Wallet
      Given the Employer Service issues the Employee ID credential
      When the Wallet receives the credential
      Then the Wallet confirms successful issuance and storage

    @US_RIEIDCIIRCD_TC_12
    Scenario: Error during credential issuance
      Given the Employer Service sends Employee ID credential
      When an error occurs in Wallet
      Then the Wallet informs the user and does not store the credential