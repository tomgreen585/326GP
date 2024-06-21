# Meeting 3

**Date**: 08/05/2024  
**Present**: 
- Tom
- Bernard
- Brodie
- Oshi
- Alex
- Phil

## Summary

In our previous meeting, we realized that several clarifications were needed before proceeding with the project. We wanted these clarifications before generating our requirements.

We sent an email to Stu asking about clarifications regarding our system. In this meeting, we reviewed some of the clarifications we received and started generating requirements based on this information.

## Key Clarifications

- **Data Generation**: The data should be generated as a stream by the testing infrastructure, and part of the challenge is developing that testing infrastructure to send data at the right frequency.
- **Pilot’s UI**: The pilot’s UI can be done in JavaFX or Java Swing – neither of which are DO-178C compliant libraries, but the software architecture can be designed such that the UI code runs separately from the main flight control code, so a failure in the former won’t crash the latter.
- **Terminal and Log Files**: The rest of the system can run via the terminal, and log files would be a helpful mechanism here.
- **No Existing System**: There isn’t an already developed system to compare against.
- **Assessment Criteria**: We aren’t being assessed directly on the system we create. We are being assessed on a critique of what we learned attempting to do the documentation, flight management code, test infrastructure code, testing, and group work.

## Progress

In this meeting, we continued documenting the various types of requirements for our system. This included:
- System requirements
- Functional requirements
- Non-functional requirements
- Safety requirements

## Next Steps

Our intention going forward is to develop the high-level requirements and low-level requirements based on what we have so far.

Once we have the low-level requirements, we will attempt to start on the code.
