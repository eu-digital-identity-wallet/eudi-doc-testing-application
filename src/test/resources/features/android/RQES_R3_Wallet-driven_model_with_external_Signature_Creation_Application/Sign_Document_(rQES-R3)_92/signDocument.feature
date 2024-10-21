@ANDROID @manual @US_SD
Feature: Sign Document through EUDI Wallet
  As a EUDI Wallet User,
  I want to provide my explicit consent on signing a document through my EUDI Wallet
  So that I can ensure that I am the sole signer of the specific document.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/92

  @US_SD_TC_01
  Scenario: Retrieve Available User Credential IDs from QTSP
    Given the user is authenticated in the EUDI Wallet and QTSP
    When the EUDI Wallet retrieves the available User Credential IDs from the QTSP
    Then EUDI Wallet should present the available Credential IDs to the user

  @US_SD_TC_02
  Scenario: No Available Credential IDs in QTSP
    Given the EUDI Wallet has retrieved available Credential IDs from the QTSP
    When there are no Credential IDs enrolled for the user
    Then the EUDI Wallet should inform the user that they must enroll a Qualified Certificate to the QTSP
    And signing flow stops

  @US_SD_TC_03
  Scenario: Multiple Credential IDs Available
    Given there is more than one Credential ID available for the user
    When the EUDI Wallet prompts the user to select a preferred Credential ID
    Then the user selects the preferred Credential ID
    And EUDI Wallet proceeds to the next step in the signing flow

  @US_SD_TC_04
  Scenario: Single Credential ID Available
    Given there is only one available Credential ID
    When the EUDI Wallet retrieves the Credential ID details from the QTSP
    Then the EUDI Wallet presents the Credential ID details to the user
    And user confirms to proceed with the signing operation

  @US_SD_TC_05
  Scenario: User Aborts the Signing Operation
    Given the user is reviewing the Credential ID details in the EUDI Wallet
    When the user decides not to proceed
    Then the user can select the Abort operation option
    And EUDI Wallet should return the user to the main page

  @US_SD_TC_06
  Scenario: Navigate to QTSP Credential Authentication Page
    Given the user has confirmed the Credential ID details
    When the EUDI Wallet navigates the user to the selected QTSP (Credential) authentication page in the mobile web browser
    Then QTSP should provide a deep link to proceed to attestation presentation

  @US_SD_TC_07
  Scenario: Redirect from Deep Link to EUDI Wallet
    Given the QTSP has provided a deep link for attestation presentation
    When the user clicks the deep link
    Then deep link should redirect the user back to the EUDI Wallet

  @US_SD_TC_08
  Scenario: Request Attestation Presentation from EUDI Wallet
    Given the user is redirected to the EUDI Wallet from the QTSP
    When the EUDI Wallet informs the user that the QTSP requests to release the matching attestation (PID)
    Then the user should be able to review the request, and the screen should display the name of the QTSP.

  @US_SD_TC_09
  Scenario: No Matching Attestation Available
    Given the EUDI Wallet checks for matching attestations
    When there are no available matching attestations
    Then the EUDI Wallet should inform the user
    And the user should be able to return to the main page

  @US_SD_TC_10
  Scenario: User Consents to Release Attestation
    Given the EUDI Wallet has found a matching attestation for the QTSP’s request
    When the EUDI Wallet requests the user’s consent to release the attestation
    And the user authenticates using the 6-digit PIN
    Then EUDI Wallet should release the attestation to the QTSP

  @US_SD_TC_11
  Scenario: Unsuccessful Authentication in EUDI Wallet
    Given the EUDI Wallet requests authentication from the user
    When the user enters an incorrect 6-digit PIN
    Then the EUDI Wallet should display an error message
    And user should be given the option to retry authentication

  @US_SD_TC_12
  Scenario: User Aborts the Attestation Release Operation
    Given the user is prompted to authenticate and release the attestation
    When the user decides not to proceed
    Then the user should be able to select the "Abort operation" option
    And EUDI Wallet should return the user to the main page

  @US_SD_TC_13
  Scenario: QTSP Verifies Attestation
    Given the EUDI Wallet has released the attestation to the QTSP
    When the QTSP verifies the attestation
    Then the QTSP should inform the user of the successful verification
    And EUDI Wallet should display a confirmation message indicating the outcome

  @US_SD_TC_14
  Scenario: QTSP Cannot Verify Attestation
    Given the EUDI Wallet has released the attestation to the QTSP
    When the QTSP cannot verify the attestation
    Then the QTSP should display an error message informing the user of the failure
    And EUDI Wallet should display a message indicating the failed presentation

  @US_SD_TC_15
  Scenario: QTSP Signs the Document
    Given the QTSP has successfully verified the attestation
    When the QTSP receives the sign request and validates it
    Then the QTSP should sign the document
    And signed document should be returned to the EUDI Wallet

  @US_SD_TC_16
  Scenario: EUDI Wallet Stores the Signed Document
    Given the QTSP has signed the document and returned it
    When the EUDI Wallet receives the signed document
    Then the EUDI Wallet should store the signed document
    And EUDI Wallet should present the signed document to the user

  @US_SD_TC_17
  Scenario: Send Signed Document to Relying Party
    Given the document to be signed was retrieved from the Relying Party
    When the EUDI Wallet receives the signed document from the QTSP
    Then EUDI Wallet should send the signed document to the Relying Party service