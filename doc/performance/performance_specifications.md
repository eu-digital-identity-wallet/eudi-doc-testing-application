# Performance Test Specifications

This section supports the requirements defined in [requirements.md](performance_requirements.md).  
It describes the performance testing for the EUDI Wallet application, including the performance test scenarios, the required test environment and devices configuration, entry and exit criteria and input data.

## Performance Test Scenarios

Performance testing was conducted using end-to-end functional test flows executed across multiple real devices. The scenarios utilized to produce the results are the following:

1. Add a Document from List
2. Deferred Issuing
3. Credential Issuance (Same Device)
4. Dynamic Issuance Process
5. Pre-authorization Code (Same Device)
6. Present Attestation from EUDI Wallet
7. Sign Document
8. Batch Issuance Through Issuer, Verifier and Wallet Same/Cross Device (10 instances)
9. Filter, Sort, and Sort Documents/Transactions  

---

### Disclaimer for Scenario 4 and Reason for Non-Execution

The Dynamic Issuance Process scenario was successfully executed and included in the previous performance test report.

However, in the current testing cycle, execution of this scenario was not possible to executed. Specifically, the execution is impacted by Task #[206](https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/206) in the EUDI Wallet Product Roadmap, which affects the availability and/or functionality required for the Dynamic Issuance Process to be executed as originally designed.

### Disclaimer for Scenario 9 and Reason for Non-Execution

Some operations, such as *Filter, Sort, and Sort Documents/Transactions*, may not significantly affect performance metrics. The reasons are outlined below:

| **Resource** | **Reason for Limited Impact** |
|---------------|-------------------------------|
| **Network Usage** | All Data is already loaded locally, these operations might not require additional network calls. This is typical in apps that load data initially and perform client-side manipulations.
| **CPU Usage** | Operations are lightweight and dataset sizes are small, resulting in negligible CPU impact. |
| **Memory Usage** | Operations modify existing data structures without significant allocation of new resources, so memory usage remains stable.

---

## BrowserStack Farm and Real Device Environment Configuration

Performance tests are conducted on real devices hosted in the BrowserStack device farm.
The objective is to verify consistent performance and stability across multiple operating system versions and device types.

- BrowserStack Execution Devices

All applicable performance scenarios are executed on the following devices:

| **Platform** | **Device**            | **OS Version**        | **Purpose** |
|--------------|-----------------------|-----------------------|-------------|
| iOS          | iPhone 16             | iOS 18                | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| iOS          | iPhone 15             | iOS 17                | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| iOS          | iPhone 14             | iOS 26                | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| Android      | Huawei Nova 13        | HarmonyOs 4/ OS 12    | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| Android      | Samsung Galaxy A16 5G | Android 15            | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| Android      | Google Pixel 8        | Android 14            | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| Android      | OnePlus 11R           | Android 13 (OxygenOS) | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| Android      | Redmi Note 12 Pro     | Android 12 (MIUI)     | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |

- Scenarios Excluded from BrowserStack Execution

The following scenarios could not be executed on BrowserStack, as they require interaction between two distinct physical devices and cross-device communication that is not supported by the BrowserStack execution model:

1. Present Attestation from EUDI Wallet
2. Presentation of Batch Issued Attestations to Relying Party on Separate Device
3. Batch Issuance of Attestations in EUDI Wallet from Issuer Cross Device

- Local Device Execution for Cross-Device Scenarios

The above scenarios were executed locally using Android Studio and Xcode on physical devices, as shown below:

| **Platform** | **Device** | **OS Version** | **Purpose** |
|---------------|-------------|----------------|-------------|
| Android | POCO X5 Pro 5G | Android 14 | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability |
| iOS | iPhone 14 Plus | iOS 18 | Response Time ≤ 2s, CPU ≤ 40%, Memory ≤ 250 MB, App Stability

- Execution Environment Summary 

The use of the BrowserStack device farm enables consistent and repeatable execution on real hardware, while significantly increasing platform, OS version, and vendor diversity compared to physical device-only testing.
Local execution was selectively applied only where cross-device interaction requirements could not be met in the cloud environment.

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
- Results meet or exceed the defined **Requirements** in [requirements.md](performance_requirements.md)
- A final test report has been produced and reviewed.

---

## Data Input

To ensure accuracy and reliability of performance test results, a defined set of test data is used across all devices.

### Attestations Pre-loaded in the EUDI Wallet Application

Each device is preloaded with the following attestations to simulate a diverse user dataset:

| **Document Type** | **Quantity** |
|-------------------|--------------|
| PID (MSO Mdoc)    | 3            |
| MDL               | 3            |
| Photo ID          | 2            |
| EHIC (MSO Mdoc)   | 2            |

**Note: 10 attestations per device preloaded prior to test execution.**

---

