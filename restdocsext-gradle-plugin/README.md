RESTDocsEXT Gradle Plugin
=========================

This project is a Gradle plugin for generating the [RESTDocsEXT UI][1] for Java projects that
product [Spring REST Docs][2] snippets

### Tasks

**`restdocsext`** - Task to generate the RESTDocsEXT UI. This task should be run after the tests
that generate the snippets. Once the UI is generated, it will be stored in the configured location. See below
for configuration options.

### Conventions

**`restdcocsext`** - You will use this convention to configure the plugin. Listed below are possible configurations

* `uiOutputDir` (required) - the directory the UI should be out in.
* `snippetsDir` (required) - the directory that the Spring REST Docs snippets are output to.
* `baseUri` (required) - the base URI of the REST API application. For example 'http://localhost:8080/api'
* `homePage` (optional) - the asciidoctor generated HTML page that should be the home page. The home page should
not include the `.html` extension. For example if there is an `introduction.html` page, just specify `introduction`
as the home page. The default will the first after sorting alphabetically.
* `organizationName` (optional) - the name of the organization that will be used for sidemenu header. Default is "RESTDocsEXT"
* `organizationLink` (optional) - the link of the orginazation that the sidemenu header will link to. Default is
the link of RESTDocsEXT page.
* `uiSourceUrl` (optional) - the string URL of UI zip that will be downloaded. The default is the dist URL for
the UI project. You can change this is you want to build the UI from source and download it from the different URL
* `generalPages` - a list of pages that are not "resource" pages. For example "introduction" would be a general
page, while "get-a-dog" would not.

**`restdocsext.asciidoctor`** - This is to configure asciidoctor. The RESTDocsEXT plugin uses the asciidcotor plugin
under the hood. So you can specify the configurations here

* `sourceDir` - the location of the asciidoc source files
* `outputDir` - where the asciidoctor general HTML files should go
* `attributes` - any attributes that you would use with the normal asciidoctor

### Example configuration

```groovy
restdocsext {
    baseUri 'http://localhost:8080/api'
    homePage 'introduction'
    uiOutputDir file("src/main/resources/static/docs")
    snippetsDir file("$buildDir/generated-snippets")
    generalPages = ['introduction']

    asciidoctor {
        outputDir file("$buildDir/generated-asciidoc")
        sourceDir file('src/main/asciidoc')
        attributes snippets: file("$buildDir/generated-snippets").path
    }
}
```

[1]: https://github.com/RESTDocsEXT/restdocsext-ui
[2]: https://projects.spring.io/spring-restdocs/