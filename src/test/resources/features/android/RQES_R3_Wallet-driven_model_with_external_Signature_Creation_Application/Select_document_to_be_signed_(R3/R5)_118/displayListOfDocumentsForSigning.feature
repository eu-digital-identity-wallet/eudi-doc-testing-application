@ANDROID @manual @US_DLODFS
Feature: Select Document to be Signed
  As a EUDI Wallet User
  I want to select and open a document from my EUDI Wallet device
  So that I can view the document details prior to signing

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/118

  @US_DLODFS_TC_01
  Scenario: Display list of documents for signing
    Given the user is authenticated in the EUDI Wallet
    And the user is on the main screen of the EUDI Wallet
    When the user selects the option Sign a Document
    Then the EUDI Wallet should display a list of available documents from the device

  @US_DLODFS_TC_02
  Scenario: Select a document from the list
    Given the EUDI Wallet displays a list of available documents
    When the user selects a document from the list
    Then the EUDI Wallet should retrieve the selected document

  @US_DLODFS_TC_03
  Scenario: View the selected document details
    Given the user has selected a document from the list
    When the EUDI Wallet retrieves the document
    Then the EUDI Wallet should display metadata such as file name and kind of file(ex.pdf)
    And the EUDI Wallet should display the document's content

  @US_DLODFS_TC_04
  Scenario: View the document's content
    Given the EUDI Wallet has retrieved the document
    When the user clicks on the view button next to the document
    Then the EUDI Wallet should display the document's content
