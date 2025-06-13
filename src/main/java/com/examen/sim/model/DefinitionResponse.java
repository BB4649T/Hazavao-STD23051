package com.hazavao.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefinitionResponse {
    @JsonProperty("teny")
    private String teny;

    @JsonProperty("definition")
    private String definition;

    public DefinitionResponse() {}

    public DefinitionResponse(String teny, String definition) {
        this.teny = teny;
        this.definition = definition;
    }

    public String getTeny() { return teny; }
    public void setTeny(String teny) { this.teny = teny; }
    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }
}
