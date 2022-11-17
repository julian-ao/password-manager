# Project architecture

## Package diagram

<!-- How the arcitecture works -->

![Package Diagram](..\project-architecture\PUML-diagrams\PackageDiagram.png)

## Modules

This project contains six modules in total: core, local persistence, ui, client, restserver & encryption. Each module contains these source directories:

<!-- rephrase: -->
<!-- **src/main/java** contains code for the module
**src/test/java** contains test code for the module

The ui module contains this in addition to the above:
**src/main/resources** contains all FXML files -->

<!-- New architecture. Why and how we implemented it this way-->

<!-- Image or link to orchitecture -->

### Core

<!--core contains blabla packages and these packages includes blabla classes that do this and that. -->

### Local Persistence

<!--local persistence contains blabla packages and these packages includes blabla classes that do this and that. -->

### UI

The [ui module](../../passwordManager/ui/) contains the user interface for the application. It contains one package called [**ui**](../../passwordManager/ui/src/main/java/ui/) which contains all of the controllers and the main class. The controllers are responsible for handling the user input and displaying the data to the user. The main class is responsible for starting the application.

The ui module also contains the [**resources**](../../passwordManager/ui/src/main/resources/) folder which contains all of the FXML files as well as the images used.

<!--
- `ui` - contains all the controllers for the different views in the application.
  - `PasswordManagerApp.java` - the main class that runs the application.

  - `PasswordManagerController.java` - the main controller for the application, that acts like a superclass for all other controllers.
  - `LoginPageController.java` - The controller for the login page.
  - `RegisterPageController.java` - The controller for the register page.
  - `PasswordPageController.java` - The controller for the main page.
-->

<!--
The ui module also contains the following resources:

- `images` - contains all images used in the application.

- `ui` - contains all FXML files for the different views in the application.
  - `login.fxml` - the FXML file for the login page.

  - `register.fxml` - the FXML file for the register page.
  - `passwords.fxml` - the FXML file for the main page.
-->

<!--The user interface is created with JavaFX and FXML. All FXML files are connected to a controller blablabla. The FXML directory is located here. -->

### Client

<!--client contains blabla packages and these packages includes blabla that do this and that. -->

### Restserver

<!--restserver contains blabla packages and these packages includes blabla that do this and that. -->

### Encryption

<!-- encryption contains blabla packages and these packages includes blabla that do this and that. -->

## PlantUML diagrams

<!-- This package diagram illustrates the architecture of the Password Manager application. It shows how the components and packages relate to each other. The package diagram code can be found in the directory: **[PUML-diagrams](/design-documentation/project-architecture/PUML-diagrams)**. This directory also contains two class diagrams and one sequence diagram. The two class diagrams are **[server class diagram](/design-documentation/project-architecture/PUML-diagrams/ServerClassDiagram.png)** and **[client class diagram](/design-documentation/project-architecture/PUML-diagrams/clientClassDiagram.png)**. These diagrams show the most important parts of the system. The **[sequence diagram](/design-documentation/project-architecture/PUML-diagrams/SequenceDiagram.png)** illustrates how the client and server interact with eachother when a person adds workouts and view statistics.

![Design documentation](/design-documentation/project-architecture/PUML-diagrams/packageDiagram.png) -->
