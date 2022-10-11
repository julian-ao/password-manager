# Release 2

GOAL FOR THIS WEEK .....

## Core

Example text: \

For the core logic we had to extend the Event-class so that it can handle categorized events and weekly events. Also implement methods to handle more spesific start and end time for the events. We deleted the classes with JsonSimple, and used serializers and deserializers to convert Java objects to Json strings and reversed. The methods for reading and writing are currently reading and writing all events to and from the json file, but methods for reading and writing specific weeks will be added.

## ui

Example text: \

For the ui we had to implement buttons for the weeks of the year. So that the user can click on any week and the events for that week will show up. Also we wanted the user to pick a time for the events, instead of writing it in manually. So this had to be implemented in the ui as well. We also added som color to the timetable to make it more flattering for the user.

## Weaknesses

Example text: \

Some weaknesses of the app is that the structure is not optimal at the moment. This is something we can improve for the next release, to structure the files in "core" to "core" and "json".
Also the UML in [architecture.puml](timetable/architecture.puml) doesn't show the current architecture of the project, because there is still some improvemnts to do. But it shows what we want to achieve!

# Code quality

# workflow and workhabits

Example text: \

For this release we worked more together as a group and the communication was significantly better then for the previous release. The first couple of days we spent on planning the app and further extensions, how we should do it and assigned tasks. We had weekly meetings and regularly checkups. But due to illness in the group we were delayed in the work. Therefore, we had to work efficiently individually and communicate over zoom the last couple of days. Even though we didn't reach our goal for this release, we did many approvements both in workhabits and code development.
