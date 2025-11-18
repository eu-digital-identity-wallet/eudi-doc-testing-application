# Performance Test Specifications

> This section supports the requirements defined in [requirements.md](requirements.md).  
It describes the performance testing for the EUDI Wallet application, including the performance test scenarios, the required test environment and devices configuration, entry and exit criteria and input data.

## Performance Test Scenarios
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
| **Network Usage** | All Data is already loaded locally, these operations might not require additional network calls. This is typical in apps that load data initially and perform client-side manipulations.
| **CPU Usage** | Operations are lightweight and dataset sizes are small, resulting in negligible CPU impact. |
| **Memory Usage** | Operations modify existing data structures without significant allocation of new resources, so memory usage remains stable.

---

## Device and Environment Configuration

Performance tests are conducted on two real devices across Android and iOS platforms.
The goal is to verify consistent performance across different OS versions and devices.

| **Platform** | **Device** | **OS Version** | **Purpose** |
|---------------|-------------|----------------|-------------|
| Android | POCO X5 Pro 5G | Android 14 | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| iOS | iPhone 14 Plus | iOS 18 | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability

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
- Results meet or exceed the defined **Requirements** in [requirements.md](requirements.md).
- A final test report has been produced and reviewed.

---

## Data Input

To ensure accuracy and reliability of performance test results, a defined set of test data is used across all devices.

### Attestations Pre-loaded in the EUDI Wallet Application

Each device is preloaded with the following attestations to simulate a diverse user dataset:

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

