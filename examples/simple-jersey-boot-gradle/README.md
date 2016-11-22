
Simple Jersey/Spring Boot/Gradle Example
========================================

This example uses an API that exposes details from fictional sports teams.

### Stack

* Jersey
* Spring Boot
* Gradle

To run the example run

    gradlew clean test restdocsext bootRun

The following are desrcription of the commands

* The `test` command will run the tests that will produce the asciidoctor snippets
* The `restdocsext` command will run asciidoctor behind the scenes and also produce the required the configuration for the UI.
* The `bootRun` will run the server.

After the the server has finished initializing, visit `http://localhost:8080/docs`.