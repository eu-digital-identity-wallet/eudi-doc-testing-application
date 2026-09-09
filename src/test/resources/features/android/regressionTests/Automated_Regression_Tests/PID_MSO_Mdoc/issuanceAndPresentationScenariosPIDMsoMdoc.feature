@ANDROID @US_ETESA @automated @execution_Q2_2026 @PID_mso_mdoc
Feature: Issuance and presentation - PID (MSO Mdoc)

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer - PID (MSO Mdoc)
    Given the user initiates a <credential> issuance using the <issuer>
    And the issuance method is <issuance_method>

    Examples:
      | credential     | issuer | issuance_method | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | PID (MSO Mdoc) | Python | from list       | same device    | Web verifier | same device           | specific attributes  |
      | PID (MSO Mdoc) | Python | from list       | same device    | Web verifier | cross device          | specific attributes  |

