@IOS @US_ETESA @automated @execution_Q2_2026
Feature: Issuance and presentation - PID

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer - PID
    Given the user initiates a <credential> issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario> for the <credential>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the user shares <selective_disclosure>
    And the presentation is performed on a <presentation_scenario> for the <credential>
    Then the verifier verifies the credential successfully with <presentation_scenario> for <selective_disclosure>
    Examples:
      | credential     | issuer | issuance_method  | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | PID (MSO Mdoc) | Python | from list        | same device    | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Python | from list        | same device    | Web verifier | same device           | all attributes       |
      | PID (MSO Mdoc) | Python | from list        | same device    | Web verifier | cross device          | specific attributes  |
      | PID (MSO Mdoc) | Python | from list        | same device    | Web verifier | cross device          | all attributes       |
      | PID (MSO Mdoc) | Python | credential offer | same device    | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Python | credential offer | same device    | Web verifier | same device           | all attributes       |
      | PID (MSO Mdoc) | Python | credential offer | same device    | Web verifier | cross device          | specific attributes  |
      | PID (MSO Mdoc) | Python | credential offer | same device    | Web verifier | cross device          | all attributes       |
      | PID (MSO Mdoc) | Python | credential offer | cross device   | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Python | credential offer | cross device   | Web verifier | same device           | all attributes       |
      | PID (MSO Mdoc) | Python | credential offer | cross device   | Web verifier | cross device          | all attributes       |
      | PID (MSO Mdoc) | Python | credential offer | cross device   | Web verifier | cross device          | specific attributes  |


  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Kotlin Issuer - PID
    Given the user initiates a <credential> issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario> for the <credential>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the user shares <selective_disclosure>
    And the presentation is performed on a <presentation_scenario> for the <credential>
    Then the verifier verifies the credential successfully with <presentation_scenario> for <selective_disclosure>
    Examples:
      | credential     | issuer | issuance_method  | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | PID (MSO Mdoc) | Kotlin | from list        | same device    | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Kotlin | from list        | same device    | Web verifier | same device           | all attributes       |
      | PID (MSO Mdoc) | Kotlin | from list        | same device    | Web verifier | cross device          | specific attributes  |
      | PID (MSO Mdoc) | Kotlin | from list        | same device    | Web verifier | cross device          | all attributes       |
      | PID (MSO Mdoc) | Kotlin | credential offer | same device    | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Kotlin | credential offer | same device    | Web verifier | same device           | all attributes       |
      | PID (MSO Mdoc) | Kotlin | credential offer | same device    | Web verifier | cross device          | specific attributes  |
      | PID (MSO Mdoc) | Kotlin | credential offer | same device    | Web verifier | cross device          | all attributes       |
      | PID (MSO Mdoc) | Kotlin | credential offer | cross device   | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Kotlin | credential offer | cross device   | Web verifier | same device           | all attributes       |
      | PID (MSO Mdoc) | Kotlin | credential offer | cross device   | Web verifier | cross device          | all attributes       |
      | PID (MSO Mdoc) | Kotlin | credential offer | cross device   | Web verifier | cross device          | specific attributes  |