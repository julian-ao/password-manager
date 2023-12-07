<!--[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2216/gr2216)-->

# Password Manager - group 2216 project

Password Manager is an application for saving multiple accounts so that a person dont need to remember every username and passwords a person uses.
It is a desktop application that is made in JavaFX. The application is made for the course IT1901 at NTNU.
<!--The prosject is gitpod ready and can be found [here](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2216/gr2216), you can also click on the Gitpod button in the top of the readme.-->

## Running and building the project

### Building the project

You will need to have Java 16 installed on your computer. You can download it [here](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html).

1. Write `cd passwordManager` in the terminal to get to the root folder.

2. Write `mvn clean install` to build the project for springboot.

### Running the project

Open a terminal and navigate to **passwordManager/restserver** and run `mvn spring-boot:run`
Open a another terminal, navigate to **passwordManager/ui** and run `mvn javafx:run`

### Installing the application

If you use Windows you need to use JPackage: [WiX Toolset 3.0](https://wixtoolset.org/docs/wix3/). If you are on Linux or Mac it should work fine without.

Go to the directory `passwordManager/ui`. Then write in terminal: `mvn compile javafx:jlink jpackage:jpackage`.
After the command has completed, the executable to the application will be found in `passwordManager/ui/target/dist`.

Note the application will still only work if spring-boot is running.

## Structure

Folder structure:

- The main readme.md-file where the application is explained is located in [/passwordManager/](./passwordManager/readme.md)
- The documentation is located in [/docs/](./docs/)
- The workflow documentation is located in [/docs/workflow](./docs/workflow.md)
- The architecture documentation is located in [/docs/architecture/](./docs/architecture.md)
