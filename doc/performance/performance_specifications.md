# Performance Test Specifications

> This section supports the requirements defined in [requirements.md](requirements.md).  
> It describes *how* the performance testing for the EUDI Wallet application will be executed, detailing workloads, tools, data input, and environment specifications.

---

## Workload Profiles

The following section provides an overall perspective of the functionalities offered by the EUDI Wallet application.
Performance test scenarios are closely aligned with business test cases to ensure that the application meets all critical business and operational requirements.

By aligning the test scenarios with business cases, we validate that the application performs efficiently under expected workloads and supports all necessary business processes effectively.

### Functional Scenarios
1. Add a Document from List
2. Deferred Issuing
3. Credential Issuance (Same Device)
4. Dynamic Issuance Process
5. Pre-authorization Code (Same Device)
6. Present Attestation from EUDI Wallet
7. Sign Document
8. Batch Issuance Through Issuer and Wallet Same/Cross Device (10 instances)
9. Filter, Sort, and Sort Documents/Transactions  

---

### Disclaimer for Scenario 9 and Reason for Non-Execution

Some operations, such as *Filter, Sort, and Sort Documents/Transactions*, may not significantly affect performance metrics. The reasons are outlined below:

| **Resource** | **Reason for Limited Impact** |
|---------------|-------------------------------|
| **Network Usage** | All Data is already loaded locally, these opereations might not require additional network calls. This is typical in apps that load data initially and perform client-side manipulations.
| **CPU Usage** | Operations are lightweight and dataset sizes are small, resulting in negligible CPU impact. |
| **Memory Usage** | Operations modify existing data structures without significant allocation of new resources, so memory usage remains stable.

---

## Device and Environment Configuration

Performance tests are conducted on two real devices across Android and iOS platforms.
The goal is to verify consistent performance across different OS versions and device classes.

| **Platform** | **Device** | **OS Version** | **Purpose** |
|---------------|-------------|----------------|-------------|
| Android | POCO X5 Pro 5G | Android 14 | Real-device performance validation |
| iOS | iPhone 14 Plus | iOS 18 | Real-device performance validation

## Virtual User Distribution

Performance testing will be executed on various **mobile devices** across both **iOS** and **Android** platforms.  
The goal is to determine the maximum user load each scenario can sustain before performance degrades or failures occur.

## Load Test

The main goal of this test is to check response time, CPU/memory usage, network activity and overall stability of a system when it is subjected to varying  levels of traffic or load.

| **Test Type** | **Purpose** | **Linked Performance Requirement(s)** | **Devices** |
|---------------|-------------|----------------|-------------|
| Load Test | Measures system behavior under expected and peak workloads, focusing on responsiveness, stability, and resource usage. | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability | POCO X5 Pro 5G(Android) & iPhone 14 Plus(iOS)

By linking each test to the corresponding requirement, we ensure full traceability between Performance Requirements, Test Execution, and Measured Results. 	

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

### Attestations Pre-loaded in the EUDI Wallet Application

Each device is preloaded with the following credentials to simulate a diverse user dataset:

| **Document Type** | **Quantity** |
|--------------------|-------------|
| PID | 2 |
| PID (test) | 1 | 
| MDL | 2 |
| Age Over 18 Pseudonym | 1 |
| Age Over 18 Pseudonym | 1 |
| Photo ID | 1 |
| EHIC | 1 | 
| EHIC | 1 |

**Note: 10 attestations per device preloaded prior to test execution.**

---

## Execution Methodology

All 10 performance scenarios were executed manually on each of the two real devices listed above.
This manual approach ensured consistent execution flow and accurate measurement of each metric.

After every test execution:

- CPU and memory usage were recorded using Android Studio Profiler (Android) and Xcode Instruments (iOS).

- Network activity (requests, data sent/received, and latency) was captured using the same tools.

- Response times and app stability were observed in real-time and validated through profiler outputs.

Results were documented immediately after each scenario to ensure precision and traceability.

The detailed results and graphs for each test execution are available in the document: [performance_tests_results.md](C:\Users\ftheofil\Projects\eu-digital-identity-walleteudi-doc-testing-application-internal\doc\performance\preformance_test_results.md)

