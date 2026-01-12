@ANDROID @manual @endtoend
Feature: EUDI Wallet – End to End Credential Issuance and Presentation

  @manual:passed
  Scenario Outline: Successful credential issuance and presentation with selective disclosure
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
      | issuer | issuance_method  | issue_scenario | verifier               | presentation_scenario | selective_disclosure         |  |
      | Python | from list        | same device    | Web verifier           | same device           | specific attributes          |  |
      | Python | from list        | same device    | Web verifier           | cross device          | specific attributes          |  |
      | Python | from list        | same device    | Web verifier           | cross device          | all attributes               |  |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) |  |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | all attributes (full)        |  |
      | Python | credential offer | same device    | Web verifier           | same device           | specific attributes          |  |
      | Python | credential offer | same device    | Web verifier           | same device           | all attributes               |  |
      | Python | credential offer | same device    | Web verifier           | cross device          | specific attributes          |  |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) |  |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        |  |
      | Python | credential offer | cross device   | Web verifier           | same device           | specific attributes          |  |
      | Python | credential offer | cross device   | Web verifier           | same device           | all attributes               |  |
      | Python | credential offer | cross device   | Web verifier           | cross device          | all attributes               |  |
      | Python | credential offer | cross device   | Web verifier           | cross device          | specific attributes          |  |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) |  |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        |  |
      | Kotlin | credential offer | same device    | Web verifier           | same device           | all attributes               |  |
      | Kotlin | credential offer | same device    | Web verifier           | cross device          | specific attributes          |  |
      | Kotlin | credential offer | same device    | Web verifier           | cross device          | all attributes               |  |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) |  |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        |  |
      | Kotlin | credential offer | cross device   | Web verifier           | same device           | specific attributes          |  |
      | Kotlin | credential offer | cross device   | Web verifier           | same device           | all attributes               |  |
      | Kotlin | credential offer | cross device   | Web verifier           | cross device          | specific attributes          |  |
      | Kotlin | credential offer | cross device   | Web verifier           | cross device          | all attributes               |  |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) |  |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        |  |

  @manual:failed
  Scenario Outline: Successful credential issuance and presentation with selective disclosure
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
      | issuer | issuance_method  | issue_scenario | verifier     | presentation_scenario | selective_disclosure |  |
      | Python | from list        | same device    | Web verifier | same device           | all attributes       |  |
      | Python | credential offer | same device    | Web verifier | cross device          | all attributes       |  |
      | Kotlin | credential offer | same device    | Web verifier | same device           | specific attributes  |  |