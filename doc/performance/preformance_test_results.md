## Test Run – Performance Metrics

### Scenario 1: Add a Document from List

#### Device: POCO X5 Pro 5G
**Figures:**
- Figure 1: CPU/Memory Graph

![img.png](img.png)

- Figure 2: Network Performance Graph

![img_1.png](img_1.png)

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

![img_2.png](img_2.png)

- Figure 4: Network Performance Graph

![img_3.png](img_3.png)

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

![img_4.png](img_4.png)

- Figure 6: Network Performance Graph

![img_5.png](img_5.png)

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

![img_6.png](img_6.png)

- Figure 8: Network Performance Graph

![img_7.png](img_7.png)

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

![img_8.png](img_8.png)

- Figure 10: Network Performance Graph

![img_9.png](img_9.png)

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

![img_10.png](img_10.png)

- Figure 12: Network Performance Graph

![img_11.png](img_11.png)

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

![img_12.png](img_12.png)

- Figure 14: Network Performance Graph

![img_13.png](img_13.png)

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

![img_14.png](img_14.png)

- Figure 16: Network Performance Graph

![img_15.png](img_15.png)

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

![img_16.png](img_16.png)

- Figure 18: Network Performance Graph

![img_17.png](img_17.png)

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

![img_18.png](img_18.png)

- Figure 20: Network Performance Graph

![img_19.png](img_19.png)

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

![img_20.png](img_20.png)

- Figure 22: Network Performance Graph

![img_21.png](img_21.png)

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

![img_22.png](img_22.png)

- Figure 24: Network Performance Graph

![img_23.png](img_23.png)

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

![img_24.png](img_24.png)

- Figure 26: Network Performance Graph

![img_25.png](img_25.png)

- Figure 27: Additional Network Performance Graph

![img_26.png](img_26.png)

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

![img_27.png](img_27.png)

- Figure 29: Network Performance Graph

![img_28.png](img_28.png)

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
- [Performance Test Report (Figures 1–29)](https://github.com/eu-digital-identity-wallet/eudi-doc-developers-hub-site/blob/doc_update/docs/Reference%20Implementation/Quality%20Assurance.md#-reporting)