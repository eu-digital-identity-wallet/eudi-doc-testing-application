@ANDROID @automated @US_OIASD
Feature: Online Identification & Authentication (same-device)

  @US_OIASD_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the home screen

  @US_OIASD_TC_02 @before_01
  Scenario: User selects data to share
    Given user opens Verifier Application
    When user selects specific data to share
    Then user selects to be identified using EUDI Wallet

  @US_OIASD_TC_03 @before_01
  Scenario: User views and unselects data
    Given user selects to be identified using the EUDI Wallet
    When user views the data and can unselect any of them
    Then user presses the share button on wallet

  @US_OIASD_TC_04 @before_01 @release
  Scenario: User authorizes data disclosure
    Given user presses the share button
    When user authorizes the disclosure of the data
    And user is authenticated successfully
    And the user clicks done
    Then the user gets redirected to verifier and views the respond

  @US_OIASD_TC_05 @before_01
  Scenario: User adds document via QR code scanning
    Given the verifier has generated a QR code for presentation request
    When the user is on the Login screen
    And the user enters their PIN
    Then the user should see the home screen
    When the user clicks on Αuthenticate
    Then the user clicks the Online option
    Then the QR code scan should be activated
    When the user scans the pre-generated QR code
    Then the user clicks the share button
    Then the user succesfully shares the attestation

    @US_OIASD_TC_06 @before_01
    Scenario: Successful credential issuance and presentation with selective disclosure
      Given the user is in the Kotlin issuer
      When the user selects to issue a PID in the Kotlin issuer
      And the user clicks the wallet link
      And the details of the credential to be issued are presented Kotlin
      Then the user clicks the add button
      And the PID from Kotlin is displayed in the Documents

      Then the user opens the verifier app
      And the verifier requests a doc from the wallet user
      Then the Relying Party service redirects the user to the EUDI Wallet
      And the user presses share
      When user authorizes the disclosure of the data
      And user is authenticated successfully
      And the user clicks done
      Then the user gets redirected to verifier and views the respond