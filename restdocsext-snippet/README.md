RESTDocsEXT Snippet
===================

This project contains a `Snippet` for [Spring REST Docs][1]. The snippet produces a
JSON file for each operation the snippet is used for. The JSON file contains
information about the operation that will later be used to configure the [RESTDocsEXT UI][2].

### Usage

Create a `RestdocsextSnippet` by calling one of the static `restdocsextSnippet` methods on the
`RestdocsextDocumentation`.

```java
import static io.github.restdocsext.snippet.RestdocsextDocumentation.restdocsextSnippet

restdocsextSnippet("Dogs", "Create a Dog")
```

The first argument is the name of the collection that the operation belongs to. Each operation
will be grouped under a collection on the UI menu. The second argument is the short operation name.
This name will be used for UI menu label.

Along with creating the snippet, you can also add descriptors. Any descriptors that you use for the
main snippets, you should also use for this snippet. You can hold references to the descriptors
to make it easier. For example

```java
private final ParameterDescriptor[] pathParameterDescriptors = {
        parameterWithName("teamId").description("The identifier of the team")
};

document("get-team-by-id",
        pathParameters(this.pathParameterDescriptors),
        restdocsextSnippet("Teams", "Get a Team")
                .pathParameters(this.pathParameterDescriptors)
```

Available methods to set descriptors are

* `requestHeaders` - accepts an array of `HeaderDescriptor`s
* `responseHeaders` - accepts an array of `HeaderDescriptor`s
* `pathParameters` - accepts an array of `ParameterDescriptors`
* `requestParameters` - accepts an array of `ParameterDescriptors`
* `requestParts` - accepts an array of `RequestPartDescriptors`
* `requestFields` - accepts an array of `FieldDescriptors`
* `responseFields` - accepts an array of `FieldDescriptors`

[1]: https://projects.spring.io/spring-restdocs/
[2]: https://github.com/RESTDocsEXT/restdocsext-ui