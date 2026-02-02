@ANDROID @US_ETESM @manual @end2end
Feature: EUDI Wallet – End to End Credential Issuance and Presentation with status (Manual)

  @manual:Passed
  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer
    Given the user initiates a credential issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the presentation is performed on a <presentation_scenario>
    And the user shares <selective_disclosure>
    Then the verifier verifies the credential successfully with <status>
    Examples:
      | issuer | issuance_method  | issue_scenario | verifier               | presentation_scenario | selective_disclosure         | status |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        | passed |

  @manual:Passed
  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Kotlin Issuer
    Given the user initiates a credential issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the presentation is performed on a <presentation_scenario>
    And the user shares <selective_disclosure>
    Then the verifier verifies the credential successfully with <status>
    Examples:
      | issuer | issuance_method  | issue_scenario | verifier               | presentation_scenario | selective_disclosure         | status |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
