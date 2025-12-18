## Test Run – Performance Metrics

## Definitions

The following definitions are listed to clarify important terms used throughout this document.  
These terms are aligned with the **EUDI Architecture and Reference Framework [R01]**.

| **Acronym** | **Definition** |
|--------------|----------------|
| **EUDI Wallet User** | Citizen/Natural person using the EUDI Wallet to receive, store and present attestations (PID, QEAA or EAA) about themselves, including to prove their identity. |
| **Issuer** | A Person Identification Data Provider issuing PID or a (Qualified) Trust Service Provider issuing (Q)EAA. In the case of the EUDI Wallet, there may be multiple Issuers for PID and (Q)EAA. |
| **mDL Provider** | mDL Providers maintain an interface for requesting and providing mDL to EUDI Wallets. |
| **PID** | A set of data enabling the identity of a natural person, or a natural person representing a legal person — eIDAS Regulation. |
| **PID Provider** | A Member State or legal entity providing Person Identification Data to Users. |
| **Relying Party** | Private or Public party providing online services, intending to utilize the EUDI Wallet for user identification and authentication purposes. |

## Performance Test Execution Summary

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

### Execution Process

Before each test:

- A connection was established to the required real device hosted in the BrowserStack device farm.
- The application under test was installed and launched on the selected device.
- BrowserStack App Live performance monitoring tools were enabled and configured to collect runtime metrics.

During test execution:

- Each performance scenario was executed manually on the BrowserStack real device.
- CPU usage, memory usage, response time, and app stability were monitored using BrowserStack’s built-in performance profiling tools.
- Screenshots of performance metrics were captured during execution for documentation and analysis.

Detailed results and screenshot-based reports for each test execution are available below.

## Test Environment

The following specifications describe the environment in which performance testing was conducted.

### Android Environment
- **Device Used:** 
    1. POCO X5 Pro 5G
       - **Connection:** Connected to PC with IDE
       - **Tools Used:** Android Studio Profiler and App Inspection
       - **Network:** Wi-Fi
    2. Samsung Galaxy A16 5G - Android 15
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
    3. Google Pixel - Android 14
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
    4. OnePlus 11R - Android 13 (OxygenOS)
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
    5. Redmi Note 12 Pro - Android 12 (MIUI)
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
    6. Huawei Nova 13 - HarmonyOS 4 / OS 12
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
      
