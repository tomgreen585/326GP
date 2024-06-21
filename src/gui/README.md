# ReadMe for SWEN326 project - gui package

## Flight Management Panel - Brodie

To use the flight management panel, a pilot can specify the minimum and maximum airspeed and alitude values, which the sensor panel will adhere too. After specifying these values, the pilot should press the 'Submit Values' button to parse these.

To create a flight path, the pilot can press a start and finish destination on the map. The plane will immediately start flying to the start waypoint position. Once it arrives here, the pilot can press 'Submit Flight Path'. The timer will begin and the longitude and latitude of the start and end coordinates will be displayed under 'Waypoints'. Once reaching the end destination, the final flight time will be displayed.

If the pilot makes a mistake when choosing the flight path, they can press the 'Clear Waypoints' button to clear this at any point, and change the path that the plane will travel.

## Sensor Panel - Phil

Digital readouts for airspeed, altitude, pitch, roll, yaw and engine thrust. Provides updated information for each of these components; airspeed(1 second), altitude, pitch, roll, yaw, thrust (500 miliseconds). 

## Autopilot Panel - Tom

On startup the autopilot is disengaged as shown by the disengaged indicator having a red light. Pilot also can't use engage, disengage or override buttons until they have done initial autopilot process. To use the autopilot panel, the pilot must specify the minimum and maximum altitude and airspeed values. From here these values are then passed onto the sensor to use for the corresponding sensors once the submit button is pressed. After this button is pressed the autopilot is engaged as shown by the engaged indicator (turns from red to green -> disengaged indicator turns red indicating it is off).

If the pilot wants to turn the autopilot off, the pilot presses the disengage button which turns off the engaged indicator and turns on the disengaged indicator. If the pilot wants to turn autopilot on again, the pilot can pressed the engaged button which turns the disengaged indicator from red to green and the engaged indicator on.

If the pilot wants to override the airspeed and altitude values they set for autopilot they press the override button (switches engaged off, if autopilot is turned on, to disengaged -> if autopilot is not on, autopilot will remain disengaged). This will remove the engage, disengaged and override buttons from being able to be pressed while the pilot is specifying new airspeed and altitude values for autopilot.

Autopilot panel also has Start Fault button which will provide the sensors 'bad' airspeed and altitude values that can be specified in the min/max airspeed and altitude boxes. From here we can analyse how our system deals with conditions that are critical to the system to which the pilot has to take mitigation actions against. This function is purely for testing our system and not a function for autopilot.

## Plane on Map Panel - Alex

This JPanel displays a plane on a map with waypoints. It handles mouse clicks to add waypoints and key presses to control the plane. The panel initializes by loading a background image, setting up event listeners for mouse and keyboard interactions, and drawing the plane and waypoints on the map. The plane can be controlled to move to waypoints with mouse clicks or proceed to the next waypoint using the space key.

## Main Application -Alex

This class is the JFrame for the entire application. It initializes and manages all GUI components, including the PlaneOnMapPanel, SensorDataPanel, ManagementPanel, AutopilotPanel, ConsolePanel, and HazardPanel. The MainApplication sets up the main application window, starts a timer for regular updates, and adds all subpanels to the main panel for display. The application entry point starts a thread for the SensorSimulator and makes the main frame visible.

## Console Panel - Bernard
The purpose of the console panel is to alert the user of any sensors that detect values exceeding the range provided by the system. If a sensor is shown to exceed the its range, the console panel will print an alert message with the sensor name stating that it's exceeding its range. If the sensor that has exceeded its range changes to a value that is within its range, the console panel will alert the user that the sensor is now within range.

## Hazard Panel - Oshi

The hazard panel alerts the pilot of various hazard conditions using various coloured lights.
If the system is safe the System Safe light will be green and all other lights will be gray. If there is any type of fault the corresponding fault light/s will change from gray to red and the System Safe will change from green to gray.

The conditions represented on the hazard panel are 'System Safe', 'Sensor Fault', 'Airspeed Fault', 'Altitude Fault' and 'Attitude Fault'. These conditions are continuously checked and updated, no action is taken directly from the user in this panel.