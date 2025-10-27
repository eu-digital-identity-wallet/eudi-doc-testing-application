# Specifications

> This document supports the requirements defined in [requirements.md](requirements.md).  
> It describes *how* the performance testing for the EUDI Wallet application will be executed, detailing workloads, tools, data input, and environment specifications.

---

## Workload Profiles

The following section provides an overall perspective of all the functionalities offered by the **EUDI Wallet** application.  
Performance test scenarios are closely aligned with business test cases to ensure that the application meets all critical business requirements and operational goals.

By aligning the test scenarios with business test cases, we aim to validate that the application performs efficiently under expected workloads and supports all necessary business processes effectively.

### Functional Scenarios
1. Add a Document from List
2. Deferred Issuing
3. Credential Issuance (Same Device)
4. Dynamic Issuance Process
5. Pre-authorization Code (Same Device)
6. Present Attestation from EUDI Wallet
7. Sign Document
8. Filter, Sort, and Sort Documents/Transactions

---

### Disclaimer for Scenario 8 and Reason for Non-Execution

Some operations, such as *Filter, Sort, and Sort Documents/Transactions*, may not significantly affect performance metrics. The reasons are outlined below:

| **Resource** | **Reason for Limited Impact** |
|---------------|-------------------------------|
| **Network Usage** | Data is already loaded locally; operations may not require additional network calls. |
| **CPU Usage** | Operations are lightweight and dataset sizes are small; CPU impact is negligible. |
| **Memory Usage** | Existing data structures are reused; minimal new allocation leads to stable memory usage. |

---

## Virtual User Distribution

Performance testing will be executed on various **mobile devices** across both **iOS** and **Android** platforms.  
The goal is to determine the maximum user load each scenario can sustain before performance degrades or failures occur.

### Example Device Matrix

| **Platform** | **Device** | **OS Version** | **Purpose** |
|---------------|-------------|----------------|-------------|
| Android | POCO X5 Pro 5G | Android 14 | Real-device performance validation |
| iOS | iPhone 14 Plus | iOS 18 | Real-device performance validation


## Load Test

The main goal of this test is to check response time, CPU/memory usage, network activity and overall stability of a system when it is subjected to varying  levels of traffic or load.

---

## Test Prioritization

All test activities defined in this document will be executed within the scheduled test period.  
The primary test execution type is **Load Testing**, conducted on real mobile devices to simulate true user conditions.

### Priority Matrix

| **Priority** | **Test Type** | **Description** |
|---------------|---------------|-----------------|
| **High** | Load Test | Measure app performance under expected workloads. |

---

## Entry & Exit Criteria

### Entry Criteria
All of the following must be met before performance testing begins:
- This specifications document is approved.
- Test data and volumetrics are provided by project staff.
- Test environment is configured and available.
- Test preparation and data population are completed.
- No unresolved *Blocking* or *Critical* issues (with agreed exceptions).
- All required test cases are available.

### Exit Criteria
Testing can be considered complete when:
- All defined test scenarios have been executed.
- All identified issues are resolved or have approved workarounds.
- Results meet or exceed the defined **Acceptance Criteria** in [requirements.md](requirements.md).
- A final test report has been produced and reviewed.

---

## Data Input

To ensure accuracy and reliability of performance test results, a defined set of test data is used across all devices.

### Document Addition to the EUDI Wallet Application

The following credentials will be loaded to simulate diverse user datasets:

| **Document Type** | **Quantity** | **Format / Notes** |
|--------------------|--------------|--------------------|
| PID | 2 | Personal ID |
| PID (test) | 1 | sd-jwt-vc format |
| MDL | 2 | Mobile Driving License |
| Age Over 18 Pseudonym | 1 | Standard |
| Age Over 18 Pseudonym | 1 | sd-jwt-vc format |
| Photo ID | 1 | Image-based credential |
| EHIC | 1 | European Health Insurance Card |
| EHIC | 1 | sd-jwt-vc format |

---