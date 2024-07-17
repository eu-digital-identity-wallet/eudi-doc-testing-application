@ANDROID @automated @US_RIM
Feature: Request/Issue mDL

  @US_RIM_TC_01 @before_01
  Scenario: Log in successfully
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen manually

  @US_RIM_TC_02 @before_01
  Scenario: Select document for issuance
    Given the dashboard page is displayed on wallet
    When the user clicks the add doc button
    And the add document page is displayed automated
    And the user clicks the driving license button
    Then the user is redirected to the issuer service to issue mDL

  @US_RIM_TC_03 @before_01
  Scenario: Country and credential provider selection
    Given the issuer service -authentication method selection screen- is displayed
    When the user clicks on country selection and submits
    And the user clicks on Credential Provider FormEU and submits
    Then the provider form is displayed for the user to register personal data

  @US_RIM_TC_04 @before_01
  Scenario: Register personal data and view driving license
    Given a provider form is displayed for mdl
    When the user registers personal data
    Then a success message for mdl is displayed
    And the driving license is displayed in the wallet


