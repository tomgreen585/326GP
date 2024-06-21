# Requirements for SWEN326 Project

## System Requirements

- The flight simulation system shall facilitate the management of flight plans and adjustments to the aircraft’s control systems, control surfaces, and monitoring systems via a user interface accessible to pilots.
- It shall simulate flight plan administration, autopilot manipulation, display of sensor data, provision of hazard alerts, and emulation of engine thrust dynamics. Stakeholders shall primarily include pilots, who will serve as the system's end users.
- Additional stakeholders will include engineers and regulatory authority.
- System will be focusing on the Boeing 737-800 for our unit requirements.

## Software Requirements

### Functional Requirements

- The system shall include an input field for entering latitude, longitude, altitude, speed restrictions, and expected arrival times at each waypoint which it will accurately store.
- The system should update and display airspeed, altitude, attitude (raw etc.) and engine thrust of the aircraft and maintain it within limits - max thrust 125 kN, min thrust is 10% drop from max at idle.
- A visual display map shall be provided to show the aircraft’s current position, planned route, and waypoints so pilots can visualise their flight path.
- The system should control the activation of airspeed, altitude, and attitude based on engine thrust to assure aircraft safety and efficiency.
- Implement logic to ensure changes in altitude based on thrust settings and weight of aircraft. Logic will dynamically adjust rate of altitude change and current altitude in response to variations in thrust levels and aircraft weight to ensure efficient altitude transitions -> this will obviously depend on each aircraft.
- System must detect failure in airspeed, altitude and attitude sensor which it should then put the sensors into a safe state (2oo3)
- Buttons should be available to engage and disengage the autopilot so pilots can control the automated flight system and control aircraft.
- Buttons should also be available to load and activate flight plans.
- The Autopilot’s state must be displayed clearly  (engaged, disengaged, fault)
- - The indicator lights on the autopilot control panel will change to communicate to the user whether the autopilot is currently engaged or disengaged, it will also change to indicate to the pilot that there is a fault condition. How will they change though, change colours?
- The Autopilot must send control signals regularly at a set interval to each sensor’s engine components.
- If the autopilot indicates a fault condition, the system should…
- Audible and visual alerts shall warn pilots of immediate hazards to ensure timely response in a critical situation.
- Sensors shall simulate airspeed (in nautical miles per hour), altitude (in feet above mean sea level), attitude (in degrees), and engine parameters (thrust in pounds-force).
- The system should respond appropriately to extreme sensor values to mitigate risks with sensor malfunctions or anomalies.
- Execution check parameters shall be established to ensure the proper functioning of the program.
- Add variables/inputs that the system will need at the start at each flight - what the system should have in order to operate (start location, waypoints, finish location, min/max speed)
- The system shall feature digital readouts for airspeed, altitude, pitch, roll, yaw, and engine parameters.
- Functional tests confirm that all user inputs and sensor outputs meet specified criteria

### Non-functional Requirements

- Design the system with modular components to facilitate easy troubleshooting and maintenance.
- Data being displayed should be frequently updated every 500ms
- When autopilot is active, control signals sent every 400ms
- The architecture for airspeed, altitude and attitude sensors will be 2oo3.
- The system shall operate reliably under extreme conditions, with minimal downtime and robustness against failures.
- Visual indicators for the status of autopilot as well as alerts.
- Fail-safe mechanisms, fault detection, fault tolerance, shall be incorporated to handle sensor faults and mitigate potential hazards.
- Non-functional tests verify system performance, usability, and reliability under simulated extreme conditions.

### Safety Requirements

- System should constantly/continuously monitor -> airspeed, altitude, attitude and engine thrust to prevent errors.
- The system should promptly communicate both the status of the system and any alerts to the user in the event of sensor or engine failures or abnormalities.
- For Airspeed: If airspeed falls below a threshold of 40 knots, the system shall activate audible and visual stall warning alerts to prompt the pilot to take corrective action.
- If airspeed exceeds a maximum operating speed of 453 knots, the system shall trigger overspeed warnings to alert the pilot to reduce speed and avoid potential structural damage to the aircraft.
- For Altitude: If altitude decreases below a specified minimum altitude of 1000ft, the system shall activate terrain proximity warnings to alert the pilot of potential collision with the ground. If altitude increases above maximum altitude of 37000, the system shall trigger altitude overspeed alerts to warn the pilot of exceeding safe operating limits and potential risks for passengers on board due to factors such as decreased pressure/oxygen.
- For Attitude: -> go into specifics about different attitudes (roll, pitch, yaw).
- Pitch values sourced from https://skybrary.aero/articles/recovery-unusual-aircraft-attitudes (these are considered ‘unusual attitude’ for large transport aircrafts as defined by)
- If nose up pitch attitude greater than 25 degrees show warning light with instructions for getting the nose up pitch attitude less than 25 degrees.
- If nose down pitch greater than 10 degrees show warning light with instructions for getting the nose down pitch attitude less than 10 degrees.
- Yaw must be 0 during takeoff and landing. But slight deviations close to 0 can occur because of crosswinds, asymmetric thrust and aerodynamic forces.
- If outside of the specified ‘value’ (altitude, attitude, speed), show warning light with instructions for getting the plan back into correct ‘value’. If sensor has failed then 2oo3 will switch to 1oo2. If two or more fail then emergency procedure with respective system lights/communication. The threshold for roll attitude is between 0 and 40 degrees.  
- For Immediate Hazards: The system shall implement audible and visual alerts that promptly and clearly indicate potential risks. These alerts should be triggered in real-time based on sensor data thresholds, so that pilots can be promptly informed of critical situations to take immediate action.
- Regression tests ensure new updates do not unsettle previously tested and validated functionalities

### High-Level Requirements

- Interface effectively describes various tasks (navigation, autopilot controls, data) in a clear refined format that is easy for the user to interpret.
- Hazard alerts take priority on the interface and are prompted to the user immediately. Alerts overthrow the rest of the interface components.
- FLIGHT DISPLAY: responsible for route planning and navigation
- AUTOPILOT DISPLAY: manages aircraft control inputs based on sensor response and pilot.
- SENSOR DISPLAY: sensor data display/detection presents reliable flight data to pilot that is clear and easy to interpret and formatted correctly for the user.
- HAZARD DISPLAY: when hazards are present they are prompted through its respective light and instructions on how to mitigate.
- Code written in Java (think we said this)
- UI provides management display for handling inputs from the user.
- UI provides module to display sensor data
- UI provides module to display alerts and hazards that are visually displayed through lights and sound
- Sensors are handled and implemented in 2oo3 architecture

### Low-Level Requirements

- Validate input data format and range for latitude, longitude, altitude, and speed restrictions. 
- Define threshold values for sensor data for when the program should trigger responsive actions
- Integrate sound and visual cues for immediate hazard alerts based on sensor data thresholds. 