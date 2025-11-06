# Purpose of this document

The purpose of this document is to outline the consolidated Security
Requirements and Controls for the EUDI Wallet Reference Implementation,
as those are defined by the ARF \[R02\], the corresponding ISO Standards
\[R01\], \[R04\] and Specifications \[R05\], \[R06\]. This document is
an extension of the Security Requirements and Controls developed under
Specific Contract 01 and it has been updated to reflect the development
progress under the Specific Contract 03.

Through the detailed presentation of the Functional and Non-Functional
Requirements, the objective is to provide a clear understanding of the
security requirements and controls taken into consideration and
implemented by the development team during this iteration of the
document. This document will serve as a traceability matrix for DG CNECT
in terms of the security controls implemented during this iteration of
the document.

It should be noted that the Security Requirements described in this
document may be subject to change based on planning/scoping decisions of
the project, as well as updates in the corresponding ISO Standards or
technical protocols.

Finally, it is advised that the document is read in conjunction with
other relevant EUDI Wallet material, such as the 'Architectural
Reference Framework' \[R02\] which provides the technical framework
needed to develop an interoperable EUDI Wallet Reference Implementation
based on common standards and best practices, ISO/IEC 18013-5 \[R01\],
ISO/IEC 23220-4 \[R04\], the open specifications \[R05\], \[R06\] and
the EUDI Wallet -- Reference Implementation Roadmap hosted in GitHub
\[R08\].

# Reference and applicable documents

## Reference documents

<table>
<colgroup>
<col style="width: 10%" />
<col style="width: 30%" />
<col style="width: 36%" />
<col style="width: 21%" />
</colgroup>
<tbody>
<tr class="odd">
<td><em><strong><span class="smallcaps">ID</span></strong></em></td>
<td><em><strong><span class="smallcaps">Reference
ID</span></strong></em></td>
<td><em><strong><span class="smallcaps">Document
Title</span></strong></em></td>
<td><em><strong><span
class="smallcaps">Version</span></strong></em></td>
</tr>
<tr class="even">
<td>R01</td>
<td>ISO/IEC 18013-5</td>
<td>Personal identification - ISO-compliant driving licence - Part 5:
Mobile driving licence (mDL) application</td>
<td>1.00</td>
</tr>
<tr class="odd">
<td>R02</td>
<td>ARF</td>
<td>EUDI Architecture and Reference Framework</td>
<td>2.5</td>
</tr>
<tr class="even">
<td>R03</td>
<td>ISO/IEC 27001:2022</td>
<td>ISO/IEC 27001:2022 Information Security Management Systems</td>
<td>3</td>
</tr>
<tr class="odd">
<td>R04</td>
<td>ISO/IEC 23220-4</td>
<td><p>ISO/IEC 23220-4: Building blocks for identity</p>
<p>management via mobile devices - Part 4: Protocols and services
for</p>
<p>operational phase</p></td>
<td>DRAFT</td>
</tr>
<tr class="even">
<td>R05</td>
<td>OpenID4VP</td>
<td>OpenID for Verifiable Presentations – draft 20</td>
<td>1.0</td>
</tr>
<tr class="odd">
<td>R06</td>
<td>OpenID4VCI</td>
<td>OpenID for Verifiable Credential Issuance</td>
<td>1.0</td>
</tr>
<tr class="even">
<td>R07</td>
<td>ISO/IEC 18013 series</td>
<td><p>ISO/IEC 18013-1:2018</p>
<p>ISO/IEC 18013-2:2020</p>
<p>ISO/IEC 18013-3:2017</p>
<p>ISO/IEC 18013-4:2019</p>
<p>ISO/IEC 18013-5:2021</p></td>
<td>N/A</td>
</tr>
<tr class="odd">
<td>R08</td>
<td>EUDI Wallet – Reference Implementation Roadmap</td>
<td><a
href="https://github.com/orgs/eu-digital-identity-wallet/projects/24">EUDIW
- Quarter View · EUDI Wallet - Reference Implementation Roadmap
(github.com)</a></td>
<td>N/A</td>
</tr>
<tr class="even">
<td>R09</td>
<td>Cloud Signature Consortium</td>
<td>Architectures and protocols for remote signature applications</td>
<td>2.0.0.1</td>
</tr>
<tr class="odd">
<td>R10</td>
<td>DC4EU_BBP</td>
<td>DC4EU – Business Blueprint (BBP)</td>
<td>5.1</td>
</tr>
<tr class="even">
<td>R11</td>
<td>Security Requirements</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-reference-implementation-architecture/blob/main/security-requirements.md">security-requirements.md
(GitHub)</a></td>
<td></td>
</tr>
</tbody>
</table>

Table 2‑1: Reference Documents

# Terminology

## Abbreviations and acronyms

A list of the principal abbreviations and acronyms used is nonetheless
provided here for a better understanding of this document:

| ***[Acronym]*** | ***[Definition]***              |
|-----------------------------|---------------------------------------------|
| ARF                         | Architecture and Reference Framework        |
| ECDH                        | Elliptic Curve Diffie-Hellman key agreement |
| DS Certificate              | Document Signer Certificate                 |
| ECDSA                       | Elliptic Curve Digital Signature Algorithm  |
| EdSA                        | Edwards-curve digital signature algorithm   |
| EUDI                        | European Digital Identity                   |
| EHIC                        | European Health Insurance Card              |
| HTTPS                       | Hypertext Transfer Protocol Secure          |
| IACA                        | Issuing Authority Certificate Authority     |
| JARM                        | JWT Secured Authorization Response Mode     |
| JSON                        | JavaScript Object Notation                  |
| JWT                         | JSON Web Token                              |
| LoA                         | Level of Assurance                          |
| mDL                         | Mobile Driving License                      |
| MFA                         | Multi-Factor Authentication                 |
| MSO                         | Mobile Security Object                      |
| MSTG                        | Mobile Security Testing Guide               |
| oAuth                       | Open Authentication                         |
| PDA1                        | Portable Document A1                        |
| QES                         | Qualified Electronic Signature              |
| QR-code                     | Quick Response code                         |
| QTSP                        | Qualified Trusted Service Provider          |
| rQES                        | Remote Qualified Electronic Signature       |
| TBD                         | To Be Defined                               |
| TLS                         | Transport Layer Security                    |
| VP                          | Verifiable Presentation                     |

Table 3‑1: List of Abbreviations and Acronyms

# Audience

This document is intended to be used by all stakeholders involved in the
EUDI Wallet Reference Implementation development lifecycle.

# Security Requirements

## Functional Requirements

### PID and mDL

The EUDI Wallet Reference Implementation meets the security and privacy
recommendations as described in the ARF \[R02\] by ensuring compliance
with the ISO/IEC 18013 international standard series \[R07\].

Mobile Driving Licence (mDL) is a driving licence that fulfils at least
the same function as an Identification Licence (IDL) but, instead of
being paper or plastic based. Person Identification Data (PID) refers to
information used to uniquely identify an individual. This data includes
various attributes, such as name, date of birth, etc. End users of the
EUDIW are defined as natural or legal persons using the EUDIW to
receive, store and share attestations like PIDs.

Below, the minimum acceptable set of security requirements to maintain a
level of assurance along with interoperability, as those have been
identified by the Architecture Reference Framework \[R02\].

#### FSRQ-001

| Requirement ID     | FSRQ-001                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Data Integrity and Authenticity                                                                                                                                                                                                                                                                                                                                                             |
| Description        | PID attestation must include necessary information for performing a data integrity check.                                                                                                                                                                                                                                                                                                                   |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                   |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                                                                                                 |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | EPIC-001 - Online Identification & Authentication                                                                                                                                                                                                                                                                                                                                                           |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-342)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                                                                                                                                      |

#### FSRQ-002

| Requirement ID     | FSRQ-002                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Validity                                                                                                                                                                                                                                                                                                                                                                                    |
| Description        | PID attestation must include all necessary information for performing validity status checks.                                                                                                                                                                                                                                                                                                               |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                   |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                                                                                                 |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                                                                                                                                                                                                                           |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-342)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                                                                                                                                      |

#### FSRQ-003

| Requirement ID     | FSRQ-003                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Encoding                                                                                                                                                                                                                                                                                                                                                                                    |
| Description        | PID attestation must use signatures and encryption formats as detailed in JOSE (JSON Object Signing and Encryption) RFCs and COSE (CBOR Object Signing and Encryption) RFCs.                                                                                                                                                                                                                                |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                   |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                                                                                                 |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                                                                                                                                                                                                                           |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-342)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                                                                                                                                      |

#### FSRQ-004

| Requirement ID     | FSRQ-004                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Holder Verification                                                                                                                                                                                                                                                                                                                                                                         |
| Description        | PID attestation must include all necessary information for verifying the holder binding by a Relying Party.                                                                                                                                                                                                                                                                                                 |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                   |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                                                                                                 |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                                                                                                                                                                                                                           |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-342)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                                                                                                                                      |

#### FSRQ-005

| Requirement ID     | FSRQ-005                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation SOG-IS ACM Compliance                                                                                                                                                                                                                                                                                                                                                                       |
| Description        | PID attestation must use signature and encryption algorithms in accordance with SOG-IS ACM (Smartcard Operating System Common Criteria Application Software Protection Profile).                                                                                                                                                                                                                            |
| Status             | In Progress                                                                                                                                                                                                                                                                                                                                                                                                 |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                                                                                                 |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                                                                                                                                                                                                                           |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-342)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                                                                                                                                      |

#### FSRQ-006

