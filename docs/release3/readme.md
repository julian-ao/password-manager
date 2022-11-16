# Release 3

## New features

For this release we have created a REST-API with springboot and implemented the functionality to copy passwords and delete profile. We have also implemented the functionality to encrypt and decrypt passwords. We have also implemented a new architecture, where we have seperated the persistence layer from the core layer. The new architecture is shown in the image below.

<!-- !IMAGE -->

## Architecture

The complete architecture of the project is documented in the project-architecture folder's [readme file](../project-architecture\readme.md).

## Updated modules

### Restserver

<!-- Changes in restserver -->

### Encryption

<!-- Changes in encryption -->

### Core??

<!-- Changes in core -->

### Client?

<!-- Changes in client -->

### Local persistence?

<!-- Changes in persistence -->

### UI

<!-- Changes in UI -->

The UI has had a few functionalities added, as a few changes in the design, as well as a few bug fixes:

- The password field when creating a new profile now shows how strong the password is, ranking from weak to medium to strong. See image below.

![New password field when creating profile](../images/release3_password_field.png)

- When adding a new profile, the user can now choose to generate a password for them, or write their own. The generated password will always be rated as strong. See image above.

- Copy and delete icons have been added to the profile list. The copy icon copies the password to the clipboard, and the delete icon deletes the password from the list. See image below.

![Copy & delete icons](../images/release3_copy_delete_icons.png)

- When either the title, username or password of a profile is too long, it will be cut off and made vertically scrollable.

- In the previous release, the exit "add profile overlay" button was not visible on all operating systems. This has been fixed, by changing from a X character to a X svg icon. See image below.

![Exit overlay](../images/release3_exit_overlay.png)

- Other minor improvements and bug fixes was also made, such as the window beeing blurred when the "add profile overlay" is visible, and the possibility to mark the text of the profiles.

## Code quality

For this release we implemented a new Maven plugin to ensure better code quality:

### Spotbugs

Spotbugs is a static analysis tool that looks for bugs in Java code. It is a plugin for Maven, and can be added to the pom.xml file. We also added Spotbugs to the pipeline, so that it will run every time the code is pushed to the repository. This will ensure that the code is bug free before it is merged to the master branch.

## Testing

<!-- Changes in testing -->

<!-- improved testing, ui testing -->

## Pipeline Jobs

For this release we still use the GitLab CI/CD pipeline, but we have added a few new jobs to the pipeline.

List of stages witch jobs, and their order of execution:

- `build`
  - `validate` - validates the project is correct and all necessary information is available. Merge blocked if this fails.
  - `compile` - compiles the source code of the project. Merge blocked if this fails.

- `test`
  - `test` - runs all the tests in the project. Merge blocked if this fails.

- `coverage`
  - `test-coverage-minimum` - runs all the tests in the project and checks if the test coverage is above 60%. Merge blocked if this fails.
  - `test-coverage-80` - runs all the tests in the project and checks if the test coverage is above 80%. Merge warning if this fails.

- `quality`
  - `checkstyle` - runs checkstyle on the project. Merge warning if this fails.

- `bugs` *(new)*
  - `spotbugs` - runs spotbugs on the project. Merge warning if this fails.

[This](../../.gitlab-ci.yml) pipeline still run on every merge request to the master branch.

## Workflow

The complete documentation for the project workflow is in the [workflow.md](../workflow.md) file.

<!--

I tillegg til dokumentasjon i samsvar med tidligere krav skal dere denne gangen lage UML diagram som forelest.

!-  Et pakkedigram for løsningen
!- Et klassediagram for viktigste deler av systemet
!- Et sekvensdiagram for et viktig brukstilfelle, som viser koblingen mellom brukerinteraksjon og hva som skjer inni systemet inkl. REST-kall.

!- Dokumentasjon av REST-tjenesten, altså (format for) forespørslene som støttes.

*- dokumentasjon for hver release må plasseres i en egen mappe, så den tredje innleveringsdokumentasjonen må plasseres i gr22nn/docs/release3

I tillegg til overnevnte krav vil alle tidligere krav også være viktige, så se over tidligere kriterier, og bruk dem også!

Innlevering 2:

- dokumentasjon (readme filer og kommentarer) må oppdateres
*- dokumentasjon for hver release må plasseres i en egen mappe, så den andre innleveringsdokumentasjonen må plasseres i gr22nn/docs/release2
!- dokumentere arkitektur med minst et diagram (bruk PlantUML) i tillegg til teksten i readme
?- dokumentere valg knyttet til arbeidsvaner, arbeidsflyt og kodekvalitet (f.eks. tilnærming til testing, verktøy for sjekking av kodekvalitet og innstillinger for dem)

-->