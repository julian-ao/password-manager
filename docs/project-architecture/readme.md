# Project architecture

## PlantUML diagrams

![Package Diagram](..\project-architecture\PUML-diagrams\PackageDiagram.png)

The package diagram is a high-level overview of the project structure. It shows how the different packages and components are related to each other.

All the PUML diagrams can be found [here](PUML-diagrams).

## Modules

This project contains six modules in total: core, local persistence, ui, client, restserver, encryption & integration.

<!-- How the architecture works. Why and how we implemented it this way-->

### Core

The [core module](../../passwordManager/core/) contains TODO!

<!--core contains blabla packages and these packages includes blabla classes that do this and that. -->

### Local Persistence

The [local persistence module](../../passwordManager/localpersistence/) contains TODO!

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

The [client module](../../passwordManager/client/) contains TODO!

<!--client contains blabla packages and these packages includes blabla that do this and that. -->

### Restserver

The [restserver module](../../passwordManager/restserver/) contains TODO!

<!--restserver contains blabla packages and these packages includes blabla that do this and that. -->

### Encryption

The [encryption module](../../passwordManager/encryption/) contains TODO!

<!-- encryption contains blabla packages and these packages includes blabla that do this and that. -->

### Integration

The [integration module](../../passwordManager/integration/) contains TODO!