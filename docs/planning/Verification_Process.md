# Verification Process

## Functional Verification

- Develop JUNIT verification test cases: Directly test each functional requirement, such as input validation for latitude, longitude, altitude, and speed.
- Valid and invalid input testing: Verify the system's response to flight plan adjustments, autopilot manipulation, and sensor data display.
- Unexpected usage testing: Test for unexpected ways of using the system to check errors.

## Non-Functional Verification

- Data update frequency: Check that data updates every 500ms and control signals are sent every 400ms when autopilot is active.
- Extreme sensor values: Check extreme sensor values to ensure that the console and hazard panel operate as expected.

## Safety-Critical Verification

- Test safety mechanisms: Test all safety mechanisms, including fail-safe, fault detection, and fault tolerance systems.
- Threshold and emergency conditions: Implement detailed testing for airspeed, altitude, and attitude monitoring to validate the system's response to thresholds and emergency conditions.
- Default values safety: Ensure the default values for airspeed, altitude, and altitude are within the safe range so that a pilot cannot accidentally specify 0 (or some value out of the safe range).
- Redundancy mechanisms: Test the redundancy mechanisms in our system, to verify that they function correctly if the main system fails.

## Tool Integration and Traceability

- GitLab collaboration: Use GitLab to collaborate on each artifact.
- Traceability enhancement: Ensure that the GitLab issues and merge requests clearly reference the system requirements they are intended to satisfy.
- Automated tools for traceability and documentation: Integrate automated tools that are compliant with DO-178C standards.
- Continuous integration pipeline: Utilize a continuous integration pipeline in GitLab to reduce the risk of introducing errors into our system.

## Independent Review

- Code review by independent team members**: Ensure that a member of the team who did not work on a certain code artifact, tests and approves it before it can be merged to the main branch.
- Adherence to coding standards**: Ensure that code adheres to coding standards (Power of Ten) in each other's review of code.

## Comprehensive Testing Approach

- JUnit tests for class methods: Create JUnit tests that handle class methods that handle valid and invalid ‘situations’.
- Device and user variation testing: Ensure that the flight system behaves as expected on different devices and by different users operating it.
- Robustness and normal range testing: Implement robustness and normal range testing to ensure the software behaves as intended under all foreseeable conditions.

## Real-Time and Simulation Testing

- System component integration verification: Verify the integration and interaction of different system components, particularly the interaction between user inputs and system responses.
- Sensor simulator/simulation software: Create a sensor simulator/simulation software to generate sensor data (both good and bad values). Evaluate how the system responds to these simulated conditions.
