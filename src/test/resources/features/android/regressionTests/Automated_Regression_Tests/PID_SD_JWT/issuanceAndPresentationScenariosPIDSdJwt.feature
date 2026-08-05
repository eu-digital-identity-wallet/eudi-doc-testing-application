@ANDROID @US_ETESA @automated @PID_sdjwt
Feature: Issuance and presentation - PID (SD-JWT)

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer - PID (SD-JWT)
    Given the user initiates a <credential> issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario> for the <credential> and <issuance_method>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the <verifier>
    And the user shares <selective_disclosure>
    And the presentation is performed on a <presentation_scenario> for the <credential>
    Then the verifier verifies the credential successfully with <presentation_scenario> for <selective_disclosure>
    Examples:
      | credential   | issuer | issuance_method  | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | PID (SD-JWT) | Python | from list        | same device    | Web verifier | same device           | specific attributes  |
      | PID (SD-JWT) | Python | from list        | same device    | Web verifier | cross device          | specific attributes  |
      | PID (SD-JWT) | Python | credential offer | same device    | Web verifier | same device           | specific attributes  |
      | PID (SD-JWT) | Python | credential offer | same device    | Web verifier | cross device          | specific attributes  |
      | PID (SD-JWT) | Python | credential offer | cross device   | Web verifier | same device           | specific attributes  |
      | PID (SD-JWT) | Python | credential offer | cross device   | Web verifier | cross device          | specific attributes  |
