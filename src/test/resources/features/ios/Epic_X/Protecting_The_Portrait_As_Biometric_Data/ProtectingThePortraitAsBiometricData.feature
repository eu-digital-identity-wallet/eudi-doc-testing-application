@IOS @US_PTPABD @Q4_2026
Feature: Protecting the portrait as biometric data
  As a EUDI Wallet User
  I want the wallet to warn me when a presentation request includes my portrait and ask for confirmation before it is shared
  So that I understand that my portrait can be used as biometric data to identify me by my face

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/428

  @US_PTPABD_TC_01
  Scenario: Verify the corresponding biometric data notice for a request containing the portrait claim
    Given the user has access to the wallet
    And the wallet contains a document with the configured portrait claim
    When the wallet receives a presentation type request containing the portrait claim
    And the request screen is displayed
    Then the following notice is displayed on the request screen 'This request includes your portrait, which can be used as biometric data to identify you by your face'

  @US_PTPABD_TC_02
  Scenario: Verify that the biometric data notice is not displayed for requests without a portrait
    Given the user has access to the wallet
    And the wallet contains a document with the configured portrait claim
    When the wallet receives a presentation request that does not include the portrait claim
    And the request screen is displayed
    Then the portrait-specific biometric data notice is not displayed

  @US_PTPABD_TC_03
  Scenario: Verify portrait confirmation when the portrait is selected for sharing
    Given the wallet has received a presentation request
    And the request includes the configured portrait claim
    And the request screen is displayed
    And the portrait is included in the data selected for sharing
    When the user selects Share
    Then the portrait specific confirmation is displayed
    And the presentation awaits the user's confirmation
    And no requested document data is shared before the user confirms

  @US_PTPABD_TC_04
  Scenario: Verify the content and actions of the portrait confirmation
    Given the wallet has received a presentation request
    And the portrait is included in the data selected for sharing
    And the request screen is displayed
    When the user selects Share
    Then the portrait confirmation is displayed
    And the confirmation title is 'Share your portrait'
    And the confirmation displays the following message: 'Your portrait will be sent to this relying party. Share it only if you trust the relying party and the request needs it'
    And the confirmation provides a Share action
    And the confirmation provides a Cancel action
    And both actions are enabled

  @US_PTPABD_TC_05
  Scenario: Verify successful portrait sharing after explicit user confirmation
    Given the wallet has received a valid presentation request
    And the portrait is included in the data selected for sharing
    And the portrait confirmation is displayed
    When the user selects Share in the portrait confirmation
    Then the portrait confirmation is closed
    And the presentation continues to the applicable authorization step
    When the user successfully completes the required authorization
    Then the selected document data is shared with the relying party
    And the shared document data includes the selected portrait claim

  @US_PTPABD_TC_06
  Scenario: Verify navigation after cancelling portrait confirmation
    Given the wallet has received a presentation request
    And the portrait is included in the data selected for sharing
    And the portrait confirmation is displayed
    When the user selects Cancel in the portrait confirmation
    Then the portrait confirmation is closed
    And the original request screen is displayed
    And the original presentation request remains available
    And the user is not redirected to the PIN step
    And none of the selected document claims are transmitted to the relying party

  @US_PTPABD_TC_07
  Scenario: Verify that portrait confirmation is not displayed after deselecting the portrait
    Given the wallet has received a presentation request
    And the request includes the configured portrait claim
    And the selected data includes the portrait and other document claims
    When the user removes the portrait from the selected data
    And at least one other requested claim remains selected
    And the user selects Share
    Then the portrait-specific confirmation is not displayed
    And the presentation continues according to the standard sharing flow

  @US_PTPABD_TC_08
  Scenario: Verify standard sharing behaviour for a request without the portrait claim
    Given the wallet has received a valid presentation request
    And the request does not include the portrait claim
    And the request screen is displayed
    And the user has selected document claims for sharing
    When the user selects Share
    Then the portrait-specific confirmation is not displayed
    And the presentation continues according to the standard sharing flow
    When the user successfully completes the required authorization
    Then the selected document claims are shared
    And the shared document data does not include the portrait claim

  @US_PTPABD_TC_09
  Scenario: Verify that cancelling portrait confirmation does not bypass subsequent confirmation
    Given the wallet has received a presentation request
    And the portrait is selected for sharing
    And the portrait confirmation is displayed
    When the user selects Cancel in the portrait confirmation
    Then the original request screen is displayed
    And no requested document data is shared
    Given the portrait is included in the data selected for sharing
    When the user selects Share again
    Then the portrait-specific confirmation is displayed again
    And the user must explicitly confirm sharing the portrait
    And no requested document data is shared before confirmation