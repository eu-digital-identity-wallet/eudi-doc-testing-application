@IOS @US_RSCWEWJCRCD
  Feature: Sign Contract with EUDI Wallet
    As a EUDI Wallet User
    I want to be able to sign the job contract utilizing my EUDI Wallet
    So that I can easily complete a digital signing process remotely and securely

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application-internal/issues/228

    Background:
      Given the user has a valid EUDI Wallet
      And internet connectivity is available

    @US_RSCWEWJCRCD_TC_01
    Scenario: User logs in to Employer Service
      Given the user opens the Employer Service
      When the user selects Login with PID
      Then the user is logged in successfully

    @US_RSCWEWJCRCD_TC_02
    Scenario: Employer Service shows QR code
      Given the user is logged in to the Employer Service
      When the user selects Sign job contract
      Then a QR code is displayed for the user to scan

    @US_RSCWEWJCRCD_TC_03
    Scenario: User scans QR code with EUDI Wallet
      Given a QR code is displayed on Employer Service
      When the user opens the Wallet and selects Scan QR
      Then the contract is retrieved and displayed in the Wallet

    @US_RSCWEWJCRCD_TC_04
    Scenario: User selects QTSP
      Given the contract is displayed in the Wallet
      When the Wallet prompts for QTSP selection
      Then the user selects a QTSP from the list

    @US_RSCWEWJCRCD_TC_05
    Scenario: User selects signing certificate
      Given a QTSP is selected
      When the Wallet prompts for signing certificate
      Then the user selects a certificate from the list

    @US_RSCWEWJCRCD_TC_06
    Scenario: User consents to share PID
      Given the Wallet requests PID consent
      When the user agrees to share PID
      Then the Wallet requests PIN authentication

    @US_RSCWEWJCRCD_TC_07
    Scenario: User authenticates successfully
      Given the Wallet requests a six-digit PIN
      When the user enters the correct PIN
      Then authentication is successful

    @US_RSCWEWJCRCD_TC_08
    Scenario: User enters wrong PIN
      Given the Wallet requests a six-digit PIN
      When the user enters an incorrect PIN
      Then the Wallet prompts the user to retry

    @US_RSCWEWJCRCD_TC_09
    Scenario: User cancels signing
      Given the contract is displayed in the Wallet
      When the user cancels the process
      Then the Wallet returns to the main screen

    @US_RSCWEWJCRCD_TC_10
    Scenario: User signs the contract
      Given the user is authenticated
      When the user selects Sign document
      Then the Wallet confirms the contract is signed

    @US_RSCWEWJCRCD_TC_11
    Scenario: Employer Service receives signed contract
      Given the Wallet has signed the contract
      When the document is passed to the Employer Service
      Then the Employer Service shows confirmation with a timestamp
