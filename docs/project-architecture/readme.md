# Project architecture

## PlantUML diagrams

![Package Diagram](..\project-architecture\PUML-diagrams\PackageDiagram.png)

The package diagram is a high-level overview of the project structure. It shows how the different packages and components are related to each other.

All the PUML diagrams can be found [here](PUML-diagrams).

## Modules

This project contains six modules in total: core, local persistence, ui, client, restserver, encryption & integration.

### Core

The [core module](../../passwordManager/core/) contains Profile and User class, used by the backend as intermediate representations of the data that is stored in our database. Additonally, the userbuilder package which contains various validator functions for creating new accounts on our platform.

### Local Persistence

The **[localpersistence-module](/passwordManager/localpersistence)** contains one package, **[localpersistence](/passwordManager/localpersistence/src/main/java/localpersistence)**.

This package includes classes that handle serialization and deserialization of classes in the core-module. It also handles reading and writing to and from a file.

### UI

The [ui module](../../passwordManager/ui/) contains the user interface for the application. It contains one package called [**ui**](../../passwordManager/ui/src/main/java/ui/) which contains all of the controllers and the main class. The controllers are responsible for handling the user input and displaying the data to the user. The main class is responsible for starting the application.

The ui module also contains the [**resources**](../../passwordManager/ui/src/main/resources/) folder which contains all of the FXML files as well as the images used.

### Client

The [client module](/passwordManager/client/) contains one package, **[client](/get-fit/client/src/main/java/client)**. This package makues up the transportation layer and transfers data between the server and the UI.

### Restserver

The **[restserver module](../../passwordManager/restserver/)** contains one packages, restserver. This packages make up the service layer and handles requests from the UI. It is responsible for the logic and computation implemented in the core-module & encryption-module and the persistance implemented in the localpersistance-module.

### Encryption

The **[encryption module](/passwordManager/encryption/)** is responsible for encrypting and decrypting the passwords.

### Integration
**[integration-module](/passwordManager/integration/)** only exists to house the integration tests. Thus enabling us to test everything together without the front-end and back-end tepending directly on each other.