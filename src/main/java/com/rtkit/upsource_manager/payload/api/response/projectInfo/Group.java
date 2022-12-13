
package com.rtkit.upsource_manager.payload.api.response.projectInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

    /**
     * No args constructor for use in serialization
     */
    public Group() {
    }

    /**
     * @param name
     * @param description
     */
    public Group(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

}
