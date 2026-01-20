@ANDROID @automated @US_OIASDKOTL
Feature: EUDI Wallet – End to End Credential Issuance and Presentation Kotlin

  @endtoend @manual:Failed
  Scenario: Kotlin credential offer same device Web verifier same device specific attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a same device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a same device
    And the user shares specific attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer same device Web verifier same device all attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a same device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a same device
    And the user shares all attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer same device Web verifier cross device specific attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a same device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a cross device
    And the user shares specific attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer same device Web verifier cross device all attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a same device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a cross device
    And the user shares all attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer same device Proximity Verifier App proximity case specific attributes custom
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a same device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Proximity Verifier App
    And the presentation is performed on a proximity case
    And the user shares specific attributes (custom)

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer same device Proximity Verifier App proximity case all attributes full
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a same device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Proximity Verifier App
    And the presentation is performed on a proximity case
    And the user shares all attributes (full)

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer cross device Web verifier same device specific attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a cross device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a same device
    And the user shares specific attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer cross device Web verifier same device all attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a cross device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a same device
    And the user shares all attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer cross device Web verifier cross device specific attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a cross device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a cross device
    And the user shares specific attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer cross device Web verifier cross device all attributes
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a cross device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Web verifier
    And the presentation is performed on a cross device
    And the user shares all attributes

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer cross device Proximity Verifier App proximity case specific attributes custom
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a cross device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Proximity Verifier App
    And the presentation is performed on a proximity case
    And the user shares specific attributes (custom)

  @endtoend @manual:Passed
  Scenario: Kotlin credential offer cross device Proximity Verifier App proximity case all attributes full
    Given the user initiates a credential issuance using the Kotlin
    And the issuance method is credential offer
    And the issuance is performed on a cross device
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    When the user presents the credential to the Proximity Verifier App
    And the presentation is performed on a proximity case
    And the user shares all attributes (full)