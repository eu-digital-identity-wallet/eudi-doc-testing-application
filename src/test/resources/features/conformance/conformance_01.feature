@WEB @automated @CONFORMANCE
Feature: OpenID Foundation Conformance Suite

  @CONFORMANCE_01
  Scenario: User opens the conformance suite and sees the login page
    Given the user opens the conformance suite
#    When the page loads
#    Then the login page should be displayed

  @CONFORMANCE_02
  Scenario: User logs in with Google account
    Given the conformance suite login page is displayed
    When the user proceeds with Google
    And the user enters email
    And the user enters password
    Then the user should be logged in successfully

  @CONFORMANCE_03
  Scenario: User creates new test plan
    Given the user logged in successfully
    When the user checks the show early version tests box
    Then the user selects a test plan

  @CONFORMANCE_04
  Scenario: User fills in the required fields
    Given the user has selected a test plan
    When the correct fields are displayed
    Then the user fills in the required fields
    Then configure test section appears

  @CONFORMANCE_05
  Scenario: User fills configure test form and starts the test
    Given configure test form is displayed
    When the user fills the configure test form
    And the user scrolls until the create test plan button
    And the user executes the test plan

  @CONFORMANCE_06
  Scenario: User verifies tests executed successfully
    Given the user executed the test plan
    When the test plan results page is displayed
    And the user runs the first test
    Then the user verifies test's success