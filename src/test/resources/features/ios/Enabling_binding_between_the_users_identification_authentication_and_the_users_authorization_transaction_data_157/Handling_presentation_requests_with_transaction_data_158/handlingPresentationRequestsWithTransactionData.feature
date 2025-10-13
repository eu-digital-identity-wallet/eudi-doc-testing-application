@IOS @manual @US_HPRWTD
Feature: View and Authorize Transaction with EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/158

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user has received a presentation request from a Relying Party in either a cross or same device flow
    And the user has authenticated to the Remote Signing Service

  @US_HPRWTD_TC_01 @manual:Passed
  Scenario Outline: View presentation request from Relying Party
    Given the user views a presentation request from a Relying Party
    Then the wallet displays the presentation request which includes <Field> and <Details>
    Examples:
      | Field                 | Details                                                                                                                |
      | Name                  | The name of the requesting Relying Party                                                                               |
      | Attestations          | The attestation(s) requested by the Relying Party                                                                      |
      | Expand Details Option | The option to expand on the details for each requested attestation                                                     |
      | Unselect Option       | The option to unselect specific data elements from each requested attestation                                          |
      | Data to be signed     | Data to be signed/signature details (where 'type'='qes_authorization')                                                 |
      | Details Field         | A 'Details' field (mapped to label; free text defined by the requestor) (mandatory)                                    |
      | Location Field        | A 'Location' field (mapped to documentLocation_uri; including a URI where the file to be signed is located) (optional) |

  @US_HPRWTD_TC_02 @manual:Passed
  Scenario: User cancels data sharing process
    Given the user views a presentation request from a Relying Party
    When the user selects the back button
    Then the user is returned to the Home screen

  @US_HPRWTD_TC_03 @manual:Passed
  Scenario: User proceeds with data sharing flow
    Given the user views a presentation request from a Relying Party
    When the user selects the Share buttin
    Then the wallet requests the user to enter the PIN to proceed

  @US_HPRWTD_TC_04 @manual:Passed
  Scenario: User completes data sharing flow
    Given the user has entered the PIN to proceed with the data sharing flow
    Then the wallet displays a success screen which provides details about the data that were shared from the wallet
    And the wallet displays a Close button to return to the Home screen


