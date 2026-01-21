@ANDROID @manual @US_BRCD @report_analysis
Feature: EUDI Wallet – End to End Credential Issuance and Presentation

  @manual-result:<status>
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
      | issuer | issuance_method  | issue_scenario | verifier               | presentation_scenario | selective_disclosure         | status |
      | Python | from list        | same device    | Web verifier           | same device           | specific attributes          | passed |
      | Python | from list        | same device    | Web verifier           | same device           | all attributes               | failed |
      | Python | from list        | same device    | Web verifier           | cross device          | specific attributes          | passed |
      | Python | from list        | same device    | Web verifier           | cross device          | all attributes               | passed |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | from list        | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Python | credential offer | same device    | Web verifier           | same device           | specific attributes          | passed |
      | Python | credential offer | same device    | Web verifier           | same device           | all attributes               | passed |
      | Python | credential offer | same device    | Web verifier           | cross device          | specific attributes          | passed |
      | Python | credential offer | same device    | Web verifier           | cross device          | all attributes               | failed |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Python | credential offer | cross device   | Web verifier           | same device           | specific attributes          | passed |
      | Python | credential offer | cross device   | Web verifier           | same device           | all attributes               | passed |
      | Python | credential offer | cross device   | Web verifier           | cross device          | all attributes               | passed |
      | Python | credential offer | cross device   | Web verifier           | cross device          | specific attributes          | passed |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Python | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Kotlin | credential offer | same device    | Web verifier           | same device           | specific attributes          | failed |
      | Kotlin | credential offer | same device    | Web verifier           | same device           | all attributes               | passed |
      | Kotlin | credential offer | same device    | Web verifier           | cross device          | specific attributes          | passed |
      | Kotlin | credential offer | same device    | Web verifier           | cross device          | all attributes               | passed |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Kotlin | credential offer | same device    | Proximity Verifier App | proximity case        | all attributes (full)        | passed |
      | Kotlin | credential offer | cross device   | Web verifier           | same device           | specific attributes          | passed |
      | Kotlin | credential offer | cross device   | Web verifier           | same device           | all attributes               | passed |
      | Kotlin | credential offer | cross device   | Web verifier           | cross device          | specific attributes          | passed |
      | Kotlin | credential offer | cross device   | Web verifier           | cross device          | all attributes               | passed |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | specific attributes (custom) | passed |
      | Kotlin | credential offer | cross device   | Proximity Verifier App | proximity case        | all attributes (full)        | passed |

