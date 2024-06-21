# Technical Documentation

## Design Directory
SWEN326_UI_DESIGN.pdf -> holds UI design created at the start of the project. Design phase for how the group thought the UI was going to be layed out. Shows early concepts of interpretation of the contents of each panel. Contains drawings for Hazard Alerts Panel (issues hazard warnings and mitigations actions) as well as console panel where the alerts will be outputed with a description of the certain fault. Contains drawings for Sensor Data display (readouts for airspeed, altitude, pitch, roll, yaw) with early design layout on how this data will be presented in the UI. Contains early design phase for constructing the Autopilot Control Panel (Buttons to engage/disengage autopilot, Override of speed, altitude, indicators) and its different states in use. Also contains flight plan management specifically the visual map (visually shows maps current location and waypoints) and manager that specifies the way points as well as inputs for min and max speed/altitude with submit and reset buttons. Also has a completed UI diagram to display what each component will look like when put together.

GUIDesign.png -> formatted GUI constructed from SWEN326_UI_DESIGN.pdf. This was used when constructing the code on what we wanted each of our panels to look like at the end of the implementation process.

## Meeting notes Directory
Contains markdown files with meeting notes from in-person meetings. Contains specific topic points for the meetings and objectives completed for the specfic meeting. 

## Planning Directory
Hazard_Analysis_for_DO178C.pdf -> Contains Hazard Analysis created for DO178C. Contains a table analysing certain faults formatted to each hazard category (Sensor Data Simulation, Autopilot System Simulation, Hazard Alerts Simulation, Data Display and interference, Control Signal verification, redundant system, miscellaneous simulation), address what sensor/overall issue this would create if existed, detailed on what would cause this issue, specified design contraints and where in the implementation this constraint will be evident. Fault trees were then constructed from this so we could then address the minimal cut-sets to understand where our system's vulnerabilities would lie so we can mitigate it in early stages of the planning.

CRC.png -> Constructed CRC cards. Cards for each class constructed which was used in the implementation process. Made sure that we followed this so we did not deviate from our requirement and design process.

UML.png -> Constructed UML diagram. Template of components and methods needed in each class. Also shows how each class is relating to each other. Made sure we followed this so we did not deviate from our design process during implementation.

Verification_Process.png -> Contains processes used when constructing code implementation. Highlights functional, non-functional, safety-critical, independent review, comprehensive testing approach, real-time and simulation testing, tool integration and traceability. Each of these aspects were to remain constant over implementation from each team member.

## Requirements Directory
requirements.md -> Final copy of our finalized requirements from planning stage. Specifies our system requirements and software requirements. Our system requirements was our top level plan for what our system is able to do. Proper management of a flight plan with control systems in place that had a easy to use UI was our main goal through all stages. The software requirements illustrates functional, non-functional, safety, high/low-level requirements put in place in early planning meetings.

group-agreement.md -> Group agreement signed by each team member. Highlights on group having to agree to: attending regular meeting schedule, task allocation and progress reporting, commitment of hours, communication, accountability and contribution, tools and platforms. This was signed by the group in our first team meeting.