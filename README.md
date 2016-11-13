RESTDocsEXT Plugins
===================

This project consists of plugins for build systems like Maven and Gradle,
that will help to generate the configuration file required for the [restdocsext-ui][1].
Along with the plugins, will be a [Spring REST Docs][2] snippet to help in producing
configuration data from unit tests.

### Build

#### Build project

    ./gradlew build

#### Build and install all modules to local Maven Repo

    ./gradlew installToMavenLocal


### Test

    ./gradlew test


[1]: https://github.com/RESTDocsEXT/restdocsext-plugin
[2]: https://projects.spring.io/spring-restdocs/