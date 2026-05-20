@IOS @US_ETESM @Q1_2026
Feature: Issuance and presentation - Proximity case

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer
    Given user initiates credential issuance using the <issuer>
    And issuance method is <issuance_method>
    And issuance is performed on a <issue_scenario>
    When issuance flow is completed
    Then credential is stored in the Wallet
    When user presents the credential to the <verifier>
    And presentation is performed on a <presentation_scenario>
    And user shares <selective_disclosure>
    Then verifier verifies the credential successfully with <status>
    Examples:
      | issuer | issuance_method  | issue_scenario | verifier               | presentation_scenario | selective_disclosure         | status |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        | passed |

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Kotlin Issuer
    Given user initiates credential issuance using the <issuer>
    And issuance method is <issuance_method>
    And issuance is performed on a <issue_scenario>
    When issuance flow is completed
    Then credential is stored in the Wallet
    When user presents the credential to the <verifier>
    And presentation is performed on a <presentation_scenario>
    And user shares <selective_disclosure>
    Then verifier verifies the credential successfully with <status>
    Examples:
      | issuer | issuance_method  | issue_scenario | verifier               | presentation_scenario | selective_disclosure         | status |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