- **Test Data:**
    - Simulated EUDI Wallet application with 10 documents
    - Dev Issuer: [https://ec.dev.issuer.eudiw.dev/](https://ec.dev.issuer.eudiw.dev/)
    - Dev Verifier: [https://dev.verifier.eudiw.dev/homee](https://dev.verifier.eudiw.dev/home)

### iOS Environment
- **Device Used:** 
    1. iPhone 14 Plus - iOS 18
       - **Connection:** Connected to PC with IDE
       - **Tools Used:** Xcode Instruments
       - **Network:** Wi-Fi
    2. iPhone 16 - iOS 18
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
    3. iPhone 15 - iOS 17
       - **Connection:** Connected to Browserstack App Live Dashboard
       - **Tools Used:** BrowserStack’s built-in performance profiling tools.
       - **Network:** Wi-Fi
    4. iPhone 14 - iOS 26
      - **Connection:** Connected to Browserstack App Live Dashboard
      - **Tools Used:** BrowserStack’s built-in performance profiling tools.
      - **Network:** Wi-Fi

- **Test Data:**
    - Simulated EUDI Wallet application with 10 documents
    - Dev Issuer: [https://ec.dev.issuer.eudiw.dev/](https://ec.dev.issuer.eudiw.dev/)
    - Dev Verifier: [https://dev.verifier.eudiw.dev/homee](https://dev.verifier.eudiw.dev/home)


## Test Execution and Results

### Scenario 1: Add a Document from List

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 1: CPU/Memory Graph

![img_1.png](charts_images/img_1.png)

- Figure 2: Network Performance Graph

![img_2.png](charts_images/img_2.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4% |
| Memory Usage | 234.1 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 3: CPU/Memory Graph

![img_3.png](charts_images/img_3.png)

- Figure 4: Network Performance Graph

![img_4.png](charts_images/img_4.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 57.64 MB |
| Response Time | < 1 s |

---

### Scenario 2: Deferred Issuing

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 5: CPU/Memory Graph

![img_6.png](charts_images/img_6.png)

- Figure 6: Network Performance Graph

![img_7.png](charts_images/img_7.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 10% |
| Memory Usage | 231.8 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 7: CPU/Memory Graph

![img_8.png](charts_images/img_8.png)

- Figure 8: Network Performance Graph

![img_9.png](charts_images/img_9.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 18.8% |
| Memory Usage | 55.47 MB |
| Response Time | < 1 s |

---

### Scenario 3: Credential Issuance (Same Device)

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 9: CPU/Memory Graph

![img_11.png](charts_images/img_11.png)

- Figure 10: Network Performance Graph

![img_12.png](charts_images/img_12.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3% |
| Memory Usage | 210.3 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 11: CPU/Memory Graph

![img_13.png](charts_images/img_13.png)

- Figure 12: Network Performance Graph

![img_14.png](charts_images/img_14.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 57.47 MB |
| Response Time | < 1 s |

---

### Scenario 4: Dynamic Issuance Process

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 13: CPU/Memory Graph

![img_15.png](charts_images/img_15.png)

- Figure 14: Network Performance Graph

![img_17.png](charts_images/img_17.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0% |
| Memory Usage | 161.5 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 15: CPU/Memory Graph

![img_18.png](charts_images/img_18.png)

- Figure 16: Network Performance Graph

![img_20.png](charts_images/img_20.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 50.36 MB |
| Response Time | < 1 s |

---

### Scenario 5: Pre-Authorization Code (Same Device)

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 17: CPU/Memory Graph

![img_21.png](charts_images/img_21.png)

- Figure 18: Network Performance Graph

![img_22.png](charts_images/img_22.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1% |
| Memory Usage | 213.2 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 19: CPU/Memory Graph

![img_23.png](charts_images/img_23.png)

- Figure 20: Network Performance Graph

![img_24.png](charts_images/img_24.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 50.64 MB |
| Response Time | < 1 s |

---

### Scenario 6: Present Attestation from EUDI Wallet

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 21: CPU/Memory Graph

![img_25.png](charts_images/img_25.png)

- Figure 22: Network Performance Graph

![img_26.png](charts_images/img_26.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5% |
| Memory Usage | 213.2 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 23: CPU/Memory Graph

![img_27.png](charts_images/img_27.png)

- Figure 24: Network Performance Graph

![img_28.png](charts_images/img_28.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0% |
| Memory Usage | 58.24 MB |
| Response Time | < 1 s |

---

### Scenario 7: Sign Document

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 25: CPU/Memory Graph

![img_29.png](charts_images/img_29.png)

- Figure 26: Network Performance Graph

![img_30.png](charts_images/img_30.png)

- Figure 27: Additional Network Performance Graph

![img_31.png](charts_images/img_31.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0% |
| Memory Usage | 229.6 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus
**Figures:**
- Figure 28: CPU/Memory Graph

![img_32.png](charts_images/img_32.png)

- Figure 29: Network Performance Graph

![img_33.png](charts_images/img_33.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 62.58 MB |
| Response Time | < 1 s |

---

# 7. Conclusion and Comments

Based on the collected performance metrics, the **EUDI Wallet application** demonstrates strong stability and efficiency across both platforms (Android and iOS).

### Summary of Findings
- **Response Time:** Remained consistently under 1 second in all test scenarios (well below the 2.0s acceptance limit).
- **Stability:** No crashes or critical errors occurred during any test runs.
- **Network Behavior:** All network requests were limited to expected production endpoints (issuer/verifier).
- **Resource Usage:** CPU and memory remained within acceptable limits for all devices, even under repeated load.

### Overall Conclusion
The application successfully meets all defined **performance acceptance criteria** and exhibits robust, efficient behavior across devices and platforms.  
No significant performance issues were detected during testing.

---

## References
- [requirements.md](requirements.md)
- [specifications.md](specifications.md)