@IOS @manual @US_SD
Feature: Selective Disclosure

#  @US_SD_TC_01
#  Scenario: Unselect Data
#    Given the user has selected some data
#    When the user unselects some of this data
#    Then a corresponding message is displayed
#
#  @US_SD_TC_02
#  Scenario: Initiate Share Process
#    Given the user has data selected
#    When the user clicks on the 'SHARE' button
#    Then the PIN field is displayed to authorize sharing
#
#  @US_SD_TC_03
#  Scenario: Enter PIN Correctly
#    Given the user is prompted to enter a PIN
#    When the user enters the correct PIN
#    Then a successful message is displayed indicating authorized sharing

  @US_SD_TC_01
  Scenario: View Requested Data from Verifier
    Given the user is in the verifier app
    When the verifier requests a doc from the wallet user
    Then the requestor of the data is displayed in the wallet
    And the document from which the data are requested is displayed

  @US_SD_TC_02
  Scenario: View Actual Values of Requested Data
    Given the user is viewing the optional data
    When the user clicks the 'eye' icon
    Then the actual values of the data are displayed

  @US_SD_TC_03
  Scenario: Expand Verification Section
    Given the user is viewing the data request details
    When the user clicks to expand the 'Verification' section
    Then the expanded verification details are displayed

  @US_SD_TC_04
  Scenario: Unselect Some Data
    Given the user has selected some data
    When the user unselects some of this data
    Then a corresponding message is displayed

  @US_SD_TC_05
  Scenario: Initiate Sharing Process
    Given the user has finalized data selection
    When the user clicks the 'SHARE' button
    Then the PIN field is displayed to authorize sharing

  @US_SD_TC_06
  Scenario: Enter PIN Correctly for Data Sharing
    Given the user is prompted to enter a PIN for sharing
    When the user enters the correct PIN
    Then a successful message is displayed indicating the data has been authorized for sharing

