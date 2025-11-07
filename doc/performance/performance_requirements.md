# Performance Requirements

To evaluate the performance of the **EUDI Wallet application** under average user load, mobile performance testing will be conducted across a variety of mobile devices. This testing will focus on **CPU and memory usage**, as well as **network activity** during common user scenarios.

The goal is to ensure that the application performs efficiently, remains stable under expected workloads, and provides a consistent user experience across devices and conditions. The following Performance Requirements define the minimum expected standards for system performance during testing.

---

## Performance Requirements Traceability Matrix
The following performance requirements define the expected operational thresholds for the system during testing.

| **Requirement**                   | **Description / Target**  | **Test Type / Scenario**                  | **Measurement Method** | **Success Criteria**  |
|-----------------------------------|---------------------------|-------------------------------------------|------------------------|-----------------------|
| **Response Time**                 | 1.6 – 2.0 seconds         | Load Test (Performance Test Scenarios)    | Android Studio Profiler / Xcode Instruments (Performance Profiling) | Average ≤ 2.0s per scenario 
| **CPU / Memory Usage**            | Must not exceed 40% CPU utilization and 250 MB of memory on any device | Load Test (Performance Test Scenarios)    | Android Studio Profiler / Xcode Instruments (Performance Profiling) | CPU ≤ 40%, Memory ≤ 250MB per scenario 
| **App Stability (No Crashes)**    | No crashes or unexpected terminations during testing | Load Test (Performance Test Scenarios)    | Android Studio Profiler / Xcode Instruments (Performance Profiling) | 0 Crashes or Unexpected Terminations per scenario 

---

## Notes
- These thresholds are based on baseline performance expectations for average user load conditions.
- Any deviation beyond these limits should be analyzed and documented, with recommendations for optimization. 
- Detailed Performance Test Scenarios are provided in [specification.md](C:\Users\ftheofil\Projects\eu-digital-identity-walleteudi-doc-testing-application-internal\doc\performance\performance_specifications.md), which defines the individual test cases, workloads, and measurement approaches supporting the Performance Requirements.
