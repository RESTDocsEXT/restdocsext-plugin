/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.restdocsext.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Models a collection of API operations.
 *
 * @author Paul Samsotha
 */
public class RestdocsextOperationCollection {

    private final String name;

    private final List<RestdocsextOperation> operations;

    @JsonCreator
    public RestdocsextOperationCollection(@JsonProperty("name") String name,
            @JsonProperty("operations") List<RestdocsextOperation> operations) {

        Objects.requireNonNull(name, "name cannot by null");
        Objects.requireNonNull(operations, "operations cannot by null");
        this.name = name;
        this.operations = operations;
    }

    public String getName() {
        return this.name;
    }

    public List<RestdocsextOperation> getOperations() {
        return this.operations;
    }

    @Override
    public String toString() {
        return "RestdocsextOperationCollection{" + "name=" + name
                + ", operations=" + operations + '}';
    }
}
