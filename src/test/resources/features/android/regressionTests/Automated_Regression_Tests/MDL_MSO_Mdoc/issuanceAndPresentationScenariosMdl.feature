@ANDROID @US_ETESA @automated @execution_Q1_2026
Feature: Issuance and presentation - mDL

  Scenario Outline: Successful credential issuance and presentation with selective disclosure - Python Issuer - mDL
    Given the user initiates a <credential> issuance using the <issuer>

    Examples:
      | credential     | issuer | issuance_method | issue_scenario | verifier     | presentation_scenario | selective_disclosure |
      | mDL (MSO Mdoc) | Python | from list       | same device    | Web verifier | same device           | specific attributes  |