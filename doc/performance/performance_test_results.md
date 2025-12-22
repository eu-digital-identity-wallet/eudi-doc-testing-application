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

Browserstack Farm:
- A connection was established to the required real device hosted in the BrowserStack device farm.
- The application under test was installed and launched on the selected device.
- BrowserStack App Live performance monitoring tools were enabled and configured to collect runtime metrics.

Local Execution:
- Both devices were connected to the laptop via USB.
- Android Studio Profiler (for Android) and Xcode Instruments (for iOS) were launched and configured for live performance tracking.
- The application was launched on each device.

During test execution:

Browserstack Farm:
- Each performance scenario was executed manually on the BrowserStack real device.
- CPU usage, memory usage, response time, and app stability were monitored using BrowserStack’s built-in performance profiling tools.
- Screenshots of performance metrics were captured during execution for documentation and analysis.

Local Execution:
- Each scenario was performed manually on the devices.
- CPU usage, memory usage, response time, and app stability were monitored in real time.
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

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 1: CPU/Memory Graph
  
![img_1.png](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/blob/milestone/2025.Q4/doc/performance/charts_images/img_1.png)

- Figure 2: Network Performance Graph

![img_2.png](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/blob/milestone/2025.Q4/doc/performance/charts_images/img_2.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.03% |
| Memory Usage | 237.18 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 3: CPU/Memory Graph

![img_3.png](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/blob/milestone/2025.Q4/doc/performance/charts_images/img_3.png)

- Figure 4: Network Performance Graph

![img_4.png](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/blob/milestone/2025.Q4/doc/performance/charts_images/img_4.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 6.28% |
| Memory Usage | 228.68 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 5: CPU/Memory Graph

![img_5.png](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/blob/milestone/2025.Q4/doc/performance/charts_images/img_5.png)

- Figure 6: Network Performance Graph

![img_6.png](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/blob/milestone/2025.Q4/doc/performance/charts_images/img_6.png)
**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.77% |
| Memory Usage | 218.79 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 7: CPU/Memory Graph

![img_7.png](charts_images\img_7.png)

- Figure 8: Network Performance Graph

![img_8.png](charts_images\img_8.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 237.99 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 9: CPU/Memory Graph

![img_9.png](charts_images\img_9.png)

- Figure 10: Network Performance Graph

![img_10.png](charts_images\img_10.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.27% |
| Memory Usage | 238.13 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 11: CPU/Memory Graph

![img_11.png](charts_images\img_11.png)

- Figure 12: Network Performance Graph

![img_12.png](charts_images\img_12.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5.06% |
| Memory Usage | 55.81 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 13: CPU/Memory Graph

![img_13.png](charts_images\img_13.png)

- Figure 14: Network Performance Graph

![img_14.png](charts_images\img_14.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.88% |
| Memory Usage | 46.74 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 15: CPU/Memory Graph

![img_15.png](charts_images\img_15.png)

- Figure 16: Network Performance Graph

![img_16.png](charts_images\img_16.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.56% |
| Memory Usage | 44.33 MB |
| Response Time | < 1 s |

---

### Scenario 2: Deferred Issuing

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 17: CPU/Memory Graph

![img_17.png](charts_images/img_17.png)

- Figure 18: Network Performance Graph

![img_18.png](charts_images/img_18.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.41% |
| Memory Usage | 237.12 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 19: CPU/Memory Graph

![img_19.png](charts_images/img_19.png)

- Figure 20: Network Performance Graph

![img_20.png](charts_images/img_20.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5.95% |
| Memory Usage | 280.32 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 21: CPU/Memory Graph

![img_21.png](charts_images/img_21.png)

- Figure 22: Network Performance Graph

![img_22.png](charts_images/img_22.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.57% |
| Memory Usage | 219.27 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 23: CPU/Memory Graph

![img_23.png](charts_images/img_23.png)

- Figure 24: Network Performance Graph

![img_24.png](charts_images/img_24.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.64% |
| Memory Usage | 242.02 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 25: CPU/Memory Graph

![img_25.png](charts_images/img_25.png)

- Figure 26: Network Performance Graph

![img_26.png](charts_images/img_26.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.03% |
| Memory Usage | 239.31 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 27: CPU/Memory Graph

![img_27.png](charts_images/img_27.png)

- Figure 28: Network Performance Graph

![img_28.png](charts_images/img_28.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.08% |
| Memory Usage | 83.65 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 29: CPU/Memory Graph

![img_29.png](charts_images/img_29.png)

- Figure 30: Network Performance Graph

![img_30.png](charts_images/img_30.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.86% |
| Memory Usage | 49.09 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 31: CPU/Memory Graph

![img_31.png](charts_images/img_31.png)

- Figure 32: Network Performance Graph

![img_32.png](charts_images/img_32.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.73% |
| Memory Usage | 46.55 MB |
| Response Time | < 1 s |

---

### Scenario 3: Credential Issuance (Same Device)

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 33: CPU/Memory Graph

![img_33.png](charts_images/img_33.png)

- Figure 34: Network Performance Graph

![img_34.png](charts_images/img_34.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.15% |
| Memory Usage | 227.72 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 35: CPU/Memory Graph

![img_35.png](charts_images/img_35.png)

- Figure 36: Network Performance Graph

![img_36.png](charts_images/img_36.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 6.71% |
| Memory Usage | 262.56 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 37: CPU/Memory Graph

![img_37.png](charts_images/img_37.png)

- Figure 38: Network Performance Graph

![img_38.png](charts_images/img_38.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.95% |
| Memory Usage | 220.38 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 39: CPU/Memory Graph

![img_39.png](charts_images/img_39.png)

- Figure 40: Network Performance Graph

![img_40.png](charts_images/img_40.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 243.31 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 41: CPU/Memory Graph

![img_41.png](charts_images/img_41.png)

- Figure 42: Network Performance Graph

![img_42.png](charts_images/img_42.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.56% |
| Memory Usage | 235.9 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 43: CPU/Memory Graph

![img_43.png](charts_images/img_43.png)

- Figure 44: Network Performance Graph

![img_44.png](charts_images/img_44.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.54% |
| Memory Usage | 52.06 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 45: CPU/Memory Graph

![img_45.png](charts_images/img_45.png)

- Figure 46: Network Performance Graph

![img_46.png](charts_images/img_46.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.84% |
| Memory Usage | 49.62 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 47: CPU/Memory Graph

![img_47.png](charts_images/img_47.png)

- Figure 48: Network Performance Graph

![img_48.png](charts_images/img_48.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.93% |
| Memory Usage | 47.28 MB |
| Response Time | < 1 s |

---

### Scenario 4: Batch Issuance Through Issuer (Same Device)

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 49: CPU/Memory Graph

![img_49.png](charts_images/img_49.png)

- Figure 50: Network Performance Graph

![img_50.png](charts_images/img_50.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.15% |
| Memory Usage | 227.72 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 51: CPU/Memory Graph

![img_51.png](charts_images/img_51.png)

- Figure 52: Network Performance Graph

![img_52.png](charts_images/img_52.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 6.71% |
| Memory Usage | 262.56 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 53: CPU/Memory Graph

![img_53.png](charts_images/img_53.png)

- Figure 54: Network Performance Graph

![img_54.png](charts_images/img_54.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.64% |
| Memory Usage | 228.62 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 55: CPU/Memory Graph

![img_55.png](charts_images/img_55.png)

- Figure 56: Network Performance Graph

![img_56.png](charts_images/img_56.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 257.44 MB |
| Response Time | < 1 s |

---       

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 57: CPU/Memory Graph

![img_57.png](charts_images/img_57.png)

- Figure 58: Network Performance Graph

![img_58.png](charts_images/img_58.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.08% |
| Memory Usage | 249.59 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 59: CPU/Memory Graph

![img_59.png](charts_images/img_59.png)

- Figure 60: Network Performance Graph

![img_60.png](charts_images/img_60.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.54% |
| Memory Usage | 52.06 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 61: CPU/Memory Graph

![img_61.png](charts_images/img_61.png)

- Figure 62: Network Performance Graph

![img_62.png](charts_images/img_62.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.88% |
| Memory Usage | 49.92 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 63: CPU/Memory Graph

![img_63.png](charts_images/img_63.png)

- Figure 64: Network Performance Graph

![img_64.png](charts_images/img_64.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.91% |
| Memory Usage | 43.57 MB |
| Response Time | < 1 s |

---

### Scenario 5: Pre-Authorization Code (Same Device)

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 65: CPU/Memory Graph

![img_65.png](charts_images/img_65.png)

- Figure 66: Network Performance Graph

![img_66.png](charts_images/img_66.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.43% |
| Memory Usage | 229.91 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 67: CPU/Memory Graph

![img_67.png](charts_images/img_67.png)

- Figure 68: Network Performance Graph

![img_68.png](charts_images/img_68.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5.98% |
| Memory Usage | 271.11 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 69: CPU/Memory Graph

![img_69.png](charts_images/img_69.png)

- Figure 70: Network Performance Graph

![img_70.png](charts_images/img_70.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.01% |
| Memory Usage | 225.41 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 71: CPU/Memory Graph

![img_71.png](charts_images/img_71.png)

- Figure 72: Network Performance Graph

![img_72.png](charts_images/img_72.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 241.34 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 73: CPU/Memory Graph

![img_73.png](charts_images/img_73.png)

- Figure 74: Network Performance Graph

![img_74.png](charts_images/img_74.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.74% |
| Memory Usage | 219.65 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 75: CPU/Memory Graph

![img_75.png](charts_images/img_75.png)

- Figure 76: Network Performance Graph

![img_76.png](charts_images/img_76.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.59% |
| Memory Usage | 90.43 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 77: CPU/Memory Graph

![img_77.png](charts_images/img_77.png)

- Figure 78: Network Performance Graph

![img_78.png](charts_images/img_78.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.93% |
| Memory Usage | 49.44 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 79: CPU/Memory Graph

![img_79.png](charts_images/img_79.png)

- Figure 80: Network Performance Graph

![img_80.png](charts_images/img_80.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.83% |
| Memory Usage | 48.32 MB |
| Response Time | < 1 s |

---

### Scenario 6: Present Attestation from EUDI Wallet

#### Device: POCO X5 Pro 5G - Android 14
**Figures:**
- Figure 81: CPU/Memory Graph

![img_81.png](charts_images/img_81.png)

- Figure 82: Network Performance Graph

![img_82.png](charts_images/img_82.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 261.8 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus - iOS 18
**Figures:**
- Figure 83: CPU/Memory Graph

![img_83.png](charts_images/img_83.png)

- Figure 84: Network Performance Graph

![img_84.png](charts_images/img_84.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 30.75 MB |
| Response Time | < 1 s |

---

### Scenario 7: Sign Document

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 85: CPU/Memory Graph

![img_85.png](charts_images/img_85.png)

- Figure 86: Network Performance Graph

![img_86.png](charts_images/img_86.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5.34% |
| Memory Usage | 255.49 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 87: CPU/Memory Graph

![img_87.png](charts_images/img_87.png)

- Figure 88: Network Performance Graph

![img_88.png](charts_images/img_88.png)
![img_89.png](charts_images/img_89.png)
![img_90.png](charts_images/img_90.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 6.91% |
| Memory Usage | 285.82 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOs)
**Figures:**
- Figure 91: CPU/Memory Graph

![img_91.png](charts_images/img_91.png)

- Figure 92: Network Performance Graph

![img_92.png](charts_images/img_92.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5.99% |
| Memory Usage | 215.71 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 93: CPU/Memory Graph

![img_93.png](charts_images/img_93.png)

- Figure 94: Network Performance Graph

![img_94.png](charts_images/img_94.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 246.77 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 95: CPU/Memory Graph

![img_95.png](charts_images/img_95.png)

- Figure 96: Network Performance Graph

![img_96.png](charts_images/img_96.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.78% |
| Memory Usage | 295.92 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 97: CPU/Memory Graph

![img_97.png](charts_images/img_97.png)

- Figure 98: Network Performance Graph

![img_98.png](charts_images/img_98.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.24% |
| Memory Usage | 81.28 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 99: CPU/Memory Graph

![img_99.png](charts_images\img_99.png)

- Figure 100-01-02-03-04-05-06-07: Network Performance Graph

![img_100.png](charts_images/img_100.png)
![img_101.png](charts_images/img_101.png)
![img_102.png](charts_images/img_102.png)
![img_103.png](charts_images/img_103.png)
![img_104.png](charts_images/img_104.png)
![img_105.png](charts_images/img_105.png)
![img_106.png](charts_images/img_106.png)
![img_107.png](charts_images/img_107.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.76% |
| Memory Usage | 63.91 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 108: CPU/Memory Graph

![img_108.png](charts_images/img_108.png)

- Figure 109: Network Performance Graph

![img_109.png](charts_images/img_109.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.13% |
| Memory Usage | 53 MB |
| Response Time | < 1 s |

---

### Scenario 8: Batch Issuance Through Wallet (Same Device)

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 110: CPU/Memory Graph

![img_110.png](charts_images/img_110.png)

- Figure 111: Network Performance Graph

![img_111.png](charts_images/img_111.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.03% |
| Memory Usage | 237.18 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12 Pro - Android 12 (MIUI)
**Figures:**
- Figure 112: CPU/Memory Graph

![img_112.png](charts_images/img_112.png)

- Figure 113: Network Performance Graph

![img_113.png](charts_images/img_113.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 6.28% |
| Memory Usage | 228.68 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 114: CPU/Memory Graph

![img_114.png](charts_images/img_114.png)

- Figure 115: Network Performance Graph

![img_115.png](charts_images/img_115.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 4.59% |
| Memory Usage | 234.72 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 116: CPU/Memory Graph

![img_116.png](charts_images/img_116.png)

- Figure 117: Network Performance Graph

![img_117.png](charts_images/img_117.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 257.62 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 - Android 14
**Figures:**
- Figure 118: CPU/Memory Graph

![img_118.png](charts_images/img_118.png)

- Figure 119: Network Performance Graph

![img_119.png](charts_images/img_119.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.51% |
| Memory Usage | 272.75 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 120: CPU/Memory Graph

![img_120.png](charts_images\img_120.png)

- Figure 121: Network Performance Graph

![img_121.png](charts_images/img_121.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 5.06% |
| Memory Usage | 55.81 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 122: CPU/Memory Graph

![img_122.png](charts_images\img_122.png)

- Figure 123: Network Performance Graph

![img_123.png](charts_images/img_123.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.06% |
| Memory Usage | 60.02 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 124: CPU/Memory Graph

![img_124.png](charts_images\img_124.png)

- Figure 125: Network Performance Graph

![img_125.png](charts_images/img_125.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.57% |
| Memory Usage | 51.58 MB |
| Response Time | < 1 s |

---

### Scenario 9: Batch Issuance Through Verifier (Same Device)

#### Device: Samsung Galaxy A16 5G - Android 15
**Figures:**
- Figure 126: CPU/Memory Graph

![img_126.png](charts_images/img_126.png)

- Figure 127: Network Performance Graph

![img_127.png](charts_images/img_127.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.57% |
| Memory Usage | 247.72 MB |
| Response Time | < 1 s |

---

#### Device: Redmi Note 12Pro - Android 12 (MIUI)
**Figures:**
- Figure 128: CPU/Memory Graph

![img_128.png](charts_images/img_128.png)

- Figure 129: Network Performance Graph

![img_129.png](charts_images/img_129.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 6.83% |
| Memory Usage | 270.2 MB |
| Response Time | < 1 s |

---

#### Device: OnePlus 11R - Android 13 (OxygenOS)
**Figures:**
- Figure 130: CPU/Memory Graph

![img_131.png](charts_images/img_131.png)

- Figure 131: Network Performance Graph

![img_132.png](charts_images/img_132.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 3.35% |
| Memory Usage | 237.25 MB |
| Response Time | < 1 s |

---

#### Device: Huawei Nova 13 - HarmonyOS 4 OS 12
**Figures:**
- Figure 133: CPU/Memory Graph

![img_133.png](charts_images/img_133.png)

- Figure 134: Network Performance Graph

![img_134.png](charts_images/img_134.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | CPU usage is unavailable for Huawei devices on BrowserStack. |
| Memory Usage | 256.27 MB |
| Response Time | < 1 s |

---

#### Device: Google Pixel 8 -Android 14
**Figures:**
- Figure 135: CPU/Memory Graph

![img_135.png](charts_images/img_135.png)

- Figure 136: Network Performance Graph

![img_136.png](charts_images/img_136.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 2.62% |
| Memory Usage | 252.89 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 - iOS 26
**Figures:**
- Figure 137: CPU/Memory Graph

![img_137.png](charts_images/img_137.png)

- Figure 138: Network Performance Graph

![img_138.png](charts_images/img_138.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.34% |
| Memory Usage | 58.58 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 15 - iOS 17
**Figures:**
- Figure 139: CPU/Memory Graph

![img_139.png](charts_images/img_139.png)

- Figure 140: Network Performance Graph

![img_140.png](charts_images/img_140.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.32% |
| Memory Usage | 55.4 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 16 - iOS 18
**Figures:**
- Figure 141: CPU/Memory Graph

![img_141.png](charts_images/img_141.png)

- Figure 142: Network Performance Graph

![img_142.png](charts_images/img_142.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 1.17% |
| Memory Usage | 62.29 MB |
| Response Time | < 1 s |

---

### Scenario 10: Batch Issuance Through Verifier (Cross Device)

#### Device: POCO X5 Pro 5G - Android 14
**Figures:**
- Figure 143: CPU/Memory Graph

![img_143.png](charts_images/img_143.png)

- Figure 144: Network Performance Graph

![img_144.png](charts_images/img_144.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 261.8 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus - iOS 18
**Figures:**
- Figure 145: CPU/Memory Graph

![img_145.png](charts_images/img_145.png)

- Figure 146: Network Performance Graph

![img_146.png](charts_images/img_146.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 30.75 MB |
| Response Time | < 1 s |

---

### Scenario 11: Batch Issuance Through Issuer (Cross Device)

#### Device: POCO X5 Pro 5G - Android 14
**Figures:**
- Figure 147: CPU/Memory Graph

![img_147.png](charts_images/img_147.png)

- Figure 148: Network Performance Graph

![img_148.png](charts_images/img_148.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 276.5 MB |
| Response Time | < 1 s |

---

#### Device: iPhone 14 Plus - iOS 18
**Figures:**
- Figure 149: CPU/Memory Graph

![img_149.png](charts_images/img_149.png)

- Figure 150: Network Performance Graph

![img_150.png](charts_images/img_150.png)

**Results:**
| Metric | Value |
|--------|--------|
| CPU Usage | 0.1% |
| Memory Usage | 37.05 MB |
| Response Time | < 1 s |

---

# 7. Conclusion and Comments

Based on the collected performance metrics, the **EUDI Wallet application** demonstrates strong stability and efficiency across both platforms (Android and iOS).

### Summary of Findings
- **Response Time:** Remained consistently under 1 second in all test scenarios (well below the 2.0s acceptance limit).
- **Stability:** No crashes or critical errors occurred during any test runs.
- **Network Behavior:** All network requests were limited to expected dev endpoints (issuer/verifier).
- **Resource Usage:** CPU and memory remained within acceptable limits for all devices, even under repeated load.

### Overall Conclusion
The application successfully meets all defined **performance acceptance criteria** and exhibits robust, efficient behavior across devices and platforms.  
No significant performance issues were detected during testing.

---

## References
- [requirements.md](requirements.md)
- [specifications.md](specifications.md)
