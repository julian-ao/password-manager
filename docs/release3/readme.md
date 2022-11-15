# Release 3

## New features

For this release we have created a REST-API with springboot and implemented the functionality to copy passwords and delete profile. We have also implemented the functionality to encrypt and decrypt passwords. We have also implemented a new architecture, where we have seperated the persistence layer from the core layer. The new architecture is shown in the image below.

<!-- IMAGE -->

### New architecture

<!-- New architecture. Why and how we implemented it this way-->

<!-- Image or link to orchitecture -->

<!-- Changes to each module vvv -->

### Restserver

<!-- How the restserver works -->

### Encryption

<!-- How the encryption works -->

### UI

<!-- What we changed in the UI -->

<!-- Delete profile -->

<!-- Copy password to clipboard -->

<!-- Login on ENTER -->

<!-- Mark text -->

<!-- Overflow hidden -->

<!-- Blur when overlay is visible -->

<!-- Fixed X overlay bug on mac -->

<!-- Eye images from png to svg? -->

---

## Code quality

For this release we implemented a new Maven plugin to ensure better code quality:

### Spotbugs

<!--### Spotbugs

We use Spotbugs to analyze our Java code for bugs.-->

---

## Pipeline Jobs

<!-- new pipeline job "mvn clean install -DnoTestUI=true" blabla -->

---

## Workflow

<!-- continued as we did in release 2? -->