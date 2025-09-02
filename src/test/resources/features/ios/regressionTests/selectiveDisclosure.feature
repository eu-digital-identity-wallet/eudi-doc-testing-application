@IOS @automated @US_SD
Feature: Selective Disclosure

  @US_SD_TC_01 @before_01
  Scenario: View Requested Data from Verifier
    Given the user is in the verifier app
    When the verifier requests a doc from the wallet user
    Then the requestor of the data is displayed in the wallet
    And the document from which the data are requested is displayed

  @US_SD_TC_02 @before_01
  Scenario: Unselect Some Data
    Given the user has selected some data
    When the user unselects some of this data
#    Then a corresponding message is displayed

  @US_SD_TC_03 @before_01
  Scenario: Initiate Sharing Process
    Given the user has finalized data selection
    When the user clicks the share button
    Then the PIN field is displayed to authorize sharing

  @US_SD_TC_04 @before_01
  Scenario: Expand Verification Section
    Given the user views the document that is requested
    When the user clicks to view the document's details
    Then the expanded verification details are displayed

  @US_SD_TC_05 @before_01 @test
  Scenario: Completion of Process
    Given the expanded verification details are seen
    When the user clicks done
    Then the user gets redirected to verifier and views the respond