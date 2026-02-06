@ANDROID @US_ETESA @automated
Feature: Automated Regression Tests

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer
    Given the user initiates a credential issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the presentation is performed on a <presentation_scenario>
    And the user shares <selective_disclosure>
    Then the verifier verifies the credential successfully
    Examples:
      | issuer | issuance_method  | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | Python | from list        | same device    | Web verifier | same device           | specific attributes  |
      | Python | from list        | same device    | Web verifier | same device           | all attributes       |
      | Python | from list        | same device    | Web verifier | cross device          | specific attributes  |
      | Python | from list        | same device    | Web verifier | cross device          | all attributes       |
      | Python | credential offer | same device    | Web verifier | same device           | specific attributes  |
      | Python | credential offer | same device    | Web verifier | same device           | all attributes       |
      | Python | credential offer | same device    | Web verifier | cross device          | specific attributes  |
      | Python | credential offer | same device    | Web verifier | cross device          | all attributes       |
      | Python | credential offer | cross device   | Web verifier | same device           | specific attributes  |
      | Python | credential offer | cross device   | Web verifier | same device           | all attributes       |
      | Python | credential offer | cross device   | Web verifier | cross device          | all attributes       |
      | Python | credential offer | cross device   | Web verifier | cross device          | specific attributes  |

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Kotlin Issuer
    Given the user initiates a credential issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the presentation is performed on a <presentation_scenario>
    And the user shares <selective_disclosure>
    Then the verifier verifies the credential successfully
    Examples:
      | issuer | issuance_method  | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | Kotlin | credential offer | same device    | Web verifier | same device           | specific attributes  |
      | Kotlin | credential offer | same device    | Web verifier | same device           | all attributes       |
      | Kotlin | credential offer | same device    | Web verifier | cross device          | specific attributes  |
      | Kotlin | credential offer | same device    | Web verifier | cross device          | all attributes       |
      | Kotlin | credential offer | cross device   | Web verifier | same device           | specific attributes  |
      | Kotlin | credential offer | cross device   | Web verifier | same device           | all attributes       |
      | Kotlin | credential offer | cross device   | Web verifier | cross device          | specific attributes  |
      | Kotlin | credential offer | cross device   | Web verifier | cross device          | all attributes       |
