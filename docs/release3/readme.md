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

The encryption module contains the encryption algorithm and the hashing algorithm. All UserPasswords are stored as salted hashes so a database leak won´t compromise are users passwords. All profilePasswords are encrypted using a hash of their users passwords as a key, again this means that no stored data can be used to access users passwords. We have not however configured spring to use TLS, so our users passwords are not really safe.

- The encryption utilises an implementation of the TwoFish block cipher algorithm and cipher block chaining. The encryption takes in an arbitrary length byte array and encrypts it using a key, it can then later be decrypted using the same key. The encryption also uses a "nonce" or "number used once" to introduce more confusion into the encryption. This is done so the same plaintext won't be encrypted to the same ciphertext.

- The hash algorithm takes in an arbitrary length byte array and produces the hash for the data, the hashing algorithm is an implementation of the SHA256 algorithm.

### Local Persistence
The new local persistence uses two tables in seperate files, each profile now has a reference to its parent user instead of being nested inside its parent user. This is similar to a foreign key in more traditional database systems like SQL.


- Example of a stored user in our database. The salts are used to generate different hashes, and the password is hashed
![User Json Example](../images/release3_user_json_example.png)

- Exaample of a stored profile in our database. The password is encrypted using the parent users password. Nonce is used in the encryption(see encryption)
- ![Profile Json Example](../images/release3_profile_json_example.png)
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

## Code quality plugins

For this release we implemented a new Maven plugin to ensure better code quality.

A more in-depth description of the Maven plugins we have used in the project is in the [workflow.md](../workflow.md) file.

### Spotbugs

Spotbugs is a static analysis tool that looks for bugs in Java code. We have Spotbugs running in every module, so that the project as a whole is bug free. <!-- Rephrase -->

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