| Requirement ID     | FSRQ-006                                                                                                               |
|--------------------|------------------------------------------------------------------------------------------------------------------------|
| Name               | mDL Data Integrity and Authenticity                                                                                    |
| Description        | mDL must contain the necessary information for performing a data integrity check.                                      |
| Status             | Completed                                                                                                              |
| Source             | ARF \[R02\]                                                                                                            |
| Priority           | Must                                                                                                                   |
| Epic               | EPIC 002 - Proximity Data Sharing                                                                                      |
| User Story         | [US-006](https://jira.intrasoft-intl.com/browse/EUDIW-279), [US-007](https://jira.intrasoft-intl.com/browse/EUDIW-280) |
| Security Test Case | US06.STC001, US06.STC002, US06.STC003, US06.STC004, US07.STC001, US07.STC002, US07.STC003, US07.STC004                 |

#### FSRQ-007

| Requirement ID     | FSRQ-007                                                                                                                                                                                                                                      |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Encoding                                                                                                                                                                                                                                  |
| Description        | PID shall be encoded as SD-JWT-VC.                                                                                                                                                                                                            |
| Status             | In progress                                                                                                                                                                                                                                   |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                   |
| Priority           | Shall                                                                                                                                                                                                                                         |
| Epic               | [Update PID in alignment with ARF v1.8 (Rulebook v3.01) \#186](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/1586)                                                                                          |
| User Story         | Users should be able to issue a PID in SD-JWT-VC format as per the normal issuance flow (e.g. as described in \#[Using the PID (v1.5) in the RI \#167](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/1567)) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                        |

#### FSRQ-008

| Requirement ID     | FSRQ-008                                                                                                                                                                                                                                                                                   |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Provider Information                                                                                                                                                                                                                                                                   |
| Description        | PID attestation must contain the necessary information to identify the PID Provider.                                                                                                                                                                                                       |
| Status             | Completed                                                                                                                                                                                                                                                                                  |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                |
| Priority           | Must                                                                                                                                                                                                                                                                                       |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                                                                                                          |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                     |

#### FSRQ-009

| Requirement ID     | FSRQ-009                                                                                                                                                   |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Holder Data Model Compatibility                                                                                                            |
| Description        | PID attestation must be issued in accordance with both the data model specified in ISO/IEC 18013-5:2021 and the W3C Verifiable Credentials Data Model 1.1. |
| Status             | Completed                                                                                                                                                  |
| Source             | ARF \[R02\]                                                                                                                                                |
| Priority           | Must                                                                                                                                                       |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                          |
| User Story         | [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269)                                             |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                     |

### (Q)EAA

Electronic Attestation of Attributes (EAA) is an attestation in
electronic form that allows the authentication of features,
characteristics or qualities of a natural or legal person or of an
entity. These attestations are issued by a trusted authority and are
used where a high level of assurance about the identity or attributes of
the holder is required.

The attestations are Qualified ((Q)EAA), meaning that they meet specific
regulatory standards and requirements for electronic signatures or
credentials, ensuring their validity and trustworthiness. This process
also, involves cryptographic methods to bind the attested attributes to
the holder securely.

Below, the minimum acceptable set of security requirements recorded for
the (Q)EAA as those are aligned with the EU Digital Identity Wallet
Reference Implementation and its ecosystem.

#### FSRQ-010

| Requirement ID     | FSRQ-010                                                                                                               |
|--------------------|------------------------------------------------------------------------------------------------------------------------|
| Name               | (Q)EAA Attestation Validity                                                                                            |
| Description        | mDL must contain all the information required to perform validity status checks on the (Q)EAA.                         |
| Status             | Completed                                                                                                              |
| Source             | ARF \[R02\]                                                                                                            |
| Priority           | Must                                                                                                                   |
| Epic               | EPIC 002 - Proximity Data Sharing                                                                                      |
| User Story         | [US-006](https://jira.intrasoft-intl.com/browse/EUDIW-279), [US-007](https://jira.intrasoft-intl.com/browse/EUDIW-280) |
| Security Test Case | Pending.                                                                                                               |

#### FSRQ-011

| Requirement ID     | \(Q\) FSRQ-011                                                                                                                                                                                                                                                                                   |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | (Q)EAA Signature and Encryption Formats                                                                                                                                                                                                                                                          |
| Description        | (Q)EAA (Qualified and Non-Qualified Electronic Attestation of Attributes) should use one of the signature and encryption formats detailed in JOSE (JSON Object Signing and Encryption) RFCs or COSE (CBOR Object Signing and Encryption) RFCs, based on the data model used for the attestation. |
| Status             | Completed                                                                                                                                                                                                                                                                                        |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                      |
| Priority           | Should                                                                                                                                                                                                                                                                                           |
| Epic               | EPIC 002 - Proximity Data Sharing                                                                                                                                                                                                                                                                |
| User Story         | [US-006](https://jira.intrasoft-intl.com/browse/EUDIW-279), [US-007](https://jira.intrasoft-intl.com/browse/EUDIW-280)                                                                                                                                                                           |
| Security Test Case | Pending.                                                                                                                                                                                                                                                                                         |

#### FSRQ-012

| Requirement ID     | FSRQ-012                                                                                                                                                                                                                                     |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | (Q)EAA SOG-IS ACM Compliance                                                                                                                                                                                                                 |
| Description        | (Q)EAA (Qualified and Non-Qualified Electronic Attestation of Attributes) should use signature and encryption algorithms in accordance with SOG-IS ACM (Smartcard Operating System Common Criteria Application Software Protection Profile). |
| Status             | In Progress                                                                                                                                                                                                                                  |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                  |
| Priority           | Should                                                                                                                                                                                                                                       |
| Epic               | EPIC 002 - Proximity Data Sharing                                                                                                                                                                                                            |
| User Story         | [US-006](https://jira.intrasoft-intl.com/browse/EUDIW-279), [US-007](https://jira.intrasoft-intl.com/browse/EUDIW-280)                                                                                                                       |
| Security Test Case | Pending.                                                                                                                                                                                                                                     |

### Proximity Use Case

EUDI Wallet Reference Implementation meets the ISO/IEC 18013-5 \[R01\]
Standard security and privacy recommendations as mentioned in the ARF
\[R02\]. Compliance with the ISO/IEC 18013-5 \[R01\] international
standard ensures mobile IDs are secure and accepted globally as a legal
form of identification. The standard provides mechanisms to obtain and
trust the data of a Mobile Driving License (mDL). It uses Public Key
Cryptography and Public Key Infrastructure to ensure the integrity of an
EUDIW's data and its secure verification. It uses encryption and message
authentication methods that protect against cloning, eavesdropping and
unauthorized access (See Appendix A-Annex E- Ε.4 Protection of keys and
mDL data). This ensures the confidentiality, integrity, and authenticity
of the data exchange between the mDL and the Verifier.

The Proximity Use Case Scenario describes that the Holder will show a QR
code to a Verifier. The connections are secured by standardized key
exchange and encryption of the transport of data. Once the Verifier
obtains the cryptographic key, it creates its own ephemeral keypair and
establishes an encrypted and authenticated, secure wireless channel. The
use of ephemeral session keys prevents the collusion of Verifiers to
track Holder's usage information. In terms of track usage, no
transaction information is logged or uploaded in any form that permits
awareness to issuing authorities or technology providers (See Appendix
A-Annex E- E. Tracking mDL usage). Verifiers can then validate that the
received data is authentic and unchanged. The process proves to the
Verifiers that the data was not cloned from another, different device.

Social distancing can be also maintained. Proximity exchange can begin
with a QR code and once the initial token is exchanged, then the data
can be shared over Bluetooth. The ID information exchange is based
entirely on no-contact and without the Holder ever ceding possession of
their phone.

ISO/IEC 18013-5 \[R01\] was developed based on privacy-by-design
principles (See Appendix A-Annex E- E.2 Achieving privacy for the mDL
holder). Single-use encryption and security mechanisms within ISO/IEC
18013-5 \[R01\] ensure that nobody can eavesdrop transaction sessions.
The reader and mDL exchange key material ensure that only they can
participate in the conversation, and the conversation can only happen
once.

Another important aspect is that the amount of data presented can be
minimized (See Appendix A-Annex E- E.10 Collection limitation). EUDI
Wallet prototype supports dynamic selective disclosure provisions. This
means that the user selects what data would like to release which
minimizes the potential abuse and increases the personal safety of the
users. Lastly, EUDI Wallet Prototype does not respond with any
additional data other than the ones that the Verifier requested, and the
Holder is always able to grant consent to share the requested
information (See Appendix A-Annex E- E.8 Collection limitation).

EUDI Wallet prototype follows consent rules for releasing data
principles (See Appendix 1-Annex E- E.11 mDL holder consent). In the
Proximity Use Case Scenario, the Verifier requests data and the EUDI
Wallet prototype is responsible to gather consent from the Holder to
release the requested data elements and to validate the Issuer signature
on those data elements. Furthermore, it provides full transparency (See
Appendix A-Annex E- E.3 Transparency of data storage and use) to the
Holder about all the features supported and the data that reside on the
device, what data is shared and how the data is stored. EUDI Wallet
Prototype users can see what information will be collected and have the
option to leave the flow if they do not wish to share certain
information.

Multiple transaction signatures of a single EUDI Wallet Protype are also
distinct and not linkable. A different transaction with a set of
attributes of a specific Holder are not linkable to any previous
transactions. In addition to that, anonymity remains to any Verifier
(See Appendix A-Annex E- E.6 Anonymity and unlikability).

Finally, the use of ISO/IEC 18013-5 \[R01\] security and privacy
recommendations in the implementation of the EUDI Wallet prototype
stands for international use and mutual recognition of the ISO-compliant
driving licence without impeding individual countries/states in applying
their privacy rules.

#### FSRQ-013

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-013</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Session Encryption</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Session encryption using standard ephemeral key ECDH to establish
session keys for authenticated symmetric encryption between the EUDIW
and the Verifier.<br />
All the ephemeral keys should be stored securely for a short period of
time in memory only and then should be destroyed.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td>EPIC 002 - Proximity Data Sharing</td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://jira.intrasoft-intl.com/browse/EUDIW-279">US-006</a>, <a
href="https://jira.intrasoft-intl.com/browse/EUDIW-280">US-007</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>US06.STC005, US07.STC005</td>
</tr>
</tbody>
</table>

#### FSRQ-014

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-014</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Issuer Data Authentication</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Confirm that the EUDIW data is issued by the issuing authority and
that it has not changed since issuance by validating the EUDIW data
using the MSO, the MSO using DS certificate and the DS certificate using
IACA root public key.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td>EPIC 002 - Proximity Data Sharing</td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://jira.intrasoft-intl.com/browse/EUDIW-279">US-006</a>, <a
href="https://jira.intrasoft-intl.com/browse/EUDIW-280">US-007</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td><p>US06.STC001, US06.STC002, US06.STC003,</p>
<p>US07.STC001, US07.STC002, US07.STC003</p></td>
</tr>
</tbody>
</table>

#### FSRQ-015

| Requirement ID     | FSRQ-015                                                                                                                                                                                                            |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | EUDIW Authentication                                                                                                                                                                                                |
| Description        | Perform EUDIW authentication using ECDSA/EdSA signature. To authenticate the EUDIW with EUDIW ECDSA/EdDSA authentication, the EUDIW signs the device authentication data with the EUDIW authentication private key. |
| Status             | Completed                                                                                                                                                                                                           |
| Source             | ISO/IEC 18013-5 \[R01\]                                                                                                                                                                                             |
| Priority           | Must                                                                                                                                                                                                                |
| Epic               | EPIC 002 - Proximity Data Sharing                                                                                                                                                                                   |
| User Story         | [US-006](https://jira.intrasoft-intl.com/browse/EUDIW-279), [US-007](https://jira.intrasoft-intl.com/browse/EUDIW-280)                                                                                              |
| Security Test Case | US06.STC004, US07.STC004                                                                                                                                                                                            |

#### FSRQ-016

| Requirement ID     | FSRQ-016                                                                                                                                                                                                                                                                                                                             |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | mDL Selective Disclosure                                                                                                                                                                                                                                                                                                             |
| Description        | mDL should enable Selective Disclosure of attributes, either through SD-JWT or Mobile Security Object (ISO/IEC 18013-5) \[R01\] scheme, based on the data model used for the attestation.                                                                                                                                            |
| Status             | Completed                                                                                                                                                                                                                                                                                                                            |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                          |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                 |
| Epic               | EPIC 002 - Proximity Data Sharing                                                                                                                                                                                                                                                                                                    |
| User Story         | [US-006](https://jira.intrasoft-intl.com/browse/EUDIW-279?jql=project%20%3D%20EUDIW%20AND%20issuetype%20%3D%20Story%20AND%20fixVersion%20%3D%20%22R0.1%20(M3)%22), [US-007](https://jira.intrasoft-intl.com/browse/EUDIW-280?jql=project%20%3D%20EUDIW%20AND%20issuetype%20%3D%20Story%20AND%20fixVersion%20%3D%20%22R0.1%20(M3)%22) |
| Security Test Case | US006, US007.STC001, US006, US007.STC006, US015.STC002, US015.STC002                                                                                                                                                                                                                                                                 |

### Online Identification and Authentication Use Case

The implementation of remote presentation flows, i.e. Identification and
Authentication, is based on the OpenID4VP \[R05\] specification (draft
24 as of Q1 2025) which defines the interactions between the User, the
Verifiers and the Wallet in two different flows. The first flow is the
Same device, where the wallet and the verifier are located in the same
device, and the Cross Device where the Wallet and Verifier are located
in different devices.

#### FSRQ-017

| Requirement ID     | FSRQ-017                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Secure Communication                                                                                                                                                                                                                                                                                                                                                                                        |
| Description        | EUDIW must use secure communication protocols for all communications. Ensure that EUDIW does not establish any communication channels which do not use TLS and HTPS protocols.                                                                                                                                                                                                                              |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                   |
| Source             | ISO/IEC 23220-4 \[R04\]                                                                                                                                                                                                                                                                                                                                                                                     |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                                                                                                                                                                                                                           |
| User Story         | [[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-274)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-008](https://jira.intrasoft-intl.com/browse/EUDIW-518), [[US-009](https://jira.intrasoft-intl.com/browse/EUDIW-269)](https://jira.intrasoft-intl.com/browse/EUDIW-269), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-342)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | US03.STC001, US008.STC001, US015.STC001, US015.STC004                                                                                                                                                                                                                                                                                                                                                       |

#### FSRQ-018

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-018</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secured Authorization Request</td>
</tr>
<tr class="even">
<td>Description</td>
<td>EUDIW must accept authorization requests which are integrity
protected, their source of communication is authenticated, and
confidentiality is ensured.<br />
JWT-Secured Authorization Requests allow the request to be signed with
JSON Web Signature (JWS) and encrypted with JSON Web Encryption (JWE) so
that the integrity, source authentication, and confidentiality
properties of the authorization request are attained. The request can be
sent by value or by reference.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 23220-4 [R04]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td>EPIC 001 - Online Identification &amp; Authentication</td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://jira.intrasoft-intl.com/issues/?jql=labels+%3D+US-003">US-003</a>,
<a
href="https://jira.intrasoft-intl.com/browse/EUDIW-1261"><span>US-015</span></a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>US03.STC002, US03.STC003, US015.STC002, US015.STC003</td>
</tr>
</tbody>
</table>

#### FSRQ-019

| Requirement ID     | FSRQ-019                                                                                                                                                                                  |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Secured Authorization Response                                                                                                                                                            |
| Description        | EUDIW must sign, or sign and encrypt the Authorization Response. The implementations may use JWT Secured Authorization Response Mode for OAuth 2.0 (JARM).                                |
| Status             | Completed                                                                                                                                                                                 |
| Source             | ISO/IEC 23220-4 \[R04\]                                                                                                                                                                   |
| Priority           | Must                                                                                                                                                                                      |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                         |
| User Story         | [US-003](https://jira.intrasoft-intl.com/issues/?jql=labels+%3D+US-003), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-1261)](https://jira.intrasoft-intl.com/browse/EUDIW-1261) |
| Security Test Case | US03.STC002, US03.STC003, US03.STC004, US015.STC002, US015.STC003, US015.STC004                                                                                                           |

#### FSRQ-020

| Requirement ID     | FSRQ-020                                                                                                                                                                                                 |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Secured VP Token                                                                                                                                                                                         |
| Description        | EUDIW must link every Verifiable Presentation returned to the Verifier in the VP Token with a cryptographically random number with sufficient entropy which was received with the authorization request. |
| Status             | Completed                                                                                                                                                                                                |
| Source             | ISO/IEC 23220-4 \[R04\]                                                                                                                                                                                  |
| Priority           | Must                                                                                                                                                                                                     |
| Epic               | EPIC 001 - Online Identification & Authentication                                                                                                                                                        |
| User Story         | [US-003](https://jira.intrasoft-intl.com/issues/?jql=labels+%3D+US-003), [[US-015](https://jira.intrasoft-intl.com/browse/EUDIW-1261)](https://jira.intrasoft-intl.com/browse/EUDIW-1261)                |
| Security Test Case | US03.STC005, US015.STC004, US015.STC005                                                                                                                                                                  |

#### FSRQ-021

| Requirement ID     | FSRQ-021                                                                                                                                                                                                                       |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Selective Disclosure                                                                                                                                                                                           |
| Description        | PID attestation must enable Selective Disclosure of attributes using SD-JWT and Mobile Security Object (ISO/IEC 18013-5) \[R01\] scheme.                                                                                       |
| Status             | Completed                                                                                                                                                                                                                      |
| Source             | ARF \[R02\]                                                                                                                                                                                                                    |
| Priority           | Must                                                                                                                                                                                                                           |
| Epic               | EPIC 001 -- Online Identification & Authentication                                                                                                                                                                             |
| User Story         | [[[US-003](https://jira.intrasoft-intl.com/browse/EUDIW-943)](https://jira.intrasoft-intl.com/browse/EUDIW-943), [US-017](https://jira.intrasoft-intl.com/browse/EUDIW-460)](https://jira.intrasoft-intl.com/browse/EUDIW-460) |
| Security Test Case | US03.STC002, US03.STC003                                                                                                                                                                                                       |

### Verifiable Credentials Issuance

The Verifiable Credentials Issuance is the process of issuing digital
credentials through an OAuth-protected API. These credentials can take
various formats, such as ISO mDL (ISO 18013-5), and are similar to
identity assertions like ID Tokens in OpenID Connect.

A Credential Issuer provides these credentials by asserting specific
claims about an End-User according to a predefined schema. The
credentials may also be cryptographically bound to a particular holder,
ensuring that they are securely associated with the intended recipient.

The issuance process leverages OAuth 2.0 for authorization, allowing for
secure distribution of Verifiable Credentials, benefiting from the
established security protocols of OAuth 2.0 and extending the
capabilities of existing OpenID Connect deployments.

Below, the minimum acceptable set of security requirements recorded for
issuing verifiable credentials based on the OpenID specification \[R06\]
and aligned with the EU Digital Identity Wallet Reference Implementation
and its ecosystem. In order to differentiate the epics and user stories
hosted in
[GitHub](https://github.com/orgs/eu-digital-identity-wallet/projects/24/views/2),
the epics are recorded only by name and the user stories by name and
number.

#### FSRQ-022

| Requirement ID     | FSRQ-022                                                                                                                                                              |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Key Attestation                                                                                                                                                       |
| Description        | Utilize key attestation mechanisms to validate the key management policies of EUDIW, e.g., use of certificates.                                                       |
| Status             | It will be revised                                                                                                                                                    |
| Source             | OpenID4VCI \[R06\]                                                                                                                                                    |
| Priority           | Must                                                                                                                                                                  |
| Epic               | [Issuing - Support for credential offer - Issuer initiated scenario](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42) |
| User Story         | [Issuing - Support for credential offer - Issuer initiated scenario \#216](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216)        |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                            |

#### FSRQ-023

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-023</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Application Attestation</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The application managing the Credentials must be trusted. Utilize
application attestation mechanisms as provided by both iOS (e.g., iOS
DeviceCheck) and Android (e.g., Android SafetyNet) to validate the
internal integrity of the EUDIW.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing - Pre-authorised code flow (OID4VCI)</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-024

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-024</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Device Attestation</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The device running the EUDIW must be healthy and secure. Use device
attestation services to verify the security status of the device.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing - Pre-authorised code flow (OID4VCI)</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-025

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-025</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Client Authentication</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>The EUDIW must be authenticated securely with the Credential
Issuer. The Credential Issuer may establish trust with the Wallet based
on its own auditing or a trusted third-party attestation.</p>
<p>Use OAuth 2.0 Client Authentication to establish trust between the
Wallet and the Credential Issuer.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-026

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-026</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Credential Offer Trustworthiness</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>The Wallet must consider the parameter values in the Credential
Offer as not trustworthy, since the origin is not authenticated, and the
message integrity is not protected.</p>
<p>Apply verification checks on the Credential Issuer and authenticate
the origin of the offer.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing - Pre-authorised code flow (OID4VCI)</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-027

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-027</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Credential Life Management</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>The Wallet shall detect signs of fraudulent behavior related to
the Credential management in the Wallet (e.g., device rooting) and to
act upon it.</p>
<p>Perform credential revocation at the Credential Issuer and/or
invalidation of the key material used to cryptographically bind the
Credential to the identifier of the End-User possessing that
Credential.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing - Pre-authorised code flow (OID4VCI)</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-028

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-028</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secure Communication</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Ensure all communications use up-to-date TLS protocols and a TLS
server certificate check is performed.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing
- Issuing - Pre-authorised code flow (OID4VCI)</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/8">Issuing
- Dynamic PID based issuing credential offer</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/218">Issuing
- Dynamic PID based issuance #218</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-029

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-029</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secure Access Token</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>Access tokens must be secured against unauthorized use. Avoid
issuing long-lived access tokens unless they are sender constrained.</p>
<p>If Bearer Access Tokens are stored by the EUDIW, they must be stored
in a secure manner, e.g., encrypted using a key stored in a protected
key store.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing
- Issuing - Pre-authorised code flow (OID4VCI)</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-030

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-030</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secure Storage of Credentials</td>
</tr>
<tr class="even">
<td>Description</td>
<td>End-User data leakage, especially when it is signed, should be
prevented. Store credentials in encrypted form, and wherever possible,
use hardware-backed encryption.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/41">Issuing
- Issuing - Pre-authorised code flow (OID4VCI)</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/8">Issuing
- Dynamic PID based issuing credential offer</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243">Issuing
- Pre-authorization code flow #243</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/218">Issuing
- Dynamic PID based issuance #218</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-031

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-031</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secure Credential Offer Parameters</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Send the request parameters in a JSON Web Token (JWT) so the request
is signed with JSON Web Signature (JWS) and encrypted with JSON Web
Encryption (JWE) to ensure the integrity, source authentication, and
confidentiality properties of the authorization request. </td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/42">Issuing
- Support for credential offer - Issuer initiated scenario</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/8">Issuing
- Dynamic PID based issuing credential offer</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/216">Issuing
- Support for credential offer - Issuer initiated scenario #216</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/218">Issuing
- Dynamic PID based issuance #218</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-032

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-032</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>End-User Correlation Prevention</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>Issue a batch of Credentials to enable the usage of unique
credentials per presentation and use cryptographic schemes.</p>
<p>Credential Issuers should discard values that can be used in
collusion with a Verifier to track a user, e.g., the Issuer’s
signature.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Pending</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td>Pending</td>
</tr>
<tr class="odd">
<td>User Story</td>
<td>Pending</td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-034

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-034</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Request issuance of multiple attestations</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The issuance of multiple attestations of the same type, with
identical attributes and validity period, should be requested to enable
batch issuance and protect privacy against Relying Party
linkability.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/18">Batch
issuance and processing of multiple instances of the same credential
type #71</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/186">Issue
multiple instances of a credential (wallet initiated, remote/same
device) #186</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/180">Issue
multiple instances of a credential (issuer initiated, remote/cross and
same device) #180</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### FSRQ-035  {#fsrq-035}

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-035</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Presentation of batch attributes issued attestation</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Attributes of a batch-issued attestation should be presented to a
Relying Party on the same device as the EUDI Wallet, ensuring privacy by
preventing linkability across multiple presentations.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/18">Batch
issuance and processing of multiple instances of the same credential
type #71</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/185">Present
an instance of a credential (relying party initiated, remote/same
device) #185</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/179">Present
an instance of a credential (relying party initiated, remote/cross
device) #179</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/178">Present
an instance of a credential (wallet initiated, cross device/proximity)
#178</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

### Deferred Credential Issuance

Deferred Credential Issuance is a process used when a Credential Issuer
is unable to immediately issue a credential after a request has been
made at the Credential Endpoint.

In such cases, the issuance can be deferred and later completed through
a specific endpoint called the Deferred Credential Endpoint.

Below, the minimum acceptable set of security requirements recorded for
the deferred issuance of verifiable credentials according to OpenID4VCI
\[R06\] in alignment with the EU Digital Identity Wallet Reference
Implementation and its ecosystem. In order to differentiate the epics
and user stories hosted in
[GitHub](https://github.com/orgs/eu-digital-identity-wallet/projects/24/views/2),
the epics are recorded only by name and the user stories by name and
number.

#### FSRQ-036

| Requirement ID     | FSRQ-036                                                                                                                                                                                                |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Secure communication                                                                                                                                                                                    |
| Description        | The communication with the Deferred Credential Endpoint must be in a secure manner. Utilize TLS protocol to ensure secure communication with the Credential Endpoint.                                   |
| Status             | Completed                                                                                                                                                                                               |
| Source             | OpenID4VCI \[R06\]                                                                                                                                                                                      |
| Priority           | Must                                                                                                                                                                                                    |
| Epic               | [Issuing - Deferred issuance (OID4VCI)](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/40) |
| User Story         | [Issuing - Deferred issuance \#217](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/217)                                                                                 |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                              |

#### FSRQ-037

| Requirement ID     | FSRQ-0357                                                                                                                                                                                               |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Deferred Credential Endpoint Access Token Validation                                                                                                                                                    |
| Description        | The EUDIW must be authenticated and authorized for Credential Issuance. Ensure that the EUDIW presents a valid Access Token when making requests to the Deferred Credential Endpoint.                   |
| Status             | Completed                                                                                                                                                                                               |
| Source             | OpenID4VCI \[R06\]                                                                                                                                                                                      |
| Priority           | Must                                                                                                                                                                                                    |
| Epic               | [Issuing - Deferred issuance (OID4VCI)](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/40) |
| User Story         | [Issuing - Deferred issuance \#217](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/217)                                                                                 |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                              |

#### FSRQ-038

| Requirement ID     | FSRQ-038                                                                                                                                                                                                |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Deferred Credential Endpoint Access Token Refresh                                                                                                                                                       |
| Description        | Access Token upon the expiration of its validity time should be refreshed, to the request of the wallet to be completed.                                                                                |
| Status             | Completed                                                                                                                                                                                               |
| Source             | OpenID4VCI \[R06\]                                                                                                                                                                                      |
| Priority           | Should                                                                                                                                                                                                  |
| Epic               | [Issuing - Deferred issuance (OID4VCI)](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/40) |
| User Story         | [Issuing - Deferred issuance \#217](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/217)                                                                                 |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                              |

#### FSRQ-039

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-039</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Deferred Credential Endpoint Transaction ID Invalidation</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>The transaction IDs must be managed securely.</p>
<p>Invalidate the transaction ID after the Credential has been obtained
by the EUDIW to prevent reuse.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/40">Issuing
- Deferred issuing for delayed issuing in cases where issuance cannot be
handled immediately</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/217">Issuing
- Deferred issuance #217</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

### rQES

A Remote Qualified Electronic Signature (rQES) is a key component in the
context of the EU Digital Identity Wallet Reference Implementation,
enabling secure and legally recognized digital signing capabilities that
are equivalent to handwritten signatures under the eIDAS Regulation.

Unlike traditional Qualified Electronic Signatures (QES), which require
the user to possess and manage a physical signing device, rQES enables
the signing process to be performed remotely.

The private keys used for signing are securely stored in a Qualified
Signature Creation Device (QSCD), managed by a Qualified Trust Service
Provider (QTSP). Signers authenticate themselves using strong
authentication methods, such as multi-factor authentication (MFA) or
biometrics, binding the signing process to their verified digital
identity.

#### rQES FSRQ-040  {#rqes-fsrq-040}

| Requirement ID     | FSRQ-040                                                                                                                                                        |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Document Encoding                                                                                                                                               |
| Description        | Documents must be securely encoded (e.g., Base64) before signing.                                                                                               |
| Status             | Completed                                                                                                                                                       |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                           |
| Priority           | MUST                                                                                                                                                            |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15) |
| User Story         | [Select document to be signed (R3/R5) \#195](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/195)                              |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                      |

#### rQES FSRQ-041  {#rqes-fsrq-041}

| Requirement ID     | FSRQ-041                                                                                                                                                        |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Hash Generation                                                                                                                                                 |
| Description        | The document hash must be generated using a secure algorithm (e.g., SHA-256), while the hashAlgorithmOID parameter specifies the algorithm used.                |
| Status             | Completed                                                                                                                                                       |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                           |
| Priority           | MUST                                                                                                                                                            |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15) |
| User Story         | [Select and Authenticate to QTSP \#213](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/213)                                     |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                      |

#### rQES FSRQ-042  {#rqes-fsrq-042}

| Requirement ID     | FSRQ-042                                                                                                                                                        |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Hash Verification                                                                                                                                               |
| Description        | The document hash should be verified against the authorized hash, rejecting signing requests if the hashes do not match to prevent tampering.                   |
| Status             | Completed                                                                                                                                                       |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                           |
| Priority           | MUST                                                                                                                                                            |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15) |
| User Story         | [Select and Authenticate to QTSP \#213](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/213)                                     |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                      |

#### rQES FSRQ-043

| Requirement ID     | FSRQ-043                                                                                                                                                                                         |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Document Signature -- Before Signing                                                                                                                                                             |
| Description        | The document must be signed with the correct signature format (e.g., PAdES, XAdES, or JAdES) and the correct signature properties (e.g., time-stamps) based on user and regulatory requirements. |
| Status             | Completed                                                                                                                                                                                        |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                                                            |
| Priority           | MUST                                                                                                                                                                                             |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15)                                  |
| User Story         | [Sign Document (rQES - R3) \#212](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212)                                                                            |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                       |

#### rQES FSRQ-044

| Requirement ID     | FSRQ-044                                                                                                                                                        |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Document Signature -- During Signing                                                                                                                            |
| Description        | A specified *signature_format* and *signed_envelope_property* must be applied to control the signing process.                                                   |
| Status             | Completed                                                                                                                                                       |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                           |
| Priority           | MUST                                                                                                                                                            |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15) |
| User Story         | [Sign Document (rQES - R3) \#212](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212)                                           |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                      |

#### rQES FSRQ-045

| Requirement ID     | FSRQ-045                                                                                                                                                                  |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Document Signature -- After Signing                                                                                                                                       |
| Description        | The system should confirm that the final document is compliant with the format and encoding requirements to avoid vulnerabilities associated with format inconsistencies. |
| Status             | To be implemented                                                                                                                                                         |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                                     |
| Priority           | MUST                                                                                                                                                                      |
| Epic               | [rQES R5: Wallet-driven model with internal Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/27)     |
| User Story         | [\[Story\] Sign document (R5) \#193](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/193)                                                |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                |

#### rQES FSRQ-046

| Requirement ID     | FSRQ-046                                                                                                                                                              |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Batch Processing                                                                                                                                                      |
| Description        | Only the specified number of signatures should be permitted within a single session, and the service must limit the transaction to authorized hash values.            |
| Status             | To be implemented                                                                                                                                                     |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                                 |
| Priority           | MUST                                                                                                                                                                  |
| Epic               | [rQES R5: Wallet-driven model with internal Signature Creation Application \#29](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/27) |
| User Story         | [\[Story\] Sign document (R5) \#193](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/193)                                            |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                            |

#### rQES FSRQ-047

| Requirement ID     | FSRQ-047                                                                                                                                                              |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Document Signing Authorization                                                                                                                                        |
| Description        | Authorization for document signing must involve a valid Signature Activation Data (SAD) or Access Token. This binds the session to authorized users and documents.    |
| Status             | To be implemented                                                                                                                                                     |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                                 |
| Priority           | MUST                                                                                                                                                                  |
| Epic               | [rQES R5: Wallet-driven model with internal Signature Creation Application \#29](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/27) |
| User Story         | [\[Story\] Sign document (R5) \#193](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/193)                                            |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                            |

#### rQES FSRQ-048

| Requirement ID     | FSRQ-048                                                                                                                                                                                                              |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Signature Activation Data (SAD) Binding                                                                                                                                                                               |
| Description        | Ensure that the SAD remains valid and bound to 1) The Document Hashes, 2) The User, and 3) The Session, to prevent unauthorized use. Each new session or document requires a fresh SAD to maintain session integrity. |
| Status             | Completed                                                                                                                                                                                                             |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                                                                                 |
| Priority           | MUST                                                                                                                                                                                                                  |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15)                                                       |
| User Story         | [Sign Document (rQES - R3) \#212](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212)                                                                                                 |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                                            |

#### rQES FSRQ-049

| Requirement ID     | FSRQ-049                                                                                                                                                                                            |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Signature Activation Data (SAD) Expiration                                                                                                                                                          |
| Description        | SAD should expire after each transaction. Each new session (e.g., a new document batch) requires re-authorization with a new SAD to prevent unauthorized reuse of SAD across multiple transactions. |
| Status             | Completed                                                                                                                                                                                           |
| Source             | Architectures and protocols for remote signature applications \[R09\]                                                                                                                               |
| Priority           | MUST                                                                                                                                                                                                |
| Epic               | [rQES R3: Wallet-driven model with external Signature Creation Application](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15)                                     |
| User Story         | [Sign Document (rQES - R3) \#212](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212)                                                                               |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                          |

#### rQES FSRQ-050

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-050</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Error Handling – Masking Sensitive Information</td>
</tr>
<tr class="even">
<td>Description</td>
<td>During the signing process, all error responses (e.g., unauthorized
access attempt or a bad request) should mask sensitive information using
HTTP error codes to prevent leakage.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>Architectures and protocols for remote signature applications
[R09]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15">rQES
R3: Wallet-driven model with external Signature Creation
Application</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/27">rQES
R5: Wallet-driven model with internal Signature Creation Application
#29</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212">Sign
Document (rQES - R3) #212</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/193">[Story]
Sign document (R5) #193</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### rQES FSRQ-051

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-051</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Error Handling – Logging</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Detailed information about every failed attempt (e.g., unauthorized
access, bad requests) must be logged in a secure and accessible
way.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>Architectures and protocols for remote signature applications
[R09]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/15">rQES
R3: Wallet-driven model with external Signature Creation
Application</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/27">rQES
R5: Wallet-driven model with internal Signature Creation Application
#29</a></p></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212">Sign
Document (rQES - R3) #212</a></p>
<p><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/193">[Story]
Sign document (R5) #193</a></p></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

###  EHIC {#ehic}

The European Health Insurance Card (EHIC) allows EU citizens to access
medically necessary healthcare in another EU or EEA country under the
same conditions as local residents. It ensures temporary healthcare
access but does not replace travel insurance or cover private medical
costs.

A key requirement for EHIC is offline verification, enabling hospitals,
clinics, and pharmacies to authenticate the credential without internet
access. This is essential in emergencies or rural areas with limited
connectivity. Additionally, data minimization and selective disclosure
principles ensure only necessary information is shared, adhering to
privacy and security.

EHIC is not linked to permanent social security rights but facilitates
cross-border healthcare coordination. To maintain privacy, secure
authentication mechanisms and privacy-preserving identity verification
must be implemented. Compliance with EU data protection and social
security regulations ensures its interoperability and acceptance across
member states.

#### EHIC FSRQ-052

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-052</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secure Authentication and Credential Management</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Strong authentication measures and credential management required to
protect identity and prevent fraud.<br />
<br />
Cryptographic Techniques should be in place: Use encryption, digital
signatures, and cryptographic proofs to secure credentials, ensuring
data integrity and authenticity.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/45">European
Health Insurance Card (EHIC) attestation</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/183">Using
the EHIC attestation #183</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### EHIC FSRQ-053

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-053</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Revocation Mechanisms</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Institutions should be able to revoke credentials in case of
compromise while maintaining privacy.<br />
<br />
Real-Time revocation Lists should be in place: Maintain a list of
revoked credentials that verifiers can check in real time to ensure that
compromised or outdated credentials are no longer valid.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/45">European
Health Insurance Card (EHIC) attestation</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/183">Using
the EHIC attestation #183</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### EHIC FSRQ-054

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-054</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Offline Usage Capability</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Digital credentials should be able to be verified without an active
internet connection, ensuring usability in offline scenarios.<br />
<br />
Local Verification Mechanisms should be in place: Allow verification of
credentials using pre-loaded or cryptographically signed attestations
without requiring real-time network access.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/45">European
Health Insurance Card (EHIC) attestation</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/183">Using
the EHIC attestation #183</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

###  PDA1 {#pda1}

The Portable Document A1 (PDA1) certifies that a worker remains covered
by social security in their home country while working in another EU
member state. It prevents double social security contributions and
ensures access to work-related benefits under the home country's system.

Due to its legal and financial implications, PDA1 requires high-level
assurance (LoA) identity verification and Multi-Factor Authentication
(MFA) to prevent fraud and unauthorized use. Secure credential
management ensures that only authorized individuals can access and
present the document.

Unlike EHIC, PDA1 is primarily used in administrative settings, where
real-time online verification is expected. It must comply with EU social
security coordination rules, ensuring seamless cross-border recognition
and trust framework alignment between institutions.

#### PDA1 FSRQ-55

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-55</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>High-Level Assurance Digital Identity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>High Level of Assurance (LoA) should be used for digital identities
to enable secure authentication and identification.<br />
<br />
Multi-Factor Authentication (MFA) should be in place: Users should be
able to be authenticated using multiple factors (e.g., password +
biometric verification) to enhance security and prevent identity
theft.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/46">Portable
Document A1 (PDA1) attestation</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/184">Using
the PDA1 attestation #184</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### PDA1 FSRQ-56

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-56</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Secure Authentication and Credential Management</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Strong authentication measures and credential management required to
protect identity and prevent fraud.<br />
<br />
Cryptographic Techniques should be in place: Use encryption, digital
signatures, and cryptographic proofs to secure credentials, ensuring
data integrity and authenticity.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/46">Portable
Document A1 (PDA1) attestation</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/184">Using
the PDA1 attestation #184</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### PDA1 FSRQ-57

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-57</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Revocation Mechanisms</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Institutions should be able to revoke credentials in case of
compromise while maintaining privacy.<br />
<br />
Real-Time revocation Lists should be in place: Maintain a list of
revoked credentials that verifiers can check in real time to ensure that
compromised or outdated credentials are no longer valid.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>MUST</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/46">Portable
Document A1 (PDA1) attestation</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/184">Using
the PDA1 attestation #184</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

### Cross-Border Recruitment

The Cross-Border Recruitment Use Case showcases how the European Digital
Identity Wallet (EUDI Wallet) can be applied in digital recruitment
processes across EU Member States. This implementation enables
individuals to present verifiable attestations and perform secure
digital interactions when applying for a job, onboarding with an
employer, or signing an employment contract.

The use case involves four key attestations:

- Seafarer Certificate, proving maritime qualifications;

- Diploma, verifying educational background;

- Tax Residency Attestation, confirming fiscal residence, and;

- Employee ID, issued by the employer to confirm the work relationship.

These credentials can be shared both remotely and in proximity, and
support features such as selective disclosure, holder binding, and
issuer trust validation. The goal is to demonstrate the secure and
privacy-preserving use of the EUDI Wallet in a real-world, cross-border
employment scenario.

#### Seafarer Certificate

Verifiable credential issued by a maritime authority certifying the
seafarer\'s qualification and right to work at sea.

##### FSRQ-058

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-058</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Data Integrity and Authenticity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include necessary information for performing a
data integrity check.<br />
<br />
Maritime has high regulatory dependency, thus identity &amp;
authenticity are critical.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229">[Recruitment]
Apply for a job by presenting attestations remotely #229</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-059

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-059</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Attestation Validity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Attestation must include all necessary information for performing
validity status checks.<br />
<br />
Maritime has high regulatory dependency, thus identity &amp;
authenticity are critical.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229">[Recruitment]
Apply for a job by presenting attestations remotely #229</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-060

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-060</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Attestation Holder Verification</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Attestation must include all necessary information for verifying the
holder binding by a Relying Party.<br />
<br />
Maritime has high regulatory dependency, thus identity &amp;
authenticity are critical.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229">[Recruitment]
Apply for a job by presenting attestations remotely #229</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-061

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-061</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Issuer Data Authentication</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Confirm that the EUDIW data is issued by the issuing authority and
that it has not changed since issuance by validating the EUDIW data
using the MSO, the MSO using DS certificate and the DS certificate using
IACA root public key.<br />
<br />
Maritime has high regulatory dependency, thus identity &amp;
authenticity are critical.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229">[Recruitment]
Apply for a job by presenting attestations remotely #229</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td><p>US06.STC001, US06.STC002, US06.STC003,</p>
<p>US07.STC001, US07.STC002, US07.STC003</p></td>
</tr>
</tbody>
</table>

##### FSRQ-062

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-062</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Attestation Selective Disclosure</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Attestation must enable Selective Disclosure of attributes using
SD-JWT and Mobile Security Object (ISO/IEC 18013-5) [R01] scheme.<br />
<br />
User should disclose only the necessary parts (e.g., license number, not
full record).</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229">[Recruitment]
Apply for a job by presenting attestations remotely #229</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>US03.STC002, US03.STC003</td>
</tr>
</tbody>
</table>

#### Diploma

Digital credential issued by an educational institution confirming the
completion of a degree or qualification.

##### FSRQ-063

| Requirement ID     | FSRQ-063                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Attestation Validity                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Description        | Must contain all the information required to perform validity status checks on the (Q)EAA.                                                                                                                                                                                                                                                                                                                                                                                |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| Source             | ARF \[R02\], DC4EU_BBP \[R10\]                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Epic               | [Business demo: cross-border recruitment](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55)                                                                                                                                                                                                                                                                                  |
| User Story         | [\[Recruitment\] Issue attestations \#230](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230), [\[Recruitment\] Apply for a job by presenting attestations remotely \#229](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229), [\[Recruitment\] Present attestations in proximity (Employment 1st day) \#226](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226) |
| Security Test Case | Pending                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |

##### FSRQ-064

| Requirement ID     | FSRQ-064                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Signature and Encryption Formats                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| Description        | (Q)EAA (Qualified and Non-Qualified Electronic Attestation of Attributes) should use one of the signature and encryption formats detailed in JOSE (JSON Object Signing and Encryption) RFCs or COSE (CBOR Object Signing and Encryption) RFCs, based on the data model used for the attestation.                                                                                                                                                                          |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| Source             | ARF \[R02\], DC4EU_BBP \[R10\]                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Priority           | Should                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| Epic               | [Business demo: cross-border recruitment](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55)                                                                                                                                                                                                                                                                                  |
| User Story         | [\[Recruitment\] Issue attestations \#230](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230), [\[Recruitment\] Apply for a job by presenting attestations remotely \#229](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229), [\[Recruitment\] Present attestations in proximity (Employment 1st day) \#226](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226) |
| Security Test Case | Pending.                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |

##### FSRQ-065

| Requirement ID     | FSRQ-065                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | (Q)EAA SOG-IS ACM Compliance                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| Description        | (Q)EAA (Qualified and Non-Qualified Electronic Attestation of Attributes) should use signature and encryption algorithms in accordance with SOG-IS ACM (Smartcard Operating System Common Criteria Application Software Protection Profile).                                                                                                                                                                                                                              |
| Status             | In Progress                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Source             | ARF \[R02\], DC4EU_BBP \[R10\]                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Priority           | Should                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| Epic               | [Business demo: cross-border recruitment](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55)                                                                                                                                                                                                                                                                                  |
| User Story         | [\[Recruitment\] Issue attestations \#230](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230), [\[Recruitment\] Apply for a job by presenting attestations remotely \#229](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/229), [\[Recruitment\] Present attestations in proximity (Employment 1st day) \#226](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226) |
| Security Test Case | Pending                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |

##### FSRQ-066

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-066</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Holder Verification</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include all necessary information for verifying
the holder binding by a Relying Party.<br />
<br />
Ensures the diploma is issued to the correct student
(non-transferable).</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td>EPIC 001 - Online Identification &amp; Authentication</td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://jira.intrasoft-intl.com/browse/EUDIW-943"><span>US-003</span></a>,
<a href="https://jira.intrasoft-intl.com/browse/EUDIW-518">US-008</a>,
<a
href="https://jira.intrasoft-intl.com/browse/EUDIW-269"><span>US-009</span></a>,
<a
href="https://jira.intrasoft-intl.com/browse/EUDIW-1261"><span>US-015</span></a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

#### Tax Residency

A Digital Credential issued by a tax authority confirming a person's
residency for tax purposes.

##### FSRQ-067

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-067</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Data Integrity and Authenticity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include necessary information for performing a
data integrity check.<br />
<br />
Proof of tax residency must be verifiable and tamper-proof.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-068

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-068</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Validity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include all necessary information for
performing validity status checks.<br />
<br />
Proof of tax residency must be verifiable and tamper-proof.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-069

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-069</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Credential Life Management</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Perform credential revocation at the Credential Issuer and/or
invalidation of the key material used to cryptographically bind the
Credential to the identifier of the End-User possessing that
Credential.<br />
<br />
Fiscal residency may change year to year; the credential must reflect a
validity period. Revocation/Expiry information must be part of the
credential.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230">[Recruitment]
Issue attestations #230</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

#### Employee ID

A Digital Credential issued by an employer, used to confirm the
employment relationship.

##### FSRQ-070

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-070</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Data Integrity and Authenticity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include necessary information for performing a
data integrity check.<br />
<br />
Binding to user – Identifies employee and links the credential to the
holder (employee)</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/228">[Recruitment]
Sign contract with EUDI Wallet (Job Contract) (remote/cross-device)
#228</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-071

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-071</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Validity</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include all necessary information for
performing validity status checks.<br />
<br />
Binding to user – Identifies employee and links the credential to the
holder (employee)</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/228">[Recruitment]
Sign contract with EUDI Wallet (Job Contract) (remote/cross-device)
#228</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-072

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-072</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Encoding</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must use signatures and encryption formats as
detailed in JOSE (JSON Object Signing and Encryption) RFCs and COSE
(CBOR Object Signing and Encryption) RFCs.<br />
<br />
Binding to user – Identifies employee and links the credential to the
holder (employee)</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/228">[Recruitment]
Sign contract with EUDI Wallet (Job Contract) (remote/cross-device)
#228</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-073

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-073</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Holder Verification</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID Attestation must include all necessary information for verifying
the holder binding by a Relying Party.<br />
<br />
Binding to user – Identifies employee and links the credential to the
holder (employee)</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/228">[Recruitment]
Sign contract with EUDI Wallet (Job Contract) (remote/cross-device)
#228</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a>, <a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226">[Recruitment]
Present attestations in proximity (Employment 1st day) #226</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing has been covered by the functional test cases.</td>
</tr>
</tbody>
</table>

##### FSRQ-074

| Requirement ID     | FSRQ-074                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | PID Attestation Holder Data Model Compatibility                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| Description        | PID attestation must be issued in accordance with both the data model specified in ISO/IEC 18013-5:2021 and the W3C Verifiable Credentials Data Model 1.1.                                                                                                                                                                                                                                                                                                                                                                                  |
| Status             | Completed                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| Source             | ARF \[R02\]                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| Epic               | [Business demo: cross-border recruitment](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55)                                                                                                                                                                                                                                                                                                                                                    |
| User Story         | [\[Recruitment\] Sign contract with EUDI Wallet (Job Contract) (remote/cross-device) \#228](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/228), [\[Recruitment\] Issue Employee ID credential (issuer initiated, remote/cross-device) \#227](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227), [\[Recruitment\] Present attestations in proximity (Employment 1st day) \#226](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226) |
| Security Test Case | Testing has been covered by the functional test cases.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |

##### FSRQ-075

| Requirement ID     | FSRQ-075                                                                                                                                                                                                                                                                                                                                                |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name               | Key Attestation                                                                                                                                                                                                                                                                                                                                         |
| Description        | Utilize key attestation mechanisms to validate the key management policies of EUDIW, e.g., use of certificates.                                                                                                                                                                                                                                         |
| Status             | It will be revised                                                                                                                                                                                                                                                                                                                                      |
| Source             | OpenID4VCI \[R06\]                                                                                                                                                                                                                                                                                                                                      |
| Priority           | Must                                                                                                                                                                                                                                                                                                                                                    |
| Epic               | [Business demo: cross-border recruitment](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55)                                                                                                                                                                |
| User Story         | [\[Recruitment\] Sign contract with EUDI Wallet (Job Contract) (remote/cross-device) \#228](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/228), [\[Recruitment\] Present attestations in proximity (Employment 1st day) \#226](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/226) |
| Security Test Case | Testing will be covered in the Mobile Application Security Testing activity based on MSTG.                                                                                                                                                                                                                                                              |

##### FSRQ-076

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-076</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Application Attestation</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The application managing the Credentials must be trusted. Utilize
application attestation mechanisms as provided by both iOS (e.g., iOS
DeviceCheck) and Android (e.g., Android SafetyNet) to validate the
internal integrity of the EUDIW.<br />
<br />
Protects issuance integrity when employer acts as the credential
issuer.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

##### FSRQ-077

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-077</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Device Attestation</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The device running the EUDIW must be healthy and secure. Use device
attestation services to verify the security status of the device.<br />
<br />
Protects issuance integrity when employer acts as the credential
issuer.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

##### FSRQ-078

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-078</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Client Authentication</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>The EUDIW must be authenticated securely with the Credential
Issuer. The Credential Issuer may establish trust with the Wallet based
on its own auditing or a trusted third-party attestation.</p>
<p>Use OAuth 2.0 Client Authentication to establish trust between the
Wallet and the Credential Issuer.<br />
<br />
Protects issuance integrity when employer acts as the credential
issuer.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>Testing will be covered in the Mobile Application Security Testing
activity based on MSTG.</td>
</tr>
</tbody>
</table>

##### FSRQ-079

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>FSRQ-079</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>PID Attestation Selective Disclosure</td>
</tr>
<tr class="even">
<td>Description</td>
<td>PID attestation must enable Selective Disclosure of attributes using
SD-JWT and Mobile Security Object (ISO/IEC 18013-5) [R01] scheme.<br />
<br />
User should share only necessary info (e.g., job title, not full
contract details).</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ARF [R02]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Epic</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/55">Business
demo: cross-border recruitment</a></td>
</tr>
<tr class="odd">
<td>User Story</td>
<td><a
href="https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/227">[Recruitment]
Issue Employee ID credential (issuer initiated, remote/cross-device)
#227</a></td>
</tr>
<tr class="even">
<td>Security Test Case</td>
<td>US03.STC002, US03.STC003</td>
</tr>
</tbody>
</table>

### Combined Business Scenario on Travel

The combined business scenario on Travel covered in epic [A combined
business scenario on
Travel](https://github.com/eu-digital-identity-wallet/eudi-wallet-reference-implementation-roadmap/issues/6)
utilizes the previously analysed use cases and thus no separate analysis
is needed.

## Non-Functional Requirements

### NFSRQ-001

| Requirement ID                | NFSRQ-001                                                                                                                                                                                                                                                                                              |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Data minimization                                                                                                                                                                                                                                                                                      |
| Description                   | Processing of data should be minimized to that specifically necessary for the purpose specified.                                                                                                                                                                                                       |
| Status                        | Completed                                                                                                                                                                                                                                                                                              |
| Source                        | ISO/IEC 18013-5 \[R01\]                                                                                                                                                                                                                                                                                |
| Priority                      | Must                                                                                                                                                                                                                                                                                                   |
| Acceptance criteria guideline | Fields (data groups) have been separated into individual data elements to support privacy-preserving attribute queries. Selective Disclosure: Credential verifiers must request data elements individually and should ask for only those necessary for the specific use case (e.g., PD A1, EHIC, mDL). |

### NFSRQ-002

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-002</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Consent and choice</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The data subject must consent to the processing of their personal
data.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td><p>The data transfer model provides the ability for mDL solutions to
implement either pre-consent or transaction-time consent. Consent
applies to both selective disclosure and authorization for
intent-to-retain.</p>
<ul>
<li><p>Authorization for the disclosure of the applicable/selected data,
using the pre-defined authentication means.</p></li>
</ul></td>
</tr>
</tbody>
</table>

### NFSRQ-003

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-003</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Information security – Privacy preserve</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Personal data should be protected by security safeguards against
such risks as loss or unauthorized access, destruction, use,
modification, or disclosure.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td><ul>
<li><p>mDL data is signed by the issuing authority using digital
certificates for protection of integrity and authenticity.</p></li>
<li><p>mDL readers use trusted certificates to validate the integrity
and authenticity of mDL data.</p></li>
<li><p>Implement anonymization, pseudonymization, and secure multi-party
computation to protect personal data from unnecessary exposure.</p></li>
</ul>
<p>(For more technical details, please see ISO 18013-5 [R01], section
“9.3 Validation and Inspection Procedures” and Annex C).</p></td>
</tr>
</tbody>
</table>

### NFSRQ-004

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-004</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Individual participation and access</td>
</tr>
<tr class="even">
<td>Description</td>
<td>The data subject should be involved in the collection, consent,
processing, and storage management of their personal data.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td><ul>
<li><p>Sequence of “Device engagement, secure connection, Request,
Response, Repeat” permits pre-consent and transaction-time consent
models at mDL holder discretion.</p></li>
<li><p>Authorization of the disclosure of the applicable/selected data,
using the pre-defined authentication means.</p></li>
</ul></td>
</tr>
</tbody>
</table>

### NFSRQ-005

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-005</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Information security (of data and data subject)</td>
</tr>
<tr class="even">
<td>Description</td>
<td>Personal data should be protected by security safeguards against
such risks as loss or unauthorized access, destruction, use,
modification, or disclosure.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td><ul>
<li><p>Ephemeral keys are used in channel security.</p></li>
<li><p>mDL Data Authentication can prevent data tampering.</p></li>
<li><p>Perform session encryption.</p></li>
</ul></td>
</tr>
</tbody>
</table>

### NFSRQ-006

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-006</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Accuracy and quality</td>
</tr>
<tr class="even">
<td>Description</td>
<td>High accuracy of data being processed and held is in the best
interest of the data subject and processors should take measures to
ensure accuracy.</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td><ul>
<li><p>The data returned by this protocol in device retrieval is the one
that was determined valid at the time of issuance of the MSO object. The
validity element of the MSO allows the authority to limit the period
during which this data will be considered sufficiently recent for
offline use and indicates whether an update of the MSO information was
performed or not by the mDL application or mDL holder.</p></li>
<li><p>The issuing authority should define appropriate periods of
validity that balance freshness with offline capability, taking into
account that a shorter validity period mitigates certain security
risks.</p></li>
</ul></td>
</tr>
</tbody>
</table>

### NFSRQ-007

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-007</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Issuing Authority Trusted List</td>
</tr>
<tr class="even">
<td>Description</td>
<td>An Issuing Authority should be trusted and validated via an official
Trusted List. Trusted Lists cover Wallet Providers, PID Providers, QEAA
Providers, PuB-EAA Providers, Access Certificate Authorities, and QESRC
Providers, with validation performed via the IACA root
certificate.<br />
(see ISO 18013-5 [R01] Annex C &amp; ARF).</td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>ISO/IEC 18013-5 [R01], ARF [R02], DC4EU_BBP [R10]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td>Identify the IACA Public Certificate of an Issuing Authority in the
Trusted List.</td>
</tr>
</tbody>
</table>

### NFSRQ-008

| Requirement ID                | NFSRQ-008                                                                                                                                                                                                                                                            |
|-------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Secure Data at rest                                                                                                                                                                                                                                                  |
| Description                   | Data should be securely stored while saved in the device. Cryptographic algorithms should be applied based on current industry best practices for Android (such as Android Credential API or the implementors may use NiScy interface for custom solutions) and iOS. |
| Status                        | Completed                                                                                                                                                                                                                                                            |
| Source                        | ISO/IEC 27001:2022 \[R04\]                                                                                                                                                                                                                                           |
| Priority                      | Must                                                                                                                                                                                                                                                                 |
| Acceptance criteria guideline | Implementation of the Interface allowing the development of custom solutions.                                                                                                                                                                                        |

### NFSRQ-009

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-009</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Trust between Wallet and Issuer</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>The Credential Issuer may ensure that private keys are properly
protected from exfiltration and replay to prevent an adversary from
impersonating the legitimate Credential holder by presenting
Credential.</p>
<p>The Credential Issuer may ensure that the EUDIW managing the
Credentials adheres to certain policies and, potentially, was audited
and approved under a certain regulatory and/or commercial
scheme.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>Completed</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>May</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td><ul>
<li><p>Key attestation mechanism.</p></li>
<li><p>App Attestation (e.g., provided by iOS’s DeviceCheck or Android’s
SafetyNet.</p></li>
<li><p>Device Attestation (device health attestation, e.g.,
compromises).</p></li>
<li><p>Client Authentication (Rely on OAuth 2.0 Client
Authentication)</p></li>
<li><p>Nonce Endpoint.</p></li>
</ul></td>
</tr>
</tbody>
</table>

### NFSRQ-010

| Requirement ID                | NFSRQ-010                                                                                                                        |
|-------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Verifier Mobile Devices Compliance                                                                                               |
| Description                   | The mobile devices used by the verifiers and the drivers shall comply with common minimum requirements on hardware and software. |
| Status                        | In Progress                                                                                                                      |
| Source                        | HLBRD                                                                                                                            |
| Priority                      | Must                                                                                                                             |
| Acceptance criteria guideline | Compliance with the current at any time minimum requirements on hardware and software.                                           |

### NFSRQ-011

| Requirement ID                | NFSRQ-011                                                                                                   |
|-------------------------------|-------------------------------------------------------------------------------------------------------------|
| Name                          | User Consent                                                                                                |
| Description                   | Ensure transparency. Clearly communicate to the End-User what data will be included and seek their consent. |
| Status                        | Completed                                                                                                   |
| Source                        | OpenID4VCI \[R06\]                                                                                          |
| Priority                      | Must                                                                                                        |
| Acceptance criteria guideline | Credential Issuers must obtain clear and explicit consent from the End-User before issuing credentials.     |

### NSFRQ-012

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-012</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Retention of Stored Credentials</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>Store credentials only as long as necessary. After Issuance,
Credential Issuers should not store the Issuer-signed Credentials if
they contain privacy-sensitive data, including in log files. The time
logs are retained for should be minimized.</p>
<p>The wallet should not store Credentials longer than needed.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Should</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td>Compliance with the defined retention policy issued by the local
authorities or industry best practices according to GDPR.</td>
</tr>
</tbody>
</table>

### NFSRQ-013

| Requirement ID                | NFSRQ-013                                                                                     |
|-------------------------------|-----------------------------------------------------------------------------------------------|
| Name                          | Minimum Disclosure                                                                            |
| Description                   | The amount of information disclosed to Verifier should be minimized.                          |
| Status                        | Completed                                                                                     |
| Source                        | OpenID4VCI \[R06\],                                                                           |
| Priority                      | Must                                                                                          |
| Acceptance criteria guideline | The use credential formats that support selective disclosure to reveal only necessary claims. |

### NFSRQ-014

<table>
<colgroup>
<col style="width: 29%" />
<col style="width: 70%" />
</colgroup>
<thead>
<tr class="header">
<th>Requirement ID</th>
<th>NFSRQ-014</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Name</td>
<td>Authorization Request Correlation Prevention</td>
</tr>
<tr class="even">
<td>Description</td>
<td><p>Issuance/presentation sessions should be protected against
correlation.</p>
<p>Inclusion of sensitive information in the Authorization Request
should be prevented to avoid potential exposure to third
parties.</p></td>
</tr>
<tr class="odd">
<td>Status</td>
<td>In Progress</td>
</tr>
<tr class="even">
<td>Source</td>
<td>OpenID4VCI [R06]</td>
</tr>
<tr class="odd">
<td>Priority</td>
<td>Must</td>
</tr>
<tr class="even">
<td>Acceptance criteria guideline</td>
<td>N/A</td>
</tr>
</tbody>
</table>

### NFSRQ-015

| Requirement ID                | NFSRQ-015                                                                                                                                                    |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Issuer Identification Prevention                                                                                                                             |
| Description                   | Information disclosure that identifies a particular Credential Issuer and consequently reveals sensitive information about the End-User should be prevented. |
| Status                        | In Progress                                                                                                                                                  |
| Source                        | OpenID4VCI \[R06\]                                                                                                                                           |
| Priority                      | Must                                                                                                                                                         |
| Acceptance criteria guideline | N/A                                                                                                                                                          |

### NFSRQ-016

| Requirement ID                | NFSRQ-016                                                                                                   |
|-------------------------------|-------------------------------------------------------------------------------------------------------------|
| Name                          | Wallet Identification Prevention                                                                            |
| Description                   | Identification of the EUDIW through its reaction to credential offers should be prevented.                  |
| Status                        | In Progress                                                                                                 |
| Source                        | OpenID4VCI \[R06\]                                                                                          |
| Priority                      | Must                                                                                                        |
| Acceptance criteria guideline | Implement user interaction or establish trust with the Issuer before the EUDIW processes credential offers. |

### NFSRQ-017

| Requirement ID                | NFSRQ-017                                                                                                                                 |
|-------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Non-Traceability                                                                                                                          |
| Description                   | Credential issuers should not be allowed to track how and where users use their credentials, ensuring privacy.                            |
| Status                        | In Progress                                                                                                                               |
| Source                        | DC4EU_BBP \[R10\]                                                                                                                         |
| Priority                      | Must                                                                                                                                      |
| Acceptance criteria guideline | Use blockchain or distributed ledger-based identifiers that prevent third parties from tracking identity usage across different services. |

### NFSRQ-018

| Requirement ID                | NFSRQ-018                                                                                                                                    |
|-------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Trust Framework Alignment                                                                                                                    |
| Description                   | Compatibility should be ensured between different trust frameworks (eIDAS 2.0, social security coordination) for seamless interoperability.  |
| Status                        | In Progress                                                                                                                                  |
| Source                        | DC4EU_BBP \[R10\]                                                                                                                            |
| Priority                      | Must                                                                                                                                         |
| Acceptance criteria guideline | Implement recognized authentication and credential verification protocols to facilitate interoperability between different identity systems. |

### NFSRQ-019

| Requirement ID                | NFSRQ-019                                                                                                    |
|-------------------------------|--------------------------------------------------------------------------------------------------------------|
| Name                          | Compliance with Social Security Regulations                                                                  |
| Description                   | EUDIW should be aligned with existing social security laws and back-office procedures.                       |
| Status                        | In Progress                                                                                                  |
| Source                        | DC4EU_BBP \[R10\]                                                                                            |
| Priority                      | Must                                                                                                         |
| Acceptance criteria guideline | Implement periodic compliance audits to assess adherence to social security and data protection regulations. |

### NFSRQ-020

| Requirement ID                | NFSRQ-020                                                                                                                                                                     |
|-------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name                          | Self-Sovereignty                                                                                                                                                              |
| Description                   | Individuals should have control over their digital identities and the data they share, balancing security, usability, and compliance.                                         |
| Status                        | Completed                                                                                                                                                                     |
| Source                        | DC4EU_BBP \[R10\]                                                                                                                                                             |
| Priority                      | MUST                                                                                                                                                                          |
| Acceptance criteria guideline | User Consent Management should be in place: Provide users with mechanisms to explicitly consent to data sharing, ensuring transparency and control over personal information. |

# Appendix A

## **ISO/IEC 18013-5** - Annex E  {#isoiec-18013-5---annex-e}

**Annex E**

**Privacy and security recommendations**

**E.1 General**

This annex provides guidance to design and implement their mDL solutions
for privacy and security. It also contains background information on
privacy, and a section on how the existing standard for data transfer
has already addressed privacy concerns.

**E.2** **Achieving privacy for the mDL holder**

**E.2.1 Privacy goals**

Individual privacy and security of personally identifiable information
(PII) in the mobile, electronic age must be ensured and is a shared
responsibility of all involved parties. No technical standard for data
interchange can dictate how all privacy measures are achieved. Privacy
is achieved by the end-to-end solution, and with the participation of
all participants in an ecosystem. Each actor in the mDL ecosystem should
fulfil their role in a responsible manner that best protects PII.

**E.2.2 Privacy definitions, goals, and principles**

This document has been developed with the following privacy-by-design
goals and allows both mDL holder and issuing authority concerns listed
in this annex to be achieved in multiple ways:

--- data minimization and anonymization wherever possible;

--- be proactive to prevent data breach;

--- privacy should be the default setting;

--- embed privacy in design, flows, and architecture;

--- privacy does not need to be traded off for full functionality;

--- protect the full lifecycle of the identity end-to-end;

--- keep all operations visible and transparent to the mDL holder;

--- design for user-centricity and user-control of their identity.

To achieve these privacy-by-design goals, ISO/IEC 29100 defines the
following principles for privacy protection:

1\. consent and choice;

2\. purpose legitimacy and specification;

3\. collection limitation;

4\. data minimization;

5\. use, retention and disclosure limitation;

6\. accuracy and quality;

7\. openness, transparency and notice;

8\. individual participation and access;

9\. accountability;

10\. information security;

11\. privacy compliance.

These principles put names on the protections that consumers (data
subject) expect from data controllers and their data processors. These
have been codified into law (e.g. GDPR).

--- **Consent and choice** -- The data subject must consent to the
processing of their personal data (see definitions in the section on mDL
holder Consent below).

--- **Purpose legitimacy and specification** -- The data subject should
be fully aware of the purpose for which their personal data is being
collected, processed, and potentially stored.

--- **Collection limitation** -- The data controller and data processors
should only collect the data necessary for their purpose and should only
collect data consistent with these principles.

--- **Data minimization** -- Processing of data should be minimized to
that specifically necessary for the purpose specified.

--- **Use, retention, and disclosure limitation** -- Data processors
should not use personal data of the data subject except for the purposes
specified and consistent with these other principles. Personal data
should only be retained for the period necessary to provide the service.

--- **Accuracy and quality** -- High accuracy of data being processed
and held is in the best interest of the data subject and processors
should take measures to ensure accuracy.

--- **Openness, transparency and notice** -- What data and how data is
being processed should be well-known to the data subject, including
obtaining consent, and posting and updating clear notices.

--- **Individual participation and access** -- The data subject should
be involved in the collection, consent, processing, and storage
management of their personal data.

--- **Information security** -- Personal data should be protected by
security safeguards against such risks as loss or unauthorized access,
destruction, use, modification or disclosure.

--- **Privacy compliance** -- The data controller and data processors
must be accountable for all aspects of the processing of personal data
and provide audit logs and auditability to the data subject.

**E.2.3 Privacy protections embedded in the protocol design**

The privacy principles above were taken into consideration during
protocol design, and a set of protections implemented at the protocol
level in order to establish a privacy baseline. Table E.1 details
protections embedded in the protocol. It lists protections that should
be considered and implemented as part of solutions.

**Table E.1 --- Privacy principles and protections**

<table>
<colgroup>
<col style="width: 23%" />
<col style="width: 76%" />
</colgroup>
<tbody>
<tr class="odd">
<td><strong>Principle</strong></td>
<td><strong>Protection</strong></td>
</tr>
<tr class="even">
<td>4. Data minimization</td>
<td>Fields (data groups) have been separated into individual data
elements to support privacy-preserving attribute queries. mDL readers
request data elements individually and should ask for only those;</td>
</tr>
<tr class="odd">
<td><p>3. Collection limitation</p>
<p>4. Data minimization</p></td>
<td>The request-response model permits disclosing additional data if the
initial response did not fulfil the requirements of the use case. This
lessens the need to over-ask for all data in the first pass.</td>
</tr>
<tr class="even">
<td>1. Consent and choice</td>
<td>The data transfer model of this document provides the ability for
mDL solutions to implement either pre-consent or transaction-time
consent as defined below. Consent applies to both selective disclosure
and authorization for intent-to-retain.</td>
</tr>
<tr class="odd">
<td>3. Collection limitation</td>
<td><p>Replay attacks and follow-up requests are not permitted once the
connection is broken in either online or offline models.</p>
<p>Use of “intent to retain” provides a framework for limitation of
collection of data by an mDL reader.</p></td>
</tr>
<tr class="even">
<td>10. Information security</td>
<td><p>mDL data is signed by the issuing authority using digital
certificates for protection of integrity and authenticity.</p>
<p>mDL readers use trusted certificates to validate the integrity and
authenticity of mDL data.</p></td>
</tr>
<tr class="odd">
<td>8. Individual participation and access</td>
<td>Sequence of “Device engagement, secure connection, Request,
Response, Repeat” permits pre-consent and transaction-time consent
models at mDL holder discretion.</td>
</tr>
<tr class="even">
<td>10. Information security (of data and data subject)</td>
<td>Ephemeral keys are used in channel security. mDL Data Authentication
can prevent data tampering.</td>
</tr>
<tr class="odd">
<td>4. Data minimization</td>
<td>Privacy-preserving signatures: Cryptographic signatures do not leak
additional unique identifiers. That is, the protocol does not make mDL
holders identifiable if they cannot be identified by the transmitted
data attributes themselves.</td>
</tr>
<tr class="even">
<td>6. Accuracy and quality</td>
<td>The data returned by this protocol in device retrieval is the one
that was determined valid at the time of issuance of the MSO object. The
validity element of the MSO allows the authority to limit the period
during which this data will be considered sufficiently recent for
offline use and indicates whether an update of the MSO information was
performed or not by the mDL application or mDL holder. The issuing
authority should define appropriate periods of validity that balance
freshness with offline capability, taking into account that a shorter
validity period mitigates certain security risks.</td>
</tr>
</tbody>
</table>

**E.3 Transparency of data storage and use**

mDL holders should have transparency into all data that is held in the
mDL. The mDL holder should always be given the ability to consent to the
sharing of that data and be informed of the onward storage of that data
using the "intent to retain" flag. Proper informed consent as per
section below can help ensure transparency over the use of personal data
to the mDL holder.

**E.4 Protection of keys and mDL data**

Device retrieval requires that mDLs be able to dynamically authenticate
(MAC) the data they return to an mDL reader in possession of the mDL
verifier. This mDL authentication key should be protected from
unauthorized usage.

The key should be protected with industry state-of-the-art protection
methods. Examples are:

--- a trusted execution environment;

--- a hardware secure element with appropriate certifications;

--- other secure hardware solution or combination of the aforementioned;

--- use of biometrics or strong on-device authentication technology.

**E.5 PKI and trust framework**

TBD[^1]

**E.6 Anonymity and unlinkability**

**E.6.1 Applicability**

Anonymity and Unlinkability privacy recommendations apply equally to
server and device retrieval and regardless of the architecture of an mDL
solution.

This annex defines anonymity according to ISO/IEC 29100 as a
\"characteristic of information that does not permit a personally
identifiable information principal to be identified directly or
indirectly\". It is noted that this explicitly includes required
mitigations against de-anonymization techniques, which are defined as
"any process in which anonymous data is cross-referenced with other
sources of data to re-identify the anonymous data source" in ISO/IEC
20889.

Transactions between mDL holders and mDL verifiers should be anonymous
and unlinkable under at least the following threat models.

The following subsections provide an overview of technical and
organizational mitigations to help provide anonymity and unlinkability.

**E.6.2 Data minimization for metadata**

TBD[^i^](#EN1)

**E.6.3 Rotation of public keys**

TBD[^i^](#EN1)

**E.6.4 MSO digests**

TBD[^i^](#EN1)

**E.7 Tracking mDL usage**

For device retrieval, transaction information should not be logged or
uploaded in any form that permits issuing authorities or technology
providers to track usage of the mDL or the habits of mDL holders.

Technology providers should implement ephemeral session keys, and key
rotation described in the "Unlinkability" section so that mDL verifiers
cannot collude to track an mDL holder's usage of their mDL across use
cases.

**E.8 Collection limitation**

Neither the mDL during device retrieval nor the issuing authority during
server retrieval should respond with more data than was requested by the
mDL verifier in order to fulfil the transaction. This document permits
field-by-field retrieval of data elements. Consent should always be
granted by the mDL holder with full knowledge of the requester, the
requested data and purpose of granting consent.

**E.9 Data minimization**

**E.9.1 Issuing authority data minimization**

TBD[^i^](#EN1)

**E.9.2 mDL verifier data minimization**

TBD[^i^](#EN1)

**E.10 mDL holder (user) authentication**

Unlocking and use of the mDL should only be permitted with appropriate
mDL holder authentication proving that the user is the intended mDL
holder. This ensures that others cannot collect mDL data without the mDL
holder.

User authentication may be required to unlock the device and the mDL.
With this, it becomes more difficult for someone other than the mDL
holder to access and unlock the mDL data or to present and use the mDL.
However, many mobile device users enroll device-level authentication for
shared users of the device that may not be the mDL holder (e.g.,
enrolling a child fingerprint to unlock a device in order to play
games). mDL holder authentication should tie the present user to the mDL
holder to whom the mDL was originally issued.

ISO18013-5 \[R01\] provides a method for identity verification performed
by the mDL verifier by comparing the portrait received by the mDL reader
to the person presenting the mDL. If this is not sufficient to meet the
risk requirements of the transaction, the mDL reader may implement
biometric comparison of the person presenting the mDL to the portrait.

**E.11** **mDL holder consent**

**E.11.1 Informed consent**

No mDL data should be shared with any other party without informed
consent. Informed Consent dictates that the mDL holder be given
sufficient informed just-in-time notice about the data being requested,
the entity requesting the data, and the purpose for the request.
Informed Consent also requires consent gathering --- that the mDL holder
should be able to actively confirm or deny, given the informational
notice, that their data is about to be shared with the mDL verifier.
Consent Gathering can happen at two distinct times in the flow of a
transaction.

In no case should a blanket authorization to share data with any mDL
verifier or specific mDL verifiers be obtained from the mDL holder as
part of Terms and Conditions, via a well explained pre-consent
mechanism, or without a transaction-time informed consent process.

**E.11.2 Device engagement**

The action of tapping or allowing a QR code to be scanned is proximal
consent to connect the mDL to the mDL reader (device retrieval) or for
the mDL reader to connect to the issuing authority (server retrieval).

**E.11.3 Data transfer**

The mDL reader requests data and the mDL should obtain consent from the
mDL holder to release the requested data elements and to validate the
issuing authority signature on those data elements. This should be
performed in one of two modes - pre-consent or transaction-time.

**E.11.4 Pre-consent**

Pre-consent enables mDL holders to configure specific mDL verifiers with
whom they have a trust relationship to share data. Those mDL verifiers
may be permitted access to a repeat set of mDL data without
transaction-time consent. In all cases that pre-consent is long-lived,
the mDL holder should clearly select this configuration and be aware
that pre-consent is in place.

**E.11.5 Transaction-time consent**

Transaction-time consent is just-in-time informed notice and consent
gathering during the processing of the request and before the response
is provided to the mDL reader.

## **ISO/IEC 18013-5** - Annex F

**Annex F**

**IANA Considerations**

**F.1 COSE Elliptic Curves registration**

This document registers the brainpool curves described in 9.1.5.2 in the
IANA COSE Elliptic Curves registry defined in RFC 8152.

Name: brainpoolP256r1Value: under consideration from IANAKey Type:
EC2Description: BrainpoolP256r1Change Controller: ISO/IEC JTC 001/SC
17/WG10Reference: ISO/IEC 18013-5:2021, 9.1.5.2Recommended: no

Name: brainpoolP320r1Value: under consideration from IANAKey Type:
EC2Description: BrainpoolP320r1Change Controller: ISO/IEC JTC 001/SC
17/WG10Reference: ISO/IEC 18013-5:2021, 9.1.5.2Recommended: no

Name: brainpoolP384r1Value: under consideration from IANAKey Type:
EC2Description: BrainpoolP384r1Change Controller: ISO/IEC JTC 001/SC
17/WG10Reference: ISO/IEC 18013-5:2021, 9.1.5.2Recommended: no

Name: brainpoolP512r1Value: under consideration from IANAKey Type:
EC2Description: BrainpoolP512r1Change Controller: ISO/IEC JTC 001/SC
17/WG10Reference: ISO/IEC 18013-5:2021, 9.1.5.2Recommended: no

**F.2 URI registration**

This document registers the "EUDIW" URI defined in 8.2.2.3 in the IANA
Uniform Resource Identifier (URI) Schemes registry defined in RFC 7595.

URI scheme name: EUDIWStatus: permanentApplications/protocols that use
this scheme name: the EUDIW URI scheme is intended to be used by mobile
document applicationsContact: \[To be filled in by ISO secretariat\]
Change controller: ISO/IEC JTC 001/SC 17/WG10Reference: ISO/IEC
18013-5:2021.
