[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2216/gr2216)

# Password Manager - group 2216 project

Password Manager is an application for saving multiple accounts so that a person dont need to remember everu username and passwords a person uses. It is a desktop application that is made in JavaFX. The application is made for the course IT1901 at NTNU. The prosject is gitpod ready and can be found [here](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2216/gr2216), you can also click on the Gitpod button in the top of the readme.

## Running, building the project

### Building the project

You will need to have Java 16 installed on your computer. You can download it [here](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html).
Write `cd passwordManager` in the terminal to get to the root folder. Then write `mvn clean install` to build the project.

### Running the project

Navigate to **passwordManager/ui** and write `mvn javafx:run` in the terminal to run the project.

## Structure

Folder structure:

- The **main readme.md-file** where the application is explained is located: /passwordManager/
- The **backend** is located /passwordManager/core/src/main/java/core/
- The **FXML-files** is located /passwordManager/ui/src/main/resources/ui/
- The **documentation** is located in /docs/
