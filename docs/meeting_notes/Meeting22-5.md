## Meeting Wednesday 22/05 
#### Present: Tom, Phillip, Bernard, Alex, Brodie and Oshi (on Discord video)
---
First we discussed progress on the fault tree and how some cut sets needed to be removed as they were not in project scope.

A discussion was held about how to lay out the GUI. It was decided that a JPanel with six JPanels inside of it was to be used. 
The six JPanels will hold (from left to right, top to bottom):
* Current sensor data
* 2D map of NZ where plane can be drawn on top
* Flight management system
* Autopilot 
* Consol
* Alarms

**Alex** will develop the push the basic framework of empty JPanels

We talked how the program flow from start to finish.<br>
Quick Notes:
* The program will open to sensor data zeroed and no plane on map.
* The user will add Flight plan then sensor data will be generated and plane will fly to waypoints on the map
* User can also setup autopilot for plane to fly around randomly
* when setting up autopilot you can select to use bad data which will lead to faults 


We talked about who would like to do each JPanel section but this is still TBD
